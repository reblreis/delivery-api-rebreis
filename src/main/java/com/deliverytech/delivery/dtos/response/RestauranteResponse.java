package com.deliverytech.delivery.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de resposta de um restaurante")
public class RestauranteResponse {

	@Schema(description = "Identificador único do restaurante", example = "1")
	private Long id;

	@Schema(description = "Nome do restaurante", example = "Pizza Palace")
	private String nome;

	@Schema(description = "Endereço completo do restaurante", example = "Rua das Pizzas, 123 - Centro, São Paulo - SP")
	private String endereco;

	@Schema(description = "Telefone de contato do restaurante", example = "(11) 1234-5678")
	private String telefone;

	@Schema(description = "Categoria/tipo de culinária do restaurante", example = "Italiana")
	private String categoria;

	@Schema(description = "Indica se o restaurante está ativo no sistema", example = "true")
	private Boolean ativo;

	@Schema(description = "Data e hora de criação do registro", example = "2024-06-	04T10:30:00")
	private LocalDateTime dataCriacao;

	@Schema(description = "Avaliação média do restaurante (0 a 5 estrelas)", example = "4.5", minimum = "0", maximum = "5")
	private BigDecimal avaliacao;

	@Schema(description = "Taxa de entrega cobrada pelo restaurante", example = "5.90")
	private BigDecimal taxaEntrega;

	public RestauranteResponse(Long id, String nome, String endereco, String telefone, String categoria, Boolean ativo,
			LocalDateTime dataCriacao, BigDecimal avaliacao, BigDecimal taxaEntrega) {
		super();
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.categoria = categoria;
		this.ativo = ativo;
		this.dataCriacao = dataCriacao;
		this.avaliacao = avaliacao;
		this.taxaEntrega = taxaEntrega;
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public BigDecimal getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(BigDecimal avaliacao) {
		this.avaliacao = avaliacao;
	}

	public BigDecimal getTaxaEntrega() {
		return taxaEntrega;
	}

	public void setTaxaEntrega(BigDecimal taxaEntrega) {
		this.taxaEntrega = taxaEntrega;
	}

	// Converte a entidade Restaurante em RestauranteResponse. Ele será usado, por
	// exemplo, no RestauranteController.
	public static RestauranteResponse fromEntity(com.deliverytech.delivery.entities.Restaurante restaurante) {
		return new RestauranteResponse(restaurante.getId(), restaurante.getNome(), restaurante.getEndereco(),
				restaurante.getTelefone(), restaurante.getCategoria(), restaurante.getAtivo(),
				restaurante.getDataCriacao(), restaurante.getAvaliacao(), restaurante.getTaxaEntrega());
	}

}
