package com.deliverytech.delivery.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.deliverytech.delivery.config.TestDataConfiguration;
import com.deliverytech.delivery.entities.Cliente;
import com.deliverytech.delivery.repositories.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestDataConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Testes de Integração do ClienteController")
public class ClienteControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ClienteRepository clienteRepository;

	@Test
	@DisplayName("Deve criar cliente com dados válidos")
	void should_CreateCliente_When_ValidData() throws Exception {
		Cliente novoCliente = new Cliente();
		novoCliente.setNome("Maria Silva");
		novoCliente.setEmail("maria@email.com");
		novoCliente.setCpf("98765432100");
		novoCliente.setTelefone("11888888888");

		mockMvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(novoCliente))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome", is("Maria Silva"))).andExpect(jsonPath("$.email", is("maria@email.com")))
				.andExpect(jsonPath("$.id", notNullValue()));
	}

	@Test
	@DisplayName("Deve retornar erro 400 quando dados inválidos")
	void should_ReturnBadRequest_When_InvalidData() throws Exception {
		Cliente clienteInvalido = new Cliente();
		clienteInvalido.setNome(""); // Nome vazio
		clienteInvalido.setEmail("email-invalido"); // Email inválido

		mockMvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(clienteInvalido))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", hasSize(greaterThan(0))));
	}

	@Test
	@DisplayName("Deve buscar cliente por ID existente")
	void should_ReturnCliente_When_IdExists() throws Exception {
		Cliente cliente = clienteRepository.findAll().get(0);

		mockMvc.perform(get("/api/clientes/{id}", cliente.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(cliente.getId().intValue())))
				.andExpect(jsonPath("$.nome", is(cliente.getNome())))
				.andExpect(jsonPath("$.email", is(cliente.getEmail())));
	}

	@Test
	@DisplayName("Deve retornar 404 quando cliente não existe")
	void should_ReturnNotFound_When_ClienteNotExists() throws Exception {
		mockMvc.perform(get("/api/clientes/{id}", 999L)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", containsString("Cliente não encontrado")));
	}

	@Test
	@DisplayName("Deve listar clientes com paginação")
	void should_ReturnPagedClientes_When_RequestedWithPagination() throws Exception {
		mockMvc.perform(get("/api/clientes").param("page", "0").param("size", "10")).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(greaterThan(0))))
				.andExpect(jsonPath("$.totalElements", greaterThan(0))).andExpect(jsonPath("$.number", is(0)))
				.andExpect(jsonPath("$.size", is(10)));
	}

	@Test
	@DisplayName("Deve atualizar cliente existente")
	void should_UpdateCliente_When_ClienteExists() throws Exception {
		Cliente cliente = clienteRepository.findAll().get(0);
		cliente.setNome("Nome Atualizado");
		cliente.setTelefone("11777777777");

		mockMvc.perform(put("/api/clientes/{id}", cliente.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cliente))).andExpect(status().isOk())
				.andExpect(jsonPath("$.nome", is("Nome Atualizado")))
				.andExpect(jsonPath("$.telefone", is("11777777777")));
	}

	@Test
	@DisplayName("Deve deletar cliente existente")
	void should_DeleteCliente_When_ClienteExists() throws Exception {
		Cliente cliente = clienteRepository.findAll().get(0);

		mockMvc.perform(delete("/api/clientes/{id}", cliente.getId())).andExpect(status().isNoContent());

		mockMvc.perform(get("/api/clientes/{id}", cliente.getId())).andExpect(status().isNotFound());
	}
}