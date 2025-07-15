# Delivery Tech API

Sistema de delivery desenvolvido com Spring Boot e Java 21.

## 🚀 Tecnologias
- **Java 21 LTS** (versão mais recente)
- Spring Boot 3.5.3
- Spring Web
- Spring Data JPA
- Spring Security
- H2 Database
- Maven
- Swagger (via SpringDoc OpenAPI)

## ⚡ Recursos Modernos Utilizados
- Records (Java 14+)
- Text Blocks (Java 15+)
- Pattern Matching (Java 17+)
- Virtual Threads (Java 21)

## 🏃️ Como executar
1. **Pré-requisitos:** JDK 21 instalado
2. Clone o repositório
3. Execute: `./mvnw spring-boot:run`
4. Acesse:
   - http://localhost:8080/health — status da aplicação
   - http://localhost:8080/swagger-ui.html — documentação interativa da API
   - http://localhost:8080/h2-console — console do banco H2

## 📋 Endpoints
- `GET /health` — Status da aplicação (inclui versão Java)
- `GET /info` — Informações da aplicação
- `GET /h2-console` — Console do banco H2
- Swagger:
  - `GET /swagger-ui.html`
  - `GET /v3/api-docs`

## 🔧 Configuração
- Porta: 8080
- Banco: H2 em memória
- Profile: development
- Swagger e H2 liberados no SecurityConfig

## 💻 Desenvolvedor
Regina Braga Reis — Arquitetura de Sistemas — Turma 01  
Desenvolvido com JDK 21 e Spring Boot 3.5.3