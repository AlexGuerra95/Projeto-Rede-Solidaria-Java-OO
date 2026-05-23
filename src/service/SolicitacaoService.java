package service;

import model.ItemDoacao;
import model.Solicitacao;
import model.StatusSolicitacao;
import model.StatusItem;
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
        
        // Executa o seu validador
        boolean valido = validacaoService.validarDisponibilidade(item, quantidade);

        if(valido){
            // Deduz do estoque do item pai normalmente
            item.setQuantidade(item.getQuantidade() - quantidade);

            // Se a solicitação raspou o estoque por completo, o item pai vira RESERVADO
            if (item.getQuantidade() == 0) {
                item.reservar(); 
            }

            // Salva o registro da reserva na solicitação
            solicitacao.setStatus(StatusSolicitacao.APROVADA);
            repository.registrarSolicitacao(solicitacao);
            return true;
        }

        solicitacao.setStatus(StatusSolicitacao.REJEITADA);
        repository.registrarSolicitacao(solicitacao);
        return false;
    }
}
