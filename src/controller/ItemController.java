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
        System.out.println("\n--- CADASTRAR ITEM ---");
        System.out.print("Nome do item: "); String nome = scanner.nextLine();
        System.out.print("Categoria: "); String cat = scanner.nextLine();
        System.out.print("Descrição: "); String desc = scanner.nextLine();
        System.out.print("Quantidade: "); int qtd = Integer.parseInt(scanner.nextLine());
        System.out.print("Estado de Conservação: "); String estado = scanner.nextLine();

        ItemDoacao novoItem = new ItemDoacao(GeradorIds.gerarIdItem(), nome, cat, desc, qtd, estado, LocalDate.now(), StatusItem.DISPONIVEL);
        repo.salvarItem(novoItem);
        System.out.println("Item cadastrado com sucesso e disponível na rede!");
    }

    public void consultarItensFiltrados(int tipoFiltro) {
        List<ItemDoacao> filtrados = switch (tipoFiltro) {
            case 1 -> repo.filtrarPorStatus(StatusItem.DISPONIVEL);
            case 2 -> repo.filtrarPorStatus(StatusItem.RESERVADO);
            case 3 -> repo.getListaItens();
            default -> null;
        };

        if (filtrados == null) {
            System.out.println("Opção de filtro inválida.");
            return;
        }
        if (filtrados.isEmpty()) {
            System.out.println("Nenhum item encontrado.");
        } else {
            filtrados.forEach(ItemDoacao::exibirDadosItem);
        }
    }
}
