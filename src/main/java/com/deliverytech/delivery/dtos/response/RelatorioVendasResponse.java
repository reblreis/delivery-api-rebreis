package com.deliverytech.delivery.dtos.response;

import java.math.BigDecimal;
import java.util.List;

public class RelatorioVendasResponse {

	private List<RelatorioVendasDTO> relatorios;

	// Construtor
	public RelatorioVendasResponse(List<RelatorioVendasDTO> relatorios) {
		this.relatorios = relatorios;
	}

	// Getter e Setter
	public List<RelatorioVendasDTO> getRelatorios() {
		return relatorios;
	}

	public void setRelatorios(List<RelatorioVendasDTO> relatorios) {
		this.relatorios = relatorios;
	}

	// Classe interna DTO
	public static class RelatorioVendasDTO {
		private String nomeRestaurante;
		private BigDecimal totalVendas;
		private Long quantidadePedidos;

		public RelatorioVendasDTO(String nomeRestaurante, BigDecimal totalVendas, Long quantidadePedidos) {
			this.nomeRestaurante = nomeRestaurante;
			this.totalVendas = totalVendas;
			this.quantidadePedidos = quantidadePedidos;
		}

		public String getNomeRestaurante() {
			return nomeRestaurante;
		}

		public void setNomeRestaurante(String nomeRestaurante) {
			this.nomeRestaurante = nomeRestaurante;
		}

		public BigDecimal getTotalVendas() {
			return totalVendas;
		}

		public void setTotalVendas(BigDecimal totalVendas) {
			this.totalVendas = totalVendas;
		}

		public Long getQuantidadePedidos() {
			return quantidadePedidos;
		}

		public void setQuantidadePedidos(Long quantidadePedidos) {
			this.quantidadePedidos = quantidadePedidos;
		}
	}
}