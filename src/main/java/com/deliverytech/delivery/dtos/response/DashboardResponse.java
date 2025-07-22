package com.deliverytech.delivery.dtos.response;

import java.math.BigDecimal;

public class DashboardResponse {

	private Long totalPedidos;
	private BigDecimal totalVendas;
	private Long totalClientes;
	private Long totalRestaurantes;
	private BigDecimal mediaValorPedido;
	private Long pedidosConfirmados;
	private Long pedidosPendentes;

	public DashboardResponse(Long totalPedidos, BigDecimal totalVendas, Long totalClientes, Long totalRestaurantes,
			BigDecimal mediaValorPedido, Long pedidosConfirmados, Long pedidosPendentes) {
		super();
		this.totalPedidos = totalPedidos;
		this.totalVendas = totalVendas;
		this.totalClientes = totalClientes;
		this.totalRestaurantes = totalRestaurantes;
		this.mediaValorPedido = mediaValorPedido;
		this.pedidosConfirmados = pedidosConfirmados;
		this.pedidosPendentes = pedidosPendentes;
	}

	public Long getTotalPedidos() {
		return totalPedidos;
	}

	public void setTotalPedidos(Long totalPedidos) {
		this.totalPedidos = totalPedidos;
	}

	public BigDecimal getTotalVendas() {
		return totalVendas;
	}

	public void setTotalVendas(BigDecimal totalVendas) {
		this.totalVendas = totalVendas;
	}

	public Long getTotalClientes() {
		return totalClientes;
	}

	public void setTotalClientes(Long totalClientes) {
		this.totalClientes = totalClientes;
	}

	public Long getTotalRestaurantes() {
		return totalRestaurantes;
	}

	public void setTotalRestaurantes(Long totalRestaurantes) {
		this.totalRestaurantes = totalRestaurantes;
	}

	public BigDecimal getMediaValorPedido() {
		return mediaValorPedido;
	}

	public void setMediaValorPedido(BigDecimal mediaValorPedido) {
		this.mediaValorPedido = mediaValorPedido;
	}

	public Long getPedidosConfirmados() {
		return pedidosConfirmados;
	}

	public void setPedidosConfirmados(Long pedidosConfirmados) {
		this.pedidosConfirmados = pedidosConfirmados;
	}

	public Long getPedidosPendentes() {
		return pedidosPendentes;
	}

	public void setPedidosPendentes(Long pedidosPendentes) {
		this.pedidosPendentes = pedidosPendentes;
	}

}
