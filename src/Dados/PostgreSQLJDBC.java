package Dados;

import Modelo.Peticao;
import Modelo.Tag;

import java.sql.*;
import java.util.ArrayList;

/**
 * Classe para manipulação do banco de dados.
 * O banco escolhido foi o PostgreSQL.
 * @author Debora A. Cordeiro
 *
 * */
public class PostgreSQLJDBC {
    private Connection c;

    /* Método que popula o banco inteiro de uma vez. */
    public void populaBanco(ArrayList<Peticao> listaPeticoes, ArrayList<Tag> listaTags) {
        Statement stmt;
        ResultSet resultSet;
        String consulta;
        int idPeticao;

        try {
            conectaBanco();
            stmt = c.createStatement();

            // Insere tags primeiro.
            for(Tag tag : listaTags) {
                consulta = criaInsertTag(tag);
                stmt.execute(consulta);
            }

            // Insere petições e associações entre as mesmas e suas tags.
            for(Peticao peticao : listaPeticoes) {
                consulta = criaInsertPeticao(peticao);
                stmt.execute(consulta);

                // Recupera o id da nova petição criada.
                consulta = retornaIdPeticao(peticao);
                resultSet = stmt.executeQuery(consulta);
                idPeticao = 0;
                if(resultSet.next())
                    idPeticao = resultSet.getInt("id");

                // Insere associação entre Petição e Tag.
                for(Tag tag : peticao.getTags()) {
                    consulta = criaInsertPeticaoTags(idPeticao, tag);
                    stmt.execute(consulta);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* Método que realiza conexão ao banco. Este foi criado localmente na máquina em que o programa foi executado. */
    private void conectaBanco() {
        c = null;
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/msi-petitions",
                    "postgres", "postgres");
            //c.setAutoCommit(false);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    /* Método que cria a query para inserção de uma nova petição. */
    private String criaInsertPeticao(Peticao peticao) {
        String inicioInsert = "INSERT INTO petition (id_avaaz, url, title, description, " +
                "author, date, country_name, sign, target, ratio, facebook_count, twitter_count, " +
                "whatsapp_count, email_count, people, organizations, location, miscellany, language) VALUES";
        String values = "(" + peticao.getId() + ", '" + peticao.getUrl() + "', '" + peticao.getTitle() + "', '"
                + peticao.getDescription() + "', '" + peticao.getAuthor() + "', '" + (new java.sql.Date(peticao.getDate().getTime())) +
                "', '" + peticao.getCountry() + "', " + peticao.getSign() + ", " + peticao.getTarget() + ", " +
                peticao.getRatio() + ", " + peticao.getFacebook() + ", " + peticao.getTwitter() + ", " +
                peticao.getWhatsapp() + ", " + peticao.getEmail() + ", '" + peticao.getPeople() + "', '" +
                peticao.getOrganizations() + "', '" + peticao.getLocation() + "', '" + peticao.getMiscellany() +
                "', '" + peticao.getLanguage() + "');";
        String query = inicioInsert + values;

        return query;
    }

    /* Método que cria a query para inserção de uma nova tag. */
    private String criaInsertTag(Tag tag) {
        String inicioInsert = "INSERT INTO tag (name) VALUES";
        String value = "('" + tag.getNome().replaceAll("'", "''") + "');";
        String query = inicioInsert + value;

        return query;
    }

    /* Método que cria a query para retornar o id de uma petição. */
    private String retornaIdPeticao(Peticao peticao) {
        return "SELECT id FROM petition WHERE id_avaaz = " + peticao.getId();
    }

    /* Método que cria a query para inserir a associção entre petições e tags. */
    private String criaInsertPeticaoTags(int idPeticao, Tag tag) {
        int idTag = tag.getId();
        idTag++; // id proveniente da ordem no Arraylist de tags, que começa em 0; no banco, começa em 1.
        String inicioInsert = "INSERT INTO petition_tag (petition_id, tag_id) VALUES";
        String value = "(" + idPeticao + ", " + idTag + ");";
        return inicioInsert + value;
    }
}
