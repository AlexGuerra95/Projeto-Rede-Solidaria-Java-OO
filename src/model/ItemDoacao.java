package model;

import java.time.LocalDate;


public class ItemDoacao {

    private String id;
    private String nomeItem;
    private String categoria;
    private String descricao;
    private int quantidade;
    private String estadoConservacao;
    private final LocalDate dataCadastro;
    private StatusItem status;

    
    public ItemDoacao(String id, String nomeItem, String categoria, String descricao,
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if (this.quantidade > 0 && this.status == StatusItem.RESERVADO) {
            this.status = StatusItem.DISPONIVEL;
        }
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

    // Metodos de controle do clico de vida 
    public void reservar() {
        if (this.status == StatusItem.DISPONIVEL) {
            this.status = StatusItem.RESERVADO;
            System.out.println("Sucesso: O item '" + nomeItem + "' agora está RESERVADO.");
        } else {
            System.out.println("Aviso: O item '" + nomeItem + "' não pode ser reservado porque está com o status: " + this.status);
        }
    }

    public void entregar() {
        if (this.status == StatusItem.RESERVADO) {
            this.status = StatusItem.ENTREGUE;
            System.out.println("Sucesso: O item '" + nomeItem + "' foi ENTREGUE com sucesso!");
        } else {
            System.out.println("Aviso: O item '" + nomeItem + "' só pode ser entregue se estiver RESERVADO primeiro. Status atual: " + this.status);
        }
    }

    public void cancelar() {
        if (this.status != StatusItem.ENTREGUE) {
            this.status = StatusItem.CANCELADO;
            System.out.println("Sucesso: O item '" + nomeItem + "' foi CANCELADO.");
        } else {
            System.out.println("Erro: Não é possível cancelar o item '" + nomeItem + "' pois ele já foi ENTREGUE.");
        }
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
