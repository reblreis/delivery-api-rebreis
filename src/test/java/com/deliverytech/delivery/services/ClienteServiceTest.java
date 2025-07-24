package com.deliverytech.delivery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.deliverytech.delivery.entities.Cliente;
import com.deliverytech.delivery.repositories.ClienteRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ClienteService")
public class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;

	@InjectMocks
	private ClienteService clienteService;

	private Cliente cliente;

	@BeforeEach
	void setUp() {
		cliente = new Cliente();
		cliente.setId(1L);
		cliente.setNome("João Silva");
		cliente.setEmail("joao@email.com");
		cliente.setTelefone("11999999999");
		cliente.setCpf("12345678901");
	}

	@Test
	@DisplayName("Deve salvar cliente com dados válidos")
	void should_SaveCliente_When_ValidData() {
		// Given
		when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
		when(clienteRepository.existsByCpf(anyString())).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

		// When
		Cliente resultado = clienteService.cadastrar(cliente);
		// Then
		assertNotNull(resultado);
		assertEquals("João Silva", resultado.getNome());
		assertEquals("joao@email.com", resultado.getEmail());
		verify(clienteRepository).save(cliente);
		verify(clienteRepository).existsByEmail("joao@email.com");
		verify(clienteRepository).existsByCpf("12345678901");
	}

	@Test
	@DisplayName("Deve lançar exceção quando email já existe")
	void should_ThrowException_When_EmailAlreadyExists() {
		// Given
		when(clienteRepository.existsByEmail(anyString())).thenReturn(true);
		// When & Then
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> clienteService.cadastrar(cliente));
		assertEquals("Email já cadastrado: joao@email.com", exception.getMessage());
		verify(clienteRepository, never()).save(any());
	}

	@Test
	@DisplayName("Deve lançar exceção quando CPF já existe")
	void should_ThrowException_When_CpfAlreadyExists() {
		// Given
		when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
		when(clienteRepository.existsByCpf(anyString())).thenReturn(true);
		// When & Then
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> clienteService.cadastrar(cliente));
		assertEquals("CPF já cadastrado: 12345678901", exception.getMessage());
		verify(clienteRepository, never()).save(any());
	}

	@Test
	@DisplayName("Deve buscar cliente por ID existente")
	void should_ReturnCliente_When_IdExists() {
		// Given
		when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

		// When
		Optional<Cliente> resultado = clienteService.buscarPorId(1L);
		// Then
		assertTrue(resultado.isPresent());
		assertEquals("João Silva", resultado.get().getNome());
		verify(clienteRepository).findById(1L);
	}

	@Test
	@DisplayName("Deve retornar vazio quando ID não existe")
	void should_ReturnEmpty_When_IdNotExists() {
		// Given
		when(clienteRepository.findById(999L)).thenReturn(Optional.empty());
		// When
		Optional<Cliente> resultado = clienteService.buscarPorId(999L);
		// Then
		assertFalse(resultado.isPresent());
		verify(clienteRepository).findById(999L);
	}

	@Test
	@DisplayName("Deve listar clientes com paginação")
	void should_ReturnPagedClientes_When_RequestedWithPagination() {
		// Given
		Pageable pageable = PageRequest.of(0, 10);
		Page<Cliente> page = new PageImpl<>(Arrays.asList(cliente));
		when(clienteRepository.findAll(pageable)).thenReturn(page);
		// When
		Page<Cliente> resultado = clienteService.listarTodos(pageable);
		// Then
		assertNotNull(resultado);
		assertEquals(1, resultado.getTotalElements());
		assertEquals("João Silva", resultado.getContent().get(0).getNome());
		verify(clienteRepository).findAll(pageable);
	}

	@Test
	@DisplayName("Deve atualizar cliente existente")
	void should_UpdateCliente_When_ClienteExists() {
		// Given
		Cliente clienteAtualizado = new Cliente();
		clienteAtualizado.setId(1L);
		clienteAtualizado.setNome("João Santos");
		clienteAtualizado.setEmail("joao.santos@email.com");
		when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
		when(clienteRepository.existsByEmail("joao.santos@email.com")).thenReturn(false);
		when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteAtualizado);
		// When
		Cliente resultado = clienteService.atualizar(1L, clienteAtualizado);
		// Then
		assertNotNull(resultado);
		assertEquals("João Santos", resultado.getNome());
		verify(clienteRepository).save(any(Cliente.class));
	}
}