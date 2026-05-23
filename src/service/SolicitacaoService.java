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
            
            item.setQuantidade(item.getQuantidade() - quantidade);

           
            if (item.getQuantidade() == 0) {
                item.reservar(); 
            }

            
            solicitacao.setStatus(StatusSolicitacao.APROVADA);
            repository.registrarSolicitacao(solicitacao);
            return true;
        }

        solicitacao.setStatus(StatusSolicitacao.REJEITADA);
        repository.registrarSolicitacao(solicitacao);
        return false;
    }

    
    public boolean cancelarSolicitacao(Solicitacao solicitacao) {
        if (solicitacao == null) {
            System.out.println("Erro: Solicitação não encontrada no sistema.");
            return false;
        }

        if (solicitacao.getStatus() != StatusSolicitacao.APROVADA) {
            System.out.println("Erro: Esta solicitação não está ativa ou já foi modificada.");
            return false;
        }

        ItemDoacao item = solicitacao.getItem();
        
        
        item.setQuantidade(item.getQuantidade() + solicitacao.getQuantidade());

        
        solicitacao.setStatus(StatusSolicitacao.REJEITADA);
        
        System.out.println("✓ Desistência registrada com sucesso! O estoque foi devolvido à rede.");
        return true;
    }
    
}
