package com.deliverytech.delivery.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

	private Long id;

	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
	private String nome;

	@NotNull(message = "Preço é obrigatório")
	@DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
	@DecimalMax(value = "500.00", message = "Preço não pode exceder R$500,00")
	private BigDecimal preco;

	@NotBlank(message = "Categoria é obrigatória")
	private String categoria;

	@NotBlank(message = "Descrição é obrigatória")
	@Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500caracteres")
	private String descricao;

	@NotNull(message = "Restaurante ID é obrigatório")
	@Positive(message = "Restaurante ID deve ser positivo")
	private Long restauranteId;

	@Pattern(regexp = "^(https?://).*\\.(jpg|jpeg|png|gif)$", message = "URL da imagem deve ser válida e ter formato JPG, JPEG,PNG ou GIF")
	private String imagemUrl;
	@AssertTrue(message = "Produto deve estar disponível por padrão")
	private Boolean disponivel = true;	
	
	public ProdutoDTO() {
		
	}

	public ProdutoDTO(Long id, String nome, String descricao, BigDecimal preco, String categoria, Boolean disponivel) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.categoria = categoria;
		this.disponivel = disponivel;
	}

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getRestauranteId() {
		return restauranteId;
	}

	public void setRestauranteId(Long restauranteId) {
		this.restauranteId = restauranteId;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

	public Boolean getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(Boolean disponivel) {
		this.disponivel = disponivel;
	}

}