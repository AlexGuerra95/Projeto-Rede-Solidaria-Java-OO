package util;

import java.util.Scanner;
import java.util.ArrayList;
import model.Doador;
import model.Beneficiario;
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
                        String nome = scanner.nextLine();
                        
                        System.out.print("Informe seu Telefone: ");
                        String telefone = scanner.nextLine();
                        
                        System.out.print("Informe seu Email: ");
                        String email = scanner.nextLine();
                        
                        System.out.print("Informe seu Endereço: ");
                        String endereco = scanner.nextLine();
                        
                        System.out.print("Informe seu Tipo de Beneficiario: ");
                        String tipoBeneficiario = scanner.nextLine();


                        Beneficiario novoBeneficiario = new Beneficiario(contadorId++, nome, telefone, email, endereco, tipoBeneficiario);
                        listaBeneficiarios.add(novoBeneficiario);
                        
                        System.out.println("\n Beneficiario cadastrado com sucesso!");
                        break;      


                        case 3:               
                       
                        System.out.print("Informe nome do item: ");
                        String nome = scanner.nextLine();
                        
                        System.out.print("Informe a categoria: ");
                        String categoria = scanner.nextLine();
                        
                        System.out.print("Informe a descrição do item: ");
                        String descricao = scanner.nextLine();
                        
                        System.out.print("Informe a quantidade: ");
                        Int quantidade = scanner.nextLine();
                        
                        System.out.print("Informe o estado de conservação: ");
                        String estado = scanner.nextLine();

                        System.out.print("Informe a data do cadastro: ");
                        Date data = scanner.nextLine();

                        System.out.print("Informe o status do produto: ");
                        String status  = scanner.nextLine();


                        ItemDoacao novoItem = new itemDoacao(contadorId++, nome, categoria, descricao, quantidade, estado, data, status);
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
