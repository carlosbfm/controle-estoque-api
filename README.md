# 📦 API de Lançamento de Produtos e Controle de Estoque

API REST desenvolvida em **Java 21** e **Spring Boot** focada na gestão eficiente de estoque. O projeto se destaca pela utilização de chaves de negócio (Matrícula e SKU) para garantir integridade referencial, geração automática de códigos e atualizações granulares via `PATCH`.

---

## 📌 Sumário

* [Sobre o Projeto](#-sobre-o-projeto)
* [Destaques Técnicos](#-destaques-técnicos)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Arquitetura de Dados](#-arquitetura-de-dados)
* [Documentação da API](#-documentação-da-api)
* [Configuração e Execução](#-configuração-e-execução)
* [Docker Compose](#-docker-compose)
* [Autor](#-autor)

---

## 📝 Sobre o Projeto

Este sistema foi projetado para controlar o fluxo de mercadorias e o cadastro de colaboradores, com foco em regras de negócio sólidas:

* **Gestão de Produtos:** Cadastro com geração automática de código identificador (SKU) e controle preciso de preços (`BigDecimal`).
* **Rastreabilidade:** Todas as movimentações (Entrada/Saída) são vinculadas a um funcionário através de sua **Matrícula** e ao produto através do seu **Código**, sem expor IDs numéricos de banco de dados.
* **Auditoria:** Registro automático de data de criação e atualização nas movimentações.

---

## 💡 Destaques Técnicos

1.  **Atualizações Granulares (PATCH):**
    * Implementação de rotas específicas para alterar campos isolados dos produtos (apenas preço, apenas estoque, apenas descrição), economizando banda e processamento.

2.  **Chaves de Negócio (Business Keys):**
    * A API utiliza a **Matrícula** (`String`) e o **Código do Produto** (`String`) como chaves principais nas rotas e relacionamentos (`@JoinColumn referencedColumnName`), em vez de depender de chaves primárias numéricas na comunicação externa.

3.  **Sanitização e Formatação:**
    * Tratamento de datas para remoção de nanossegundos e padronização de saída (`dd/MM/yyyy HH:mm:ss`).
    * Respostas limpas (DTOs) evitando loops de serialização.

4.  **Mapeamento Avançado:**
    * Uso de `AttributeConverter` para persistência automática de Enums (Cargos).

---

## 🚀 Tecnologias Utilizadas

Baseado no `pom.xml`:

* **Java 21** (LTS)
* **Spring Boot 4.0.0**
    * Spring Web MVC
    * Spring Data JPA
    * Spring Validation
* **PostgreSQL** (Driver oficial)
* **Lombok**
* **Docker Compose Support**

---

## 🗂 Arquitetura de Dados

### 📦 Produtos (`Produtos`)
* **Código (`ID`):** String (Gerado ex: "ABC123")
* **Financeiro:** Preço de Custo e Venda (`BigDecimal`)
* **Estoque:** Quantidade atual (`Integer`)

### 👤 Funcionários (`Funcionarios`)
* **Matrícula (Chave Única):** String
* **Dados:** Nome, Data de Nascimento
* **Cargo:** Enum (Mapeado via Converter)

### 📋 Movimentação (`Movimentacao`)
* **Relacionamentos:** Vincula `Produtos` (via código) e `Funcionarios` (via matrícula).
* **Tipo:** ENTRADA / SAIDA (Enum)
* **Auditoria:** Data Cadastro e Data Atualização.

---

## 🔌 Documentação da API

### 📦 Produtos (`ProdutoController`)

| Método | Rota | Descrição |
|---|---|---|
| **POST** | `/produtos` | Cadastra novo produto |
| **GET** | `/produtos/{codigo}` | Busca produto pelo código |
| **GET** | `/produtos/pesquisar?nome={nome}` | Pesquisa por nome |
| **PATCH** | `/produtos/{codigo}/nome` | Atualiza apenas o nome |
| **PATCH** | `/produtos/{codigo}/descricao` | Atualiza apenas a descrição |
| **PATCH** | `/produtos/{codigo}/preco-custo` | Atualiza apenas o preço de custo |
| **PATCH** | `/produtos/{codigo}/preco-venda` | Atualiza apenas o preço de venda |
| **PATCH** | `/produtos/{codigo}/estoque` | Atualiza manualmente o estoque |
| **DELETE**| `/produtos/{codigo}` | Remove um produto |

### 👤 Funcionários (`FuncionarioController`)

| Método | Rota | Descrição |
|---|---|---|
| **POST** | `/funcionarios` | Cadastra funcionário |
| **GET** | `/funcionarios/{matricula}` | Busca por matrícula |
| **GET** | `/funcionarios/pesquisar?nome={nome}` | Busca por nome |
| **GET** | `/funcionarios/cargo?cargo={TIPO}` | Filtra funcionários por cargo |
| **PUT** | `/funcionarios/{matricula}` | Atualização completa de cadastro |
| **PATCH** | `/funcionarios/cargo/{cargoAlvo}` | Atualiza o cargo do funcionário |
| **DELETE**| `/funcionarios/{matricula}` | Remove funcionário |

### 📝 Movimentações (`MovimentacaoController`)

| Método | Rota | Descrição |
|---|---|---|
| **POST** | `/movimentacoes` | Registra entrada ou saída |
| **GET** | `/movimentacoes/funcionario/{matricula}` | Lista movimentações de um funcionário |
| **GET** | `/movimentacoes/tipo?tipo={ENTRADA/SAIDA}`| Filtra por tipo de operação |
| **GET** | `/movimentacoes/data?data={AAAA-MM-DD}` | Filtra por data específica |
| **DELETE**| `/movimentacoes/{id}` | Remove um registro de movimentação |

---

## ⚙️ Configuração e Execução

### 1. Clonar o Repositório
```bash
git clone [https://github.com/carlosbfm/controle-estoque-api.git](https://github.com/carlosbfm/controle-estoque-api.git)
```

### 2. Configuração do Banco de Dados
Configure o arquivo `src/main/resources/application.properties` (ou YAML) com as credenciais do seu PostgreSQL local.

**Opção A: `application.properties` (Recomendado)**
```properties
# --- Conexão com Banco de Dados ---
spring.datasource.url=jdbc:postgresql://localhost:5432/produtos_db
spring.datasource.username=postgres
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver

# --- Configuração JPA / Hibernate ---
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

**Opção B: `application.yml`**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/produtos_db
    username: postgres
    password: sua_senha
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

*Referências de Configuração:*
* [Spring Boot Data Access](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.sql.datasource.configuration)
* [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/documentation/use/)

### 3. Execução
Com o banco configurado e rodando, inicie a aplicação via Maven:

```bash
mvn spring-boot:run
```

---

## 🐳 Docker Compose

O projeto inclui o suporte ao `spring-boot-docker-compose` e possui um arquivo `compose.yaml` na raiz para facilitar a criação do ambiente (Banco + App).

⚠️ **Status Atual: Desativado / Manual**

* O arquivo `compose.yaml` está presente no repositório para fins de documentação e uso futuro.
* Atualmente, a execução automática via Docker Compose pode estar **desativada** ou requerer ajustes manuais nas variáveis de ambiente.
* Caso deseje utilizar containerização, certifique-se de ter o Docker instalado e descomente os serviços no arquivo YAML.

---

## 👨‍💻 Autor

**Carlos Manoel**
* [GitHub](https://github.com/carlosbfm)
* [LinkedIn](https://www.linkedin.com/)