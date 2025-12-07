<div align="center">
  <img src="./java/src/main/resources/img/LogoToomate.png" width="250px" alt="Logo Toomate">
</div>

# Toomate

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

## 📝 Descrição

Este projeto visa desenvolver um **Sistema de Gestão Integrada** para o restaurante de pequeno porte **Toomate**, que atualmente sofre com a ineficiência de **processos manuais** no controle de estoque.

A gestão manual de grandes volumes de **insumos perecíveis e não perecíveis** resulta em alto **desperdício**, **rupturas de estoque** e dificuldade em gerenciar o fluxo de caixa (incluindo **boletos pendentes** e **clientes devedores**).

### A Solução

A aplicação (API) oferece uma plataforma robusta, construída em **Spring Boot** e **MySQL**, que permite:

* **Acompanhamento em tempo real** das entradas e saídas de produtos.
* **Controle de prazos de validade** de insumos.
* **Gestão de fornecedores** e rotinas de compras.
* **Administração de débitos** (clientes e boletos).
* Geração de **relatórios** e **dashboard** para tomada de decisão.

## 🛠 Tecnologias e Dependências

Este projeto utiliza **Java 21** com **Spring Boot 3**. Abaixo estão as principais bibliotecas utilizadas e suas responsabilidades:

| Tecnologia / Dependência | Para que serve no projeto? |
| :--- | :--- |
| **Spring Web** | Permite criar a API RESTful e expor os endpoints HTTP (Tomcat embutido). |
| **Spring Data JPA** | Faz a abstração do banco de dados (ORM/Hibernate), facilitando consultas e persistência de dados. |
| **Spring Security** | Gerencia a segurança, autenticação e autorização de acesso aos endpoints. |
| **Bean Validation** | Valida os dados de entrada (DTOs), garantindo que não cheguem dados nulos ou inválidos (ex: `@NotNull`, `@Email`). |
| **JJWT (JSON Web Token)** | Bibliotecas (`api`, `impl`, `jackson`) usadas para gerar, assinar e validar Tokens JWT para login seguro e stateless. |
| **SpringDoc OpenAPI** | Gera automaticamente a documentação interativa da API (Swagger UI). |
| **AWS SDK S3** | Conecta a aplicação ao Amazon S3 para armazenamento de arquivos e imagens na nuvem. |
| **H2 Database** | Banco de dados em memória. Utilizado para testes rápidos ou execução local sem necessidade de instalação externa. |
| **Spring Boot Test** | Conjunto de ferramentas (JUnit, Mockito) para realizar testes automatizados na aplicação. |
| **Spring Security Test** | Utilitários para testar fluxos de autenticação e permissões de segurança. |

---

## ⚙️ Como Configurar e Executar

### 1. Pré-requisitos
Certifique-se de ter instalado em sua máquina:
- [Java JDK 21](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/)
- [MySQL](https://dev.mysql.com/downloads/mysql/)

### 2. Configuração do Banco de Dados
Crie um banco de dados no MySQL com o nome desejado:

```sql
CREATE DATABASE Toomate;
```

## 📖 Documentação da API (Swagger)
Com a aplicação rodando, acesse o Swagger UI para visualizar e testar os endpoints:

http://localhost:8080/swagger-ui.html

## 📝 Licença

Este projeto está sob a licença [MIT](./LICENSE).