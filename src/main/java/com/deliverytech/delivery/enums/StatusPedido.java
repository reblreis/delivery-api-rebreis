package com.deliverytech.delivery.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status do pedido")
public enum StatusPedido {

	@Schema(description = "Pedido criado, aguardando processamento")
	PENDENTE("Pendente"),

	@Schema(description = "Pedido confirmado")
	CONFIRMADO("Confirmado"),

	@Schema(description = "Pedido em preparo na cozinha")
	PREPARANDO("Preparando"),

	@Schema(description = "Pedido saiu para entrega")
	SAIU_PARA_ENTREGA("Saiu para Entrega"),

	@Schema(description = "Pedido entregue ao cliente")
	ENTREGUE("Entregue"),

	@Schema(description = "Pedido cancelado")
	CANCELADO("Cancelado");

	private final String descricao;

	StatusPedido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}