package model;

/**
 * Representa um usuário do tipo doador.
 * Herda atributos da classe Usuario.
 */
public class Doador extends Usuario {

    private String tipoDoacao;

    // Construtor
    public Doador(int id, String nome, String telefone, String email, String tipoDoacao) {
        super(id, nome, telefone, email);
        this.tipoDoacao = tipoDoacao;
    }
}