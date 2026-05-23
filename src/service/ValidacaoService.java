package service;

import model.ItemDoacao;
import model.StatusItem;

public class ValidacaoService {

  
    public boolean validarDisponibilidade(ItemDoacao item, int quantidadeSolicitada) {
        
        if (item == null) {
            System.out.println("X ERRO: Item solicitado não encontrado.");
            return false;
        }

        if (item.getStatus() != StatusItem.DISPONIVEL) {
            System.out.println("X ERRO: Este item não está disponível para pedidos. Status atual: " + item.getStatus());
            return false;
        }

  
        if (item.getQuantidade() <= 0) {
            System.out.println("X ERRO: O estoque deste item está esgotado.");
            return false;
        }

   
        if (quantidadeSolicitada > item.getQuantidade()) {
            System.out.println("X ERRO: Quantidade solicitada (" + quantidadeSolicitada + 
                               ") é maior que o estoque em rede (" + item.getQuantidade() + ").");
            return false;
        }

     
        if (quantidadeSolicitada <= 0) {
            System.out.println("X ERRO: A quantidade do pedido deve ser maior que zero.");
            return false;
        }

        return true;
    }
}
