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
        boolean valido = validacaoService.validarDisponibilidade(item, quantidade);

        if(valido){
            
           // Caso o beneficiário compre tudo, vira "reservado" e não indisponível.
            if (item.getQuantidade() == quantidade) {
                item.reservar(); 
            }

            // Depois de verificar se reserva, deduzir estoque
            item.setQuantidade(item.getQuantidade() - quantidade);

            solicitacao.setStatus(StatusSolicitacao.APROVADA);
            repository.registrarSolicitacao(solicitacao);

            return true;
        }

        // Se o ValidacaoService disser que não dá, a solicitação é rejeitada
        solicitacao.setStatus(StatusSolicitacao.REJEITADA);
        repository.registrarSolicitacao(solicitacao);

        return false;
    }
}
