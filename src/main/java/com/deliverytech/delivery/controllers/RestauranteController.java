package com.deliverytech.delivery.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dtos.RestauranteDTO;
import com.deliverytech.delivery.exceptions.ConflictException;
import com.deliverytech.delivery.exceptions.EntityNotFoundException;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@Tag(name = "Restaurantes", description = "Endpoints de restaurantes")
@RestController
@RequestMapping("/api/restaurantes") // Define a base de rota para este controller
@Validated
public class RestauranteController {

	private List<RestauranteDTO> restaurantes = new ArrayList<>();

	@GetMapping
	public ResponseEntity<List<RestauranteDTO>> listarTodos() {
		return ResponseEntity.ok(restaurantes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestauranteDTO> buscarPorId(

			@PathVariable @Positive(message = "ID deve ser positivo") Long id) {

		// Simulação de busca - em aplicação real seria pelo ID
		if (id > restaurantes.size()) {
			throw new EntityNotFoundException("Restaurante", id);
		}
		return ResponseEntity.ok(restaurantes.get(0)); // Simulação
	}

	@PostMapping
	public ResponseEntity<RestauranteDTO> criar(@Valid @RequestBody RestauranteDTO restauranteDTO) {
		// Verificar se já existe restaurante com mesmo nome
		boolean nomeExiste = restaurantes.stream()
				.anyMatch(r -> r.getNome().equalsIgnoreCase(restauranteDTO.getNome()));
		if (nomeExiste) {
			throw new ConflictException("Já existe um restaurante com este nome", "nome", restauranteDTO.getNome());
		}
		restaurantes.add(restauranteDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(restauranteDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RestauranteDTO> atualizar(@PathVariable @Positive(message = "ID deve ser positivo") Long id,
			@Valid @RequestBody RestauranteDTO restauranteDTO) {
		if (id > restaurantes.size()) {
			throw new EntityNotFoundException("Restaurante", id);
		}
		// Simulação de atualização
		restaurantes.set(0, restauranteDTO);
		return ResponseEntity.ok(restauranteDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable @Positive(message = "ID deve ser positivo") Long id) {
		if (id > restaurantes.size()) {
			throw new EntityNotFoundException("Restaurante", id);
		}
		restaurantes.remove(0); // Simulação
		return ResponseEntity.noContent().build();
	}
}