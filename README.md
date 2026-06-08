# Rede Solidária de Doação e Reaproveitamento

Sistema orientado a objetos desenvolvido em Java para conectar doadores, beneficiários e itens disponíveis para doação, promovendo o reaproveitamento de recursos e a redução do desperdício.

---

# Integrantes

| Nome                                  |   GitHub   |
| ------------------------------------- | ---------- |
| Alexsandro Nathaniel Guerra Magalhães |AlexGuerra95|
| Rafael Monte Tavares                  |  RAFMTDEV  |
| Evelyn Sabryna Noronha Vasconcelos    | sabrynavn  |

---

# Alinhamento com os Objetivos de Desenvolvimento Sustentável (ODS)

| ODS                                         | Objetivo                                                | Como o sistema contribui                                                                         |
| ------------------------------------------- | ------------------------------------------------------- | ------------------------------------------------------------------------------------------------ |
| ODS 1 – Erradicação da Pobreza              | Auxílio direto a pessoas em situação de vulnerabilidade | A priorização por nível de urgência garante atendimento aos beneficiários com maior necessidade. |
| ODS 2 – Fome Zero e Agricultura Sustentável | Distribuição de itens essenciais e alimentos            | O controle de estoque em tempo real reduz desperdícios e melhora a gestão das doações.           |
| ODS 10 – Redução das Desigualdades          | Ampliação do acesso a recursos doados                   | O sistema centraliza as doações e facilita o acesso para beneficiários cadastrados.              |
| ODS 12 – Consumo e Produção Responsáveis    | Incentivo ao reaproveitamento de recursos               | O rastreamento completo do ciclo de vida dos itens reduz descartes desnecessários.               |

---

# Funcionalidades

* Cadastro de doadores, beneficiários e itens para doação.
* Solicitação de itens com validação de estoque em tempo real.
* Aprovação ou rejeição automática de solicitações com base na disponibilidade.
* Controle de status dos itens:

  * DISPONIVEL
  * RESERVADO
  * ENTREGUE
  * CANCELADO
* Efetivação de entregas com registro histórico.
* Cancelamento de solicitações com devolução automática ao estoque.
* Listagem de itens por categoria ou status.
* Listagem de beneficiários ordenada por nível de prioridade.
* Tratamento de exceções em todos os fluxos do sistema.

## Funcionalidades Adicionadas no Checkpoint 3

* Persistência automática em arquivos CSV.
* Carregamento de dados ao iniciar o sistema.
* Salvamento automático ao encerrar a aplicação.
* Geração de relatórios de doações efetivadas.
* Ordenação de beneficiários por urgência.

---

# Estrutura do Projeto

```text
src/
├── model/
│   ├── Usuario.java
│   ├── Doador.java
│   ├── Beneficiario.java
│   ├── ItemDoacao.java
│   ├── Solicitacao.java
│   └── DoacaoEfetivada.java
│
├── service/
│   ├── SolicitacaoService.java
│   ├── ValidacaoService.java
│   └── RelatorioService.java
│
├── repository/
│   ├── DoacaoRepository.java
│   └── CsvManager.java
│
├── controller/
│
├── util/
│   ├── Menu.java
│   └── GeradorIds.java
│
└── main/
    └── Main.java

data/
├── usuarios.csv
├── itens.csv
├── solicitacoes.csv
└── doacoes_efetivadas.csv
```

---

# Compilação e Execução

## Requisitos

* Java 17 ou superior.

## Compilação

```bash
mkdir -p out
find src -name "*.java" -exec javac -sourcepath src -d out {} +
```

## Execução

```bash
java -cp out main.Main
```

---

# Modelagem

## Hierarquia de Classes

```text
Usuario (abstrata)
├── Doador
└── Beneficiario
```

## Classes Principais

| Classe          | Principais Atributos                                       |
| --------------- | ---------------------------------------------------------- |
| Usuario         | id, nome, telefone, email, dataCadastro                    |
| Doador          | cpf, endereco                                              |
| Beneficiario    | cpf, endereco, nivelUrgencia, descricaoNecessidade         |
| ItemDoacao      | id, descricao, categoria, quantidade, status, doador       |
| Solicitacao     | id, beneficiario, item, quantidadePedida, status, dataHora |
| DoacaoEfetivada | id, solicitacao, dataEfetivacao, observacoes               |

---

# Fluxo de Funcionamento

```text
Cadastro de Item
        ↓
Solicitação pelo Beneficiário
        ↓
Validação de Estoque
        ↓
Aprovação
        ↓
Reserva do Item
        ↓
Entrega
        ↓
Registro da Doação Efetivada
```

## Status dos Itens

```text
DISPONIVEL → RESERVADO → ENTREGUE

ou

DISPONIVEL → CANCELADO
```

## Status das Solicitações

```text
CRIADA → APROVADA → ENTREGUE

ou

CRIADA → CANCELADA
```

---

# Regras de Negócio

* Não é permitido solicitar itens com status diferente de DISPONIVEL.
* Não é permitido solicitar quantidade superior ao estoque disponível.
* Toda entrega efetivada atualiza automaticamente o status do item.
* Solicitações canceladas devolvem a quantidade reservada ao estoque.
* Beneficiários são ordenados por nível de urgência, sendo 1 a maior prioridade.

---

# Tratamento de Exceções

A camada de serviço utiliza exceções para garantir a integridade das regras de negócio:

* `IllegalArgumentException` para entradas inválidas.
* `IllegalStateException` para estados inconsistentes do sistema.

A camada de controle realiza a captura dessas exceções e exibe mensagens apropriadas ao usuário, evitando a interrupção da execução da aplicação.

---

# Persistência de Dados

Os dados são armazenados em arquivos CSV, permitindo a manutenção das informações entre diferentes execuções da aplicação.

| Arquivo                | Conteúdo                               |
| ---------------------- | -------------------------------------- |
| usuarios.csv           | Doadores e beneficiários cadastrados   |
| itens.csv              | Itens disponíveis e respectivos status |
| solicitacoes.csv       | Histórico de solicitações              |
| doacoes_efetivadas.csv | Registro das entregas concluídas       |

---

# Relatório de Doações

O sistema disponibiliza um relatório consolidado contendo:

* Quantidade de itens doados por categoria.
* Beneficiários atendidos, ordenados por nível de urgência.
* Doadores com maior volume de contribuições.
* Histórico de entregas realizadas.
* Data e status das doações efetivadas.

---

# Checkpoints

| Checkpoint | Status    | Entregas                                                                       |
| ---------- | --------- | ------------------------------------------------------------------------------ |
| 1          | Concluído | Modelagem, estrutura inicial, menu e cadastros básicos                         |
| 2          | Concluído | Solicitações, validações, controle de status, filtros e tratamento de exceções |
| 3          | Concluído | Persistência em CSV, relatórios e documentação final                           |

---

# Impacto Social

O projeto Rede Solidária foi desenvolvido com o objetivo de tornar a distribuição de recursos mais eficiente, transparente e organizada.

A utilização de critérios de urgência contribui para que pessoas em situação de maior vulnerabilidade sejam atendidas prioritariamente. O controle de estoque em tempo real reduz inconsistências e evita promessas de doação sem disponibilidade efetiva. Além disso, o rastreamento completo do ciclo de vida dos itens incentiva o reaproveitamento de recursos e reduz desperdícios.

Ao centralizar informações sobre doações e beneficiários, o sistema busca ampliar o alcance das ações solidárias e apoiar iniciativas alinhadas aos Objetivos de Desenvolvimento Sustentável.

---

# Gestão do Projeto

## Estrutura de Branches

```text
main
├── feature/modelagem-sabryna
├── feature/validacao-alexsandro
├── feature/ui-rafael
├── feature/persistencia-alexsandro
├── feature/relatorios-rafael
└── feature/documentacao-sabryna
```

## Padrão de Commits

```text
feat: adiciona classe DoacaoEfetivada com registro de entrega

fix: corrige devolução de estoque ao cancelar solicitação

docs: atualiza README com funcionalidades do Checkpoint 3

refactor: extrai lógica de persistência para CsvManager
```

---

Desenvolvido em Java como projeto acadêmico voltado à aplicação de conceitos de Programação Orientada a Objetos e à promoção de impacto social positivo.
