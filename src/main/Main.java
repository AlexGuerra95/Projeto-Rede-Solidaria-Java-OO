package main;

import repository.DoacaoRepository;
import util.Menu;
import util.PersistenciaCSV;

public class Main {
    public static void main(String[] args) {
        DoacaoRepository repo = new DoacaoRepository();

        boolean carregou = PersistenciaCSV.carregarTudo(repo);
        if (!carregou) {
            System.out.println("Nenhum dado salvo encontrado. Carregando dados iniciais de teste.");
            repo.carregarDadosIniciais();
        }

        // Salva automaticamente se o programa for encerrado de forma inesperada
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            PersistenciaCSV.salvarTudo(repo);
        }));

        Menu.executarMenu(repo);
    }
}
