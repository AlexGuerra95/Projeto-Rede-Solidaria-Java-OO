package model;

import java.time.LocalDate;

public class TesteCicloVida {
    public static void main(String[] args) {
        System.out.println("=== TESTANDO O CICLO DE VIDA DO ITEM (SABS) ===\n");

        //Criando um item novo que ja nasce como disponivel 
        ItemDoacao item = new ItemDoacao(
            1, 
            "Cesta Básica", 
            "Alimentos não perecíveis", 
            10, 
            "Excelente", 
            LocalDate.now(), 
            StatusItem.DISPONIVEL
        );

        item.exibirDadosItem();

        // Tentando entregar direto , mas da ERRO, pois precisa reservar antes
        System.out.println("--- Tentativa 1: Entregar direto ---");
        item.entregar(); 
        
        // Reservando o item 
        System.out.println("\n--- Tentativa 2: Reservar o item ---");
        item.reservar();
        System.out.println("Status atual: " + item.getStatus());

        // Entregando o item agora que está reservado 
        System.out.println("\n--- Tentativa 3: Entregar o item reservado ---");
        item.entregar();
        System.out.println("Status atual: " + item.getStatus());

        //Tentando cancelar um item depois que ele foi entrega , precisa dar erro 
        System.out.println("\n--- Tentativa 4: Cancelar item já entregue ---");
        item.cancelar();
    }
}