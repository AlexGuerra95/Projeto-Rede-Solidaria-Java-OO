package util;

public class GeradorIds {

    private static int contadorDoador = 1;
    private static int contadorBeneficiario = 1;
    private static int contadorItem = 1;
    private static int contadorSolicitacao = 1;
    private static int contadorDoacao = 1;

    public static String gerarIdDoador() {
        return String.format("DOA-%03d", contadorDoador++);
    }

    public static String gerarIdBeneficiario() {
        return String.format("BEN-%03d", contadorBeneficiario++);
    }

    public static String gerarIdItem() {
        return String.format("ITE-%03d", contadorItem++);
    }

    public static String gerarIdSolicitacao() {
        return String.format("SOL-%03d", contadorSolicitacao++);
    }

    public static String gerarIdDoacao() {
        return String.format("DOE-%03d", contadorDoacao++);
    }

    // ─── Métodos chamados pela PersistenciaCSV ao carregar dados salvos ───────
    // Garante que os contadores não repitam IDs já existentes nos arquivos CSV

    public static void atualizarContadorDoador(int numero) {
        if (numero >= contadorDoador) contadorDoador = numero + 1;
    }

    public static void atualizarContadorBeneficiario(int numero) {
        if (numero >= contadorBeneficiario) contadorBeneficiario = numero + 1;
    }

    public static void atualizarContadorItem(int numero) {
        if (numero >= contadorItem) contadorItem = numero + 1;
    }

    public static void atualizarContadorSolicitacao(int numero) {
        if (numero >= contadorSolicitacao) contadorSolicitacao = numero + 1;
    }

    public static void atualizarContadorDoacao(int numero) {
        if (numero >= contadorDoacao) contadorDoacao = numero + 1;
    }
}
