package com.deliverytech.delivery.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dtos.request.ClienteRequest;
import com.deliverytech.delivery.dtos.response.ClienteResponse;
import com.deliverytech.delivery.entities.Cliente;
import com.deliverytech.delivery.services.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Clientes", description = "Endpoints de clientes")
@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	/**
	 * Cadastrar novo cliente
	 */
	@Operation(summary = "Serviço para cadastro de clientes.")
	@PostMapping("/api/clientes")
	public ResponseEntity<ClienteResponse> criarCliente(@RequestBody @Valid ClienteRequest request) {
		Cliente cliente = new Cliente();
		cliente.setNome(request.getNome());
		cliente.setEmail(request.getEmail());
		cliente.setTelefone(request.getTelefone());
		cliente.setEndereco(request.getEndereco());

		Cliente salvo = clienteService.cadastrar(cliente);

		ClienteResponse response = new ClienteResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(),
				salvo.getTelefone(), salvo.getEndereco());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * Listar todos os clientes ativos
	 */
	@Operation(summary = "Serviço para consulta de clientes ativos.")
	@GetMapping
	public ResponseEntity<List<Cliente>> listar() {
		List<Cliente> clientes = clienteService.listarAtivos();
		return ResponseEntity.ok(clientes);
	}

	/**
	 * Buscar cliente por ID
	 */
	@Operation(summary = "Serviço para consulta de Id de clientes.")
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteService.buscarPorId(id);

		if (cliente.isPresent()) {
			Cliente c = cliente.get();
			ClienteResponse response = new ClienteResponse(c.getId(), c.getNome(), c.getEmail(), c.getTelefone(),
					c.getEndereco());
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Atualizar cliente
	 */
	@Operation(summary = "Serviço para atualização de clientes.")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
		try {
			Cliente cliente = new Cliente();
			cliente.setNome(request.getNome());
			cliente.setEmail(request.getEmail());
			cliente.setTelefone(request.getTelefone());
			cliente.setEndereco(request.getEndereco());

			Cliente clienteAtualizado = clienteService.atualizar(id, cliente);

			ClienteResponse response = new ClienteResponse(clienteAtualizado.getId(), clienteAtualizado.getNome(),
					clienteAtualizado.getEmail(), clienteAtualizado.getTelefone(), clienteAtualizado.getEndereco());

			return ResponseEntity.ok(response);

		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
		}
	}

	/**
	 * Inativar cliente (soft delete)
	 */
	@Operation(summary = "Serviço para exclusão de clientes.")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> inativar(@PathVariable Long id) {
		try {
			clienteService.inativar(id);
			return ResponseEntity.ok().body("Cliente inativado com sucesso");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
		}
	}

	/**
	 * Buscar clientes por nome
	 */
	@Operation(summary = "Serviço para consulta de clientes por nome.")
	@GetMapping("/buscar")
	public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
		List<Cliente> clientes = clienteService.buscarPorNome(nome);
		return ResponseEntity.ok(clientes);
	}

	/**
	 * Buscar cliente por email
	 */
	@Operation(summary = "Serviço para consulta de clientes por email.")
	@GetMapping("/email/{email}")
	public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
		Optional<Cliente> cliente = clienteService.buscarPorEmail(email);

		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}