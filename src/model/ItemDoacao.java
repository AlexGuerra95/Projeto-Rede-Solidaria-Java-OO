package model;

import java.time.LocalDate;


public class ItemDoacao {

    private int id;
    private String nomeItem;
    private String categoria;
    private String descricao;
    private int quantidade;
    private String estadoConservacao;
    private LocalDate dataCadastro;
    private StatusItem status;

    
    public ItemDoacao(int id, String nomeItem, String categoria, String descricao,
                      int quantidade, String estadoConservacao,
                      LocalDate dataCadastro, StatusItem status) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.categoria = categoria;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.estadoConservacao = estadoConservacao;
        this.dataCadastro = dataCadastro;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getEstadoConservacao() {
        return estadoConservacao;
    }

    public void setEstadoConservacao(String estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public StatusItem getStatus() {
        return status;
    }

    private void setStatus(StatusItem status) {
        this.status = status;
    }


 public void exibirDadosItem() {
    System.out.println("Id: " + id);
    System.out.println("Nome do item: " + nomeItem);
    System.out.println("Categoria: " + categoria);
    System.out.println("Descrição: " + descricao);
    System.out.println("Quantidade: " + quantidade);
    System.out.println("Estado de conservação: " + estadoConservacao);
    System.out.println("Data de cadastro: " + dataCadastro);
    System.out.println("Status: " + status);
    System.out.println("      ");
}
}