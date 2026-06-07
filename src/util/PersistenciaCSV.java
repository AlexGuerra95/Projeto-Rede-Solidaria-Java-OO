package util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import model.*;
import repository.DoacaoRepository;

public class PersistenciaCSV {

    private static final String PASTA         = "data/";
    private static final String ARQ_DOADORES  = PASTA + "doadores.csv";
    private static final String ARQ_BENEF     = PASTA + "beneficiarios.csv";
    private static final String ARQ_ITENS     = PASTA + "itens.csv";
    private static final String ARQ_SOLIC     = PASTA + "solicitacoes.csv";
    private static final String ARQ_DOACOES   = PASTA + "doacoes_efetivadas.csv";

    // ─── SALVAR ──────────────────────────────────────────────────────────────

    public static void salvarTudo(DoacaoRepository repo) {
        new File(PASTA).mkdirs();
        salvarDoadores(repo.getListaDoadores());
        salvarBeneficiarios(repo.getListaBeneficiarios());
        salvarItens(repo.getListaItens());
        salvarSolicitacoes(repo.getListaSolicitacoes());
        salvarDoacoesEfetivadas(repo.getListaDoacoesEfetivadas());
        System.out.println("\nDados salvos com sucesso na pasta '" + PASTA + "'.");
    }

    private static void salvarDoadores(List<Doador> lista) {
        try (PrintWriter pw = abrirEscrita(ARQ_DOADORES)) {
            pw.println("id;nome;telefone;email;endereco");
            for (Doador d : lista) {
                pw.println(d.getId() + ";" + d.getNome() + ";" +
                           d.getTelefone() + ";" + d.getEmail() + ";" + d.getEndereco());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar doadores: " + e.getMessage());
        }
    }

    private static void salvarBeneficiarios(List<Beneficiario> lista) {
        try (PrintWriter pw = abrirEscrita(ARQ_BENEF)) {
            pw.println("id;nome;telefone;email;endereco;tipo;prioridade");
            for (Beneficiario b : lista) {
                pw.println(b.getId() + ";" + b.getNome() + ";" +
                           b.getTelefone() + ";" + b.getEmail() + ";" +
                           b.getEndereco() + ";" + b.getTipoBeneficiario() + ";" +
                           b.getNivelPrioridade());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar beneficiários: " + e.getMessage());
        }
    }

    private static void salvarItens(List<ItemDoacao> lista) {
        try (PrintWriter pw = abrirEscrita(ARQ_ITENS)) {
            pw.println("id;nomeItem;categoria;descricao;quantidade;estadoConservacao;dataCadastro;status");
            for (ItemDoacao i : lista) {
                pw.println(i.getId() + ";" + i.getNomeItem() + ";" +
                           i.getCategoria() + ";" + i.getDescricao() + ";" +
                           i.getQuantidade() + ";" + i.getEstadoConservacao() + ";" +
                           i.getDataCadastro() + ";" + i.getStatus());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar itens: " + e.getMessage());
        }
    }

    private static void salvarSolicitacoes(List<Solicitacao> lista) {
        try (PrintWriter pw = abrirEscrita(ARQ_SOLIC)) {
            pw.println("id;beneficiarioId;itemId;quantidade;status;justificativa");
            for (Solicitacao s : lista) {
                // justificativa por último — pode conter ponto-e-vírgula
                pw.println(s.getId() + ";" + s.getBeneficiario().getId() + ";" +
                           s.getItem().getId() + ";" + s.getQuantidade() + ";" +
                           s.getStatus() + ";" + s.getJustificativa());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar solicitações: " + e.getMessage());
        }
    }

    private static void salvarDoacoesEfetivadas(List<DoacaoEfetivada> lista) {
        try (PrintWriter pw = abrirEscrita(ARQ_DOACOES)) {
            pw.println("id;itemId;doadorId;beneficiarioId;quantidade;data;observacoes");
            for (DoacaoEfetivada de : lista) {
                // observacoes por último — pode conter ponto-e-vírgula
                pw.println(de.getId() + ";" + de.getItem().getId() + ";" +
                           de.getDoador().getId() + ";" + de.getBeneficiario().getId() + ";" +
                           de.getQuantidade() + ";" + de.getDataCadastro() + ";" +
                           de.getObservacoes());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar doações efetivadas: " + e.getMessage());
        }
    }

    // ─── CARREGAR ─────────────────────────────────────────────────────────────

    public static boolean carregarTudo(DoacaoRepository repo) {
        if (!new File(ARQ_DOADORES).exists()) {
            return false; // nenhum arquivo salvo — usar dados de seed
        }
        carregarDoadores(repo);
        carregarBeneficiarios(repo);
        carregarItens(repo);
        carregarSolicitacoes(repo);
        carregarDoacoesEfetivadas(repo);
        ajustarContadores(repo);
        System.out.println("Dados carregados com sucesso.");
        return true;
    }

    private static void carregarDoadores(DoacaoRepository repo) {
        try (BufferedReader br = abrirLeitura(ARQ_DOADORES)) {
            br.readLine(); // pula cabeçalho
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";", -1);
                if (p.length < 5) continue;
                repo.salvarDoador(new Doador(p[0], p[1], p[2], p[3], p[4]));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar doadores: " + e.getMessage());
        }
    }

    private static void carregarBeneficiarios(DoacaoRepository repo) {
        try (BufferedReader br = abrirLeitura(ARQ_BENEF)) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";", -1);
                if (p.length < 7) continue;
                repo.salvarBeneficiario(new Beneficiario(
                    p[0], p[1], p[2], p[3], p[4], p[5], Integer.parseInt(p[6])
                ));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar beneficiários: " + e.getMessage());
        }
    }

    private static void carregarItens(DoacaoRepository repo) {
        try (BufferedReader br = abrirLeitura(ARQ_ITENS)) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";", -1);
                if (p.length < 8) continue;
                repo.salvarItem(new ItemDoacao(
                    p[0], p[1], p[2], p[3],
                    Integer.parseInt(p[4]), p[5],
                    LocalDate.parse(p[6]),
                    StatusItem.valueOf(p[7])
                ));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar itens: " + e.getMessage());
        }
    }

    private static void carregarSolicitacoes(DoacaoRepository repo) {
        try (BufferedReader br = abrirLeitura(ARQ_SOLIC)) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                // limite 6: justificativa (última coluna) pode ter ponto-e-vírgula
                String[] p = linha.split(";", 6);
                if (p.length < 6) continue;
                Beneficiario b    = repo.buscarBeneficiarioPorId(p[1]);
                ItemDoacao item   = repo.buscarItemPorId(p[2]);
                if (b == null || item == null) continue;
                Solicitacao s = new Solicitacao(p[0], b, item, Integer.parseInt(p[3]), p[5]);
                s.setStatus(StatusSolicitacao.valueOf(p[4]));
                repo.salvarSolicitacao(s);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar solicitações: " + e.getMessage());
        }
    }

    private static void carregarDoacoesEfetivadas(DoacaoRepository repo) {
        try (BufferedReader br = abrirLeitura(ARQ_DOACOES)) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                // limite 7: observacoes (última coluna) pode ter ponto-e-vírgula
                String[] p = linha.split(";", 7);
                if (p.length < 7) continue;
                ItemDoacao   item  = repo.buscarItemPorId(p[1]);
                Doador       doad  = repo.buscarDoadorPorId(p[2]);
                Beneficiario ben   = repo.buscarBeneficiarioPorId(p[3]);
                if (item == null || doad == null || ben == null) continue;
                repo.salvarDoacaoEfetivada(new DoacaoEfetivada(
                    p[0], item, doad, ben,
                    Integer.parseInt(p[4]),
                    LocalDate.parse(p[5]),
                    p[6]
                ));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar doações efetivadas: " + e.getMessage());
        }
    }

    // ─── AUXILIARES ──────────────────────────────────────────────────────────

    // Após carregar, ajusta os contadores do GeradorIds para não repetir IDs
    private static void ajustarContadores(DoacaoRepository repo) {
        for (Doador d      : repo.getListaDoadores())          GeradorIds.atualizarContadorDoador(extrairNumero(d.getId()));
        for (Beneficiario b: repo.getListaBeneficiarios())     GeradorIds.atualizarContadorBeneficiario(extrairNumero(b.getId()));
        for (ItemDoacao i  : repo.getListaItens())             GeradorIds.atualizarContadorItem(extrairNumero(i.getId()));
        for (Solicitacao s : repo.getListaSolicitacoes())      GeradorIds.atualizarContadorSolicitacao(extrairNumero(s.getId()));
        for (DoacaoEfetivada de : repo.getListaDoacoesEfetivadas()) GeradorIds.atualizarContadorDoacao(extrairNumero(de.getId()));
    }

    // Extrai o número de um ID no formato "XXX-001" → 1
    private static int extrairNumero(String id) {
        try {
            return Integer.parseInt(id.split("-")[1]);
        } catch (Exception e) {
            return 0;
        }
    }

    private static PrintWriter abrirEscrita(String caminho) throws IOException {
        return new PrintWriter(new OutputStreamWriter(
            new FileOutputStream(caminho), StandardCharsets.UTF_8));
    }

    private static BufferedReader abrirLeitura(String caminho) throws IOException {
        return new BufferedReader(new InputStreamReader(
            new FileInputStream(caminho), StandardCharsets.UTF_8));
    }
}
