# Projeto API Rest Cadastro de Produtos

![](https://github.com/edvaldoljr/Projeto-Java-API-Rest-Produtos/blob/main/img/img-projeto.gif?raw=true)

Esta API RESTful é projetada para gerenciar produtos em um sistema. Ela utiliza o framework Spring Boot e o banco de dados JPA para armazenar e recuperar informações sobre os produtos. A API oferece duas operações principais: listar todos os produtos cadastrados e salvar um novo produto.

A classe ProdutoRepository é responsável por gerenciar as operações de banco de dados, utilizando JPA para acessar e persistir os dados

E está sendo persistida em um banco de dados PostgreSQL. Para facilitar o desenvolvimento e testes, foi criada uma imagem no Docker que possui o banco já configurado e pronto para uso.

A  API possui dois endpoints principais: "/produtos" e "/salvar". O endpoint "/produtos" retorna uma lista de todos os produtos cadastrados no banco de dados. Já o endpoint "/salvar" permite o cadastro de novos produtos na base.

Além disso, estamos utilizando o Swagger para documentação da API, permitindo uma fácil visualização e testes dos endpoints diretamente pelo navegador em "http://localhost:8080/swagger-ui/index.html".

## Para utilizar essa API, é necessário ter os seguintes requisitos:

- Java 8 ou superior
- Maven
- Docker (para subir a imagem do banco PostgreSQL)
- Postman ou outra ferramenta de teste de API REST
- Conhecimento básico de Spring Boot e JPA.

# **Desenvolvimento**

## Class ApiApplication

A classe ApiApplication é a classe principal da aplicação, responsável por iniciar a execução do projeto. Ela possui a anotação @SpringBootApplication, que indica que é uma classe de configuração principal do Spring Boot, e automaticamente configura as configurações necessárias para que a aplicação funcione corretamente.

O método main é o método principal da classe, responsável por iniciar a aplicação. Ele chama o método estático run da classe SpringApplication, passando como parâmetros a própria classe ApiApplication e os argumentos passados pelo usuário na linha de comando. Esse método é responsável por criar um contexto do Spring, gerenciar os beans e iniciar o servidor embutido.

```java
// Importação da classe SpringApplication da biblioteca Spring Boot para iniciar a aplicação.
import org.springframework.boot.SpringApplication;
// Importação da classe SpringBootApplication da biblioteca Spring Boot para configuração automática da aplicação.
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Anotação para indicar que essa classe é a classe principal da aplicação e que ela possui configurações de automação.
@SpringBootApplication
public class ApiApplication {

// Método principal que será executado quando a aplicação for iniciada.
public static void main(String[] args) {
	// Método da classe SpringApplication para iniciar a aplicação.
	SpringApplication.run(ApiApplication.class, args);
}
}
```

## class Produto

A classe "Produto" é uma classe Java que representa um produto em uma aplicação. Ela é marcada com a anotação @Entity, indicando que ela é uma entidade JPA (Java Persistence API) e que será mapeada para uma tabela no banco de dados. A tabela será chamada de "TB_PRODUTO".

A classe possui 4 atributos: id, nome, quantidade e valor. O atributo id é uma chave primária gerada automaticamente pelo banco de dados ao salvar um novo produto na tabela. O atributo nome é uma string que representa o nome do produto e é marcado com a anotação @NotNull, indicando que o nome não pode ser nulo. O atributo quantidade é um BigDecimal que representa a quantidade disponível do produto e também é marcado com a anotação @NotNull. O atributo valor é um BigDecimal que representa o valor do produto e também é marcado com a anotação @NotNull.

A classe implementa a interface Serializable, permitindo que seja serializada e armazenada em arquivos ou transmitida através de redes. A classe também possui métodos getters e setters para cada atributo, além de métodos equals, hashCode e toString. O método equals compara dois objetos Produto baseados em seus id, nome, quantidade e valor. O método hashCode retorna um código hash único para cada objeto Produto. O método toString retorna uma string representando o objeto Produto.

```java
package com.produtos.apirest.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="TB_PRODUTO")
public class Produto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotNull
	private String nome;
	
	@NotNull
	private BigDecimal quantidade;
	
	@NotNull
	private BigDecimal valor;

	public Produto() {
	}

	public Produto(long id, String nome, BigDecimal quantidade, BigDecimal valor) {
		this.id = id;
		this.nome = nome;
		this.quantidade = quantidade;
		this.valor = valor;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Produto produto = (Produto) o;
		return id == produto.id && Objects.equals(nome, produto.nome) && Objects.equals(quantidade, produto.quantidade) && Objects.equals(valor, produto.valor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, quantidade, valor);
	}

	@Override
	public String toString() {
		return "Produto{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", quantidade=" + quantidade +
				", valor=" + valor +
				'}';
	}
}
```

## class ProdutoRepository

Esta classe "ProdutoRepository" é uma interface que estende o "JpaRepository" do Spring Data JPA, que fornece um conjunto de recursos prontos para acesso a dados usando o JPA (Java Persistence API). A interface "ProdutoRepository" está mapeada para a entidade "Produto" e especifica que a chave primária desta entidade é do tipo Long. Além disso, ela tem um método "findById" que retorna um objeto "Produto" pelo seu identificador (id). O "JpaRepository" fornece uma série de operações CRUD (Create, Read, Update, Delete) básicas para a entidade "Produto", sem precisar escrever implementações.

```java
package com.produtos.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.produtos.apirest.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	Produto findById(long id);
}
```

## class ProdutoResource

Esta é uma classe de controlador de API REST na estrutura Spring que define os terminais para gerenciamento de produtos. A classe é anotada com @RestController e @RequestMapping("/api") que especifica a URL base da API.

A classe usa ProdutoRepository para acessar os dados dos produtos em um banco de dados. A anotação @Autowired é usada para criar automaticamente uma instância do repositório.

A classe tem dois endpoints, /produtos e /salvar. O primeiro retorna uma lista de todos os produtos chamando o método findAll() do repositório. O segundo é usado para salvar um novo produto chamando o método save() do repositório e passando as informações do produto como @RequestBody.

```java
package com.produtos.apirest.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.produtos.apirest.repository.ProdutoRepository;
import com.produtos.apirest.models.Produto;


@RestController
@RequestMapping("/api")
public class ProdutoResource {

	@Autowired
	ProdutoRepository produtoRepository;
	
	@GetMapping("/produtos")
	@ResponseStatus(HttpStatus.OK)
	public List<Produto> listaProdutos(){
		return produtoRepository.findAll();
	}

	@PostMapping("/salvar")
    @ResponseStatus(HttpStatus.CREATED)
	public Produto salvar(@RequestBody Produto produto) {
		return produtoRepository.save(produto);
	}
}
```

## class Swagger

Este código é um arquivo de configuração para Swagger em um projeto Spring Boot. Swagger é uma ferramenta que permite gerar documentação para suas APIs. Este arquivo cria um Bean que retorna um objeto Docket. O objeto Docket configura o tipo de documentação como Swagger 2, seleciona qualquer API e qualquer caminho e cria a documentação. Quando a API estiver em execução, você poderá acessar a documentação na URL "http://localhost:8080/swagger-ui/index.html".

```java
package com.produtos.apirest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

// Classe de configuração para a utilização do Swagger
@Configuration
public class Swagger {
// Método que define as configurações da API do Swagger
@Bean
public Docket swaggerEditoraApi() {
// Configurações da API
return new Docket(DocumentationType.SWAGGER_2)
// Seleciona todos os controladores
.select()
.apis(RequestHandlerSelectors.any())
// Seleciona todas as rotas
.paths(PathSelectors.any())
// Constroi a API
.build();
}
}
```

# ⭐️ **Deixe um Star** ⭐️

Obrigado por conferir meu repository! É muito gratificante saber que alguém está interessado no meu trabalho. Se você gostou do que viu, deixar um star seria uma grande ajuda no meu crescimento e me motivaria a continuar fazendo projetos. O apoio de pessoas como você é fundamental para que eu possa seguir evoluindo e produzindo conteúdos cada vez melhores. Obrigado mais uma vez e espero que você possa acompanhar meus futuros projetos!