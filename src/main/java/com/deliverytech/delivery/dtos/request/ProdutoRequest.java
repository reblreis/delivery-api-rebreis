package com.deliverytech.delivery.dtos.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Objeto de requisição para criação de produto")
public class ProdutoRequest {

	@NotBlank
	@Schema(description = "Nome do produto", example = "Pizza Margherita")
	private String nome;

	@Schema(description = "Descrição do produto", example = "Pizza com molho de tomate, mussarela e manjericão")
	private String descricao;

	@NotNull
	@Positive
	@Schema(description = "Preço do produto", example = "39.90")
	private BigDecimal preco;

	@NotNull
	@Schema(description = "ID do restaurante", example = "1")
	private Long restauranteId;

	@Schema(description = "Categoria do produto", example = "Pizza")
	private String categoria;

	@Schema(description = "Indica se o produto está disponível", example = "true")
	private Boolean disponivel = true;

	// Getters e Setters
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

	public Long getRestauranteId() {
		return restauranteId;
	}

	public void setRestauranteId(Long restauranteId) {
		this.restauranteId = restauranteId;
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
}