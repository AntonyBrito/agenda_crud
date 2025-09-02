# Agenda de Contatos

Este é um projeto simples de uma Agenda de Contatos em Java, desenvolvido como um exemplo de aplicação com funcionalidades CRUD (Criar, Ler, Editar, Deletar), testes unitários e de aceitação com relatórios Allure.

## Funcionalidades

- **Criar Contato**: Adiciona um novo contato com nome, telefone e e-mail.
- **Listar Contatos**: Exibe todos os contatos cadastrados.
- **Buscar Contato**: Procura por contatos específicos pelo nome ou e-mail.
- **Editar Contato**: Atualiza as informações de um contato existente.
- **Remover Contato**: Exclui um contato da agenda.

## Tecnologias Utilizadas

- **Java 11**: Linguagem de programação principal.
- **Maven**: Gerenciador de dependências e build da aplicação.
- **JUnit 5**: Framework para a criação de testes unitários e de aceitação.
- **Allure Framework**: Ferramenta para a geração de relatórios de teste detalhados e interativos.

## Pré-requisitos

Para compilar e executar este projeto, você precisará ter instalado em sua máquina:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) ou superior.
- [Apache Maven](https://maven.apache.org/download.cgi) 3.6 ou superior.

## Como Executar o Projeto

Siga os passos abaixo para compilar e testar o projeto.

### 1. Clone o Repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd agenda-contatos
```

### 2. Compile o Código Fonte

Use o Maven para compilar todo o código-fonte do projeto.

```bash
mvn compile
```

### 3. Execute os Testes

Para rodar todos os testes (unitários e de aceitação), execute o seguinte comando:

```bash
mvn test
```

Após a execução, os resultados dos testes serão gerados no diretório `target/surefire-reports` e os dados para o relatório Allure estarão em `target/allure-results`.

## Gerando o Relatório de Testes com Allure

Para visualizar os resultados dos testes de uma forma mais rica e interativa, você pode gerar e servir o relatório Allure.

Execute o comando abaixo na raiz do projeto:

```bash
mvn allure:serve
```

Este comando irá processar os resultados e abrirá uma página web no seu navegador padrão com o relatório completo, incluindo os passos detalhados de cada teste de aceitação.
