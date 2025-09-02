# Agenda de Contatos - API REST com Spring Boot

Este projeto é uma API REST para uma Agenda de Contatos, desenvolvida com Spring Boot. A aplicação oferece um conjunto de endpoints para gerenciar contatos, incluindo operações de CRUD (Criar, Ler, Atualizar, Deletar).

O projeto foi refatorado a partir de uma versão de linha de comando, adotando uma arquitetura moderna com Spring Boot, Spring Data JPA e um banco de dados em memória (H2).

## Funcionalidades da API

A API expõe os seguintes endpoints sob a URL base `/api/contatos`:

- **`GET /api/contatos`**: Lista todos os contatos cadastrados.
- **`GET /api/contatos/{id}`**: Busca um contato específico pelo seu ID.
- **`POST /api/contatos`**: Adiciona um novo contato. O corpo da requisição deve conter o nome, telefone e e-mail.
- **`PUT /api/contatos/{id}`**: Atualiza as informações de um contato existente.
- **`DELETE /api/contatos/{id}`**: Remove um contato da agenda.

## Tecnologias Utilizadas

- **Java 11**: Linguagem de programação principal.
- **Spring Boot**: Framework para a criação de aplicações web e APIs REST.
- **Spring Data JPA**: Para persistência de dados de forma simplificada.
- **H2 Database**: Banco de dados em memória para desenvolvimento e testes.
- **Maven**: Gerenciador de dependências e build da aplicação.
- **JUnit 5 & Mockito**: Para a criação de testes unitários e de integração.
- **Allure Framework**: Ferramenta para a geração de relatórios de teste detalhados.

## Pré-requisitos

Para compilar e executar este projeto, você precisará ter instalado em sua máquina:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) ou superior.
- [Apache Maven](https://maven.apache.org/download.cgi) 3.6 ou superior.

## Como Executar o Projeto

Siga os passos abaixo para compilar, testar e executar a aplicação.

### 1. Clone o Repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd agenda-contatos-api
```

### 2. Execute os Testes

Para rodar todos os testes (unitários e de integração), execute o seguinte comando na raiz do projeto:

```bash
mvn test
```

### 3. Inicie a Aplicação

Use o plugin do Spring Boot para iniciar a API:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

### 4. Acesse o Console do Banco de Dados H2

Com a aplicação em execução, você pode acessar o console do H2 para visualizar e gerenciar o banco de dados em memória.

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:agendadb`
- **User Name**: `sa`
- **Password**: (deixe em branco)

## Gerando o Relatório de Testes com Allure

Para visualizar os resultados dos testes de uma forma mais rica e interativa, você pode gerar e servir o relatório Allure.

Execute o comando abaixo na raiz do projeto:

```bash
mvn allure:serve
```

Este comando irá processar os resultados e abrirá uma página web no seu navegador padrão com o relatório completo.
