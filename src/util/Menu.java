package util;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import model.Beneficiario;
import model.Doador;
import model.ItemDoacao;


public class Menu {
    public static void executarMenu() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Doador> listaDoadores = new ArrayList<>();
        ArrayList<Beneficiario> listaBeneficiarios = new ArrayList<>();
        ArrayList<ItemDoacao> listaItem = new ArrayList<>();
        
        
        int opcao = -1;
        int contadorId = 1;


        do {
                System.out.println("\n===MENU PRINCIPAL ===");
                System.out.println("1 - Cadastrar Doador");
                System.out.println("2 - Cadastrar Beneficiario");
                System.out.println("3 - Cadastrar Item"); 
                System.out.println("0 - Sair");        
                System.out.print("Escolha uma opção: ");
    
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch(opcao){
                    case 1:
                    
                        System.out.print("Informe seu Nome: ");
                        String nome = scanner.nextLine();
                        
                        System.out.print("Informe seu Telefone: ");
                        String telefone = scanner.nextLine();
                        
                        System.out.print("Informe seu Email: ");
                        String email = scanner.nextLine();
                        
                        System.out.print("Informe seu Endereço: ");
                        String endereco = scanner.nextLine();

                        Doador novoDoador = new Doador(contadorId++, nome, telefone, email, endereco);
                        listaDoadores.add(novoDoador);
                        
                        System.out.println("\n Doador cadastrado com sucesso!");
                        break;      

                        
                    case 2:               
                       
                        System.out.print("Informe seu Nome: ");
                        String nomeBeneficiario = scanner.nextLine();
                        
                        System.out.print("Informe seu Telefone: ");
                        String telefoneBeneficiario = scanner.nextLine();
                        
                        System.out.print("Informe seu Email: ");
                        String emailBeneficiario = scanner.nextLine();
                        
                        System.out.print("Informe seu Endereço: ");
                        String enderecoBeneficiario = scanner.nextLine();
                        
                        System.out.print("Informe seu Tipo de Beneficiario: ");
                        String tipoBeneficiario = scanner.nextLine();

                        System.out.print("Informe o nível de prioridade: ");
                        int nivelPrioridade = scanner.nextInt();
                        scanner.nextLine();  


                        Beneficiario novoBeneficiario = new Beneficiario(contadorId++, nomeBeneficiario, telefoneBeneficiario, emailBeneficiario, enderecoBeneficiario, tipoBeneficiario, nivelPrioridade);
                        listaBeneficiarios.add(novoBeneficiario);
                        
                        System.out.println("\n Beneficiario cadastrado com sucesso!");
                        break;      


                        case 3:               
                       
                        System.out.print("Informe nome do item: ");
                        String nomeItem = scanner.nextLine();
                        
                        System.out.print("Informe a categoria: ");
                        String categoria = scanner.nextLine();
                        
                        System.out.print("Informe a descrição do item: ");
                        String descricao = scanner.nextLine();
                        
                        System.out.print("Informe a quantidade: ");
                        int quantidade = scanner.nextInt();
                        scanner.nextLine();
                        
                        System.out.print("Informe o estado de conservação: ");
                        String estadoConservacao = scanner.nextLine();

                        LocalDate dataCadastro = LocalDate.now();

                        System.out.print("Informe o status do produto: ");
                        String status  = scanner.nextLine();


                        ItemDoacao novoItem = new ItemDoacao(contadorId++, nomeItem, categoria, descricao, quantidade, estadoConservacao, dataCadastro, status);
                        listaItem.add(novoItem);
                        
                        System.out.println("\n Item cadastrado com sucesso!");
                        break;      


                    case 0:
                        System.out.println("Saindo do sistema");
                        break;

                    default:
                        System.out.println("Opção inválida!");

                }         
        } while (opcao != 0);

        scanner.close();
    }
   
}
