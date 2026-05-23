package service;

import model.ItemDoacao;
import model.Solicitacao;
import model.StatusSolicitacao;
import repository.DoacaoRepository;

public class SolicitacaoService {

    private final ValidacaoService validacaoService;
    private final DoacaoRepository repository;

    public SolicitacaoService(ValidacaoService validacaoService, DoacaoRepository repository){
        this.validacaoService = validacaoService;
        this.repository = repository;
    }

    public boolean solicitarItem(Solicitacao solicitacao){
        ItemDoacao item = solicitacao.getItem();
        int quantidade = solicitacao.getQuantidade();
        
        // Passa pela validação
        boolean valido = validacaoService.validarDisponibilidade(item, quantidade);

        if(valido){
            // Deduz do estoque
            item.setQuantidade(item.getQuantidade() - quantidade);

            // Se a pessoa zerar o estoque, muda o status geral
            if (item.getQuantidade() == 0) {
                item.reservar(); 
            }

            // reserva parcial
            solicitacao.setStatus(StatusSolicitacao.APROVADA);
            repository.registrarSolicitacao(solicitacao);
            
            System.out.println("✓ Pedido aprovado! A quantidade foi separada para o beneficiário.");
            return true;
        }

        solicitacao.setStatus(StatusSolicitacao.REJEITADA);
        repository.registrarSolicitacao(solicitacao);
        return false;
    }
}
