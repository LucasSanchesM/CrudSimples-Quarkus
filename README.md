# CrudSimples-Quarkus

API REST de cadastro de usuários (CRUD) construída com **Quarkus**, **Hibernate ORM com Panache** e **PostgreSQL**. Este foi o meu primeiro projeto com Quarkus, feito para aprender o framework vindo do Spring Boot.

## Tecnologias

- Java 21
- [Quarkus](https://quarkus.io/) (Quarkus REST + Jackson)
- Hibernate ORM com Panache (padrão Repository)
- PostgreSQL 17
- Docker Compose
- Maven (wrapper incluso)

## Funcionalidades

- Criação de usuários
- Listagem paginada de usuários
- Busca de usuário por ID (UUID)
- Persistência em PostgreSQL rodando em container, com volume para manter os dados entre reinicializações

## Estrutura do projeto

```
src/main/java/org/acme
├── controler/    # Endpoints REST
├── entity/       # Entidades JPA
├── exception/    # Tratamento de exceções
├── repository/   # Acesso a dados (Panache)
└── service/      # Regras de negócio
```

## Pré-requisitos

- JDK 21 (ou superior, conforme o `pom.xml`)
- Docker e Docker Compose
- Maven (opcional, o projeto inclui `./mvnw`)

## Como executar

### 1. Clonar o repositório

```bash
git clone https://github.com/LucasSanchesM/CrudSimples-Quarkus.git
cd CrudSimples-Quarkus
```

### 2. Subir o banco de dados

```bash
docker compose up -d
```

O PostgreSQL fica disponível em `localhost:5431` (mapeado para a porta 5432 do container). Confira se subiu com `docker compose ps`.

### 3. Configurar a aplicação

Em `src/main/resources/application.properties`:

```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=${DB_USER:user}
quarkus.datasource.password=${DB_PASSWORD:1234}
quarkus.datasource.jdbc.url=${DB_URL:jdbc:postgresql://localhost:5431/bancodedados}

quarkus.hibernate-orm.schema-management.strategy=update
```

Os valores depois de `:` são padrões **apenas para desenvolvimento local**. Em qualquer outro ambiente, defina as variáveis de ambiente `DB_USER`, `DB_PASSWORD` e `DB_URL`.

### 4. Rodar em modo dev

```bash
./mvnw quarkus:dev
```

A API sobe em `http://localhost:8080`. O modo dev tem hot reload e a Dev UI em `http://localhost:8080/q/dev/`.

## Endpoints

Base: `/users`

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/users` | Cria um usuário |
| `GET` | `/users?page=0&pageSize=10` | Lista usuários com paginação |
| `GET` | `/users/{userId}` | Busca um usuário pelo UUID |

### Exemplos

**Criar usuário**

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name": "Lucas", "email": "lucas@email.com"}'
```

**Listar usuários**

```bash
curl "http://localhost:8080/users?page=0&pageSize=10"
```

**Buscar por ID**

```bash
curl http://localhost:8080/users/<uuid>
```

> Ajuste os campos do corpo da requisição conforme os atributos da entidade `User`.

## Comandos úteis

```bash
docker compose stop        # para o banco (dados preservados)
docker compose down        # remove o container (volume continua)
docker compose down -v     # remove também o volume (APAGA os dados)
docker compose logs -f     # acompanha os logs do banco
```

## Empacotamento

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

Executável nativo (requer GraalVM, ou use o build em container):

```bash
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

## Segurança

- Não versione credenciais reais. Use variáveis de ambiente ou um arquivo `.env` listado no `.gitignore`.
- A senha `1234` do `docker-compose.yaml` serve apenas para desenvolvimento local.
- `schema-management.strategy=update` é indicado só para desenvolvimento. Em produção, use migrations (Flyway ou Liquibase).

## Próximos passos

- [ ] Endpoints de atualização (`PUT`/`PATCH`) e remoção (`DELETE`)
- [ ] DTOs de entrada e saída
- [ ] Validação com Hibernate Validator
- [ ] Migrations com Flyway
- [ ] Testes automatizados

## Autor

**Lucas Sanches**: [@LucasSanchesM](https://github.com/LucasSanchesM)
