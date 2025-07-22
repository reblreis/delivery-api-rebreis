package com.deliverytech.delivery.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.deliverytech.delivery.enums.FormaPagamento;
import com.deliverytech.delivery.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate dataPedido;

	@Column(name = "endereco_entrega", nullable = false)
	private String enderecoEntrega;

	@Column(name = "cep_entrega", nullable = false)
	private String cepEntrega;

	private String numeroPedido;

	private BigDecimal subtotal;

	@Column(name = "taxa_entrega", precision = 10, scale = 2)
	private BigDecimal taxaEntrega;

	private BigDecimal valorTotal;

	private String observacoes;

	@Enumerated(EnumType.STRING)
	private StatusPedido status;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	@JsonIgnore
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "restaurante_id")
	@JsonIgnore
	private Restaurante restaurante;

	@Column(name = "forma_pagamento", nullable = false)
	@Enumerated(EnumType.STRING) // se for enum
	private FormaPagamento formaPagamento;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();

	// ==== MÉTODOS GETTERS/SETTERS ====

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
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

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTaxaEntrega() {
		return taxaEntrega;
	}

	public void setTaxaEntrega(BigDecimal taxaEntrega) {
		this.taxaEntrega = taxaEntrega;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	// ==== MÉTODOS UTILITÁRIOS ====

	public void adicionarItem(ItemPedido item) {
		this.itens.add(item);
		item.setPedido(this);
	}

	public void confirmar() {
		this.status = StatusPedido.CONFIRMADO;
	}

	public void calcularTotais() {
		BigDecimal subtotal = itens.stream().map(ItemPedido::calcularSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);

		this.subtotal = subtotal;
		this.valorTotal = subtotal.add(this.taxaEntrega != null ? this.taxaEntrega : BigDecimal.ZERO);
	}

	public void gerarNumeroPedido() {
		this.numeroPedido = "PED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	}

	@PrePersist
	public void definirDataPedido() {
		this.dataPedido = LocalDate.now();
	}

	public void alterarStatus(StatusPedido novoStatus) {
		// Exemplo básico — aplique regras de negócio conforme necessário
		if (this.status == StatusPedido.PENDENTE && novoStatus == StatusPedido.CONFIRMADO) {
			this.status = novoStatus;
		} else {
			throw new IllegalStateException("Transição de status inválida.");
		}
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public static Pedido criarNovo(Cliente cliente, Restaurante restaurante, String endereco, String cep,
			FormaPagamento formaPagamento) {
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setEnderecoEntrega(endereco);
		pedido.setCepEntrega(cep);
		pedido.setFormaPagamento(formaPagamento);
		pedido.setStatus(StatusPedido.PENDENTE);
		pedido.gerarNumeroPedido();
		pedido.definirDataPedido(); // ou deixar que o @PrePersist trate isso
		return pedido;
	}

}