package main;

import util.Menu;
import repository.DoacaoRepository;

public class Main {
    public static void main(String[] args) {
        DoacaoRepository repo = new DoacaoRepository();
        repo.carregarDadosIniciais();
        Menu.executarMenu(repo);
    }
}
