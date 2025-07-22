package com.deliverytech.delivery.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public class StatusPedidoDTO {

	@Schema(description = "Nome do status", example = "PENDENTE")
	private String nome;

	@Schema(description = "Descrição legível do status", example = "Aguardando confirmação do restaurante")
	private String descricao;

	public StatusPedidoDTO(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}

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
}