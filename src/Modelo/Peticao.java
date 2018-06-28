package Modelo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Esta classe modela os dados de uma Petição.
 * Implementa métodos get e set dos atributos da mesma.
 * @author Debora A. Cordeiro
 *
 * */
public class Peticao {
    private int id, sign, target, facebook, twitter, whatsapp, email;
    private double ratio;
    private String url, title, description, author, country, language, people, organizations, location, miscellany;
    private Date date;
    private ArrayList<Tag> tags = new ArrayList<>();

    /* Construtores. */
    public Peticao() {

    }
    public Peticao(Peticao peticao) {
        this.id = peticao.getId();
        this.sign = peticao.getSign();
        this.target = peticao.getTarget();
        this.ratio = peticao.getRatio();
        this.facebook = peticao.getFacebook();
        this.twitter = peticao.getTwitter();
        this.whatsapp = peticao.getWhatsapp();
        this.email = peticao.getEmail();
        this.url = peticao.getUrl();
        this.title = peticao.getTitle();
        this.description = peticao.getDescription();
        this.author = peticao.getAuthor();
        this.country = peticao.getCountry();
        this.language = peticao.getLanguage();
        this.people = peticao.getPeople();
        this.organizations = peticao.getOrganizations();
        this.location = peticao.getLocation();
        this.miscellany = peticao.getMiscellany();
        this.date = peticao.getDate();
        this.tags = peticao.getTags();
    }

    /* Id - identificador no Avaaz. */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /* Sign - total de assinaturas. */
    public int getSign() {
        return sign;
    }
    public void setSign(int sign) {
        this.sign = sign;
    }

    /* Target - total de assinaturas alvo da petição. */
    public int getTarget() {
        return target;
    }
    public void setTarget(int target) {
        this.target = target;
    }

    /* Ratio - proporção entre os valores de Sign e Target. */
    public double getRatio() {
        return ratio;
    }
    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    /* Facebook - total de compartilhamentos no Facebook. */
    public int getFacebook() {
        return facebook;
    }
    public void setFacebook(int facebook) {
        this.facebook = facebook;
    }

    /* Twitter - total de compartilhamentos no Twitter. */
    public int getTwitter() {
        return twitter;
    }
    public void setTwitter(int twitter) {
        this.twitter = twitter;
    }

    /* Whatsapp - total de compartilhamentos no WhatsApp. */
    public int getWhatsapp() {
        return whatsapp;
    }
    public void setWhatsapp(int whatsapp) {
        this.whatsapp = whatsapp;
    }

    /* Email - total de compartilhamentos por E-mail. */
    public int getEmail() {
        return email;
    }
    public void setEmail(int email) {
        this.email = email;
    }

    /* URL - url da petição*/
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    /* Title - título da petição. */
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /* Descrição - texto da descrição da petição. */
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /* Author - nome do autor da petição. */
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    /* Country - país de origem do autor. */
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    /* Language - idioma. */
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    /* People - pessoas encontradas no título e na descrição da petição. */
    public String getPeople() {
        return people;
    }
    public void setPeople(String people) {
        this.people = people;
    }

    /* Organizations - organizações encontradas no título e na descrição. */
    public String getOrganizations() {
        return organizations;
    }
    public void setOrganizations(String organizations) {
        this.organizations = organizations;
    }

    /* Location - locais encontrados no título e na descrição. */
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    /* Miscellany. */
    public String getMiscellany() {
        return miscellany;
    }
    public void setMiscellany(String miscellany) {
        this.miscellany = miscellany;
    }

    /* Date - data de publicação da petição. */
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    /* Tags - lista de tags*/
    public ArrayList<Tag> getTags() {
        return tags;
    }
    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }
    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    /* Corrige problemas de codificação dos textos das petições. */
    public String corrigeString(String string) {
        String aux;

        try {
            string = string.replaceAll("'", "''");

            aux = new String("á".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "á");

            aux = new String("Á".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "Á");

            aux = new String("à".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "à");

            aux = new String("À".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "À");

            aux = new String("â".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "â");

            aux = new String("Â".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "Â");

            aux = new String("ã".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "ã");

            aux = new String("Ã".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "Ã");

            aux = new String("é".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "é");

            aux = new String("É".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "É");

            aux = new String("ê".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "ê");

            aux = new String("Ê".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "Ê");

            aux = new String("í".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "í");

            aux = new String("Í".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "Í");

            aux = new String("ó".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "ó");

            aux = new String("Ó".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "Ó");

            aux = new String("ô".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "ô");

            aux = new String("Ô".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "Ô");

            aux = new String("õ".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "õ");

            aux = new String("Õ".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "Õ");

            aux = new String("ú".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "ú");

            aux = new String("Ú".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "Ú");

            aux = new String("ç".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "ç");

            aux = new String("Ç".getBytes("UTF-8"), "ISO-8859-1");
            string = string.replaceAll(aux, "Ç");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return string;
    }
}
