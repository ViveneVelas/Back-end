# Backend Vivene Velas

O backend do projeto **Vivene Velas** foi desenvolvido utilizando **Java 17** como linguagem de programação, e **Maven** como ferramenta de automação de compilação. O serviço é baseado em uma API REST construída com **Spring Boot**.
 
[![My Skills](https://skillicons.dev/icons?i=java,maven,spring,)](https://skillicons.dev)

O backend oferece um serviço completo com base na arquitetura RESTful, permitindo operações CRUD (Create, Read, Update, Delete) para gerenciar os dados relacionados às velas.

## Andamento do projeto

<p align = "center">
<img src="https://img.shields.io/static/v1?label=STATUS&message=EM%20ANDAMENTO&color=yellow&style=for-the-badge"/>
</p>

## Convenções de Codificação

- **Idioma:** Português
- **Padrão para Variáveis:** Camel Case
- **Padrão para Métodos:** Pascal Case
- **Padrão para Pastas:** Minúsculas

## Estrutura do Projeto

- **Commons:** Contém classes base, como exceções e permissões para requisições do frontend (porta: 3000).

- **Controller:** Gerencia as requisições e respostas da API, incluindo a documentação Swagger e a ligação com os serviços.

- **Datastructure:** Implementa a lógica da HashTable e o Node.

- **DTO:** Contém Data Transfer Objects (DTOs) para Request e Response, além dos mappers separados por entidade.

- **Entity:** Define as entidades e views do banco de dados.

- **Repository:** Gerencia a conexão entre as entidades e o banco de dados.

- **Service:** Implementa a lógica de negócio e validações da aplicação.

## Acesso ao backend

Este backend permite que qualquer requisição (GET, POST, UPDATE, DELETE) do backend ([http://localhost:3000](http://localhost:3000)) possa acessar os endpoints definidos na API.

## Especificações Necessárias

### Controller

- **Cliente:**  Gerencia requisições relacionadas aos clientes.

- **Login:** Gerencia requisições relacionadas ao login dos usuários na plataforma.

- **Lote:** Gerencia requisições relacionadas aos lotes de velas.

- **Meta:** Gerencia requisições relacionadas às metas mensais.

- **Pedido:** Gerencia requisições relacionadas aos pedidos.

- **PedidoLote:** Gerencia requisições que envolvem o relacionamento entre pedidos e lotes (relacionamento muitos para muitos).

- **Usuario:** Gerencia requisições relacionadas aos usuários (beneficiários).

- **Vela:** Gerencia requisições relacionadas às velas.

- **Venda:** Gerencia requisições relacionadas às vendas.
