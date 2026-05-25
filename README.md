<div align="center">
  <img src="./java/src/main/resources/img/LogoToomate.png" width="250px" alt="Logo Toomate">

# Toomate Backend

**Sistema de Gestao Integrada para restaurantes**

![Java](https://img.shields.io/badge/Java_21-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL_8-%234479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-%23DC382D.svg?style=for-the-badge&logo=redis&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-%23FF6600.svg?style=for-the-badge&logo=rabbitmq&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-%232496ED.svg?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-%2385EA2D.svg?style=for-the-badge&logo=swagger&logoColor=black)
![Prometheus](https://img.shields.io/badge/Prometheus-%23E6522C.svg?style=for-the-badge&logo=prometheus&logoColor=white)

</div>

---

## Sobre o projeto

API REST para gestao integrada do restaurante **Toomate**, substituindo processos manuais de controle de estoque, validade de insumos, fornecedores, financeiro e auditoria.

### Problema

A gestao manual de insumos pereciveis e nao pereciveis resulta em desperdicio, rupturas de estoque e dificuldade no controle do fluxo de caixa.

### Solucao

Plataforma centralizada que oferece:

- Controle de estoque em tempo real (entradas, saidas e validades)
- Gestao de fornecedores, marcas e rotinas de compras
- Administracao de boletos e dividas de clientes
- Dashboard com KPIs e relatorios para tomada de decisao
- Auditoria completa de operacoes com armazenamento em nuvem

---

## Arquitetura

```
src/main/java/com/toomate/backend/
|-- audit/              # Servico de auditoria (S3)
|-- config/             # Seguranca, JWT, Redis, RabbitMQ
|-- controller/         # 12 controllers REST
|-- dto/                # Data Transfer Objects
|-- enums/              # Enumeracoes
|-- exceptions/         # Excecoes customizadas
|-- integration/        # S3 Uploader, RabbitMQ Producer
|-- mapper/             # Conversores Entity <-> DTO
|-- model/              # 13 entidades JPA
|-- observer/           # Padrao Observer
|-- repository/         # Repositorios Spring Data JPA
|-- service/            # Regras de negocio
```

---

## Stack Tecnologica

| Tecnologia | Finalidade |
| :--- | :--- |
| **Spring Web** | API RESTful com Tomcat embutido |
| **Spring Data JPA** | ORM/Hibernate para persistencia de dados |
| **Spring Security + JWT** | Autenticacao stateless com tokens e controle de roles (USER/ADMIN) |
| **Spring Data Redis** | Cache de endpoints para otimizar performance |
| **Spring AMQP** | Mensageria assincrona via RabbitMQ |
| **Spring Actuator + Micrometer** | Metricas e health checks expostos para Prometheus |
| **SpringDoc OpenAPI** | Documentacao interativa da API (Swagger UI) |
| **AWS SDK S3** | Armazenamento de arquivos e logs de auditoria na nuvem |
| **Bean Validation** | Validacao de DTOs (`@NotNull`, `@Email`, etc.) |
| **Lombok** | Reducao de boilerplate (getters, setters, logs) |
| **JaCoCo** | Relatorio de cobertura de testes |
| **H2 Database** | Banco em memoria para testes |

---

## Endpoints da API

### Usuarios (`/usuarios`)

| Metodo | Rota | Descricao |
| :--- | :--- | :--- |
| `POST` | `/usuarios` | Cadastrar usuario |
| `POST` | `/usuarios/login` | Autenticar e obter token JWT |
| `GET` | `/usuarios` | Listar todos |
| `GET` | `/usuarios/paginado` | Listagem paginada |
| `GET` | `/usuarios/{id}` | Buscar por ID |
| `GET` | `/usuarios/nome?nome=` | Buscar por nome |
| `PUT` | `/usuarios/{id}` | Atualizar usuario |
| `PATCH` | `/usuarios/{id}` | Atualizar status de administrador |
| `DELETE` | `/usuarios/{id}` | Remover usuario |

### Lotes / Estoque (`/lotes`)

| Metodo | Rota | Descricao |
| :--- | :--- | :--- |
| `GET` | `/lotes` | Listar todos os lotes |
| `GET` | `/lotes/paginado` | Listagem paginada com filtro de periodo |
| `GET` | `/lotes/resumo-periodo` | Resumo de valor e registros por periodo |
| `GET` | `/lotes/{id}` | Buscar lote por ID |
| `GET` | `/lotes/estoque` | Estoque agrupado por insumo |
| `GET` | `/lotes/estoque/{categoria}` | Estoque filtrado por categoria |
| `GET` | `/lotes/estoque/search?insumo=` | Pesquisar estoque por insumo |
| `GET` | `/lotes/estoque/vencimentos` | Lotes proximos do vencimento |
| `GET` | `/lotes/estoque/kpis` | KPIs de vencimentos |
| `POST` | `/lotes` | Cadastrar lote |
| `PUT` | `/lotes/{id}` | Atualizar lote |
| `PUT` | `/lotes/adicionarEstoque/{id}` | Adicionar quantidade |
| `PUT` | `/lotes/removerEstoque/{id}` | Remover quantidade |
| `PATCH` | `/lotes` | Atualizar quantidades em lote |
| `DELETE` | `/lotes/{id}` | Remover lote |

### Categorias (`/categorias`)

| Metodo | Rota | Descricao |
| :--- | :--- | :--- |
| `GET` | `/categorias` | Listar categorias |
| `GET` | `/categorias/por-nome?nome=` | Filtrar por nome |
| `GET` | `/categorias/{id}/fornecedores` | Fornecedores por categoria |
| `POST` | `/categorias` | Criar categoria |
| `PUT` | `/categorias/{id}` | Atualizar |
| `DELETE` | `/categorias/{id}` | Remover |

### Insumos (`/insumos`)

| Metodo | Rota | Descricao |
| :--- | :--- | :--- |
| `GET` | `/insumos` | Listar insumos |
| `GET` | `/insumos/por-nome?nome=` | Buscar por nome |
| `POST` | `/insumos` | Cadastrar insumo |
| `PUT` | `/insumos/{id}` | Atualizar |
| `DELETE` | `/insumos/{id}` | Remover |

### Marcas (`/marcas`)

| Metodo | Rota | Descricao |
| :--- | :--- | :--- |
| `GET` | `/marcas` | Listar marcas |
| `GET` | `/marcas/por-nome?nome=` | Filtrar por nome |
| `POST` | `/marcas` | Cadastrar marca |
| `PUT` | `/marcas/{id}` | Atualizar |
| `DELETE` | `/marcas/{id}` | Remover |

### Fornecedores (`/fornecedores`)

| Metodo | Rota | Descricao |
| :--- | :--- | :--- |
| `GET` | `/fornecedores` | Listagem paginada com filtro |
| `GET` | `/fornecedores/{id}` | Buscar por ID |
| `GET` | `/fornecedores/pesquisa?razaoSocial=` | Pesquisar por razao social |
| `POST` | `/fornecedores` | Cadastrar fornecedor |
| `PUT` | `/fornecedores/{id}` | Atualizar |
| `DELETE` | `/fornecedores/{id}` | Remover |

### Boletos (`/boletos`)

| Metodo | Rota | Descricao |
| :--- | :--- | :--- |
| `GET` | `/boletos` | Listar boletos |
| `GET` | `/boletos/{id}` | Buscar por ID |
| `GET` | `/boletos/por-categoria?categoria=` | Filtrar por categoria |
| `GET` | `/boletos/fornecedores/{idFornecedor}` | Boletos por fornecedor |
| `GET` | `/boletos/categorias` | Listar categorias de boletos |
| `POST` | `/boletos` | Cadastrar boleto |
| `PUT` | `/boletos/{id}` | Atualizar |
| `DELETE` | `/boletos/{id}` | Remover |

### Clientes (`/clientes`)

| Metodo | Rota | Descricao |
| :--- | :--- | :--- |
| `GET` | `/clientes` | Listar clientes |
| `GET` | `/clientes/{idCliente}` | Buscar por ID |
| `GET` | `/clientes/por-nome?nome=` | Buscar por nome |
| `GET` | `/clientes/aberto` | Total de clientes com dividas abertas |
| `GET` | `/clientes/com-dividas` | Listar clientes com dividas |
| `POST` | `/clientes` | Cadastrar cliente |
| `PUT` | `/clientes/{idCliente}` | Atualizar |
| `DELETE` | `/clientes/{id}` | Remover |

### Dividas (`/dividas`)

| Metodo | Rota | Descricao |
| :--- | :--- | :--- |
| `GET` | `/dividas` | Listagem paginada |
| `GET` | `/dividas/pedidos` | Listar pedidos |
| `POST` | `/dividas` | Registrar divida |
| `PUT` | `/dividas/{idDivida}` | Atualizar divida |
| `PUT` | `/dividas/atualizarEstado/{idDivida}` | Alternar estado de pagamento |

### Rotinas (`/rotinas`)

| Metodo | Rota | Descricao |
| :--- | :--- | :--- |
| `GET` | `/rotinas` | Listagem paginada com filtro por titulo |
| `GET` | `/rotinas/{id}` | Buscar por ID |
| `POST` | `/rotinas` | Criar rotina |
| `PUT` | `/rotinas/{id}` | Atualizar rotina |
| `PUT` | `/rotinas/associar-rotina-insumo` | Associar insumos a rotina |
| `PUT` | `/rotinas/baixa/{id}` | Dar baixa na rotina |
| `DELETE` | `/rotinas/{id}` | Remover |

### Arquivos (`/arquivos`)

| Metodo | Rota | Descricao |
| :--- | :--- | :--- |
| `GET` | `/arquivos` | Listar arquivos |
| `GET` | `/arquivos/{id}` | Buscar por ID |
| `GET` | `/arquivos/{bucket}/{chave}` | Download do S3 |
| `POST` | `/arquivos/{bucket}` | Upload para S3 (multipart) |
| `PATCH` | `/arquivos/{bucket}/{chave}` | Atualizar imagem |
| `DELETE` | `/arquivos/{id}` | Remover |

### Auditoria (`/audit-logs`)

| Metodo | Rota | Descricao |
| :--- | :--- | :--- |
| `GET` | `/audit-logs?data=` | Logs de auditoria por data |
| `GET` | `/audit-logs/paginado` | Listagem paginada de logs |

### Monitoramento (`/actuator`)

| Rota | Descricao |
| :--- | :--- |
| `/actuator/health` | Health check da aplicacao |
| `/actuator/info` | Informacoes da aplicacao |
| `/actuator/prometheus` | Metricas para Prometheus/Grafana |

---

## Pre-requisitos

- [Java JDK 21](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/)
- [MySQL 8](https://dev.mysql.com/downloads/mysql/)
- [Redis](https://redis.io/download)
- [RabbitMQ](https://www.rabbitmq.com/download.html)

---

## Como executar

### 1. Banco de dados

```sql
CREATE DATABASE toomate;
```

### 2. Configuracao

Edite `java/src/main/resources/application.yml` com as credenciais do seu ambiente (MySQL, Redis, RabbitMQ, AWS S3).

### 3. Build e execucao

```bash
cd java
./mvnw clean package -DskipTests
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### 4. Docker

```bash
cd java
docker build -t toomate-backend .
docker run -p 8080:8080 toomate-backend
```

A aplicacao estara disponivel em `http://localhost:8080`.

---

## Documentacao interativa

Com a aplicacao rodando, acesse o Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

---

## Autenticacao

A API utiliza **JWT (JSON Web Token)** para autenticacao stateless.

1. Registre um usuario via `POST /usuarios`
2. Autentique via `POST /usuarios/login` para obter o token
3. Inclua o token no header das requisicoes:

```
Authorization: Bearer <seu-token>
```

Roles disponiveis: `USER` e `ADMIN`.

---

## Testes

```bash
cd java
./mvnw test
```

Relatorio de cobertura gerado pelo JaCoCo em `java/target/site/jacoco/`.

---

## Licenca

Este projeto esta sob a licenca [MIT](./LICENSE).
