package repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoacaoRepository {
    public List<Doador> doadores = new ArrayList<>();
    public List<Beneficiario> beneficiarios = new ArrayList<>();
    public List<ItemDoacao> listaItens = new ArrayList<>();
    public List<Solicitacao> listaSolicitacoes = new ArrayList<>();
    public List<DoacaoEfetivada> listaDoacaoesEfetivadas = new ArrayList<>(); 

    public void salvarDoador(Doador d) { doadores.add(d); }
    public void salvarBeneficiario(Beneficiario b) { beneficiarios.add(b); }
    public void salvarItem(ItemDoacao i) { listaItens.add(i); }
    public void registrarSolicitacao(Solicitacao s) { listaSolicitacoes.add(s); }
    public void salvarDoacaoEfetivada(DoacaoEfetivada de) {listaDoacaoesEfetivadas.add(de);}

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
    public List<DoacaoEfetivada> getListaDoacoesEfetivadas() { return listaDoacaoesEfetivadas; }
    
    
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
    }

    //Do ciclo de vida
   public List<ItemDoacao> filtrarPorStatus(StatusItem status) {
        List<ItemDoacao> resultado = new ArrayList<>();
        
        for (ItemDoacao item : listaItens) {
            if (item.getStatus() == status) {
                resultado.add(item);
            }
        }
        
        if (status == StatusItem.RESERVADO) {
            for (Solicitacao s : listaSolicitacoes) {
                if (s.getStatus() == StatusSolicitacao.APROVADA) {
                    
                    if (s.getItem().getStatus() == StatusItem.DISPONIVEL) {
                        
                        
                        ItemDoacao itemVirtual = new ItemDoacao(
                            s.getItem().getId(), 
                            s.getItem().getNomeItem(),
                            s.getItem().getCategoria(),
                            s.getItem().getDescricao(),
                            s.getQuantidade(), 
                            s.getItem().getEstadoConservacao(),
                            s.getItem().getDataCadastro(),
                            StatusItem.RESERVADO
                        );
                        resultado.add(itemVirtual);
                    }
                }
            }
        }
        return resultado;
    }



    //Por id
    public ItemDoacao buscarItemPorId(String id) {
        for (ItemDoacao item : listaItens) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public Beneficiario buscarBeneficiarioPorId(String id) {
        for (Beneficiario b : beneficiarios) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    public Doador buscarDoadorPorId(String id) {
        for (Doador d : doadores) {
            if (d.getId().equals(id)) {
            return d;
            }
        }
        return null;
    }
    }



