# UrbanEyes

UrbanEyes é uma plataforma colaborativa desenvolvida para o reporte e acompanhamento de problemas urbanos. Este projeto utiliza **Spring Boot** no backend, implementa uma arquitetura **RESTful**, e possui cobertura completa de testes unitários. O objetivo principal do UrbanEyes é facilitar a comunicação entre cidadãos e autoridades locais para a resolução eficiente de problemas urbanos.

## Recursos

- **Cadastro e Login de Usuários**
- **Gerenciamento de Categorias**
- **Criação e Atualização de Problemas Urbanos (Issues)**
- **Filtragem de Problemas por Categoria**
- **Cobertura Completa de Testes Unitários para Controllers e Services**

---

## Tecnologias Utilizadas

- **Linguagem**: Java 17
- **Framework**: Spring Boot 3.1.3
- **Banco de Dados**: H2 Database (em memória, para testes e desenvolvimento)
- **Dependências**:
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Validation
  - Lombok
  - Spring Boot DevTools
  - Spring Boot Starter Test
  - Jackson Databind
- **Ferramenta de Build**: Maven

---

## Estrutura do Projeto

```
UrbanEyes
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.urbaneyes
│   │   │       ├── controller
│   │   │       │   ├── CategoryController.java
│   │   │       │   ├── IssueController.java
│   │   │       │   └── UserController.java
│   │   │       ├── model
│   │   │       │   ├── Category.java
│   │   │       │   ├── Issue.java
│   │   │       │   ├── IssueStatus.java
│   │   │       │   └── User.java
│   │   │       ├── repository
│   │   │       │   ├── CategoryRepository.java
│   │   │       │   ├── IssueRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       └── service
│   │   │           ├── CategoryService.java
│   │   │           ├── IssueService.java
│   │   │           └── UserService.java
│   └── test
│       ├── java
│       │   └── com.urbaneyes
│       │       ├── controller
│       │       │   ├── CategoryControllerTest.java
│       │       │   ├── IssueControllerTest.java
│       │       │   └── UserControllerTest.java
│       │       └── service
│       │           ├── CategoryServiceTest.java
│       │           ├── IssueServiceTest.java
│       │           └── UserServiceTest.java
├── pom.xml
└── README.md
```

---

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas:

- **Java 17** ou superior
- **Maven** (versão 3.6.0 ou superior)
- **IDE** como IntelliJ IDEA ou VS Code

---

## Como Executar o Projeto

1. Clone o repositório:

   ```bash
   git clone https://github.com/O-Farias/urbaneyes.git
   cd urbaneyes
   ```

2. Compile e execute o projeto utilizando o Maven:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. Acesse a aplicação em:

   ```
   http://localhost:8080
   ```

---

## Testes

Este projeto possui cobertura completa de testes unitários para os **Controllers** e **Services**.

### Executar os Testes

```bash
mvn test
```

Resultados esperados:

```bash
Tests run: 48, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

## Endpoints da API

### Usuários (`/api/users`)

- **POST** `/api/users/register`: Cadastro de um novo usuário
- **POST** `/api/users/login`: Login de um usuário
- **GET** `/api/users`: Retorna todos os usuários
- **GET** `/api/users/{id}`: Retorna um usuário por ID
- **PUT** `/api/users/{id}`: Atualiza as informações de um usuário
- **DELETE** `/api/users/{id}`: Deleta um usuário por ID

### Categorias (`/api/categories`)

- **GET** `/api/categories`: Retorna todas as categorias
- **POST** `/api/categories`: Cria uma nova categoria
- **GET** `/api/categories/{id}`: Retorna uma categoria por ID
- **PUT** `/api/categories/{id}`: Atualiza uma categoria
- **DELETE** `/api/categories/{id}`: Deleta uma categoria

### Problemas Urbanos (`/api/issues`)

- **GET** `/api/issues`: Retorna todos os problemas urbanos
- **POST** `/api/issues`: Cria um novo problema
- **GET** `/api/issues/{id}`: Retorna um problema por ID
- **PUT** `/api/issues/{id}`: Atualiza as informações de um problema
- **DELETE** `/api/issues/{id}`: Deleta um problema por ID
- **GET** `/api/issues/category/{categoryId}`: Retorna problemas filtrados por categoria
- **PATCH** `/api/issues/{id}/status`: Atualiza o status de um problema

---

## Licença

Este projeto é licenciado sob a **MIT License** - veja o arquivo [LICENSE](LICENSE) para mais detalhes.
