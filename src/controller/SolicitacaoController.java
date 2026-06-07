package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Beneficiario;
import model.DoacaoEfetivada;
import model.Doador;
import model.ItemDoacao;
import model.Solicitacao;
import model.StatusItem;
import model.StatusSolicitacao;
import repository.DoacaoRepository;
import service.SolicitacaoService;
import service.ValidacaoService;
import util.GeradorIds;

public class SolicitacaoController {

    private final DoacaoRepository repo;
    private final SolicitacaoService solicitacaoService;
    private final Scanner scanner;

    public SolicitacaoController(DoacaoRepository repo, Scanner scanner) {
        this.repo = repo;
        this.scanner = scanner;
        ValidacaoService validacaoService = new ValidacaoService();
        this.solicitacaoService = new SolicitacaoService(validacaoService, repo);
    }

    public void criarSolicitacao() {
        try {
            System.out.println("\n=== NOVA SOLICITAÇÃO DE ITEM ===");

            System.out.println("\nBeneficiários cadastrados:");
            repo.getListaBeneficiarios().forEach(b ->
                System.out.println("ID: " + b.getId() + " | Nome: " + b.getNome())
            );

            System.out.print("\nInforme o ID do beneficiário: ");
            String idBeneficiario = formatarId("BEN", scanner.nextLine());
            if (idBeneficiario.isEmpty()) throw new IllegalArgumentException("ID do beneficiário não pode ser vazio.");

            Beneficiario beneficiario = repo.buscarBeneficiarioPorId(idBeneficiario);
            if (beneficiario == null) throw new IllegalArgumentException("Beneficiário não encontrado com ID: " + idBeneficiario);

            List<ItemDoacao> disponiveis = new ArrayList<>();
            for (ItemDoacao item : repo.getListaItens()) {
                if (item.getStatus() == StatusItem.DISPONIVEL) {
                    disponiveis.add(item);
                }
            }

            if (disponiveis.isEmpty()) throw new IllegalStateException("Nenhum item disponível no momento.");

            System.out.println("\nItens disponíveis:");
            for (ItemDoacao item : disponiveis) {
                System.out.println(
                    "ID: " + item.getId() +
                    " | Nome: " + item.getNomeItem() +
                    " | Categoria: " + item.getCategoria() +
                    " | Qtd: " + item.getQuantidade() +
                    " | Estado: " + item.getEstadoConservacao()
                );
            }

            System.out.print("\nInforme o ID do item desejado: ");
            String idItem = formatarId("ITE", scanner.nextLine());
            if (idItem.isEmpty()) throw new IllegalArgumentException("ID do item não pode ser vazio.");

            ItemDoacao itemSelecionado = null;
            for (ItemDoacao item : disponiveis) {
                if (item.getId().equalsIgnoreCase(idItem)) {
                    itemSelecionado = item;
                    break;
                }
            }
            if (itemSelecionado == null) throw new IllegalArgumentException("Item não encontrado ou não disponível com ID: " + idItem);

            System.out.print("Informe a quantidade necessária: ");
            int quantidadeSolicitada = lerNumero();

            System.out.print("Informe a justificativa do pedido: ");
            String justificativa = scanner.nextLine().trim();
            if (justificativa.isEmpty()) throw new IllegalArgumentException("Justificativa não pode ser vazia.");

            Solicitacao solicitacao = new Solicitacao(
                GeradorIds.gerarIdSolicitacao(),
                beneficiario,
                itemSelecionado,
                quantidadeSolicitada,
                justificativa
            );

            solicitacaoService.solicitarItem(solicitacao);

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro na solicitação: " + e.getMessage());
        }
    }

    public void efetivarSolicitacao() {
        try {
            System.out.println("\n=== EFETIVAR / ENTREGAR SOLICITAÇÃO ===");

            List<Solicitacao> abertas = new ArrayList<>();
            for (Solicitacao s : repo.getListaSolicitacoes()) {
                if (s.getStatus() == StatusSolicitacao.APROVADA) {
                    abertas.add(s);
                }
            }

            if (abertas.isEmpty()) throw new IllegalStateException("Nenhuma solicitação aprovada aguardando entrega.");

            System.out.println("\nPedidos aprovados:");
            for (Solicitacao s : abertas) {
                System.out.println(
                    "ID Pedido: " + s.getId() +
                    " | Beneficiário: " + s.getBeneficiario().getNome() +
                    " | Item: " + s.getItem().getNomeItem() +
                    " | Quantidade: " + s.getQuantidade()
                );
            }

            System.out.print("\nDigite o ID do pedido que deseja efetivar: ");
            String idPedido = formatarId("SOL", scanner.nextLine());
            if (idPedido.isEmpty()) throw new IllegalArgumentException("ID do pedido não pode ser vazio.");

            Solicitacao sol = null;
            for (Solicitacao s : abertas) {
                if (s.getId().equals(idPedido)) {
                    sol = s;
                    break;
                }
            }
            if (sol == null) throw new IllegalArgumentException("Pedido não encontrado com ID: " + idPedido);

            System.out.println("\nDoadores cadastrados:");
            repo.getListaDoadores().forEach(d ->
                System.out.println("ID: " + d.getId() + " | Nome: " + d.getNome())
            );

            System.out.print("\nInforme o ID do doador deste item: ");
            String idDoador = formatarId("DOA", scanner.nextLine());
            if (idDoador.isEmpty()) throw new IllegalArgumentException("ID do doador não pode ser vazio.");

            Doador doador = repo.buscarDoadorPorId(idDoador);
            if (doador == null) throw new IllegalArgumentException("Doador não encontrado com ID: " + idDoador);

            sol.setStatus(StatusSolicitacao.ENTREGUE);

            // Garante a transição correta do item: DISPONIVEL → RESERVADO → ENTREGUE
            if (sol.getItem().getStatus() == StatusItem.DISPONIVEL) {
                sol.getItem().reservar();
            }
            sol.getItem().entregar();

            DoacaoEfetivada efetivada = new DoacaoEfetivada(
                GeradorIds.gerarIdDoacao(),
                sol.getItem(),
                doador,
                sol.getBeneficiario(),
                sol.getQuantidade(),
                LocalDate.now(),
                "Doação entregue com sucesso. Justificativa original: " + sol.getJustificativa()
            );

            repo.salvarDoacaoEfetivada(efetivada);
            System.out.println("\n[SUCESSO] Pedido finalizado e doação registrada no histórico.");

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro ao efetivar entrega: " + e.getMessage());
        }
    }

    public void cancelarSolicitacao() {
        try {
            System.out.println("\n=== CANCELAR PEDIDO ===");

            List<Solicitacao> aprovadas = new ArrayList<>();
            for (Solicitacao s : repo.getListaSolicitacoes()) {
                if (s.getStatus() == StatusSolicitacao.APROVADA) {
                    aprovadas.add(s);
                }
            }

            if (aprovadas.isEmpty()) throw new IllegalStateException("Nenhuma solicitação disponível para cancelamento.");

            for (Solicitacao s : aprovadas) {
                System.out.println(
                    "ID Pedido: " + s.getId() +
                    " | Beneficiário: " + s.getBeneficiario().getNome() +
                    " | Item: " + s.getItem().getNomeItem() +
                    " | Quantidade: " + s.getQuantidade()
                );
            }

            System.out.print("\nInforme o ID da solicitação: ");
            String idSolCancelar = formatarId("SOL", scanner.nextLine());
            if (idSolCancelar.isEmpty()) throw new IllegalArgumentException("ID da solicitação não pode ser vazio.");

            Solicitacao solSelected = null;
            for (Solicitacao s : repo.getListaSolicitacoes()) {
                if (s.getId().equals(idSolCancelar)) {
                    solSelected = s;
                    break;
                }
            }
            if (solSelected == null) throw new IllegalArgumentException("Solicitação não encontrada com ID: " + idSolCancelar);

            if (solSelected.getStatus() == StatusSolicitacao.ENTREGUE) {
                throw new IllegalStateException("Pedido já entregue, não pode ser cancelado.");
            }

            solicitacaoService.cancelarSolicitacao(solSelected);

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro ao cancelar pedido: " + e.getMessage());
        }
    }

    public void consultarSolicitacoes() {
        try {
            System.out.println("\n=== CONSULTAR SOLICITAÇÕES ===");

            List<Solicitacao> lista = repo.getListaSolicitacoes();

            if (lista == null || lista.isEmpty()) {
                System.out.println("Nenhuma solicitação cadastrada.");
                return;
            }

            for (Solicitacao s : lista) {
                System.out.println(
                    "ID Pedido: " + s.getId() +
                    " | Beneficiário: " + s.getBeneficiario().getNome() +
                    " | Item: " + s.getItem().getNomeItem() +
                    " | Quantidade: " + s.getQuantidade() +
                    " | Status: " + s.getStatus()
                );
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar solicitações: " + e.getMessage());
        }
    }

    public void consultarSolicitacoesFiltradas(int tipoFiltro) {
        try {
            System.out.println("\n=== CONSULTAR SOLICITAÇÕES ===");

            List<Solicitacao> filtradas = new ArrayList<>();

            switch (tipoFiltro) {
                case 1:
                    filtradas = repo.getListaSolicitacoes();
                    break;
                case 2:
                    for (Solicitacao s : repo.getListaSolicitacoes()) {
                        if (s.getStatus() == StatusSolicitacao.APROVADA) filtradas.add(s);
                    }
                    break;
                case 3:
                    for (Solicitacao s : repo.getListaSolicitacoes()) {
                        if (s.getStatus() == StatusSolicitacao.ENTREGUE
                                || s.getStatus() == StatusSolicitacao.CANCELADA) filtradas.add(s);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Opção de filtro inválida: " + tipoFiltro);
            }

            if (filtradas.isEmpty()) {
                System.out.println("Nenhuma solicitação encontrada para este filtro.");
                return;
            }

            for (Solicitacao s : filtradas) {
                System.out.println(
                    "ID Pedido: " + s.getId() +
                    " | Beneficiário: " + s.getBeneficiario().getNome() +
                    " | Item: " + s.getItem().getNomeItem() +
                    " | Quantidade: " + s.getQuantidade() +
                    " | Status: " + s.getStatus()
                );
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        }
    }

    public void consultarDoacoesEfetivadas() {
        try {
            System.out.println("\n=== CONSULTA DE DOAÇÕES EFETIVADAS ===");

            List<DoacaoEfetivada> lista = repo.getListaDoacoesEfetivadas();

            if (lista == null || lista.isEmpty()) {
                System.out.println("Nenhuma doação foi efetivada ainda.");
                return;
            }

            for (DoacaoEfetivada d : lista) {
                d.exibirDadosItem();
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar doações efetivadas: " + e.getMessage());
        }
    }

    private int lerNumero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida! Digite apenas números: ");
            }
        }
    }

    private String formatarId(String prefixo, String entrada) {

        entrada = entrada.trim().toUpperCase();
    
        // Se digitou apenas número
        if (!entrada.startsWith(prefixo + "-")) {
            int numero = Integer.parseInt(entrada);
            return String.format(prefixo + "-%03d", numero);
        }
    
        return entrada;
    }



}
