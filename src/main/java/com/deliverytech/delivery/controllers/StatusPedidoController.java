package com.deliverytech.delivery.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dtos.StatusPedidoDTO;
import com.deliverytech.delivery.enums.StatusPedido;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/status-pedidos")
@Tag(name = "Status do Pedido", description = "Lista os status possíveis de um pedido")
public class StatusPedidoController {

	@GetMapping
	@Operation(summary = "Lista todos os status de pedidos disponíveis", description = "Retorna todos os valores do enum StatusPedido com nome e descrição.", responses = {
			@ApiResponse(responseCode = "200", description = "Lista de status retornada com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StatusPedidoDTO.class)))) })
	public List<StatusPedidoDTO> listarStatusPedidos() {
		return Arrays.stream(StatusPedido.values())
				.map(status -> new StatusPedidoDTO(status.name(), status.getDescricao())).collect(Collectors.toList());
	}
}