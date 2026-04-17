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

    // Getters (acesso aos dados)
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Exibe os dados básicos do usuário no terminal
     */
    public void exibirDados() {
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Telefone: " + telefone);
        System.out.println("Email: " + email);
    }
}