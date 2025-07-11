package com.deliverytech.delivery.dtos;

import com.deliverytech.delivery.validations.ValidCategoria;
import com.deliverytech.delivery.validations.ValidTelefone;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RestauranteDTO {

	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
	private String nome;

	@NotNull(message = "Categoria é obrigatória")
	@ValidCategoria
	private String categoria;

	@NotBlank(message = "Telefone é obrigatório")
	@ValidTelefone
	private String telefone;

	@NotNull(message = "Taxa de entrega é obrigatória")
	@DecimalMin(value = "0.0", inclusive = false, message = "Taxa de entregadeve ser positiva")
	@DecimalMax(value = "50.0", message = "Taxa de entrega não podeexceder R$ 50,00")
	private Double taxaEntrega;

	@NotNull(message = "Tempo de entrega é obrigatório")
	@Min(value = 10, message = "Tempo mínimo de entrega é 10 minutos")
	@Max(value = 120, message = "Tempo máximo de entrega é 120 minutos")
	private Integer tempoEntrega;

	@NotBlank(message = "Endereço é obrigatório")
	@Size(max = 200, message = "Endereço não pode exceder 200 caracteres")
	private String endereco;

	@Email(message = "Email deve ter formato válido")
	private String email;

	// Construtor padrão
	public RestauranteDTO() {
	}

	// Getters e Setters

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Double getTaxaEntrega() {
		return taxaEntrega;
	}

	public void setTaxaEntrega(Double taxaEntrega) {
		this.taxaEntrega = taxaEntrega;
	}

	public Integer getTempoEntrega() {
		return tempoEntrega;
	}

	public void setTempoEntrega(Integer tempoEntrega) {
		this.tempoEntrega = tempoEntrega;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}