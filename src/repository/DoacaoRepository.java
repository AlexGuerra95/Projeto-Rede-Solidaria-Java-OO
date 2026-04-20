package repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Doador;
import model.ItemDoacao;

public class DoacaoRepository {
    private List<Doador> doadores = new ArrayList<>();
    private List<ItemDoacao> listaItens = new ArrayList<>();

    public void salvarDoador(Doador d) { doadores.add(d); }
    public List<Doador> listarDoadores() { return doadores; }

    // Doações iniciais de exemplo para teste no terminal.
    public void carregarDadosIniciais() {
        listaItens.add(new ItemDoacao(
            1, "Cesta básica", "Alimentos", "Cesta com 12 itens essenciais",
            10, "Novo", LocalDate.now(), "Disponível"
        ));
        
        listaItens.add(new ItemDoacao(
            2, "Mesa de escritório", "Móveis", "Madeira escura, 1,20m", 
            1, "Bom estado", LocalDate.now(), "Disponível"
        ));

        listaItens.add(new ItemDoacao(
            3, "Kit Escolar", "Educação", "Mochila, 5 cadernos e estojo", 
            15, "Novo", LocalDate.now(), "Disponível"
        ));
    }

    public List<ItemDoacao> getListaItens() {
        return listaItens;
    }
}
