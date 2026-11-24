# BSAA — Catálogo Resident Evil (API REST + Aplicação Web)

Aplicação web integrada a uma API REST em Java com Spring Boot para catalogar e gerenciar registros do universo *Resident Evil* (**Personagens**, **Sobreviventes**, **Incidentes**, **Vírus** e **Criaturas**).

## Problema atendido, público usuário e funcionalidades
- **Problema:** centralizar o cadastro, consulta, atualização e remoção de registros catalogados sem depender de planilhas ou anotações soltas.
- **Público usuário:** operadores/analistas de arquivos e estudantes/avaliadores testando o fluxo completo entre interface web, API REST e banco de dados.
- **Funcionalidades:**
    - Operações CRUD para 5 entidades independentes: `personagens`, `sobreviventes`, `incidentes`, `virus` e `criaturas`.
    - Interface web em `src/main/resources/static/` (`index.html`, `styles.css`, `app.js`) consumindo a API via `fetch`, com abas por entidade, filtro, edição, exclusão e atualização dinâmica sem recarregar a página.

## Tecnologias utilizadas
- **Backend:** Java, Spring Boot, Spring Web, Spring Data JPA, MySQL Driver
- **Documentação:** Springdoc OpenAPI / Swagger UI
- **Frontend:** HTML5, CSS3, JavaScript (Fetch API)
- **Banco de dados:** Banco relacional configurado via JPA (ajustar conforme o `application.properties`)

## Pré-requisitos e instruções de instalação e execução
1. Ter o **JDK** e o **Maven** (ou `./mvnw` / `mvnw.cmd`) instalados.
2. Configurar o banco de dados e as variáveis de ambiente.
3. Subir a aplicação:
   ```bash
   ./mvnw spring-boot:run