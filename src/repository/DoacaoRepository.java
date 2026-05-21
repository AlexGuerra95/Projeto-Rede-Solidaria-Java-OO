package repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Beneficiario;
import model.Doador;
import model.ItemDoacao;
import model.StatusItem;
import model.Solicitacao;

public class DoacaoRepository {
    private List<Doador> doadores = new ArrayList<>();
    private List<Beneficiario> beneficiarios = new ArrayList<>();
    private List<ItemDoacao> listaItens = new ArrayList<>();
    private List<Solicitacao> listaSolicitacoes = new ArrayList<>();

    public void salvarDoador(Doador d) { doadores.add(d); }
    public void salvarBeneficiario(Beneficiario b) { beneficiarios.add(b); }
    public void salvarItem(ItemDoacao i) { listaItens.add(i); }
    public void registrarSolicitacao(Solicitacao s) { listaSolicitacoes.add(s); }

    // Dados Iniciais (Seed)
    public void carregarDadosIniciais() {
        listaItens.add(new ItemDoacao(1, "Cesta Básica", "Alimentos", "Arroz, feijão e itens básicos", 10, "Novo", LocalDate.now(), StatusItem.DISPONIVEL));
        listaItens.add(new ItemDoacao(2, "Mesa de Escritório", "Móveis", "Madeira, 1.20m", 1, "Bom estado", LocalDate.now(), StatusItem.DISPONIVEL));
        listaItens.add(new ItemDoacao(3, "Kit Escolar", "Educação", "Mochila e cadernos", 15, "Novo", LocalDate.now(), StatusItem.DISPONIVEL));
    }
    public List<ItemDoacao> getListaItens() { return listaItens; }
    public List<Doador> getListaDoadores() { return doadores; }
    public List<Beneficiario> getListaBeneficiarios() { return beneficiarios; }
    public List<Solicitacao> getListaSolicitacoes() { return listaSolicitacoes; }

    //Métodos de filtro

    //Da categoria
    public List<ItemDoacao> filtrarPorCategoria(String categoria) {
        List<ItemDoacao> resultado = new ArrayList<>();
        for (ItemDoacao item : listaItens) {
            if (item.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(item);
            }
        }
        return resultado;

    //Do ciclo de vida
    public List<ItemDoacao> filtrarPorStatus(StatusItem status) {
        List<ItemDoacao> resultado = new ArrayList<>();
        for (ItemDoacao item : listaItens) {
            if (item.getStatus() == status) {
                resultado.add(item);
            }
        }
        return resultado;

    //Por id
    public ItemDoacao buscarItemPorId(int id) {
        for (ItemDoacao item : listaItens) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public Beneficiario buscarBeneficiarioPorId(int id) {
        for (Beneficiario b : beneficiarios) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }
}

    public List<ItemDoacao> getListaItens() { return listaItens; }
}
