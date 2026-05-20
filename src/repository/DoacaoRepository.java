package repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Beneficiario;
import model.Doador;
import model.ItemDoacao;

public class DoacaoRepository {
    private List<Doador> doadores = new ArrayList<>();
    private List<Beneficiario> beneficiarios = new ArrayList<>();
    private List<ItemDoacao> listaItens = new ArrayList<>();

    public void salvarDoador(Doador d) { doadores.add(d); }
    public void salvarBeneficiario(Beneficiario b) { beneficiarios.add(b); }
    public void salvarItem(ItemDoacao i) { listaItens.add(i); }

    // Dados Iniciais (Seed)
    public void carregarDadosIniciais() {
        listaItens.add(new ItemDoacao(1, "Cesta Básica", "Alimentos", "Arroz, feijão e itens básicos", 10, "Novo", LocalDate.now(), "Disponível"));
        listaItens.add(new ItemDoacao(2, "Mesa de Escritório", "Móveis", "Madeira, 1.20m", 1, "Bom estado", LocalDate.now(), "Disponível"));
        listaItens.add(new ItemDoacao(3, "Kit Escolar", "Educação", "Mochila e cadernos", 15, "Novo", LocalDate.now(), "Disponível"));
    }

    public List<ItemDoacao> getListaItens() { return listaItens; }
}