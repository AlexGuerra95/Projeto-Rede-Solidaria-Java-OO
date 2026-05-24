package util;

import java.util.Scanner;

import controller.BeneficiarioController;
import controller.DoadorController;
import controller.ItemController;
import controller.SolicitacaoController;
import repository.DoacaoRepository;

public class Menu {

    public static void executarMenu(DoacaoRepository repo) {
        Scanner scanner = new Scanner(System.in);

        DoadorController doadorCtrl = new DoadorController(repo, scanner);
        BeneficiarioController beneficiarioCtrl = new BeneficiarioController(repo, scanner);
        ItemController itemCtrl = new ItemController(repo, scanner);
        SolicitacaoController solicitacaoCtrl = new SolicitacaoController(repo, scanner);

        int opcao = -1;

        do {
            exibirMenuPrincipal();
            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        menuDoador(scanner, doadorCtrl);
                        break;
                    case 2:
                        menuBeneficiario(scanner, beneficiarioCtrl);
                        break;
                    case 3:
                        menuItens(scanner, itemCtrl);
                        break;
                    case 4:
                        menuSolicitacoes(scanner, solicitacaoCtrl);
                        break;
                    case 0:
                        System.out.println("Encerrando o sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite apenas números.");
            }
        } while (opcao != 0);
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1 - Gerenciar Doador");
        System.out.println("2 - Gerenciar Beneficiário");
        System.out.println("3 - Gerenciar Itens");
        System.out.println("4 - Gestão de Solicitações");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void menuItens(Scanner sc, ItemController ctrl) {
        System.out.println("\n--- GERENCIAR ITENS ---");
        System.out.println("1 - Cadastrar Novo Item");
        System.out.println("2 - Consultar Itens Cadastrados");
        System.out.print("Escolha: ");
        try {
            int sub = Integer.parseInt(sc.nextLine());
            if (sub == 1) {
                ctrl.cadastrarItem();
            } else if (sub == 2) {
                menuConsultaFiltros(sc, ctrl);
            } else {
                System.out.println("Opção inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido.");
        }
    }

    private static void menuConsultaFiltros(Scanner sc, ItemController ctrl) {
        System.out.println("\nCONSULTAR ITENS");
        System.out.println("1 - Apenas Disponíveis");
        System.out.println("2 - Apenas Reservados");
        System.out.println("3 - Filtrar por Categoria");
        System.out.println("4 - Todos");
        System.out.print("Escolha o tipo de listagem: ");
        try {
            int filtro = Integer.parseInt(sc.nextLine());
            if (filtro == 3) {
                ctrl.consultarItensPorCategoria();
            } else {
                ctrl.consultarItensFiltrados(filtro == 4 ? 3 : filtro);
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido.");
        }
    }

    private static void menuDoador(Scanner sc, DoadorController ctrl) {
        System.out.println("\nOpções do Doador:\n1 - Cadastrar Doador | 2 - Consultar Doadores");
        System.out.print("Escolha: ");
        try {
            int sub = Integer.parseInt(sc.nextLine());
            if (sub == 1) ctrl.cadastrarDoador();
            else if (sub == 2) ctrl.consultarDoadores();
            else System.out.println("Opção inválida.");
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido.");
        }
    }

    private static void menuBeneficiario(Scanner sc, BeneficiarioController ctrl) {
        System.out.println("\nOpções do Beneficiário:");
        System.out.println("1 - Cadastrar Beneficiário | 2 - Consultar Beneficiário");
        System.out.print("Escolha: ");
        try {
            int sub = Integer.parseInt(sc.nextLine());
            if (sub == 1) ctrl.cadastrarBeneficiario();
            else if (sub == 2) ctrl.consultarBeneficiarios();
            else System.out.println("Opção inválida.");
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido.");
        }
    }

    private static void menuSolicitacoes(Scanner sc, SolicitacaoController ctrl) {
        System.out.println("\n--- GESTÃO DE SOLICITAÇÕES ---");
        System.out.println("1 - Criar Nova Solicitação");
        System.out.println("2 - Efetivar / Entregar Solicitação");
        System.out.println("3 - Cancelar Pedido");
        System.out.println("4 - Consultar Solicitações");
        System.out.println("5 - Consultar Doações Efetivadas");
        System.out.print("Escolha: ");

        try {
            int sub = Integer.parseInt(sc.nextLine());
            switch (sub) {
                case 1:
                    ctrl.criarSolicitacao();
                    break;
                case 2:
                    ctrl.efetivarSolicitacao();
                    break;
                case 3:
                    ctrl.cancelarSolicitacao();
                    break;
                case 4:
                    menuConsultaSolicitacoes(sc, ctrl);
                    break;
                case 5:
                    ctrl.consultarDoacoesEfetivadas();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número.");
        }
    }

    private static void menuConsultaSolicitacoes(Scanner sc, SolicitacaoController ctrl) {
        System.out.println("\nCONSULTAR SOLICITAÇÕES");
        System.out.println("1 - Todas");
        System.out.println("2 - Apenas Aprovadas");
        System.out.println("3 - Entregues / Canceladas");
        System.out.print("Escolha o tipo de listagem: ");
        try {
            int filtro = Integer.parseInt(sc.nextLine());
            ctrl.consultarSolicitacoesFiltradas(filtro);
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido.");
        }
    }
}
