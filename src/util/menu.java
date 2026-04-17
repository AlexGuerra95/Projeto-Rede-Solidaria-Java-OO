import java.util.Scanner;
import java.util.ArrayList;

public class MenuSwitchCase {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
    do {
            // Exibe o menu
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1 - Cadastrar Doador");
            System.out.println("2 - Cadastrar Beneficiario");
            System.out.println("3 - Cadastrar Item");          
            System.out.print("Escolha uma opção: ");
   
            opcao = scanner.nextInt();

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

                    Doador novoDoador = new Doador(, nome, telefone, email, endereco);
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

                    Beneficiario novoBeneficiario = new Beneficiario(, nome, telefone, email, endereco);
                    listaDoadores.add(novoBeneficiario);
                    
                    System.out.println("\n Beneficiario cadastrado com sucesso!");
                    break;      
   





                    
        }
   
    }
   
}