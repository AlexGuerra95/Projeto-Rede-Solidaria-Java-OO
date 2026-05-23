package controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import model.Beneficiario;
import model.DoacaoEfetivada;
import model.Doador;
import model.ItemDoacao;
import model.Solicitacao;
import model.StatusSolicitacao;
import repository.DoacaoRepository;
import service.SolicitacaoService;
import service.ValidacaoService;

public class SolicitacaoController {

    private final DoacaoRepository repo;
    private final SolicitacaoService solicitacaoService;
    private final Scanner scanner;
    private static int contadorId = 1;

    public SolicitacaoController(DoacaoRepository repo, Scanner scanner) {
        this.repo = repo;
        this.scanner = scanner;
        
        ValidacaoService validacaoService = new ValidacaoService();
        this.solicitacaoService = new SolicitacaoService(validacaoService, repo);
    }

    public void criarSolicitacao() {
        System.out.println(" NOVA SOLICITAÇÃO DE ITEM ");
        
        
        if (repo.getListaItens().isEmpty()) {
            System.out.println("Nenhum item cadastrado no sistema.");
            return;
        }

        
        System.out.println("Itens Disponíveis:");
        repo.getListaItens().forEach(i -> 
            System.out.println("ID: " + i.getId() + " - " + i.getNomeItem() + " | Qtd: " + i.getQuantidade() + " | Status: " + i.getStatus())
        );

        
        System.out.print("\nInforme o ID do item desejado: ");
        int idItem = lerNumero();
        ItemDoacao itemSelecionado = repo.buscarItemPorId(idItem);
        
        if (itemSelecionado == null) {
            System.out.println("Item não encontrado.");
            return;
        }

        
        System.out.print("Informe a quantidade solicitada: ");
        int quantidadeSolicitada = lerNumero();
        
        System.out.print("Informe a justificativa do pedido: ");
        String justificativa = scanner.nextLine();

        
        System.out.print("Informe o ID do beneficiário: ");
        int idBeneficiario = lerNumero();
        Beneficiario beneficiario = repo.buscarBeneficiarioPorId(idBeneficiario);
        
        if (beneficiario == null) {
            System.out.println("Beneficiário não encontrado.");
            return;
        }

        System.out.print("Informe o ID do doador deste item: ");
        int idDoador = lerNumero();
        Doador doador = repo.buscarDoadorPorId(idDoador); 
    
        if (doador == null) {
        System.out.println("Doador não encontrado. Operação cancelada.");
        return;
    }

       
        Solicitacao solicitacao = new Solicitacao(contadorId++, beneficiario, itemSelecionado, quantidadeSolicitada, justificativa);
        boolean aprovado = solicitacaoService.solicitarItem(solicitacao);

        if (aprovado) {
            System.out.println("Solicitação aprovada com sucesso! Quantidade restante do item: " + itemSelecionado.getQuantidade());
            DoacaoEfetivada efetivada = new DoacaoEfetivada(contadorId++, itemSelecionado, doador, beneficiario, quantidadeSolicitada, LocalDate.now(), 
            "Solicitação aprovada via menu. Justificativa: " + justificativa);
            
            repo.salvarDoacaoEfetivada(efetivada);
            System.out.println("Doação registrada no histórico de efetivadas!");
        
        } else {
            System.out.println("Solicitação rejeitada (Estoque insuficiente ou regras de validação violadas).");
        }
    }

    public void cancelarSolicitacao() {
        System.out.println("\n--- CANCELAR PEDIDO ---");
        
        if (repo.getListaSolicitacoes().isEmpty()) {
            System.out.println("Nenhuma solicitação encontrada no sistema.");
            return;
        }

       
        long totalAtivas = repo.getListaSolicitacoes().stream()
                .filter(s -> s.getStatus() == StatusSolicitacao.APROVADA)
                .peek(s -> System.out.println("ID Pedido: " + s.getId() + " | Beneficiário: " + s.getBeneficiario().getNome() + " | Item: " + s.getItem().getNomeItem() + " | Qtd Reservada: " + s.getQuantidade()))
                .count();

        if (totalAtivas == 0) {
            System.out.println("Nenhuma solicitação ativa disponível para cancelamento no momento.");
            return;
        }

        System.out.print("\nInforme o ID da solicitação que deseja cancelar: ");
        int idSolCancelar = lerNumero();

        
        repo.getListaSolicitacoes().stream()
                .filter(s -> s.getId() == idSolCancelar)
                .findFirst()
                .ifPresentOrElse(
                    solicitacaoService::cancelarSolicitacao,
                    () -> System.out.println("Solicitação com o ID informado não foi encontrada.")
                );
    }

        public void consultarSolicitacoes() {
            System.out.println("\n--- CONSULTAR SOLICITAÇÕES ---");
            
            java.util.List<model.Solicitacao> lista = repo.getListaSolicitacoes();
            
            if (lista == null || lista.isEmpty()) {
                System.out.println("Nenhuma solicitação cadastrada no sistema.");
                return;
            }
        
            System.out.println("Histórico de Pedidos:");
            for (model.Solicitacao s : lista) {
                System.out.println(
                    "ID Pedido: " + s.getId() + 
                    " | Beneficiário: " + s.getBeneficiario().getNome() + 
                    " | Item: " + s.getItem().getNomeItem() + 
                    " | Qtd: " + s.getQuantidade() + 
                    " | Status: " + s.getStatus()
                );
            }
        }

        public void consultarDoacoesEfetivadas() {
            System.out.println("Consulta de Doações Efetivadas");
            List<DoacaoEfetivada> lista = repo.getListaDoacoesEfetivadas();

            if (lista == null || lista.isEmpty()) {
                System.out.println("Nenhuma doação foi efetivada ainda.");
                return;
            }

            for (DoacaoEfetivada d : lista) {
                d.exibirDadosItem(); 
            }
        }


    private int lerNumero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida! Digite apenas números inteiros: ");
            }
        }
    }
}