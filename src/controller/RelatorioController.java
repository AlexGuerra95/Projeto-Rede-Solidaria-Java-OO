package controller;

import repository.DoacaoRepository;
import service.RelatorioService;

public class RelatorioController {

    private final RelatorioService service;

    public RelatorioController( DoacaoRepository repo) {
        this.service = new RelatorioService(repo);
    }

    public void exibirResumoGeral() {
        service.gerarResumoGeral();
    }

    public void exportarDoaçõesEfetivadas() {
        service.exportarDoaçõesEfetivadasCSV();
    }

    public void exportarSolicitacoesCanceladas(){
        service.exportarSolicitacoesCanceladasCSV();
    }

    public void exportarResumoGeral(){
        service.exportarResumoGeralCSV();
    }

    public void exportarRankingBeneficiarios(){
        service.exportarRankingBeneficiariosCSV();
    }
}