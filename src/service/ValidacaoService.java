package service;

import model.ItemDoacao;
import model.StatusItem;

public class ValidacaoService {

    public void validarDisponibilidade(ItemDoacao item, int quantidadeSolicitada) {

        if (item == null) {
            throw new IllegalArgumentException("Item solicitado não encontrado.");
        }

        if (item.getStatus() != StatusItem.DISPONIVEL) {
            throw new IllegalStateException(
                "Este item não está disponível para pedidos. Status atual: " + item.getStatus()
            );
        }

        if (item.getQuantidade() <= 0) {
            throw new IllegalStateException("O estoque deste item está esgotado.");
        }

        if (quantidadeSolicitada <= 0) {
            throw new IllegalArgumentException("A quantidade solicitada deve ser maior que zero.");
        }

        if (quantidadeSolicitada > item.getQuantidade()) {
            throw new IllegalArgumentException(
                "Quantidade solicitada (" + quantidadeSolicitada +
                ") é maior que o estoque disponível (" + item.getQuantidade() + ")."
            );
        }
    }
}
