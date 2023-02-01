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