package service;

import model.ItemDoacao;
import model.Solicitacao;
import model.StatusSolicitacao;
import repository.DoacaoRepository;

public class SolicitacaoService {

    private final ValidacaoService validacaoService;
    private final DoacaoRepository repository;

    public SolicitacaoService(ValidacaoService validacaoService, DoacaoRepository repository) {
        this.validacaoService = validacaoService;
        this.repository = repository;
    }

    public void solicitarItem(Solicitacao solicitacao) {
        if (solicitacao == null) {
            throw new IllegalArgumentException("Solicitação inválida.");
        }

        ItemDoacao item = solicitacao.getItem();
        int quantidade = solicitacao.getQuantidade();

        // Lança exceção se inválido (IllegalArgumentException ou IllegalStateException)
        validacaoService.validarDisponibilidade(item, quantidade);

        item.setQuantidade(item.getQuantidade() - quantidade);
        if (item.getQuantidade() == 0) {
            item.reservar();
        }

        solicitacao.setStatus(StatusSolicitacao.APROVADA);
        repository.salvarSolicitacao(solicitacao);
        System.out.println("Solicitação registrada e aprovada com sucesso!");
    }

    public void cancelarSolicitacao(Solicitacao solicitacao) {
        if (solicitacao == null) {
            throw new IllegalArgumentException("Solicitação não encontrada no sistema.");
        }

        if (solicitacao.getStatus() != StatusSolicitacao.APROVADA) {
            throw new IllegalStateException(
                "Esta solicitação não pode ser cancelada. Status atual: " + solicitacao.getStatus()
            );
        }

        ItemDoacao item = solicitacao.getItem();
        item.setQuantidade(item.getQuantidade() + solicitacao.getQuantidade());
        solicitacao.setStatus(StatusSolicitacao.CANCELADA);

        System.out.println("Cancelamento registrado! O estoque foi devolvido à rede.");
    }
}
