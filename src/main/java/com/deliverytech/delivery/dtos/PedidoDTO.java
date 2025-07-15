package com.deliverytech.delivery.dtos;

import java.util.List;

import com.deliverytech.delivery.validations.ValidCEP;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class PedidoDTO {

	@NotNull(message = "Cliente ID é obrigatório")
	@Positive(message = "Cliente ID deve ser positivo")
	private Long clienteId;

	@NotNull(message = "Restaurante ID é obrigatório")
	@Positive(message = "Restaurante ID deve ser positivo")
	private Long restauranteId;

	@NotEmpty(message = "Lista de itens não pode estar vazia")
	@Valid
	private List<ItemPedidoDTO> itens;

	@NotBlank(message = "Endereço de entrega é obrigatório")
	@Size(max = 200, message = "Endereço não pode exceder 200 caracteres")
	private String enderecoEntrega;

	@NotBlank(message = "CEP é obrigatório")
	@ValidCEP
	private String cepEntrega;

	@Size(max = 500, message = "Observações não podem exceder 500 caracteres")
	private String observacoes;

	@NotBlank(message = "Forma de pagamento é obrigatória")
	@Pattern(regexp = "^(DINHEIRO|CARTAO_CREDITO|CARTAO_DEBITO|PIX)$", message = "Forma de pagamento deve ser: DINHEIRO,CARTAO_CREDITO,CARTAO_DEBITO ou PIX")

	private String formaPagamento;

	public PedidoDTO() {

	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Long getRestauranteId() {
		return restauranteId;
	}

	public void setRestauranteId(Long restauranteId) {
		this.restauranteId = restauranteId;
	}

	public List<ItemPedidoDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoDTO> itens) {
		this.itens = itens;
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

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

}