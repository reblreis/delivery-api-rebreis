package com.deliverytech.delivery.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Formas de pagamento disponíveis")
public enum FormaPagamento {

	DINHEIRO("Pagamento em dinheiro"), 
	CARTAO_CREDITO("Cartão de crédito"), 
	CARTAO_DEBITO("Cartão de débito"),
	PIX("Pagamento via PIX"), 
	VALE_REFEICAO("Vale-refeição");

	private final String descricao;

	FormaPagamento(String descricao) {
		this.descricao = descricao;
	}

	@Schema(description = "Descrição da forma de pagamento")
	public String getDescricao() {
		return descricao;
	}
}
