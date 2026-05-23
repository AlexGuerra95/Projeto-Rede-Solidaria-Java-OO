package controller;

import java.util.List;
import java.util.Scanner;

import model.Beneficiario;
import repository.DoacaoRepository;
import util.GeradorIds;

public class BeneficiarioController {
    private final DoacaoRepository repo;
    private final Scanner scanner;

    public BeneficiarioController(DoacaoRepository repo, Scanner scanner) {
        this.repo = repo;
        this.scanner = scanner;
    }

    public void cadastrarBeneficiario() {
        try {
            System.out.println("\n--- CADASTRAR BENEFICIÁRIO ---");

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

            System.out.print("Tipo de Beneficiário (Ex: Família, Instituição): ");
            String tipo = scanner.nextLine().trim();
            if (tipo.isEmpty()) throw new IllegalArgumentException("Tipo de beneficiário não pode ser vazio.");

            System.out.print("Nível de Prioridade (número inteiro): ");
            int prioridade = lerNumero();
            if (prioridade < 0) throw new IllegalArgumentException("Nível de prioridade não pode ser negativo.");

            repo.salvarBeneficiario(new Beneficiario(
                GeradorIds.gerarIdBeneficiario(), nome, tel, email, end, tipo, prioridade
            ));
            System.out.println("Beneficiário cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar beneficiário: " + e.getMessage());
        }
    }

    public void consultarBeneficiarios() {
        try {
            System.out.println("\n--- LISTAR BENEFICIÁRIOS ---");
            List<Beneficiario> lista = repo.getListaBeneficiarios();
            if (lista.isEmpty()) {
                System.out.println("Nenhum beneficiário cadastrado.");
            } else {
                lista.forEach(b -> System.out.println(
                    "ID: " + b.getId() +
                    " | Nome: " + b.getNome() +
                    " | Tipo: " + b.getTipoBeneficiario() +
                    " | Prioridade: " + b.getNivelPrioridade()
                ));
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar beneficiários: " + e.getMessage());
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
