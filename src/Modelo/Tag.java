package Modelo;

/**
 * Esta classe modela os dados de uma Tag.
 * Implementa métodos get e set dos atributos da mesma.
 * @author Debora A. Cordeiro
 *
 * */
public class Tag {
    private int id;
    private String nome;

    /* Id - id da tag (valor gerado). */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /* Nome - nome da tag. */
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}
