package com.deliverytech.delivery.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status possíveis de um pedido no sistema")
public enum StatusPedido {

	PENDENTE("Aguardando confirmação do restaurante"), 
	CONFIRMADO("Confirmado pelo restaurante"),
	PREPARANDO("Em preparação"), 
	PRONTO("Pronto para entrega"), 
	SAIU_PARA_ENTREGA("Saiu para entrega"),
	ENTREGUE("Entregue"), 
	CANCELADO("Cancelado");

	private final String descricao;

	StatusPedido(String descricao) {
		this.descricao = descricao;
	}

	@Schema(description = "Descrição legível do status")
	public String getDescricao() {
		return descricao;
	}
}