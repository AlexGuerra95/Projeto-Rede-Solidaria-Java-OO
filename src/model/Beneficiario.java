package model;

public class Beneficiario extends Usuario {

    private String id;
    private String tipoBeneficiario;
    private int nivelPrioridade;

    // Construtor
    public Beneficiario(String id, String nome, String telefone, String email, String endereco,
                        String tipoBeneficiario, int nivelPrioridade) {
        super(id, nome, telefone, email, endereco);
        this.tipoBeneficiario = tipoBeneficiario;
        this.nivelPrioridade = nivelPrioridade;
    }

    // Getters e Setters
    public String getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    public void setTipoBeneficiario(String tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    public int getNivelPrioridade() {
        return nivelPrioridade;
    }

    public void setNivelPrioridade(int nivelPrioridade) {
        this.nivelPrioridade = nivelPrioridade;
    }


    @Override
    public void exibirDados() {
        super.exibirDados();
        System.out.println("Tipo de beneficiário: " + tipoBeneficiario);
        System.out.println("Nível de prioridade: " + nivelPrioridade);
        System.out.println("    ");
    }

}