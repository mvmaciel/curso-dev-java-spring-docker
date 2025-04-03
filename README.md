# Projeto do Curso: Aplicação Java com Spring Boot e Docker

Este repositório contém um projeto desenvolvido como parte do curso sobre Java e Spring Boot, abordando conceitos como microserviços, persistência de dados, autenticação e deploy com Docker.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Gradle**
- **Docker**
- **MySQL**
- **Flyway** (Migration de banco de dados)
- **Spring Security** (Autenticação e autorização)

## Pré-requisitos

Antes de iniciar, certifique-se de ter os seguintes softwares instalados:

- [Java 17](https://adoptium.net/)
- [Docker](https://www.docker.com/)
- [Gradle](https://gradle.org/)
- [Git](https://git-scm.com/)

## Configuração do Ambiente

### Clonando o repositório

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

### Configuração do Banco de Dados

O projeto utiliza um banco de dados MySQL, configurado no `docker-compose.yml`. Para subir a instância localmente:

```bash
docker-compose up -d
```

Isso criará um banco de dados MySQL acessível em `localhost:3306`.

## Executando o Projeto

### Com Gradle

```bash
./gradlew bootRun
```

### Com Docker

```bash
docker build -t meu-projeto .
docker run -p 8080:8080 meu-projeto
```

O serviço estará disponível em `http://localhost:8080`.

## Estrutura do Projeto

```
├── src
│   ├── main
│   │   ├── java/com/curso/projeto
│   │   ├── resources
│   │   │   ├── application.yml
│   │   │   ├── db/migration (migrations do Flyway)
├── build.gradle
├── Dockerfile
├── docker-compose.yml
└── README.md
```

## Endpoints Principais

| Método | Endpoint      | Descrição               |
| ------ | ------------- | ----------------------- |
| GET    | /api/usuarios | Lista todos os usuários |
| POST   | /api/usuarios | Cria um novo usuário    |
| GET    | /api/saude    | Verifica status da API  |

## Contribuindo

Sinta-se à vontade para contribuir com melhorias. Para isso:

1. Faça um fork do projeto.
2. Crie uma branch para sua feature (`git checkout -b minha-feature`).
3. Commit suas alterações (`git commit -m 'Adicionando minha feature'`).
4. Faça um push para a branch (`git push origin minha-feature`).
5. Abra um Pull Request.

## Licença

Este projeto está sob a licença GNU. Consulte o arquivo `LICENSE` para mais detalhes.

