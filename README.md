# Avalia��o T�cnica
- Esta � uma aplica��o web que tem como objetivo realizar o cadastro de proposta de cr�dito para um determinado cliente, efetuar a an�lise de dados e efetivar a aprova��o ou nega��o de um limite de cr�dito para o mesmo.

## Entradas para a An�lise
- A proposta de cr�dito consiste em um formul�rio contendo as seguintes informa��es de cadastro de cliente:
-- Nome, CPF, idade, sexo, estado civil, estado, n�mero de dependentes e valor de renda.

##### Poss�veis Melhorias
- incluir valida��o de CPF
- melhorar as m�scaras de entrada
- implementar valida��o de campos obrigat�rios

## M�todo de An�lise
- Para criticar os dados existentes no banco de dados e realizar uma an�lise de cr�dito com base em dados hist�ricos foi utilizado o Algoritmo de Classifica��o Naive Bayes, que avalia as informa��es de entrada e defini se o cliente foi negado ou se foi aprovado, caso negado o sistema informa o motivo e caso aprovado, informa em qual faixa de limite de cr�dito o cliente se encaixou.

##### Poss�veis Melhorias
- agrupar as entradas em grupos, ao inv�s de considerar a idade de forma individual seria interessante agrup�-las atrav�s de um crit�rio que fa�a sentido na an�lise de cr�dito, uma sugest�o seria: menor de idade(<18 anos), jovem(>=18 & <30), adulto(>=30 & <50), senior(>=50), este mesmo agrupamento poderia ser feito por regi�es do pa�s, n�mero de dependentes e renda.
- analisar e testar outros algoritmos de classifica��o para identificar o mais eficaz e perform�tico.

## Resultado da An�lise  
- Ap�s a an�lise o sistema salva os dados de entrada de cada an�lise juntamente com o resultado no banco de dados, e demonstra os mesmos em uma nova linha que � adicionada no in�cio da tabela de hist�rico de an�lises.
Assim � poss�vel realizar consultas das an�lises atrav�s do CPF do cliente.

##### Poss�veis Melhorias
- adicionar pagina��o na lista de resultados.
- adicionar filtros de ordena��o na lista de resultados.
- realizar uma an�lise de UX para verificar e melhorar a experi�ncia do usu�rio tanto na inser��o de dados como na maneira com que se demonstra os resultados.

## Tecnolog�as Utilizadas
  - Frontend: [Angular5], [Html5], [Bootstrap3]
  - Backend: [Java], [Spring-boot], [Spring-data-rest]
  - Integra��es: [Rest], [Restful], [Hateoas]
  - Documenta��o: [Swagger]

## Arquitetura 
- A solu��o foi feita em tr�s projetos distintos e independentes um espec�fico para o Frontend e outro para o Backend e mais um projeto integrador.

### Backend
- Solu��o desenvolvida em Java utilizando Spring-boot, Spring-data-rest, hibernate, JPA, Lombok 
 - API Restful dispon�vel na porta 8080 contendo documenta��o swagger dos endpoints dispon�vel em `http://localhost:8080/swagger-ui.html`.
  - API para cadastro e consulta das propostas dispon�vel em `http://localhost:8080/api/v1/cliente`.
  - API motor de cr�dito que efetuar� a an�lise da proposta dispon�vel em `http://localhost:8080/api/v1/cliente/avaliaProposta`.

##### Melhorias
- trabalhar com banco de dados chave valor, Redis por exemplo, para armazenar as contagem de ocorr�ncias de forma j� pr�-processada diminuindo assim o tempo necess�rio para an�lise de cr�dito e tamb�m o n�mero de requisi��es ao Banco de Dados.
- agrupar os dados de entrada seguindo uma l�gica de neg�cio apropriada para an�lise de cr�dito

### Frontend  
- Solu��o desenvolvida em Angular5, Html5 e CSS3 utilizando Bootstrap3, [Angular CLI](https://github.com/angular/angular-cli) e hospedada em um servidor NGINX. 

##### Melhorias
- enquanto a pagina��o n�o � feito pelo backend o filtro de CPF poderia ser feito pelo frontend diminuindo o n�mero de requisi��es para o backend e por consequ�ncia ao banco de dados.
- melhoria no desacoplamento do c�digo em componentes distintos para que os mesmos possam ser reutilizados em outros lugares da aplica��o.

### Banco de dados
- para esta prova de conceito foi utilizado banco de dados em mem�ria, H2, os dados iniciais s�o populados atrav�s de um script sql na pasta de resources.

### Build
- esta solu��o � composta por projetos independentes para o server side (backend) dispon�vel por padr�o em `http://localhost:8080` e o client (frontend) disponivel por padrao em `http://localhost:4200`, ambos os projetos podem ter o build executados de forma independente. Existe um terceiro projeto integrador capaz de executar o build de ambos os projetos de forma simult�nea.

##### Parent
- esse projeto visa englobar os outros dois como sendo m�dulos de um projeto principal, este projeto pode ser executado atrav�s de Docker Compose ou de forma nativa utilizando maven e node.

##### Parent com Docker Composer
- para subir o servidor dessa aplica��o � poss�vel gerar e executar dois container docker atrav�s do Docker Compose, basta acessar o diret�rio raiz do projeto calcard-parent e executar
```sh 
$ docker-compose -f docker-compose.yml up -d --force-recreate --build
``` 
- ser�o geradas duas imagens docker chamadas `calcard-server` e `calcard-client`, ambas ser�o executadas atrav�s de containers dispon�veis nas portas `8080` (backend) e `4200` (frontend), caso os containers j� existam os mesmos ser�o destru�dos e recriados.

##### Backend com Docker
- para subir o servidor dessa aplica��o � poss�vel gerar um container docker atrav�s do Dockerfile  basta acessar o diret�rio raiz do projeto `calcard-backend `e executar
```sh 
$ mvn clean package docker:build
``` 
- ser� gerado uma imagem docker chamada `calcard-server-0.1.0` que pode ser executada atrav�s do comando
```sh 
$ docker run -d --name calcard-server -p8080:8080 calcard-server
```
##### Backend com Maven
- para compilar este projeto basta acessar o diret�rio raiz do projeto `calcard-backend` e executar o comando 
```sh 
$ mvn clean package
``` 
- para executar a aplica��o basta acessar o diret�rio raiz do projeto ``calcard-backend e executar o comando 
```sh 
$ mvn spring-boot:run
``` 
##### Frontend com Docker
- para subir a aplica��o � poss�vel gerar um container docker atrav�s do Dockerfile  basta acessar o diret�rio raiz do projeto `calcard-frontend` e executar
```sh 
$ docker build -t calcard-client .
``` 
- ser� gerado uma imagem docker chamada `calcard-client` que pode ser executada atrav�s do comando
```sh 
$ docker run -d --name calcard-client -p4200:4200 calcard-client
```
##### Frontend com Node
- para compilar este projeto basta acessar o diret�rio raiz do projeto `calcard-frontend` e executar o comando 
```sh 
$ npm install
``` 
- para executar a aplica��o basta acessar o diret�rio raiz do projeto `calcard-frontend` e executar o comando 
```sh 
$ ng serve
``` 
## Testes
##### Frontend
- Testes unit�rios: executar o comando `ng test` para rodar os testes unitarios via [Karma](https://karma-runner.github.io).
- Teste ponta a ponta: executar o comando `ng e2e` para rodar os testes ponta a ponta via [Protractor](http://www.protractortest.org/).

##### Backend
- Testes unit�rios: .
- Teste ponta a ponta: .

## Documenta��o
- esta solu��o possui documenta��o em ambos c�digos fontes e a documenta��o da API Rest est� dispon�vel atrav�s da ferramenta Swagger em /swagger-ui.html

### Observa��es 
> Devido ao curto prazo alguns pontos foram implementados por�m precisam de melhorias, algumas delas j� foram mencionadas nos itens anteriores, mas outras tamb�m se fazem necess�rias como os testes unit�rios e a documenta��o do c�digo fazem que tamb�m fazem parte do escopo do projeto.

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
   
   

