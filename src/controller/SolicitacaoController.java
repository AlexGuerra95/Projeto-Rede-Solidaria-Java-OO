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

    public SolicitacaoController(DoacaoRepository repo, Scanner scanner) {
        this.repo = repo;
        this.scanner = scanner;

        ValidacaoService validacaoService = new ValidacaoService();
        this.solicitacaoService =
                new SolicitacaoService(validacaoService, repo);
    }

    public void criarSolicitacao() {

        System.out.println("\n=== NOVA SOLICITAÇÃO DE ITEM ===");

        if (repo.getListaItens().isEmpty()) {
            System.out.println("Nenhum item cadastrado no sistema.");
            return;
        }

        System.out.println("\nItens Disponíveis:");

        repo.getListaItens().forEach(i ->
            System.out.println(
                "ID: " + i.getId() +
                " | Item: " + i.getNomeItem() +
                " | Quantidade: " + i.getQuantidade() +
                " | Status: " + i.getStatus()
            )
        );

        System.out.print("\nInforme o ID do item desejado: ");
        String idItem = scanner.nextLine();

        ItemDoacao itemSelecionado =
                repo.buscarItemPorId(idItem);

        if (itemSelecionado == null) {
            System.out.println("Item não encontrado.");
            return;
        }

        System.out.print("Informe a quantidade solicitada: ");
        int quantidadeSolicitada = lerNumero();

        System.out.print("Informe a justificativa do pedido: ");
        String justificativa = scanner.nextLine();

        System.out.println("\nBeneficiários:");

        repo.getListaBeneficiarios().forEach(b ->
            System.out.println(
                "ID: " + b.getId() +
                " | Nome: " + b.getNome()
            )
        );

        System.out.print("\nInforme o ID do beneficiário: ");
        String idBeneficiario = scanner.nextLine();

        Beneficiario beneficiario =
                repo.buscarBeneficiarioPorId(idBeneficiario);

        if (beneficiario == null) {
            System.out.println("Beneficiário não encontrado.");
            return;
        }

        Solicitacao solicitacao = new Solicitacao(
                GeradorIds.gerarIdSolicitacao(),
                beneficiario,
                itemSelecionado,
                quantidadeSolicitada,
                justificativa
        );

        boolean aprovado =
                solicitacaoService.solicitarItem(solicitacao);

        if (aprovado) {

            repo.salvarSolicitacao(solicitacao);

            System.out.println(
                "\nSolicitação registrada e aprovada com sucesso!"
            );

        } else {

            System.out.println(
                "\nSolicitação rejeitada " +
                "(Estoque insuficiente ou regras inválidas)."
            );
        }
    }

    public void efetivarSolicitacao() {

        System.out.println("\n=== EFETIVAR / ENTREGAR SOLICITAÇÃO ===");

        List<Solicitacao> abertas = repo.getListaSolicitacoes()
                .stream()
                .filter(s ->
                        s.getStatus() == StatusSolicitacao.APROVADA)
                .toList();

        if (abertas.isEmpty()) {

            System.out.println(
                "Nenhuma solicitação aprovada aguardando entrega."
            );

            return;
        }

        System.out.println("\nPedidos disponíveis:");

        for (Solicitacao s : abertas) {

            System.out.println(
                "ID Pedido: " + s.getId() +
                " | Beneficiário: " + s.getBeneficiario().getNome() +
                " | Item: " + s.getItem().getNomeItem() +
                " | Quantidade: " + s.getQuantidade()
            );
        }

        System.out.print(
                "\nDigite o ID do pedido que deseja efetivar: "
        );

        String idPedido = scanner.nextLine();

        Solicitacao sol = abertas.stream()
                .filter(s -> s.getId().equals(idPedido))
                .findFirst()
                .orElse(null);

        if (sol == null) {

            System.out.println(
                "Pedido não encontrado ou já finalizado."
            );

            return;
        }

        System.out.println("\nDoadores cadastrados:");

        repo.getListaDoadores().forEach(d ->
            System.out.println(
                "ID: " + d.getId() +
                " | Nome: " + d.getNome()
            )
        );

        System.out.print(
                "\nInforme o ID do doador deste item: "
        );

        String idDoador = scanner.nextLine();

        Doador doador =
                repo.buscarDoadorPorId(idDoador);

        if (doador == null) {

            System.out.println(
                "Doador não encontrado. Operação cancelada."
            );

            return;
        }

        sol.setStatus(StatusSolicitacao.ENTREGUE);

        DoacaoEfetivada efetivada =
                new DoacaoEfetivada(

                    GeradorIds.gerarIdDoacao(),

                    sol.getItem(),

                    doador,

                    sol.getBeneficiario(),

                    sol.getQuantidade(),

                    LocalDate.now(),

                    "Doação entregue com sucesso. " +
                    "Justificativa original: " +
                    sol.getJustificativa()
                );

        repo.salvarDoacaoEfetivada(efetivada);

        System.out.println(
            "\n[SUCESSO] Pedido finalizado e " +
            "doação registrada no histórico."
        );
    }

    public void cancelarSolicitacao() {

        System.out.println("\n=== CANCELAR PEDIDO ===");

        if (repo.getListaSolicitacoes().isEmpty()) {

            System.out.println(
                "Nenhuma solicitação encontrada."
            );

            return;
        }

        long totalAtivas = repo.getListaSolicitacoes()
                .stream()
                .filter(s ->
                        s.getStatus() == StatusSolicitacao.APROVADA)
                .peek(s ->
                    System.out.println(
                        "ID Pedido: " + s.getId() +
                        " | Beneficiário: " +
                        s.getBeneficiario().getNome() +
                        " | Item: " +
                        s.getItem().getNomeItem() +
                        " | Quantidade: " +
                        s.getQuantidade()
                    )
                )
                .count();

        if (totalAtivas == 0) {

            System.out.println(
                "Nenhuma solicitação disponível para cancelamento."
            );

            return;
        }

        System.out.print(
                "\nInforme o ID da solicitação: "
        );

        String idSolCancelar = scanner.nextLine();

        Solicitacao solSelected =
                repo.getListaSolicitacoes()
                        .stream()
                        .filter(s ->
                                s.getId().equals(idSolCancelar))
                        .findFirst()
                        .orElse(null);

        if (solSelected == null) {

            System.out.println(
                "Solicitação não encontrada."
            );

            return;
        }

        if (solSelected.getStatus() ==
                StatusSolicitacao.ENTREGUE) {

            System.out.println(
                "Erro: pedido já entregue."
            );

            return;
        }

        solicitacaoService.cancelarSolicitacao(solSelected);

        System.out.println(
            "\nSolicitação cancelada com sucesso."
        );
    }

    public void consultarSolicitacoes() {

        System.out.println(
                "\n=== CONSULTAR SOLICITAÇÕES ==="
        );

        List<Solicitacao> lista =
                repo.getListaSolicitacoes();

        if (lista == null || lista.isEmpty()) {

            System.out.println(
                "Nenhuma solicitação cadastrada."
            );

            return;
        }

        System.out.println("\nHistórico de pedidos:");

        for (Solicitacao s : lista) {

            System.out.println(
                "ID Pedido: " + s.getId() +
                " | Beneficiário: " +
                s.getBeneficiario().getNome() +
                " | Item: " +
                s.getItem().getNomeItem() +
                " | Quantidade: " +
                s.getQuantidade() +
                " | Status: " +
                s.getStatus()
            );
        }
    }

    public void consultarDoacoesEfetivadas() {

        System.out.println(
                "\n=== CONSULTA DE DOAÇÕES EFETIVADAS ==="
        );

        List<DoacaoEfetivada> lista =
                repo.getListaDoacoesEfetivadas();

        if (lista == null || lista.isEmpty()) {

            System.out.println(
                "Nenhuma doação foi efetivada ainda."
            );

            return;
        }

        for (DoacaoEfetivada d : lista) {
            d.exibirDadosItem();
        }
    }

    private int lerNumero() {

        while (true) {

            try {

                return Integer.parseInt(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.print(
                    "Entrada inválida! Digite apenas números: "
                );
            }
        }
    }
}