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

    public void exportarCSV() {
        service.exportarCSV();
    }
}