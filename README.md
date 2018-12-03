# Avaliação Técnica
- Esta é uma aplicação web que tem como objetivo realizar o cadastro de proposta de crédito para um determinado cliente, efetuar a análise de dados e efetivar a aprovação ou negação de um limite de crédito para o mesmo.

## Entradas para a Análise
- A proposta de crédito consiste em um formulário contendo as seguintes informações de cadastro de cliente:
-- Nome, CPF, idade, sexo, estado civil, estado, número de dependentes e valor de renda.

##### Possíveis Melhorias
- incluir validação de CPF
- melhorar as máscaras de entrada
- implementar validação de campos obrigatórios

## Método de Análise
- Para criticar os dados existentes no banco de dados e realizar uma análise de crédito com base em dados históricos foi utilizado o Algoritmo de Classificação Naive Bayes, que avalia as informações de entrada e defini se o cliente foi negado ou se foi aprovado, caso negado o sistema informa o motivo e caso aprovado, informa em qual faixa de limite de crédito o cliente se encaixou.

##### Possíveis Melhorias
- agrupar as entradas em grupos, ao invés de considerar a idade de forma individual seria interessante agrupá-las através de um critério que faça sentido na análise de crédito, uma sugestão seria: menor de idade(<18 anos), jovem(>=18 & <30), adulto(>=30 & <50), senior(>=50), este mesmo agrupamento poderia ser feito por regiões do país, número de dependentes e renda.
- analisar e testar outros algoritmos de classificação para identificar o mais eficaz e performático.

## Resultado da Análise  
- Após a análise o sistema salva os dados de entrada de cada análise juntamente com o resultado no banco de dados, e demonstra os mesmos em uma nova linha que é adicionada no início da tabela de histórico de análises.
Assim é possível realizar consultas das análises através do CPF do cliente.

##### Possíveis Melhorias
- adicionar paginação na lista de resultados.
- adicionar filtros de ordenação na lista de resultados.
- realizar uma análise de UX para verificar e melhorar a experiência do usuário tanto na inserção de dados como na maneira com que se demonstra os resultados.

## Tecnologías Utilizadas
  - Frontend: [Angular5], [Html5], [Bootstrap3]
  - Backend: [Java], [Spring-boot], [Spring-data-rest]
  - Integrações: [Rest], [Restful], [Hateoas]
  - Documentação: [Swagger]

## Arquitetura 
- A solução foi feita em três projetos distintos e independentes um específico para o Frontend e outro para o Backend e mais um projeto integrador.

### Backend
- Solução desenvolvida em Java utilizando Spring-boot, Spring-data-rest, hibernate, JPA, Lombok 
 - API Restful disponível na porta 8080 contendo documentação swagger dos endpoints disponível em `http://localhost:8080/swagger-ui.html`.
  - API para cadastro e consulta das propostas disponível em `http://localhost:8080/api/v1/cliente`.
  - API motor de crédito que efetuará a análise da proposta disponível em `http://localhost:8080/api/v1/cliente/avaliaProposta`.

##### Melhorias
- trabalhar com banco de dados chave valor, Redis por exemplo, para armazenar as contagem de ocorrências de forma já pré-processada diminuindo assim o tempo necessário para análise de crédito e também o número de requisições ao Banco de Dados.
- agrupar os dados de entrada seguindo uma lógica de negócio apropriada para análise de crédito

### Frontend  
- Solução desenvolvida em Angular5, Html5 e CSS3 utilizando Bootstrap3, [Angular CLI](https://github.com/angular/angular-cli) e hospedada em um servidor NGINX. 

##### Melhorias
- enquanto a paginação não é feito pelo backend o filtro de CPF poderia ser feito pelo frontend diminuindo o número de requisições para o backend e por consequência ao banco de dados.
- melhoria no desacoplamento do código em componentes distintos para que os mesmos possam ser reutilizados em outros lugares da aplicação.

### Banco de dados
- para esta prova de conceito foi utilizado banco de dados em memória, H2, os dados iniciais são populados através de um script sql na pasta de resources.

### Build
- esta solução é composta por projetos independentes para o server side (backend) disponível por padrão em `http://localhost:8080` e o client (frontend) disponivel por padrao em `http://localhost:4200`, ambos os projetos podem ter o build executados de forma independente. Existe um terceiro projeto integrador capaz de executar o build de ambos os projetos de forma simultânea.

##### Parent
- esse projeto visa englobar os outros dois como sendo módulos de um projeto principal, este projeto pode ser executado através de Docker Compose ou de forma nativa utilizando maven e node.

##### Parent com Docker Composer
- para subir o servidor dessa aplicação é possível gerar e executar dois container docker através do Docker Compose, basta acessar o diretório raiz do projeto calcard-parent e executar
```sh 
$ docker-compose -f docker-compose.yml up -d --force-recreate --build
``` 
- serão geradas duas imagens docker chamadas `calcard-server` e `calcard-client`, ambas serão executadas através de containers disponíveis nas portas `8080` (backend) e `4200` (frontend), caso os containers já existam os mesmos serão destruídos e recriados.

##### Backend com Docker
- para subir o servidor dessa aplicação é possível gerar um container docker através do Dockerfile  basta acessar o diretório raiz do projeto `calcard-backend `e executar
```sh 
$ mvn clean package docker:build
``` 
- será gerado uma imagem docker chamada `calcard-server-0.1.0` que pode ser executada através do comando
```sh 
$ docker run -d --name calcard-server -p8080:8080 calcard-server
```
##### Backend com Maven
- para compilar este projeto basta acessar o diretório raiz do projeto `calcard-backend` e executar o comando 
```sh 
$ mvn clean package
``` 
- para executar a aplicação basta acessar o diretório raiz do projeto ``calcard-backend e executar o comando 
```sh 
$ mvn spring-boot:run
``` 
##### Frontend com Docker
- para subir a aplicação é possível gerar um container docker através do Dockerfile  basta acessar o diretório raiz do projeto `calcard-frontend` e executar
```sh 
$ docker build -t calcard-client .
``` 
- será gerado uma imagem docker chamada `calcard-client` que pode ser executada através do comando
```sh 
$ docker run -d --name calcard-client -p4200:4200 calcard-client
```
##### Frontend com Node
- para compilar este projeto basta acessar o diretório raiz do projeto `calcard-frontend` e executar o comando 
```sh 
$ npm install
``` 
- para executar a aplicação basta acessar o diretório raiz do projeto `calcard-frontend` e executar o comando 
```sh 
$ ng serve
``` 
## Testes
##### Frontend
- Testes unitários: executar o comando `ng test` para rodar os testes unitarios via [Karma](https://karma-runner.github.io).
- Teste ponta a ponta: executar o comando `ng e2e` para rodar os testes ponta a ponta via [Protractor](http://www.protractortest.org/).

##### Backend
- Testes unitários: .
- Teste ponta a ponta: .

## Documentação
- esta solução possui documentação em ambos códigos fontes e a documentação da API Rest está disponível através da ferramenta Swagger em /swagger-ui.html

### Observações 
> Devido ao curto prazo alguns pontos foram implementados porém precisam de melhorias, algumas delas já foram mencionadas nos itens anteriores, mas outras também se fazem necessárias como os testes unitários e a documentação do código fazem que também fazem parte do escopo do projeto.

   [AngularJS]: <http://angularjs.org>	
   [Html5]: <https://www.w3.org/TR/html5/>
   [Bootstrap]: <http://getbootstrap.com/>
   [Java]: <http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html>
   [Spring-boot]: <https://projects.spring.io/spring-boot/>
   [Rest]: <https://www.w3.org/2001/sw/wiki/REST>
   [Swagger]: <https://swagger.io/>  
   [Angular5]: <https://blog.angular.io/version-5-0-0-of-angular-now-available-37e414935ced>
   [Bootstrap3]: <https://getbootstrap.com/docs/3.3/>
   [Spring-data-rest]: <https://spring.io/projects/spring-data-rest>
   [Restful]:<https://www.devmedia.com.br/introducao-a-web-services-restful/37387>
   [Hateoas]: <https://spring.io/understanding/HATEOAS>
   
   

