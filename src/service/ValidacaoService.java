package service;

import model.ItemDoacao;
import model.StatusItem;

public class ValidacaoService {

  //Validação pra ver se o item pode receber uma solicitação com base no estoque e ciclo de vida.
  
    public boolean validarDisponibilidade(ItemDoacao item, int quantidadeSolicitada) {
        
        if (item == null) {
            System.out.println("X ERRO: Item solicitado não encontrado.");
            return false;
        }

        //Verificação 1: verifica se tá disponível.
        if (item.getStatus() != StatusItem.DISPONIVEL) {
            System.out.println("X ERRO: Este item não está disponível para pedidos. Status atual: " + item.getStatus());
            return false;
        }

        //Verificação 2: Impede solicitações de itens com estoque zerado.
        if (item.getQuantidade() <= 0) {
            System.out.println("X ERRO: O estoque deste item está esgotado.");
            return false;
        }

        //Verificação 3: Garante que a quantidade pedida cabe no estoque disponível
        if (quantidadeSolicitada > item.getQuantidade()) {
            System.out.println("X ERRO: Quantidade solicitada (" + quantidadeSolicitada + 
                               ") é maior que o estoque em rede (" + item.getQuantidade() + ").");
            return false;
        }

        //Verificação 4: Evita quantidades inválidas (zero ou negativas)
        if (quantidadeSolicitada <= 0) {
            System.out.println("X ERRO: A quantidade do pedido deve ser maior que zero.");
            return false;
        }

        return true;
    }
}
