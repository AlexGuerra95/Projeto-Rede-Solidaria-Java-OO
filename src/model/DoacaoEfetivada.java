package model;

import java.time.LocalDate;

public class DoacaoEfetivada {
    public String id;
    public ItemDoacao item;
    public Doador doador;
    public Beneficiario beneficiario;
    public int quantSolicitada;
    public LocalDate dataCadastro;
    public String observacoes;



    public DoacaoEfetivada(int id, ItemDoacao item, Doador doador, Beneficiario beneficiario, int quantSolicitada, LocalDate dataCadastro, String observacoes){
    this.id = id;
    this.item = item;
    this.doador = doador;
    this.beneficiario = beneficiario;
    this.quantSolicitada = quantSolicitada;
    this.dataCadastro = dataCadastro;
    this.observacoes = observacoes;
   
}



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemDoacao getItem() {
        return item;
    }

    public void setItem(ItemDoacao item) {
        this.item = item;
    }

    public Doador getDoador() {
        return doador;
    }

    public void setDoador(Doador doador) {
        this.doador = doador;
    }


    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public int getQuantidade() {
        return quantSolicitada;
    }

    public void setQuantidade(int quantSolicitada) {
        this.quantSolicitada = quantSolicitada;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public void exibirDadosItem() {
        System.out.println("Id: " + id);
        System.out.println("Item: " + item.getNomeItem());
        System.out.println("Doador: " + doador.getNome());
        System.out.println("Beneficiario: " + beneficiario.getNome());
        System.out.println("Quantidade: " + quantSolicitada);
        System.out.println("Observações: " + observacoes);
        System.out.println("Data de cadastro: " + dataCadastro);
        System.out.println("      ");
    }
    }
    






