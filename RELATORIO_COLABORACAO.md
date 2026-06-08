# RELATÓRIO DE COLABORAÇÃO DA EQUIPE

## Projeto

**Rede Solidária de Doação e Reaproveitamento**

Sistema desenvolvido em Java com foco na gestão de doações, conectando doadores e beneficiários por meio de um processo estruturado de cadastro, solicitação, validação e entrega de itens. O projeto foi concebido com base nos princípios da Programação Orientada a Objetos e alinhado aos Objetivos de Desenvolvimento Sustentável (ODS) da Organização das Nações Unidas.

---

# Equipe de Desenvolvimento

| Integrante                            | Principais Responsabilidades                                                 |
| ------------------------------------- | ---------------------------------------------------------------------------- |
| Alexsandro Nathaniel Guerra Magalhães | Arquitetura do sistema, persistência de dados e validações de negócio        |
| Rafael Monte Tavares                  | Interface de usuário, fluxo de solicitações e geração de relatórios          |
| Evelyn Sabryna Noronha Vasconcelos    | Modelagem de entidades, controle do ciclo de vida das doações e documentação |

---

# Planejamento e Distribuição das Atividades

As atividades foram distribuídas desde o início do projeto com o objetivo de promover uma participação equilibrada entre os integrantes, permitindo que cada membro contribuísse em áreas específicas do desenvolvimento do sistema.

## Checkpoint 1 — Estrutura Base e Modelagem de Dados

### Alexsandro Nathaniel Guerra Magalhães

* Configuração inicial do repositório Git.
* Organização da arquitetura do projeto.
* Estruturação dos pacotes da aplicação.
* Implementação do repositório de armazenamento em memória.

### Rafael Monte Tavares

* Desenvolvimento da interface de terminal.
* Implementação dos menus de navegação.
* Criação dos mecanismos de entrada e interação com o usuário.

### Evelyn Sabryna Noronha Vasconcelos

* Modelagem das entidades relacionadas aos usuários do sistema.
* Implementação das classes `Usuario`, `Doador` e `Beneficiario`.
* Aplicação dos conceitos de herança, encapsulamento e reutilização de código.

---

## Checkpoint 2 — Regras de Negócio e Controle de Fluxo

### Alexsandro Nathaniel Guerra Magalhães

* Implementação das validações de disponibilidade de itens.
* Desenvolvimento das regras de consistência para solicitações.
* Controle das restrições de estoque.

### Rafael Monte Tavares

* Desenvolvimento da classe `Solicitacao`.
* Implementação do fluxo de criação e gerenciamento de pedidos.
* Aprovação e rejeição automática de solicitações com base na disponibilidade.

### Evelyn Sabryna Noronha Vasconcelos

* Implementação da classe `ItemDoacao`.
* Desenvolvimento do controle de status dos itens.
* Gerenciamento do ciclo de vida das doações, incluindo reserva, entrega e cancelamento.

---

## Checkpoint 3 — Persistência e Documentação Final

### Alexsandro Nathaniel Guerra Magalhães

* Implementação da persistência de dados em arquivos CSV.
* Desenvolvimento das rotinas de leitura e gravação de informações.
* Garantia da manutenção dos dados entre execuções do sistema.

### Rafael Monte Tavares

* Implementação da classe `DoacaoEfetivada`.
* Desenvolvimento do módulo de relatórios.
* Consolidação das informações referentes às doações concluídas.

### Evelyn Sabryna Noronha Vasconcelos

* Elaboração da documentação final do projeto.
* Atualização do diagrama de classes.
* Produção da análise de impacto social.
* Relacionamento das funcionalidades do sistema com os Objetivos de Desenvolvimento Sustentável (ODS 1, 2, 10 e 12).

---

# Metodologia de Trabalho

A equipe utilizou o GitHub como plataforma de versionamento e colaboração, adotando uma estratégia baseada em branches individuais para o desenvolvimento das funcionalidades.

O processo de integração do código foi realizado por meio de commits frequentes e revisões contínuas, permitindo o acompanhamento da evolução do projeto e reduzindo conflitos durante a consolidação das funcionalidades.

As responsabilidades definidas no planejamento inicial foram mantidas ao longo dos três checkpoints, contribuindo para uma divisão equilibrada das tarefas e para a entrega consistente de cada etapa do projeto.

---

# Resultados Alcançados

Ao final do desenvolvimento, o sistema apresentou todas as funcionalidades previstas no escopo do projeto, incluindo:

* Cadastro de doadores e beneficiários.
* Gerenciamento de itens para doação.
* Controle de solicitações.
* Validação de disponibilidade de estoque.
* Controle de status das doações.
* Persistência de dados em arquivos CSV.
* Geração de relatórios de doações efetivadas.
* Documentação técnica completa.

Além dos aspectos técnicos, o projeto também contemplou objetivos sociais relacionados à redução do desperdício, ao reaproveitamento de recursos e ao apoio a pessoas em situação de vulnerabilidade.

---

# Considerações Finais

O desenvolvimento do sistema Rede Solidária proporcionou à equipe a aplicação prática dos conceitos de Programação Orientada a Objetos, organização em camadas, persistência de dados e controle de versão colaborativo.

A divisão estruturada das responsabilidades, associada ao uso de boas práticas de desenvolvimento, possibilitou a conclusão do projeto dentro dos objetivos propostos, resultando em uma solução funcional, organizada e alinhada às diretrizes acadêmicas estabelecidas para a disciplina.
