package com.deliverytech.delivery.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ItemPedidoDTO {

	@NotNull(message = "Produto ID é obrigatório")
	@Positive(message = "Produto ID deve ser positivo")
	private Long produtoId;

	@NotNull(message = "Quantidade é obrigatória")
	@Min(value = 1, message = "Quantidade deve ser pelo menos 1")
	@Max(value = 50, message = "Quantidade não pode exceder 50 unidades")
	private Integer quantidade;

	@Size(max = 200, message = "Observações não podem exceder 200 caracteres")
	private String observacoes;

	public ItemPedidoDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
}