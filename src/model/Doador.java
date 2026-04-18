package model;

/**
 * Representa um usuário do tipo doador.
 * Herda atributos da classe Usuario.
 */
public class Doador extends Usuario {

    private String tipoDoacao;

    // Construtor
    public Doador(int id, String nome, String telefone, String email, String tipoDoacao) {
        super(id, nome, telefone, email, endereco);
        this.tipoDoacao = tipoDoacao;
    }

    public String getTipoDoacao() {
        return tipoDoacao;
    }

    @Override
    public void exibirDados() {
        super.exibirDados();
        System.out.println("Tipo de Doação: " + tipoDoacao);
    }
}