package controller;

import java.util.Scanner;
import model.Beneficiario;
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
        
        // Inicializa o serviço de solicitação injetando as dependências necessárias
        ValidacaoService validacaoService = new ValidacaoService();
        this.solicitacaoService = new SolicitacaoService(validacaoService, repo);
    }

    public void criarSolicitacao() {
        System.out.println("\n--- NOVA SOLICITAÇÃO DE ITEM ---");
        
        // 1. Verifica se há itens no sistema
        if (repo.getListaItens().isEmpty()) {
            System.out.println("Nenhum item cadastrado no sistema.");
            return;
        }

        // 2. Lista os itens para o usuário escolher
        System.out.println("Itens Disponíveis:");
        repo.getListaItens().forEach(i -> 
            System.out.println("ID: " + i.getId() + " - " + i.getNomeItem() + " | Qtd: " + i.getQuantidade() + " | Status: " + i.getStatus())
        );

        // 3. Busca o Item selecionado
        System.out.print("\nInforme o ID do item desejado: ");
        int idItem = lerNumero();
        ItemDoacao itemSelecionado = repo.buscarItemPorId(idItem);
        
        if (itemSelecionado == null) {
            System.out.println("Item não encontrado.");
            return;
        }

        // 4. Coleta dados da solicitação
        System.out.print("Informe a quantidade solicitada: ");
        int quantidadeSolicitada = lerNumero();
        
        System.out.print("Informe a justificativa do pedido: ");
        String justificativa = scanner.nextLine();

        // 5. Busca o Beneficiário solicitante
        System.out.print("Informe o ID do beneficiário: ");
        int idBeneficiario = lerNumero();
        Beneficiario beneficiario = repo.buscarBeneficiarioPorId(idBeneficiario);
        
        if (beneficiario == null) {
            System.out.println("Beneficiário não encontrado.");
            return;
        }

        // 6. Cria o objeto e envia para o Service processar
        Solicitacao solicitacao = new Solicitacao(contadorId++, beneficiario, itemSelecionado, quantidadeSolicitada, justificativa);
        boolean aprovado = solicitacaoService.solicitarItem(solicitacao);

        if (aprovado) {
            System.out.println("Solicitação aprovada com sucesso! Quantidade restante do item: " + itemSelecionado.getQuantidade());
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

        // Filtra e exibe apenas as solicitações que estão ativas (Aprovadas) para cancelamento
        long totalAtivas = repo.getListaSolicitacoes().stream()
                .filter(s -> s.getStatus() == StatusSolicitacao.APROVADA)
                .peek(s -> System.out.println("ID Pedido: " + s.getId() + " | Beneficiário: " + s.getBeneficiario().getNome() + " | Item: " + s.getItem().getNomeItem() + " | Qtd Reservada: " + s.getQuantidade()))
                .count();

        if (totalAtivas == 0) {
            System.out.println("Nenhuma solicitação ativa elegível para cancelamento no momento.");
            return;
        }

        System.out.print("\nInforme o ID da solicitação que deseja cancelar: ");
        int idSolCancelar = lerNumero();

        // Busca a solicitação correspondente e executa o cancelamento via Service
        repo.getListaSolicitacoes().stream()
                .filter(s -> s.getId() == idSolCancelar)
                .findFirst()
                .ifPresentOrElse(
                    solicitacaoService::cancelarSolicitacao,
                    () -> System.out.println("Solicitação com o ID informado não foi encontrada.")
                );
    }

    /**
     * Método utilitário local para garantir leituras numéricas seguras do teclado
     */
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