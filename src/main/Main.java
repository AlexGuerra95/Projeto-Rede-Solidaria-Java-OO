package main;

import repository.DoacaoRepository;
import util.Menu;

public class Main {
    public static void main(String[] args) {
        DoacaoRepository repo = new DoacaoRepository();
        repo.carregarDadosIniciais();
        Menu.executarMenu(repo);
    }
}