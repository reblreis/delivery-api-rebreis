package com.deliverytech.delivery.dtos.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para criação ou atualização de um restaurante")
public class RestauranteRequest {

	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
	@Schema(description = "Nome do restaurante", example = "Pizza Palace", required = true, minLength = 2, maxLength = 100)
	private String nome;

	@NotBlank(message = "Endereço é obrigatório")
	@Schema(description = "Endereço completo do restaurante", example = "Rua das Pizzas, 123 - Centro, São Paulo - SP", required = true)
	private String endereco;

	@NotBlank(message = "Telefone é obrigatório")
	@Schema(description = "Telefone de contato do restaurante", example = "(11) 1234-5678", required = true)
	private String telefone;

	@NotBlank(message = "Categoria é obrigatória")
	@Schema(description = "Categoria/tipo de culinária do restaurante", example = "Italiana", required = true)
	private String categoria;

	@Schema(description = "Valor da taxa de entrega cobrada pelo restaurante", example = "5.90", required = true)
	@NotNull(message = "Taxa de entrega é obrigatória")
	@PositiveOrZero(message = "Taxa de entrega deve ser zero ou positiva")
	private BigDecimal taxaEntrega;

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

	public BigDecimal getTaxaEntrega() {
		return taxaEntrega;
	}

	public void setTaxaEntrega(BigDecimal taxaEntrega) {
		this.taxaEntrega = taxaEntrega;
	}

}