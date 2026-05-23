package controller;

import java.util.List;
import java.util.Scanner;

import model.Doador;
import repository.DoacaoRepository;

public class DoadorController {
    private final DoacaoRepository repo;
    private final Scanner scanner;
    private static int contadorId = 1; // Controle de ID local ou global

    public DoadorController(DoacaoRepository repo, Scanner scanner) {
        this.repo = repo;
        this.scanner = scanner;
    }

    public void cadastrarDoador() {
        System.out.println("\n--- CADASTRAR DOADOR ---");
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("Telefone: "); String tel = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        System.out.print("Endereço: "); String end = scanner.nextLine();

        repo.salvarDoador(new Doador(contadorId++, nome, tel, email, end));
        System.out.println("Doador cadastrado com sucesso!");
    }

    public void consultarDoadores() {
        System.out.println("\n--- LISTAR DOADORES ---");
        List<Doador> lista = repo.getListaDoadores();
        if (lista.isEmpty()) {
            System.out.println("Nenhum doador cadastrado.");
        } else {
            lista.forEach(d -> System.out.println("ID: " + d.getId() + " | Nome: " + d.getNome() + " | Email: " + d.getEmail()));
        }
    }
}