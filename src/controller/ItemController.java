package controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.ItemDoacao;
import model.StatusItem;
import repository.DoacaoRepository;
import util.GeradorIds;

public class ItemController {
    private final DoacaoRepository repo;
    private final Scanner scanner;

    public ItemController(DoacaoRepository repo, Scanner scanner) {
        this.repo = repo;
        this.scanner = scanner;
    }

    public void cadastrarItem() {
        try {
            System.out.println("\n--- CADASTRAR ITEM ---");

            System.out.print("Nome do item: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) throw new IllegalArgumentException("Nome do item não pode ser vazio.");

            System.out.print("Categoria: ");
            String cat = scanner.nextLine().trim();
            if (cat.isEmpty()) throw new IllegalArgumentException("Categoria não pode ser vazia.");

            System.out.print("Descrição: ");
            String desc = scanner.nextLine().trim();
            if (desc.isEmpty()) throw new IllegalArgumentException("Descrição não pode ser vazia.");

            System.out.print("Quantidade: ");
            int qtd = lerNumero();
            if (qtd <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");

            System.out.print("Estado de Conservação: ");
            String estado = scanner.nextLine().trim();
            if (estado.isEmpty()) throw new IllegalArgumentException("Estado de conservação não pode ser vazio.");

            ItemDoacao novoItem = new ItemDoacao(
                GeradorIds.gerarIdItem(), nome, cat, desc, qtd, estado, LocalDate.now(), StatusItem.DISPONIVEL
            );
            repo.salvarItem(novoItem);
            System.out.println("Item cadastrado com sucesso e disponível na rede!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar item: " + e.getMessage());
        }
    }

    public void consultarItensFiltrados(int tipoFiltro) {
        try {
            List<ItemDoacao> filtrados;

            switch (tipoFiltro) {
                case 1:
                    filtrados = repo.filtrarPorStatus(StatusItem.DISPONIVEL);
                    break;
                case 2:
                    filtrados = repo.filtrarPorStatus(StatusItem.RESERVADO);
                    break;
                case 3:
                    filtrados = repo.getListaItens();
                    break;
                default:
                    throw new IllegalArgumentException("Opção de filtro inválida: " + tipoFiltro);
            }

            if (filtrados.isEmpty()) {
                System.out.println("Nenhum item encontrado para este filtro.");
            } else {
                for (ItemDoacao item : filtrados) {
                    item.exibirDadosItem();
                }
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        }
    }

    public void consultarItensPorCategoria() {
    try {
        System.out.print("Digite a categoria desejada: ");
        String categoria = scanner.nextLine().trim();
        if (categoria.isEmpty()) throw new IllegalArgumentException("Categoria não pode ser vazia.");

        List<ItemDoacao> filtrados = repo.filtrarPorCategoria(categoria);

        if (filtrados.isEmpty()) {
            System.out.println("Nenhum item encontrado para a categoria: " + categoria);
        } else {
            for (ItemDoacao item : filtrados) {
                item.exibirDadosItem();
            }
        }
    } catch (IllegalArgumentException e) {
        System.out.println("Erro na consulta: " + e.getMessage());
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
}
