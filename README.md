Rede Solidária de Doação e Reaproveitamento
Sistema orientado a objetos em Java para conectar doadores, beneficiários e itens disponíveis para doação, apoiando o reaproveitamento de recursos e a redução do desperdício.
Mostrar Imagem
Mostrar Imagem
Mostrar Imagem
</div>

 Integrantes
NomeGitHubAlexsandro Nathaniel Guerra Magalhães—Rafael Monte Tavares—Evelyn Sabryna Noronha Vasconcelos—

 Alinhamento com ODS
ODSObjetivoComo o sistema contribuiODS 1 — Erradicação da PobrezaAuxílio direto a pessoas em vulnerabilidadePriorização de beneficiários por urgência garante que quem mais precisa seja atendido primeiroODS 2 — Fome ZeroDoação de itens básicos e alimentosControle de estoque em tempo real evita desperdício de doações alimentaresODS 10 — Redução das DesigualdadesAcesso equitativo de ONGs e comunidadesCanal centralizado democratiza o acesso a recursos doados, independente de localizaçãoODS 12 — Consumo ResponsávelReaproveitamento e combate ao desperdícioCiclo DISPONIVEL → ENTREGUE rastreia cada item e evita descarte desnecessário

 Funcionalidades

Cadastro de doadores, beneficiários e itens para doação
Solicitação de itens com validação de estoque em tempo real
Aprovação ou rejeição automática com base na disponibilidade
Controle de status do item: DISPONIVEL → RESERVADO → ENTREGUE / CANCELADO
Efetivação de entregas com registro histórico de doação
Cancelamento de pedidos com devolução automática ao estoque
Listagem de itens com filtro por status ou por categoria
Listagem de beneficiários ordenada por nível de prioridade
Tratamento de exceções em todos os fluxos
[Novo — CP3] Persistência automática em arquivos .csv (leitura ao iniciar, gravação ao encerrar)
[Novo — CP3] Relatório final de doações efetivadas, ordenado por urgência do beneficiário


 Estrutura do Projeto
src/
├── model/       # Classes de domínio
│   ├── Usuario.java          # Classe abstrata base
│   ├── Doador.java
│   ├── Beneficiario.java
│   ├── ItemDoacao.java
│   ├── Solicitacao.java
│   └── DoacaoEfetivada.java  # [CP3] Registro de entrega concluída
├── service/     # Regras de negócio
│   ├── SolicitacaoService.java
│   ├── ValidacaoService.java
│   └── RelatorioService.java # [CP3] Geração de relatórios
├── repository/  # Armazenamento
│   ├── DoacaoRepository.java # Listas em memória
│   └── CsvManager.java       # [CP3] Leitura e gravação de CSV
├── controller/  # Interação com o usuário
├── util/        # Menu, GeradorIds
└── main/        # Ponto de entrada (Main.java)

data/            # [CP3] Arquivos gerados automaticamente
├── usuarios.csv
├── itens.csv
├── solicitacoes.csv
└── doacoes_efetivadas.csv

 Como Compilar e Executar

Requer Java 17 ou superior.

bash# 1. Compile o projeto
mkdir -p out
find src -name "*.java" -exec javac -sourcepath src -d out {} +

# 2. Execute
java -cp out main.Main

 Modelagem
Hierarquia de classes
Usuario (abstract)
├── Doador
└── Beneficiario
ClasseAtributos principaisUsuarioid, nome, telefone, email, dataCadastroDoadorcpf, enderecoBeneficiariocpf, endereco, nivelUrgencia, descricaoNecessidadeItemDoacaoid, descricao, categoria, quantidade, status, doadorSolicitacaoid, beneficiario, item, quantidadePedida, status, dataHoraDoacaoEfetivadaid, solicitacao, dataEfetivacao, observacoes
Status do Item
DISPONIVEL → RESERVADO → ENTREGUE
                       → CANCELADO
Status da Solicitação
(criada) → APROVADA → ENTREGUE
         → CANCELADA
Fluxo principal
Cadastrar item → Beneficiário solicita → Sistema valida estoque
    → Aprovada  → Efetivar entrega → ENTREGUE  →  DoacaoEfetivada registrada
    → Cancelada → Estoque devolvido automaticamente

 Regras de Negócio

Não é possível solicitar um item com status diferente de DISPONIVEL
Não é possível solicitar quantidade maior que o estoque disponível
Ao efetivar uma entrega, o status percorre DISPONIVEL → RESERVADO → ENTREGUE automaticamente
Ao cancelar um pedido, a quantidade é devolvida ao estoque
Beneficiários são listados em ordem crescente de prioridade (1 = maior urgência)


 Padrão de Tratamento de Exceções
A camada service lança IllegalArgumentException e IllegalStateException para entradas inválidas e estados inconsistentes. A camada controller captura essas exceções e exibe mensagens amigáveis ao usuário, sem travar o sistema.

 Persistência em CSV (Checkpoint 3)
Os dados são lidos automaticamente ao iniciar o sistema e gravados ao encerrar, garantindo que nenhuma informação seja perdida entre sessões.
ArquivoConteúdodata/usuarios.csvDoadores e beneficiários cadastradosdata/itens.csvItens e seus status atuaisdata/solicitacoes.csvHistórico completo de solicitaçõesdata/doacoes_efetivadas.csvRegistro final das entregas concluídas

 Relatório de Doações (Checkpoint 3)
Acessível pelo menu principal, o relatório exibe:

Total de itens doados por categoria
Beneficiários atendidos, ordenados por nivelUrgencia
Doadores com maior volume de contribuição
Data e status de cada entrega efetivada


 Checkpoints
CheckpointStatusEntregas1 — Modelagem + estrutura inicial✅ ConcluídoRepositório, README, modelagem, menu, cadastros básicos2 — Regras de negócio✅ ConcluídoSolicitações, validações, status, listagens, filtros, tratamento de erros3 — Finalização✅ ConcluídoPersistência CSV, relatório de doações, documentação final e análise ODS

 Reflexão: Impacto Social e ODS
O Rede Solidária nasceu de uma pergunta simples: e se a tecnologia pudesse tornar a solidariedade mais eficiente?
Doações mal gerenciadas se perdem. Itens chegam a quem não precisa, ou não chegam a quem precisa. Esse sistema existe para resolver exatamente esse problema — com código limpo, regras bem definidas e rastreabilidade completa em cada etapa do processo.
ODS 1 e 2: O campo nivelUrgencia garante que famílias em situação crítica de fome ou privação sejam atendidas antes das demais. A validação de estoque em tempo real evita que promessas de doação sejam feitas sem respaldo real.
ODS 10: O acesso às doações não deveria depender de quem você conhece. O sistema centraliza os recursos disponíveis e permite que qualquer beneficiário cadastrado solicite itens, independentemente de localização ou conexões sociais.
ODS 12: Cada item tem um ciclo de vida rastreado. Isso evita descarte desnecessário, duplicidade de doações e desperdício dentro do próprio processo de distribuição.

"Um sistema bem projetado pode ser a diferença entre uma doação que se perde e uma família que se alimenta."


 Gestão do Projeto (Git)
Branches por integrante:
main
├── feature/modelagem-sabryna
├── feature/validacao-alexsandro
├── feature/ui-rafael
├── feature/persistencia-alexsandro
├── feature/relatorios-rafael
└── feature/documentacao-sabryna
Padrão de commits:
feat: adiciona classe DoacaoEfetivada com registro de entrega
fix: corrige devolução de estoque ao cancelar solicitação
docs: atualiza README com funcionalidades do checkpoint 3
refactor: extrai lógica de CSV para CsvManager

<div align="center">
Desenvolvido com ☕ Java e propósito social · Equipe Rede Solidária · 2025
</div>
