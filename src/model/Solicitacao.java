package model;

public class Solicitacao {

    public int id;
    public Beneficiario beneficiario;
    public ItemDoacao item;
    public int quantSolicitada;
    public String justificativa;
    public String status;

    public Solicitacao(int id, Beneficiario beneficiario, ItemDoacao item, int quantSolicitada, String justificativa, String status){
        this.id = id;
        this.beneficiario = beneficiario;
        this.item = item;
        this.quantSolicitada = quantSolicitada;
        this.justificativa = justificativa;
        this.status = status;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public ItemDoacao getItem() {
        return item;
    }

    public void setItem(ItemDoacao item) {
        this.item = item;
    }

    public int getQuantidade() {
        return quantSolicitada;
    }

    public void setQuantidade(int quantSolicitada) {
        this.quantSolicitada = quantSolicitada;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void exibirSolicitacao() {
        System.out.println("Id: " + id);
        System.out.println("Beneficiario: " + beneficiario);
        System.out.println("Item: " + item);
        System.out.println("Quantidade: " + quantSolicitada);
        System.out.println("Justificativa: " + justificativa);
        System.out.println("Status: " + status);
        System.out.println("      ");
    }

}
