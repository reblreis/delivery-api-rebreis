package com.deliverytech.delivery.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dtos.ProdutoDTO;
import com.deliverytech.delivery.entities.Produto;
import com.deliverytech.delivery.services.ProdutoService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Produtos", description = "Endpoints de produtos")
@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@PostMapping
	public ResponseEntity<?> cadastrar(@Validated @RequestBody ProdutoDTO produtoDTO) {
		try {
			Produto produtoSalvo = produtoService.cadastrar(produtoDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
		}
	}

	// Listar todos os produtos
	@GetMapping
	public ResponseEntity<?> listarTodos() {
		try {
			return ResponseEntity.ok(produtoService.listarTodos());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
		}
	}

	// Buscar produto por ID
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		try {
			Produto produto = produtoService.buscarPorId(id);
			if (produto != null) {
				return ResponseEntity.ok(produto);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
		}
	}

	// Atualizar produto
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @Validated @RequestBody ProdutoDTO produtoDTO) {
		try {
			Produto atualizado = produtoService.atualizar(id, produtoDTO);
			return ResponseEntity.ok(atualizado);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
		}
	}

	// Excluir produto
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		try {
			produtoService.excluir(id);
			return ResponseEntity.noContent().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
		}
	}

	// inativar produto
	@PutMapping("/{id}/inativar")
	public ResponseEntity<?> inativar(@PathVariable Long id) {
		try {
			Produto produtoInativado = produtoService.inativar(id);
			return ResponseEntity.ok(produtoInativado);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
		}
	}

	// buscar produto por restaurante ID
	@GetMapping("/restaurante/{restauranteId}")
	public ResponseEntity<?> buscarPorRestaurante(@PathVariable Long restauranteId) {
		try {
			return ResponseEntity.ok(produtoService.buscarPorRestaurante(restauranteId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
		}
	}

	// Apenas produtos disponíveis
	@GetMapping("/disponiveis")
	public ResponseEntity<?> listarDisponiveis() {
		try {
			return ResponseEntity.ok(produtoService.buscarDisponiveis());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
		}
	}

	// Produtos por categoria
	@GetMapping("/categoria/{categoria}")
	public ResponseEntity<?> buscarPorCategoria(@PathVariable String categoria) {
		try {
			return ResponseEntity.ok(produtoService.buscarPorCategoria(categoria));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
		}
	}

	// Produtos por faixa de preço (menor ou igual)
	@GetMapping("/preco/{preco}")
	public ResponseEntity<?> buscarPorPreco(@PathVariable BigDecimal preco) {
		try {
			return ResponseEntity.ok(produtoService.buscarPorFaixaDePreco(preco));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
		}
	}

}