package Processamento;

import Dados.PostgreSQLJDBC;
import Modelo.Peticao;
import Modelo.Tag;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Classe que realiza a leitura da planilha de petições.
 * @author Debora A. Cordeiro
 *
 * */
public class LeituraAvaaz {
    private ArrayList<Peticao> listaPeticoes = new ArrayList<>();
    private ArrayList<Tag> listaTags = new ArrayList<>();
    private int indexId, indexUrl, indexTitle, indexDesc, indexAuthor, indexDate,
            indexCountry, indexSign, indexTarget, indexRatio, indexFb, indexTw,
            indexWh, indexEmail, indexLang, indexPeople, indexOrg, indexLoc, indexMisc;
    private PostgreSQLJDBC consultas = new PostgreSQLJDBC();

    /* Método principal de leitura do arquivo. */
    public void leArquivoAvaaz() throws ParseException {
        String nomeArquivo = leNomeArquivo();
        boolean possuiTag, peticaoBrasileira;

        // Abrindo o arquivo e recuperando a planilha
        FileInputStream file;

        try {
            file = new FileInputStream(new File(nomeArquivo));

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            Peticao peticaoLida;

            criaListaTags(); // Inicializa ArrayList de tags.

            // Para cada linha do arquivo.
            while (rowIterator.hasNext()) {
                row = rowIterator.next();

                // Identifica id das colunas e pula primeira linha.
                if(row.getRowNum() == 0){
                    identificaColunas();
                    continue;
                }

                peticaoLida = leLinha(row, workbook);
                // System.out.println("id " + peticaoLida.getId());
                possuiTag = identificaTagsPeticao(peticaoLida);
                peticaoBrasileira = paisBrasil(peticaoLida);

                // Só insere petição na lista de petições a serem inseridas no banco
                // se for pertencente ao Brasil e possuir alguma tag.
                if(possuiTag && peticaoBrasileira) {
                    listaPeticoes.add(peticaoLida);
                }
            }

            // Envia dados para o banco de dados.
            consultas.populaBanco(listaPeticoes, listaTags);

            file.close();
            workbook.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /* Método que solicita o nome do arquivo a ser aberto. */
    public String leNomeArquivo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nome do arquivo: ");
        String nomeArquivo = scanner.nextLine();

        return nomeArquivo;
    }

    /* Identifica a posição das colunas de acordo com as informações a serem coletadas. */
    private void identificaColunas() {
        indexId = 0;
        indexUrl = 1;
        indexTitle = 2;
        indexDesc = 3;
        indexAuthor = 4;
        indexDate = 5;
        indexCountry = 6;
        indexSign = 8;
        indexTarget = 9;
        indexRatio = 10;
        indexFb = 11;
        indexTw = 12;
        indexWh = 13;
        indexEmail = 14;
        indexLang = 15;
        indexPeople = 17;
        indexOrg = 18;
        indexLoc = 19;
        indexMisc = 20;
    }

    /* Método que pega dados de uma linha e coloca os mesmos em um objeto Petição */
    private Peticao leLinha(Row row, XSSFWorkbook workbook) throws ParseException {
        Peticao peticao = new Peticao();
        FormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator(workbook);
        DataFormatter objDefaultFormat = new DataFormatter();
        String id, sign, target, ratio, facebookCount, twitterCount, whatsappCount, emailCount;

        // Validações de números inteiros a fim de evitar problemas com células vazias, por exemplo.

        objFormulaEvaluator.evaluate(row.getCell(indexId));
        id = objDefaultFormat.formatCellValue(row.getCell(indexId), objFormulaEvaluator);
        peticao.setId(verificaInteiro(id));

        objFormulaEvaluator.evaluate(row.getCell(indexSign));
        sign = objDefaultFormat.formatCellValue(row.getCell(indexSign), objFormulaEvaluator);
        peticao.setSign(verificaInteiro(sign));

        objFormulaEvaluator.evaluate(row.getCell(indexTarget));
        target = objDefaultFormat.formatCellValue(row.getCell(indexTarget), objFormulaEvaluator);
        peticao.setTarget(verificaInteiro(target));

        objFormulaEvaluator.evaluate(row.getCell(indexRatio));
        ratio = objDefaultFormat.formatCellValue(row.getCell(indexRatio), objFormulaEvaluator);
        peticao.setRatio(verificaDouble(ratio));

        objFormulaEvaluator.evaluate(row.getCell(indexFb));
        facebookCount = objDefaultFormat.formatCellValue(row.getCell(indexFb), objFormulaEvaluator);
        peticao.setFacebook(verificaInteiro(facebookCount));

        objFormulaEvaluator.evaluate(row.getCell(indexTw));
        twitterCount = objDefaultFormat.formatCellValue(row.getCell(indexTw), objFormulaEvaluator);
        peticao.setTwitter(verificaInteiro(twitterCount));

        objFormulaEvaluator.evaluate(row.getCell(indexWh));
        whatsappCount = objDefaultFormat.formatCellValue(row.getCell(indexWh), objFormulaEvaluator);
        peticao.setWhatsapp(verificaInteiro(whatsappCount));

        objFormulaEvaluator.evaluate(row.getCell(indexEmail));
        emailCount = objDefaultFormat.formatCellValue(row.getCell(indexEmail), objFormulaEvaluator);
        peticao.setEmail(verificaInteiro(emailCount));

        peticao.setUrl(row.getCell(indexUrl).getStringCellValue());
        peticao.setTitle(peticao.corrigeString(row.getCell(indexTitle).getStringCellValue()));
        peticao.setDescription(peticao.corrigeString(row.getCell(indexDesc).getStringCellValue()));
        peticao.setAuthor(peticao.corrigeString(row.getCell(indexAuthor).getStringCellValue()));
        peticao.setCountry(peticao.corrigeString(row.getCell(indexCountry).getStringCellValue()));
        peticao.setLanguage(row.getCell(indexLang).getStringCellValue());
        peticao.setPeople(peticao.corrigeString(row.getCell(indexPeople).getStringCellValue()));
        peticao.setOrganizations(peticao.corrigeString(row.getCell(indexOrg).getStringCellValue()));
        peticao.setLocation(peticao.corrigeString(row.getCell(indexLoc).getStringCellValue()));
        peticao.setMiscellany(peticao.corrigeString(row.getCell(indexMisc).getStringCellValue()));
        peticao.setDate(row.getCell(indexDate).getDateCellValue());

        return peticao;
    }

    /* Método para identificar se valor de célula corresponde a um inteiro
    * Se a mesma estiver vazia, faz o valor igual a zero.*/
    private int verificaInteiro(String str) {
        double numero;

        if(str.isEmpty())
            return 0;
        else {
            numero = Double.parseDouble(str);
            return (int) numero;
        }
    }

    /* Método para evitar problemas com números decimais
    * Substitui vírgula por ponto, e faz valor igual a zero se célula for vazia. */
    private double verificaDouble(String str) {
        double numero;

        str = str.replaceAll(",", ".");

        if(str.isEmpty())
            return 0.0;
        else {
            numero = Double.parseDouble(str);
            return numero;
        }
    }

    /* Identifica as tags de uma petição.
    * Retorna se alguma tag foi adicionada à petição ou não. */
    private boolean identificaTagsPeticao(Peticao peticao) {

        for(Tag tag : listaTags) {
            if(contemTag(peticao, tag)) {
                peticao.addTag(tag);
            }
        }

        // Se tamanho for diferente de zero, quer dizer que é uma petição sobre política.
        return !peticao.getTags().isEmpty();
    }

    /* Método que cria lista de tags. */
    private void criaListaTags() {
        String[] tags = {"corrupção", "corrupcao", "corrupçao", "lula", "temer", "dilma", "aécio",
                "aecio", "bolsonaro", "bolsomito", "ciro gomes", "marina silva", "geraldo alckmin",
                "rodrigo maia", "aldo rebelo", "álvaro dias", "alvaro dias", "manuela d'ávila",
                "manuela d'avila", "manuela davila", "boulos", "collor", "cristovam buarque", "fernando haddad",
                "levy fidelix", "joaquim barbosa", "manifestação", "manifestacao", "manifestaçao", "eleição",
                "eleicao", "eleiçao", "eleições", "eleicoes", "eleiçoes", "política", "politica", "político",
                "politico", "partido", "brasília", "brasilia", "deputado", "senador", "assembléia", "assembleia",
                "presidente", "câmara", "camara", "senado", "lava jato", "foro", "foro privilegiado", "governo"};

        for(int i = 0; i < tags.length; i++) {
            Tag tag = new Tag();
            tag.setId(i);
            tag.setNome(tags[i]);

            listaTags.add(tag);
        }
    }

    /* Método que verifica se uma petição contém em seu título,
    ou em outros atributos, os termos das tags.*/
    private boolean contemTag(Peticao peticao, Tag tag) {
        String title = peticao.getTitle().toUpperCase();
        String description = peticao.getDescription().toUpperCase();
        String people = peticao.getPeople().toUpperCase();
        String organizations = peticao.getOrganizations().toUpperCase();
        String tagNome = tag.getNome().toUpperCase();

        return title.contains(tagNome) || description.contains(tagNome) || people.contains(tagNome) || organizations.contains(tagNome);
    }

    /* Método que retorna se uma petição possui país Brasil ou Brazil. */
    private boolean paisBrasil(Peticao peticao) {
        return peticao.getCountry().equals("Brazil") || peticao.getCountry().equals("Brasil");
    }

}
