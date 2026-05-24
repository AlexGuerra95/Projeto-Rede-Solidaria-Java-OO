Projeto: Rede Solidária de Doação e Reaproveitamento

Sistema orientado a objetos em Java para conectar doadores, beneficiários e itens disponíveis para doação, apoiando o reaproveitamento de recursos e a redução do desperdício.

*Integrantes*

- Alexsandro Nathaniel Guerra Magalhães
- Rafael Monte Tavares
- Evelyn Sabryna Noronha Vasconcelos

*Alinhamento com ODS*

- **ODS 1 e 2** — Auxílio direto a pessoas em vulnerabilidade através da doação de itens básicos e alimentos
- **ODS 10** — Redução de desigualdades ao facilitar o acesso de ONGs e comunidades a recursos doados
- **ODS 12** — Estímulo ao consumo consciente e reaproveitamento de materiais, combatendo o desperdício

*Funcionalidades*

- Cadastro de doadores, beneficiários e itens para doação
- Solicitação de itens com validação de estoque em tempo real
- Aprovação ou rejeição automática com base na disponibilidade
- Controle de status do item: `DISPONIVEL` → `RESERVADO` → `ENTREGUE` / `CANCELADO`
- Efetivação de entregas com registro histórico de doação
- Cancelamento de pedidos com devolução automática ao estoque
- Listagem de itens com filtro por **status** ou por **categoria**
- Listagem de beneficiários **ordenada por nível de prioridade**
- Tratamento de exceções em todos os fluxos

*Estrutura do Projeto*

```
src/
├── model/       # Classes de domínio (Usuario, Doador, Beneficiario, ItemDoacao, Solicitacao, DoacaoEfetivada)
├── service/     # Regras de negócio (SolicitacaoService, ValidacaoService)
├── repository/  # Armazenamento em memória (DoacaoRepository)
├── controller/  # Camada de interação com o usuário
├── util/        # Menu, GeradorIds
└── main/        # Ponto de entrada (Main.java)
```

*Como Compilar e Executar*

> Requer Java 17 ou superior.

```bash
mkdir -p out
find src -name "*.java" -exec javac -sourcepath src -d out {} +
java -cp out main.Main
```

*Modelagem*

*Hierarquia de classes*

```
Usuario
├── Doador
└── Beneficiario
```

*Status do Item*

```
DISPONIVEL → RESERVADO → ENTREGUE
                       → CANCELADO
```

*Status da Solicitação*

```
(criada) → APROVADA → ENTREGUE
         → CANCELADA
```

*Fluxo principal*

```
Cadastrar item → Beneficiário solicita → Sistema valida estoque
→ Aprovada  → Efetivar entrega → ENTREGUE
→ Cancelada → Estoque devolvido automaticamente
```

*Regras de Negócio*

- Não é possível solicitar um item com status diferente de `DISPONIVEL`
- Não é possível solicitar quantidade maior que o estoque disponível
- Ao efetivar uma entrega, o status do item percorre `DISPONIVEL → RESERVADO → ENTREGUE` automaticamente
- Ao cancelar um pedido, a quantidade é devolvida ao estoque
- Beneficiários são listados em ordem crescente de prioridade (1 = maior urgência)

*Padrão de Tratamento de Exceções*

A camada `service` lança `IllegalArgumentException` e `IllegalStateException` para entradas inválidas e estados inconsistentes. A camada `controller` captura essas exceções e exibe mensagens amigáveis ao usuário, sem travar o sistema.

*Checkpoints*

| Checkpoint | Status | Entregas |
|---|---|---|
| 1 — Modelagem + estrutura inicial | ✅ Concluído | Repositório, README, modelagem, menu, cadastros básicos |
| 2 — Regras de negócio | ✅ Concluído | Solicitações, validações, status, listagens, filtros, tratamento de erros |
| 3 — Finalização | 🔲 Em andamento | Persistência, relatório, documentação final |
