package com.deliverytech.delivery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dtos.request.PedidoRequest;
import com.deliverytech.delivery.dtos.response.PedidoResponse;
import com.deliverytech.delivery.enums.StatusPedido;
import com.deliverytech.delivery.services.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Operações relacionadas ao gerenciamento de pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@GetMapping("/meus")
	@PreAuthorize("hasRole('CLIENTE')")
	@Operation(summary = "Listar meus pedidos", description = "Retorna os pedidos do cliente autenticado", security = @SecurityRequirement(name = "Bearer Authentication"), tags = {
			"Pedidos" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pedidos retornados com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponse.class))) })
	public ResponseEntity<Page<PedidoResponse>> listarMeusPedidos(
			@Parameter(description = "Informações de paginação") Pageable pageable) {
		Page<PedidoResponse> pedidos = pedidoService.listarPorCliente(pageable);
		return ResponseEntity.ok(pedidos);
	}

	@PostMapping
	@PreAuthorize("hasRole('CLIENTE')")
	@Operation(summary = "Criar novo pedido", description = "Cria um novo pedido para o cliente autenticado", security = @SecurityRequirement(name = "Bearer Authentication"), tags = {
			"Pedidos" })
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos"),
			@ApiResponse(responseCode = "401", description = "Não autorizado") })
	public ResponseEntity<PedidoResponse> criar(
			@Parameter(description = "Dados do pedido a ser criado") @Valid @RequestBody PedidoRequest pedidoRequest) {
		PedidoResponse novoPedido = pedidoService.cadastrar(pedidoRequest);
		return ResponseEntity.status(201).body(novoPedido);
	}

	@PutMapping("/{id}/status")
	@PreAuthorize("hasRole('RESTAURANTE') or hasRole('ADMIN')")
	@Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido", security = @SecurityRequirement(name = "Bearer Authentication"), tags = {
			"Pedidos" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado") })
	public ResponseEntity<PedidoResponse> atualizarStatus(
			@Parameter(description = "ID do pedido", example = "1") @PathVariable Long id,
			@Parameter(description = "Novo status do pedido") @RequestParam StatusPedido status) {
		PedidoResponse pedidoAtualizado = pedidoService.atualizarStatus(id, status);
		return ResponseEntity.ok(pedidoAtualizado);
	}
}