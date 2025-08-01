# 🍔 Delivery Tech API

API REST para sistema de delivery desenvolvida com **Java 21** e **Spring Boot 3.5.3**, focada em boas práticas de arquitetura, segurança, testes, monitoramento e observabilidade.

---

## 🚀 Tecnologias Utilizadas

- **Java 21 LTS**
- **Spring Boot 3.5.3**
- Spring Web
- Spring Data JPA
- Spring Security + JWT
- SpringDoc OpenAPI (Swagger)
- Spring Boot Actuator
- Micrometer + Prometheus
- H2 Database (dev)
- Logback (logs estruturados)
- Maven
- JaCoCo (cobertura de testes)
- Spring Cache (ConcurrentMap)

---

## ⚙️ Principais Funcionalidades

- CRUD de entidades: restaurantes, produtos, pedidos, clientes, etc.
- Autenticação e autorização com JWT
- Validações personalizadas com anotações customizadas
- Filtros dinâmicos e projeções em consultas
- Tratamento global de exceções
- Observabilidade:
  - Health Checks customizados
  - Métricas expostas para Prometheus
  - Logging estruturado com correlação de requisições
  - Tracing distribuído com Spring Cloud Sleuth
- Cache de produtos por categoria com @Cacheable/@CacheEvict

---

## 🧠 Cache de Produtos por Categoria (NOVO)

### ✅ Objetivo
Melhorar a performance do sistema reduzindo consultas repetidas ao banco de dados ao listar produtos por categoria.

### Estratégia
- Utilizado **ConcurrentMapCache** (in-memory).
- Nome do cache: `produtosPorCategoria`
- Anotações utilizadas:
  - `@Cacheable`: para armazenar produtos por categoria.
  - `@CacheEvict`: para limpar o cache ao salvar novos produtos.

### Exemplo:
@Cacheable("produtosPorCategoria")
public List<Produto> buscarPorCategoria(String categoria) { ... }

@CacheEvict(value = "produtosPorCategoria", allEntries = true)
public Produto salvarProduto(Produto produto) { ... }

### Teste automatizado
- Verifica se o cache é utilizado e invalidado corretamente com uso de `@MockBean` e `verify(...)` do Mockito.
- Resultado: ✅ 100% verde após configuração correta do cache.

### Prints de validação
Antes (erro) e Depois (sucesso): test-falhou.png, test-passou.png

---

## 🧪 Testes

- Testes unitários e de integração com cobertura JaCoCo
- Pacote `integration` dedicado para testes de integração
- Cobertura de testes para controllers, serviços e validações
- Testes de cache com simulação de chamadas e verificação de invocação

---

## 📁 Estrutura de Pacotes

```
com.deliverytech.delivery
├── config              # Configurações gerais (Swagger, Security, Micrometer)
├── controllers         # Controllers REST
├── dtos                # DTOs organizados por tipo (request, response)
│   ├── request
│   └── response
├── entities            # Entidades JPA
├── enums               # Enums da aplicação
├── exceptions          # Tratamento global de exceções
├── filter              # Filtros HTTP (ex: CorrelationIdFilter)
├── health              # Health indicators customizados
├── repositories        # Repositórios JPA
├── security            # JWT, filtros de autenticação, configs de segurança
├── services            # Regras de negócio
├── validations         # Validações customizadas (ex: anotação de CPF)
```

> Os testes seguem a mesma estrutura de pacotes em `src/test/java`.

---

## 📋 Endpoints úteis

| Endpoint                  | Descrição                            |
|---------------------------|----------------------------------------|
| `GET /health`            | Status da aplicação e dependências    |
| `GET /info`              | Informações da aplicação              |
| `GET /swagger-ui.html`   | Documentação da API (Swagger)         |
| `GET /h2-console`        | Console do banco H2                   |
| `GET /actuator/prometheus` | Métricas Prometheus (Micrometer)     |

---

## 🏃 Como Executar

1. **Pré-requisitos:**
   - JDK 21 instalado
   - Maven Wrapper

2. **Clone o projeto:**
   ```bash
   git clone https://github.com/seu-usuario/delivery-tech-api.git
   cd delivery-tech-api
   ```

3. **Execute a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Acesse os serviços:**
   - Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - Health: [http://localhost:8080/health](http://localhost:8080/health)
   - Banco: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

---

## 📦 Recursos Java Modernos

- **Records** (DTOs)
- **Text Blocks**
- **Pattern Matching**
- **Virtual Threads (Java 21 preview)**

---

## 👩‍💻 Autora

**Regina Braga Reis**  
_Arquitetura de Sistemas — Turma 01_

---

## 📌 Histórico de Commits (Resumo)

- 🔐 Segurança com JWT e perfis de usuários
- 📄 Documentação Swagger com SpringDoc
- 🧪 Cobertura de testes com JaCoCo
- 📊 Métricas com Actuator, Micrometer e Prometheus
- 📑 Logging estruturado com correlação
- 🔭 Tracing distribuído com Spring Cloud Sleuth
- 🧠 Cache de produtos por categoria com @Cacheable/@CacheEvict + teste automatizado
---