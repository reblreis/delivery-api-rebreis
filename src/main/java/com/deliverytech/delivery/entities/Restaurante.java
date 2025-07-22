package com.deliverytech.delivery.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "restaurante")
@Schema(description = "Entidade que representa um restaurante no sistema")
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Identificador único do restaurante", example = "1")
	private Long id;

	@Column(nullable = false)
	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
	@Schema(description = "Nome do restaurante", example = "Pizza Palace", required = true)
	private String nome;

	@Column(nullable = false)
	@NotBlank(message = "Categoria é obrigatória")
	@Schema(description = "Categoria/tipo de culinária do restaurante", example = "Italiana", required = true)
	private String categoria;

	@Column(nullable = false)
	@NotBlank(message = "Endereço é obrigatório")
	@Schema(description = "Endereço completo do restaurante", example = "Rua das Pizzas, 123 - Centro, São Paulo - SP", required = true)
	private String endereco;

	@Column(nullable = false)
	@NotBlank(message = "Telefone é obrigatório")
	@Schema(description = "Telefone de contato do restaurante", example = "(11) 1234-5678", required = true)
	private String telefone;

	@Column(name = "data_criacao", nullable = false)
	@Schema(description = "Data e hora de criação do registro", example = "2024-06-04T10:30:00")
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@Column(nullable = false)
	@Schema(description = "Indica se o restaurante está ativo no sistema", example = "true", defaultValue = "true")
	private boolean ativo;

	@Column(precision = 3, scale = 2)
	@Schema(description = "Avaliação média do restaurante (0 a 5 estrelas)", example = "4.5", minimum = "0", maximum = "5")
	private BigDecimal avaliacao;

	@Schema(description = "Valor da taxa de entrega cobrada pelo restaurante", example = "5.90")
	@Column(name = "taxa_entrega", precision = 10, scale = 2, nullable = false)
	private BigDecimal taxaEntrega;

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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
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

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public BigDecimal getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(BigDecimal avaliacao) {
		this.avaliacao = avaliacao;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public BigDecimal getTaxaEntrega() {
		return taxaEntrega;
	}

	public void setTaxaEntrega(BigDecimal taxaEntrega) {
		this.taxaEntrega = taxaEntrega;
	}

	@OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Schema(description = "Lista de produtos oferecidos pelo restaurante")
	private List<Produto> produtos;

}
