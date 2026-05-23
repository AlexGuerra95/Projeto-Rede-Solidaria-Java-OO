package controller;

import java.util.List;
import java.util.Scanner;

import model.Doador;
import repository.DoacaoRepository;
import util.GeradorIds;

public class DoadorController {
    private final DoacaoRepository repo;
    private final Scanner scanner;

    public DoadorController(DoacaoRepository repo, Scanner scanner) {
        this.repo = repo;
        this.scanner = scanner;
    }

    public void cadastrarDoador() {
        try {
            System.out.println("\n--- CADASTRAR DOADOR ---");

            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) throw new IllegalArgumentException("Nome não pode ser vazio.");

            System.out.print("Telefone: ");
            String tel = scanner.nextLine().trim();
            if (tel.isEmpty()) throw new IllegalArgumentException("Telefone não pode ser vazio.");

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) throw new IllegalArgumentException("Email não pode ser vazio.");

            System.out.print("Endereço: ");
            String end = scanner.nextLine().trim();
            if (end.isEmpty()) throw new IllegalArgumentException("Endereço não pode ser vazio.");

            repo.salvarDoador(new Doador(GeradorIds.gerarIdDoador(), nome, tel, email, end));
            System.out.println("Doador cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar doador: " + e.getMessage());
        }
    }

    public void consultarDoadores() {
        try {
            System.out.println("\n--- LISTAR DOADORES ---");
            List<Doador> lista = repo.getListaDoadores();
            if (lista.isEmpty()) {
                System.out.println("Nenhum doador cadastrado.");
            } else {
                lista.forEach(d -> System.out.println(
                    "ID: " + d.getId() + " | Nome: " + d.getNome() + " | Email: " + d.getEmail()
                ));
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar doadores: " + e.getMessage());
        }
    }
}
