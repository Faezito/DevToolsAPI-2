# 🛠️ DevTools API

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Status](https://img.shields.io/badge/status-online-brightgreen?style=for-the-badge)

> API REST para gestão de operações de TI — chamados de suporte, atualizações de desenvolvedores e controle de equipe, com deploy em nuvem e documentação interativa via Swagger.

## 📋 Sobre o projeto

O **DevTools API** é uma API REST desenvolvida para centralizar e organizar processos comuns em equipes de tecnologia, como abertura e acompanhamento de chamados de suporte e o registro de atualizações feitas por desenvolvedores.

O projeto foi desenvolvido com foco em boas práticas de desenvolvimento back-end: arquitetura em camadas, documentação automática via Swagger/OpenAPI e containerização com Docker para facilitar o deploy.

---

## ✨ Funcionalidades

- 📌 **Chamados (Tickets)** — criação, listagem, atualização e encerramento de chamados de suporte
- 🚀 **Developer Updates** — registro de atualizações e entregas realizadas pela equipe de desenvolvimento
- 📄 **Documentação automática** — endpoints documentados e testáveis via Swagger UI
- 🐳 **Containerizado** — pronto para rodar via Docker em qualquer ambiente
- ☁️ **Deploy em nuvem** — API publicada e acessível no Render

---

## 🏗️ Arquitetura

O projeto segue a arquitetura em camadas padrão do Spring Boot:

```
Controller  →  Service  →  Repository  →  Database
    ↕
Swagger/OpenAPI (documentação automática)
```

```
src/
└── main/
    └── java/
        └── com/devtools/
            ├── controller/     # Endpoints REST
            ├── service/        # Regras de negócio
            ├── repository/     # Acesso ao banco de dados
            ├── model/          # Entidades
            └── dto/            # Objetos de transferência de dados
```

---

## 🚀 Como rodar localmente

### Pré-requisitos

- Java 17+
- Maven ou use o `./mvnw` incluído no projeto
- Docker (opcional)

### Com Maven

```bash
# Clone o repositório
git clone https://github.com/Faezito/DevToolsAPI-2.git
cd DevToolsAPI-2

# Rode a aplicação
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`  
O Swagger estará em `http://localhost:8080/swagger-ui/index.html`

### Com Docker

```bash
# Build da imagem
docker build -t devtools-api .

# Rodar o container
docker run -p 8080:8080 devtools-api
```

---

## 📡 Endpoints principais

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/chamados/Listar` | Lista todos os chamados |
| `POST` | `/chamados/Cadastrar` | Abre um novo chamado |
| `PUT` | `/chamados/Editar` | Atualiza um chamado |
| `DELETE` | `/chamados/Deletar/{id}` | Encerra/remove um chamado |
| `GET` | `/updates/Listar/{sistemaId}` | Lista atualizações dos devs daquele sistema |
| `POST` | `/updates/Inserir` | Registra uma nova atualização |

---

## 🛠️ Tecnologias utilizadas

| Tecnologia | Uso |
|-----------|-----|
| Java 17 | Linguagem principal |
| Spring Boot 3 | Framework back-end |
| Spring Data JPA | Persistência de dados |
| SQL Server | Banco de Dados |
| Swagger / OpenAPI 3 | Documentação da API |
| Docker | Containerização |
| Maven | Gerenciamento de dependências |
| Render | Plataforma de deploy |


---

## 👨‍💻 Autor

Feito por **[Faezito](https://github.com/Faezito)**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/seu-perfil)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Faezito)
