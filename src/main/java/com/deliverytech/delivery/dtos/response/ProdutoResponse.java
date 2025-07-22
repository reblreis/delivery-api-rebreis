package com.deliverytech.delivery.dtos.response;

import java.math.BigDecimal;

import com.deliverytech.delivery.entities.Produto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de resposta com os dados do produto")
public class ProdutoResponse {

	@Schema(description = "ID do produto", example = "10")
	private Long id;

	@Schema(description = "Nome do produto", example = "Pizza Margherita")
	private String nome;

	@Schema(description = "Descrição do produto", example = "Pizza com molho de tomate, mussarela e manjericão")
	private String descricao;

	@Schema(description = "Preço do produto", example = "39.90")
	private BigDecimal preco;

	@Schema(description = "Categoria do produto", example = "Pizza")
	private String categoria;

	@Schema(description = "Disponibilidade do produto", example = "true")
	private Boolean disponivel;

	@Schema(description = "Nome do restaurante dono do produto", example = "Pizza Palace")
	private String restaurante;

	public ProdutoResponse(Long id, String nome, String descricao, BigDecimal preco, String categoria,
			Boolean disponivel, String restaurante) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.categoria = categoria;
		this.disponivel = disponivel;
		this.restaurante = restaurante;
	}

	public ProdutoResponse(Produto produto) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.preco = produto.getPreco();
		this.categoria = produto.getCategoria();
		this.disponivel = produto.getDisponivel();
		this.restaurante = produto.getRestaurante() != null ? produto.getRestaurante().getNome() : null;
	}

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Boolean getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(Boolean disponivel) {
		this.disponivel = disponivel;
	}

	public String getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(String restaurante) {
		this.restaurante = restaurante;
	}
}