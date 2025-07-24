package com.deliverytech.delivery.entities;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClienteTest {

	@Test
	@DisplayName("Deve criar cliente com construtor padrão")
	void should_CreateCliente_When_DefaultConstructor() {
		Cliente cliente = new Cliente();
		assertNotNull(cliente);
		assertNull(cliente.getId());
	}

	@Test
	@DisplayName("Deve definir e obter propriedades corretamente")
	void should_SetAndGetProperties_When_ValidValues() {
		Cliente cliente = new Cliente();
		cliente.setNome("Teste");
		cliente.setEmail("teste@email.com");
		assertEquals("Teste", cliente.getNome());
		assertEquals("teste@email.com", cliente.getEmail());
	}

	@Test
	@DisplayName("Deve comparar clientes corretamente")
	void should_CompareClientes_When_SameId() {
		Cliente cliente1 = new Cliente();
		cliente1.setId(1L);
		Cliente cliente2 = new Cliente();
		cliente2.setId(1L);
		assertEquals(cliente1, cliente2);
		assertEquals(cliente1.hashCode(), cliente2.hashCode());
	}

	@Test
	@DisplayName("Deve gerar string representation corretamente")
	void should_GenerateToString_When_Called() {
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setNome("João");
		String result = cliente.toString();
		assertTrue(result.contains("João"));
		assertTrue(result.contains("1"));
	}

}
