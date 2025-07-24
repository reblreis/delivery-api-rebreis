package com.deliverytech.delivery.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;

import com.deliverytech.delivery.entities.Cliente;
import com.deliverytech.delivery.entities.Produto;
import com.deliverytech.delivery.repositories.ClienteRepository;
import com.deliverytech.delivery.repositories.ProdutoRepository;

import jakarta.annotation.PostConstruct;

@TestConfiguration
public class TestDataConfiguration {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ProdutoRepository produtoRepository;

	@PostConstruct
	public void setupTestData() {
		clienteRepository.deleteAll();
		produtoRepository.deleteAll();

		Cliente cliente = new Cliente();
		cliente.setNome("João Teste");
		cliente.setEmail("joao.teste@email.com");
		cliente.setCpf("12345678901");
		cliente.setTelefone("11999999999");
		clienteRepository.save(cliente);

		Produto produto = new Produto();
		produto.setNome("Pizza Teste");
		produto.setDescricao("Pizza para testes");
		produto.setPreco(BigDecimal.valueOf(29.90));
		produto.setEstoque(50);
		produtoRepository.save(produto);
	}
}