package service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Beneficiario;
import model.DoacaoEfetivada;
import model.Solicitacao;
import model.StatusSolicitacao;
import repository.DoacaoRepository;

public class RelatorioService {

    private final DoacaoRepository repo;

    public RelatorioService(DoacaoRepository repo) {
        this.repo = repo;
    }

   public void gerarResumoGeral() {

    int totalDoadores =
        repo.getListaDoadores().size();

    int totalBeneficiarios =
        repo.getListaBeneficiarios().size();

    int totalItens =
        repo.getListaItens().size();

    int totalSolicitacoes =
        repo.getListaSolicitacoes().size();

    int totalDoacoes =
        repo.getListaDoacoesEfetivadas().size();

    int totalAprovadas = 0;
    int totalCanceladas = 0;
    

    for (Solicitacao s : repo.getListaSolicitacoes()) {

        if (s.getStatus() == StatusSolicitacao.APROVADA) {
            totalAprovadas++;
        }

        if (s.getStatus() == StatusSolicitacao.CANCELADA) {
            totalCanceladas++;
        }
      
    }

    System.out.println("\n=== RESUMO GERAL ===");
    System.out.println("Total de Doadores: " + totalDoadores);
    System.out.println("Total de Beneficiários: " + totalBeneficiarios);
    System.out.println("Total de Itens: " + totalItens);
    System.out.println("Total de Solicitações: " + totalSolicitacoes);
    System.out.println("Solicitações Aprovadas: " + totalAprovadas);
    System.out.println("Solicitações Canceladas: " + totalCanceladas);
    System.out.println("Doações Efetivadas: " + totalDoacoes);
}

    public void exportarDoaçõesEfetivadasCSV() {

        try (FileWriter writer =
                new FileWriter("relatorio_doacoes.csv")) {

            writer.append("ID;Beneficiario;Prioridade;Item;Quantidade;Data\n");

            List<DoacaoEfetivada> listaOrdenada = new ArrayList<>(repo.getListaDoacoesEfetivadas());

            listaOrdenada.sort(Comparator.comparingInt(d -> d.getBeneficiario().getNivelPrioridade()));

            for (DoacaoEfetivada d : listaOrdenada) {

                writer.append(d.getId()).append(";");
                writer.append(d.getBeneficiario().getNome()).append(";");
                writer.append(String.valueOf(d.getBeneficiario().getNivelPrioridade())).append(";");
                writer.append(d.getItem().getNomeItem()).append(";");
                writer.append(String.valueOf(d.getQuantidade())).append(";");
                writer.append(d.getDataCadastro().toString()).append("\n");
            }

            System.out.println("Relatório exportado para relatorio_doacoes.csv");

        } catch (IOException e) {
            System.out.println("Erro ao exportar relatório: "+ e.getMessage());
        }
    }

    public void exportarSolicitacoesCanceladasCSV() {

        try (FileWriter writer = new FileWriter("solicitacoes_canceladas.csv")) {
    
            writer.append("ID;Beneficiario;Item;Quantidade;Justificativa\n");
    
            for (Solicitacao s :
                    repo.getListaSolicitacoes()) {
    
                if (s.getStatus() == StatusSolicitacao.CANCELADA) {
                    writer.append(s.getId()).append(";");
                    writer.append(s.getBeneficiario().getNome()).append(";");
                    writer.append(s.getItem().getNomeItem()).append(";");
                    writer.append(String.valueOf(s.getQuantidade())).append(";");
                    writer.append(s.getJustificativa()).append("\n");
                }
            }
    
            System.out.println("Arquivo solicitacoes_canceladas.csv gerado!");
    
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
     }

     public void exportarResumoGeralCSV() {

        int aprovadas = 0;
        int canceladas = 0;
        int pendentes = 0;
    
        for (Solicitacao s :
                repo.getListaSolicitacoes()) {
    
            switch (s.getStatus()) {
    
                case APROVADA:
                    aprovadas++;
                    break;

                case ENTREGUE:
                    aprovadas++;
                    break;
                    
                case CANCELADA:
                    canceladas++;
                    break;
 
            }
        }
    
        try (FileWriter writer = new FileWriter("resumo_geral.csv")) {
    
            writer.append("Indicador;Quantidade\n");
            
            writer.append("Doadores;");
            writer.append(String.valueOf(repo.getListaDoadores().size())).append("\n");
            
            writer.append("Beneficiarios;");
            writer.append(String.valueOf(repo.getListaBeneficiarios().size())).append("\n");
            
            writer.append("Itens;");
            writer.append(String.valueOf(repo.getListaItens().size())).append("\n");
            
            writer.append("Solicitacoes;");
            writer.append(String.valueOf(repo.getListaSolicitacoes().size())).append("\n");
            
            writer.append("Aprovadas;");
            writer.append(String.valueOf(aprovadas)).append("\n");
    
            writer.append("Canceladas;");
            writer.append(String.valueOf(canceladas)).append("\n");
    
            writer.append("Pendentes;");
            writer.append(String.valueOf(pendentes)).append("\n");
    
            writer.append("Doacoes Efetivadas;");
            writer.append(String.valueOf( repo.getListaDoacoesEfetivadas().size())).append("\n");
    
            System.out.println("Arquivo resumo_geral.csv gerado!");
    
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exportarRankingBeneficiariosCSV() {

        try (FileWriter writer = new FileWriter("ranking_prioridade.csv")) {

            writer.append("Prioridade;Beneficiario;TotalRecebido\n");

            List<Beneficiario> lista =
                new ArrayList<>(repo.getListaBeneficiarios());

            lista.sort(Comparator.comparingInt(Beneficiario::getNivelPrioridade));

            for (Beneficiario b : lista) {

                int totalRecebido = 0;

                for (DoacaoEfetivada d :
                        repo.getListaDoacoesEfetivadas()) {

                    if (d.getBeneficiario().getId().equals(b.getId())) {
                        totalRecebido += d.getQuantidade();
                    }
                }

                writer.append(String.valueOf(b.getNivelPrioridade())).append(";");

                writer.append(b.getNome()).append(";");

                writer.append(String.valueOf(totalRecebido)).append("\n");
            }

            System.out.println("Ranking por prioridade exportado com sucesso!");

        } catch (IOException e) {

            System.out.println("Erro: " + e.getMessage());
        }
    }

}