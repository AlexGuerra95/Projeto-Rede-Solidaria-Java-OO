package controller;

import java.util.List;
import java.util.Scanner;
import model.Beneficiario;
import repository.DoacaoRepository;

public class BeneficiarioController {
    private final DoacaoRepository repo;
    private final Scanner scanner;
    private static int contadorId = 1;

    public BeneficiarioController(DoacaoRepository repo, Scanner scanner) {
        this.repo = repo;
        this.scanner = scanner;
    }

    public void cadastrarBeneficiario() {
        System.out.println("\n--- CADASTRAR BENEFICIÁRIO ---");
        System.out.print("Nome: "); 
        String nome = scanner.nextLine();
        
        System.out.print("Telefone: "); 
        String tel = scanner.nextLine();
        
        System.out.print("Email: "); 
        String email = scanner.nextLine();
        
        System.out.print("Endereço: "); 
        String end = scanner.nextLine();
        
        System.out.print("Tipo de Beneficiário (Ex: Família, Instituição): "); 
        String tipo = scanner.nextLine();
        
        System.out.print("Nível de Prioridade (Digite um número inteiro): "); 
        int prioridade = lerNumero();

        // Agora com todos os 7 parâmetros preenchidos corretamente:
        Beneficiario novoBeneficiario = new Beneficiario(contadorId++, nome, tel, email, end, tipo, prioridade);
        repo.salvarBeneficiario(novoBeneficiario);
        
        System.out.println("Beneficiário cadastrado com sucesso!");
    }

    public void consultarBeneficiarios() {
        System.out.println("\n--- LISTAR BENEFICIÁRIOS ---");
        List<Beneficiario> lista = repo.getListaBeneficiarios();
        if (lista.isEmpty()) {
            System.out.println("Nenhum beneficiário cadastrado.");
        } else {
            lista.forEach(b -> System.out.println("ID: " + b.getId() + " | Nome: " + b.getNome() + " | Tipo: " + b.getTipoBeneficiario() + " | Prioridade: " + b.getNivelPrioridade()));
        }
    }

    /**
     * Método interno para garantir que a prioridade seja digitada apenas como número
     */
    private int lerNumero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida! Digite apenas números inteiros para a prioridade: ");
            }
        }
    }
}