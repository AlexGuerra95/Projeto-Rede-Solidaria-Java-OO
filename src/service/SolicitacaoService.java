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

            // Se a solicitação raspou o estoque por completo, o item RESERVADO
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

    //Sobre a desistência
    public boolean cancelarSolicitacao(Solicitacao solicitacao) {
        if (solicitacao == null) {
            System.out.println("❌ Erro: Solicitação não encontrada no sistema.");
            return false;
        }

        if (solicitacao.getStatus() != StatusSolicitacao.APROVADA) {
            System.out.println("❌ Erro: Esta solicitação não está ativa ou já foi modificada.");
            return false;
        }

        ItemDoacao item = solicitacao.getItem();
        
        //Devolver os itens ao estoque
        item.setQuantidade(item.getQuantidade() + solicitacao.getQuantidade());

        // atualizar o status do pedido
        solicitacao.setStatus(StatusSolicitacao.REJEITADA);
        
        System.out.println("✓ Desistência registrada com sucesso! O estoque foi devolvido à rede.");
        return true;
    }
    
}
