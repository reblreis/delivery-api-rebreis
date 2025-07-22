package com.deliverytech.delivery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dtos.request.ProdutoRequest;
import com.deliverytech.delivery.dtos.response.ProdutoResponse;
import com.deliverytech.delivery.services.ProdutoService;

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
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Operações relacionadas ao gerenciamento de produtos dos restaurantes")
@CrossOrigin(origins = "*")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping
	@Operation(summary = "Listar produtos", description = "Retorna uma lista paginada de produtos. Pode ser filtrada por restaurante, categoria ou disponibilidade.", tags = {
			"Produtos" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponse.class))) })
	public ResponseEntity<Page<ProdutoResponse>> listar(
			@Parameter(description = "Informações de paginação") Pageable pageable,
			@Parameter(description = "Filtro por ID do restaurante") @RequestParam(required = false) Long restauranteId,
			@Parameter(description = "Filtro por categoria") @RequestParam(required = false) String categoria,
			@Parameter(description = "Filtro por disponibilidade") @RequestParam(required = false) Boolean disponivel) {
		Page<ProdutoResponse> produtos = produtoService.listar(pageable, restauranteId, categoria, disponivel);
		return ResponseEntity.ok(produtos);
	}

	@PostMapping
	@PreAuthorize("hasRole('RESTAURANTE') or hasRole('ADMIN')")
	@Operation(summary = "Criar novo produto", description = "Cria um novo produto para um restaurante", security = @SecurityRequirement(name = "Bearer Authentication"), tags = {
			"Produtos" })
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos"),
			@ApiResponse(responseCode = "401", description = "Não autorizado"),
			@ApiResponse(responseCode = "403", description = "Acesso negado") })
	public ResponseEntity<ProdutoResponse> criar(
			@Parameter(description = "Dados do produto a ser criado") @Valid @RequestBody ProdutoRequest produtoRequest) {
		ProdutoResponse novoProduto = produtoService.criar(produtoRequest);
		return ResponseEntity.status(201).body(novoProduto);
	}
}