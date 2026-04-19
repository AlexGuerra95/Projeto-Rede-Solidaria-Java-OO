package model;

/**
 * Representa um usuário do tipo beneficiário.
 * Herda atributos da classe Usuario.
 */
public class Beneficiario extends Usuario {

    private String necessidade;
    private String situacao;

    // Construtor
    public Beneficiario(int id, String nome, String telefone, String email, String endereco,
                        String necessidade, String situacao) {
        super(id, nome, telefone, email, endereco);
        this.necessidade = necessidade;
        this.situacao = situacao;
    }

    // Getters
    public String getNecessidade() {
        return necessidade;
    }

    public String getSituacao() {
        return situacao;
    }

    /**
     * Exibe os dados completos do beneficiário
     */
    @Override
    public void exibirDados() {
        super.exibirDados();
        System.out.println("Necessidade: " + necessidade);
        System.out.println("Situação: " + situacao);
    }
}