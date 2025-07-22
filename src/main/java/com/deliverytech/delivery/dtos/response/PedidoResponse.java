package com.deliverytech.delivery.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.deliverytech.delivery.dtos.ItemPedidoDTO;
import com.deliverytech.delivery.entities.Pedido;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de resposta com os dados do pedido")
public class PedidoResponse {

	// Construtor que recebe um Pedido
	public PedidoResponse(Pedido pedido) {
		this.id = pedido.getId();
		this.restaurante = pedido.getRestaurante().getNome();
		this.dataHora = pedido.getDataPedido().atStartOfDay(); // ou getDataHora(), dependendo do nome real
		this.total = pedido.getValorTotal(); // ou getValorTotal(), conforme sua entidade
		this.itens = pedido.getItens().stream().map(ItemPedidoDTO::new) // ou outro DTO que você estiver usando
				.collect(Collectors.toList());
	}

	@Schema(description = "ID do pedido", example = "1001")
	private Long id;

	@Schema(description = "Nome do restaurante", example = "Pizza Palace")
	private String restaurante;

	@Schema(description = "Data e hora do pedido", example = "2024-06-04T19:30:00")
	private LocalDateTime dataHora;

	@Schema(description = "Lista de itens do pedido")
	private List<ItemPedidoDTO> itens;

	@Schema(description = "Valor total do pedido", example = "89.90")
	private BigDecimal total;

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(String restaurante) {
		this.restaurante = restaurante;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public List<ItemPedidoDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoDTO> itens) {
		this.itens = itens;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
