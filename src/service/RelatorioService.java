package service;

import java.io.FileWriter;
import java.io.IOException;
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

    public void exportarCSV() {

        try (FileWriter writer =
                new FileWriter("relatorio_doacoes.csv")) {

            writer.append(
                "ID;Beneficiario;Prioridade;Item;Quantidade;Data\n"
            );

            for (DoacaoEfetivada d :
                    repo.getListaDoacoesEfetivadas()) {

                writer.append(d.getId()).append(";");
                writer.append(d.getBeneficiario().getNome()).append(";");
                writer.append(String.valueOf(
                        d.getBeneficiario().getNivelPrioridade()))
                        .append(";");
                writer.append(d.getItem().getNomeItem()).append(";");
                writer.append(String.valueOf(d.getQuantidade()))
                        .append(";");
                writer.append(d.getDataCadastro().toString())
                        .append("\n");
            }

            System.out.println(
                "Relatório exportado para relatorio_doacoes.csv"
            );

        } catch (IOException e) {

            System.out.println(
                "Erro ao exportar relatório: "
                + e.getMessage()
            );
        }
    }
}