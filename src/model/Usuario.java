package model;

/**
 * Classe base que representa um usuário do sistema.
 */
public class Usuario {

    private int id;
    private String nome;
    private String telefone;
    private String email;

    // Construtor principal
    public Usuario(int id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }
}