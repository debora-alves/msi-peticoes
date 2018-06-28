package Processamento;

import java.text.ParseException;

/**
 * Classe principal.
 * @author Debora A. Cordeiro
 *
 * */
public class Main {

    public static void main (String[] a) throws ParseException {
        LeituraAvaaz leituraAvaaz = new LeituraAvaaz();
        leituraAvaaz.leArquivoAvaaz();
    }
}
