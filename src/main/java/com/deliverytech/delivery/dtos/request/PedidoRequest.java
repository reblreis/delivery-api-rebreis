package com.deliverytech.delivery.dtos.request;

import java.util.List;

import com.deliverytech.delivery.enums.FormaPagamento;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Objeto de requisição para criação de pedido")
public class PedidoRequest {

	@NotNull
	@Schema(description = "ID do cliente que fez o pedido", example = "42")
	private Long clienteId;

	private Long restauranteId;

	@Schema(description = "Endereço de entrega do pedido", example = "Rua das Flores, 123")
	private String enderecoEntrega;

	@Schema(description = "CEP do endereço de entrega", example = "12345-678")
	private String cepEntrega;

	@Schema(description = "Forma de pagamento", example = "CARTAO_CREDITO")
	private FormaPagamento formaPagamento;

	private List<ItemPedidoRequest> itens;

	// Getters e Setters

	public Long getClienteId() {
		return clienteId;
	}

	public Long getRestauranteId() {
		return restauranteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public String getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(String enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public String getCepEntrega() {
		return cepEntrega;
	}

	public void setCepEntrega(String cepEntrega) {
		this.cepEntrega = cepEntrega;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public void setRestauranteId(Long restauranteId) {
		this.restauranteId = restauranteId;
	}

	public List<ItemPedidoRequest> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoRequest> itens) {
		this.itens = itens;
	}
}