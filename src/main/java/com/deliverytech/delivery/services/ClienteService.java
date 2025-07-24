package com.deliverytech.delivery.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery.entities.Cliente;
import com.deliverytech.delivery.repositories.ClienteRepository;

@Service
@Transactional
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	/**
	 * Cadastrar novo cliente
	 */
	public Cliente cadastrar(Cliente cliente) {
		// Validar email único
		if (clienteRepository.existsByEmail(cliente.getEmail())) {
			throw new IllegalArgumentException("Email já cadastrado: " + cliente.getEmail());
		}

		// Validar CPF único
		if (clienteRepository.existsByCpf(cliente.getCpf())) {
			throw new IllegalArgumentException("CPF já cadastrado: " + cliente.getCpf());
		}

		// Validações de negócio
		validarDadosCliente(cliente);

		// Definir como ativo por padrão
		cliente.setAtivo(true);

		return clienteRepository.save(cliente);
	}

	/**
	 * Buscar cliente por ID
	 */
	@Transactional(readOnly = true)
	public Optional<Cliente> buscarPorId(Long id) {
		return clienteRepository.findById(id);
	}

	/**
	 * Buscar cliente por email
	 */
	@Transactional(readOnly = true)
	public Optional<Cliente> buscarPorEmail(String email) {
		return clienteRepository.findByEmail(email);
	}

	/**
	 * Listar todos os clientes ativos
	 */
	@Transactional(readOnly = true)
	public List<Cliente> listarAtivos() {
		try {
			return clienteRepository.findByAtivoTrue();
		} catch (Exception e) {
			e.printStackTrace(); // imprime o erro completo no console
			throw e;
		}
	}

	/**
	 * Atualizar dados do cliente
	 */
	public Cliente atualizar(Long id, Cliente clienteAtualizado) {
		Cliente cliente = buscarPorId(id)
				.orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));

		// Verificar se email não está sendo usado por outro cliente
		if (!cliente.getEmail().equals(clienteAtualizado.getEmail())
				&& clienteRepository.existsByEmail(clienteAtualizado.getEmail())) {
			throw new IllegalArgumentException("Email já cadastrado: " + clienteAtualizado.getEmail());
		}

		// Atualizar campos
		cliente.setNome(clienteAtualizado.getNome());
		cliente.setEmail(clienteAtualizado.getEmail());
		cliente.setTelefone(clienteAtualizado.getTelefone());
		cliente.setEndereco(clienteAtualizado.getEndereco());

		return clienteRepository.save(cliente);
	}

	@Transactional(readOnly = true)
	public Page<Cliente> listarTodos(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	/**
	 * Inativar cliente (soft delete)
	 */
	public void inativar(Long id) {
		Cliente cliente = buscarPorId(id)
				.orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));

		cliente.inativar();
		clienteRepository.save(cliente);
	}

	/**
	 * Buscar clientes por nome
	 */
	@Transactional(readOnly = true)
	public List<Cliente> buscarPorNome(String nome) {
		return clienteRepository.findByNomeContainingIgnoreCase(nome);
	}

	/**
	 * Validações de negócio
	 */
	private void validarDadosCliente(Cliente cliente) {
		if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
			throw new IllegalArgumentException("Nome é obrigatório");
		}

		if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
			throw new IllegalArgumentException("Email é obrigatório");
		}

		if (cliente.getNome().length() < 2) {
			throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres");
		}
	}
}