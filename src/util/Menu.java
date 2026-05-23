package util;


import java.time.LocalDate;
import java.util.Scanner;

import model.Beneficiario;
import model.Doador;
import model.ItemDoacao;
import model.Solicitacao;
import model.StatusItem;
import repository.DoacaoRepository;
import service.SolicitacaoService;
import service.ValidacaoService;


public class Menu {
    public static void executarMenu(DoacaoRepository repo) {
        Scanner scanner = new Scanner(System.in);

        
        int opcao = -1;
        int contadorId = 1;


        do {
                System.out.println("\n===MENU PRINCIPAL ===");
                System.out.println("1 - Cadastrar Doador");
                System.out.println("2 - Cadastrar Beneficiario");
                System.out.println("3 - Cadastrar Item");
                System.out.println("4 - Listar Itens Disponíveis");
                System.out.println("5 - Efetuar Solicitação");
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
                        repo.salvarDoador(novoDoador);
                        
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
                        repo.salvarBeneficiario(novoBeneficiario);
                        
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

                        StatusItem statusInicial = StatusItem.DISPONIVEL;

                        ItemDoacao novoItem = new ItemDoacao(contadorId++, nomeItem, categoria, descricao, quantidade, estadoConservacao, dataCadastro, statusInicial);
repo.salvarItem(novoItem); 
                        
                        System.out.println("\n Item cadastrado com sucesso e disponível na rede!");
                        break;     

                case 4:
                    System.out.println("CONSULTAR ITENS");
                    System.out.println("1 - Apenas Itens Disponíveis");
                    System.out.println("2 - Apenas Itens Reservados");
                    System.out.println("3 - Todos os Itens Cadastrados");
                    System.out.print("Escolha o tipo de listagem: ");
                    
                    int subOpcao = scanner.nextInt();
                    scanner.nextLine();
                    
                    java.util.List<ItemDoacao> itensFiltrados = null;
                    
                    switch (subOpcao) {
                        case 1:
                            System.out.println("Exibindo Itens Disponíveis");
                            // Usa seu método de filtro com o Enum da Sabryna
                            itensFiltrados = repo.filtrarPorStatus(StatusItem.DISPONIVEL);
                            break;
                        case 2:
                            System.out.println("Exibindo Itens Reservados");
                            itensFiltrados = repo.filtrarPorStatus(StatusItem.RESERVADO);
                            break;
                        case 3:
                            System.out.println("Exibindo Todos os Itens");
                            // Puxa a lista global completa do repositório
                            itensFiltrados = repo.getListaItens();
                            break;
                        default:
                            System.out.println("X Opção de filtro inválida!");
                            break;
                    }
                    
                    // Faz a listagem de acordo com a escolha.
                    if (itensFiltrados != null) {
                        if (itensFiltrados.isEmpty()) {
                            System.out.println("Nenhum item encontrado para o filtro selecionado.");
                        } else {
                            for (ItemDoacao item : itensFiltrados) {
                                item.exibirDadosItem();
                            }
                        }
                    }
                    break;
                    

                        case 5:

                        ValidacaoService validacaoService =
                            new ValidacaoService();
                    
                        SolicitacaoService solicitacaoService =
                            new SolicitacaoService(
                                validacaoService,
                                repo
                            );
                    
                        System.out.println(
                            "\n=== SOLICITAÇÃO DE ITEM ==="
                        );
                    
                        if(repo.getListaItens().isEmpty()){
                    
                            System.out.println(
                                "Nenhum item disponível."
                            );
                    
                            break;
                        }
                    
                        for(ItemDoacao i : repo.getListaItens()){
                    
                            System.out.println(
                                i.getId()
                                + " - "
                                + i.getNomeItem()
                                + " | Quantidade: "
                                + i.getQuantidade()
                                + " | Status: "
                                + i.getStatus()
                            );
                        }
                    
                        System.out.print(
                            "\nInforme o ID do item: "
                        );
                    
                        int idItem = scanner.nextInt();
                        scanner.nextLine();
                    
                        ItemDoacao itemSelecionado =
                            repo.buscarItemPorId(idItem);
                    
                        if(itemSelecionado == null){
                    
                            System.out.println(
                                "Item não encontrado."
                            );
                    
                            break;
                        }
                    
                        System.out.print(
                            "Informe a quantidade solicitada: "
                        );
                    
                        int quantidadeSolicitada =
                            scanner.nextInt();
                    
                        scanner.nextLine();
                    
                        System.out.print(
                            "Informe a justificativa: "
                        );
                    
                        String justificativa =
                            scanner.nextLine();
                    
                           
                        System.out.print(
                                "Informe o ID do beneficiário: "
                            );
                            
                            int idBeneficiario =
                                scanner.nextInt();
                            
                            scanner.nextLine();
                            
                            Beneficiario beneficiario =
                                repo.buscarBeneficiarioPorId(
                                    idBeneficiario
                                );
                            
                            if(beneficiario == null){
                            
                                System.out.println(
                                    "Beneficiário não encontrado."
                                );
                            
                                break;
                            }


                            Solicitacao solicitacao =
                            new Solicitacao(
                                contadorId++,
                                beneficiario,
                                itemSelecionado,
                                quantidadeSolicitada,
                                justificativa
                            );
                    
                        boolean aprovado =
                            solicitacaoService.solicitarItem(
                                solicitacao
                            );
                    
                        if(aprovado){
                    
                            System.out.println(
                                "\n✓ Solicitação aprovada!"
                            );
                    
                            System.out.println(
                                "Doação concluída com sucesso."
                            );
                    
                            System.out.println(
                                "Quantidade restante: "
                                + itemSelecionado.getQuantidade()
                            );
                    
                        } else {
                    
                            System.out.println(
                                "\nX Solicitação rejeitada."
                            );
                        }
                    
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
