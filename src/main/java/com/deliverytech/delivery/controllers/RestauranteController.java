package com.deliverytech.delivery.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dtos.request.RestauranteRequest;
import com.deliverytech.delivery.dtos.response.RestauranteResponse;
import com.deliverytech.delivery.entities.Restaurante;
import com.deliverytech.delivery.services.RestauranteService;

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
@RequestMapping("/api/restaurantes")
@Tag(name = "Restaurantes", description = "Operações relacionadas ao gerenciamento de restaurantes")
@CrossOrigin(origins = "*")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@GetMapping
	@Operation(summary = "Listar todos os restaurantes ativos", description = "Retorna uma lista de restaurantes ativos", tags = {
			"Restaurantes" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de restaurantes retornada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestauranteResponse.class))) })
	public ResponseEntity<List<RestauranteResponse>> listar() {
		List<Restaurante> restaurantes = restauranteService.listarAtivos();
		List<RestauranteResponse> response = restaurantes.stream().map(RestauranteResponse::fromEntity)
				.collect(Collectors.toList());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar restaurante por ID", description = "Retorna os detalhes de um restaurante específico pelo seu ID", tags = {
			"Restaurantes" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado") })
	public ResponseEntity<RestauranteResponse> buscarPorId(
			@Parameter(description = "ID do restaurante", example = "1") @PathVariable Long id) {
		Optional<Restaurante> restauranteOpt = restauranteService.buscarPorId(id);
		if (restauranteOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		RestauranteResponse response = RestauranteResponse.fromEntity(restauranteOpt.get());
		return ResponseEntity.ok(response);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Criar novo restaurante", description = "Cria um novo restaurante no sistema. Requer permissão de administrador.", security = @SecurityRequirement(name = "Bearer Authentication"), tags = {
			"Restaurantes" })
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos"),
			@ApiResponse(responseCode = "401", description = "Não autorizado"),
			@ApiResponse(responseCode = "403", description = "Acesso negado") })
	public ResponseEntity<RestauranteResponse> criar(
			@Parameter(description = "Dados do restaurante a ser criado") @Valid @RequestBody RestauranteRequest request) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(request.getNome());
		restaurante.setCategoria(request.getCategoria());
		restaurante.setEndereco(request.getEndereco());
		restaurante.setTelefone(request.getTelefone());
		restaurante.setTaxaEntrega(request.getTaxaEntrega());

		Restaurante salvo = restauranteService.cadastrar(restaurante);
		RestauranteResponse response = RestauranteResponse.fromEntity(salvo);
		return ResponseEntity.status(201).body(response);
	}
}