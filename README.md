# Projeto: Rede Solidária de Doação e Reaproveitamento

Sistema orientado a objetos em Java para conectar doadores, beneficiários e itens disponíveis para doação, apoiando o reaproveitamento de recursos e a redução do desperdício.

## Integrantes

- Alexsandro Nathaniel Guerra Magalhães
- Rafael Monte Tavares
- Evelyn Sabryna Noronha Vasconcelos

## Alinhamento com ODS

- **ODS 1 e 2** — Auxílio direto a pessoas em vulnerabilidade através da doação de itens básicos e alimentos
- **ODS 10** — Redução de desigualdades ao facilitar o acesso de ONGs e comunidades a recursos doados
- **ODS 12** — Estímulo ao consumo consciente e reaproveitamento de materiais, combatendo o desperdício

## Funcionalidades

- Cadastro de doadores, beneficiários e itens para doação
- Solicitação de itens com validação de estoque em tempo real
- Controle de status do item: `DISPONIVEL` → `RESERVADO` → `ENTREGUE` / `CANCELADO`
- Efetivação de entregas com registro de doação
- Cancelamento de pedidos com devolução automática ao estoque
- Listagens e filtros por status
- Tratamento de exceções em todos os fluxos

## Estrutura do Projeto

```
src/
├── model/          # Classes de domínio (Usuario, Doador, Beneficiario, ItemDoacao, Solicitacao, DoacaoEfetivada)
├── service/        # Regras de negócio (SolicitacaoService, ValidacaoService)
├── repository/     # Armazenamento em memória (DoacaoRepository)
├── controller/     # Camada de interação com o usuário
├── util/           # Menu, GeradorIds
└── main/           # Ponto de entrada (Main.java)
```

## Como Compilar e Executar

```bash
mkdir -p out
find src -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp out main.Main
```

## Modelagem

### Hierarquia de classes

```
Usuario
├── Doador
└── Beneficiario
```

### Status do Item

```
DISPONIVEL → RESERVADO → ENTREGUE
                       → CANCELADO
```

### Fluxo principal

```
Cadastrar item → Beneficiário solicita → Sistema valida estoque
→ Solicitação APROVADA → Efetivar entrega → ENTREGUE
                       → Cancelar pedido  → CANCELADO
```

## Padrão de Tratamento de Exceções

A camada `service` lança `IllegalArgumentException` e `IllegalStateException` para entradas inválidas e estados inconsistentes. A camada `controller` captura essas exceções e exibe mensagens amigáveis ao usuário, sem travar o sistema.
