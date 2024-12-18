# Projeto de Votação

## Tecnologias Utilizadas
- **Java 17 ou superior**
- **Spring Boot 3.x**
    - Spring Web
    - Spring Data JPA
    - Spring Security
- **Banco de Dados**
    - PostgreSQL 
- **Swagger** para documentação da API
- **Apache Kafka** para comunicação assíncrona
- **Maven** como gerenciador de dependências
- **Docker** para executar o projeto em contêineres

---

## Executando esse projeto local

### 1. Pré-Requisitos
Certifique-se de que os seguintes itens estejam instalados e configurados no seu ambiente:
1. **Java 17** ou superior.
2. **Maven** 
3. **Banco de Dados PostgreSQL**
4. **Apache Kafka** 
4. **Docker** 

No diretório raiz do projeto, execute o seguinte comando para rodar a aplicação:
```shell
mvn spring-boot:run
```
A aplicação estará disponível em: http://localhost:8081
A documentação da API pode ser acessada em: http://localhost:8081/swagger-ui/index.html

### 2. Clonar o Repositório
Faça o clone do repositório para sua máquina local:
```bash
git clone https://github.com/BernardoBortolini/vote.git
cd vote
```
