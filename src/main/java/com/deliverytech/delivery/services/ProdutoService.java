package com.deliverytech.delivery.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.dtos.ProdutoDTO;
import com.deliverytech.delivery.entities.Produto;
import com.deliverytech.delivery.entities.Restaurante;
import com.deliverytech.delivery.repositories.ProdutoRepository;
import com.deliverytech.delivery.repositories.RestauranteRepository;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	/**
	 * Cadastrar novo produto a partir do DTO
	 */
	@Transactional
	public Produto cadastrar(ProdutoDTO produtoDTO) {
		validarDadosProduto(produtoDTO);

		Produto produto = new Produto();
		produto.setNome(produtoDTO.getNome());
		produto.setDescricao(produtoDTO.getDescricao());
		produto.setPreco(produtoDTO.getPreco());
		produto.setCategoria(produtoDTO.getCategoria());
		produto.setDisponivel(produtoDTO.getDisponivel());

		produto.setRestaurante(restauranteRepository.findById(produtoDTO.getRestauranteId()).orElseThrow(
				() -> new IllegalArgumentException("Restaurante não encontrado: " + produtoDTO.getRestauranteId())));

		return produtoRepository.save(produto);
	}

	/**
	 * Listar todos os produtos como DTO
	 */
	public List<ProdutoDTO> listarTodos() {
		List<Produto> produtos = produtoRepository.findAll();
		List<ProdutoDTO> produtosDTO = new ArrayList<>();

		for (Produto produto : produtos) {
			ProdutoDTO dto = new ProdutoDTO(produto.getId(), produto.getNome(), produto.getDescricao(),
					produto.getPreco(), produto.getCategoria(), produto.getDisponivel());
			dto.setRestauranteId(produto.getRestaurante().getId());
			produtosDTO.add(dto);
		}

		return produtosDTO;
	}

	/**
	 * Buscar produto por ID
	 */
	public Produto buscarPorId(Long id) {
		return produtoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
	}

	/**
	 * Atualizar produto a partir do DTO
	 */
	@Transactional
	public Produto atualizar(Long id, ProdutoDTO produtoDTO) {
		Produto produtoExistente = produtoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

		validarDadosProduto(produtoDTO);

		produtoExistente.setNome(produtoDTO.getNome());
		produtoExistente.setDescricao(produtoDTO.getDescricao());
		produtoExistente.setPreco(produtoDTO.getPreco());
		produtoExistente.setCategoria(produtoDTO.getCategoria());
		produtoExistente.setDisponivel(produtoDTO.getDisponivel());

		produtoExistente.setRestaurante(restauranteRepository.findById(produtoDTO.getRestauranteId()).orElseThrow(
				() -> new IllegalArgumentException("Restaurante não encontrado: " + produtoDTO.getRestauranteId())));

		return produtoRepository.save(produtoExistente);
	}

	/**
	 * Excluir produto
	 */
	@Transactional
	public void excluir(Long id) {
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

		produtoRepository.delete(produto);
	}

	private void validarDadosProduto(ProdutoDTO produto) {
		if (produto.getNome() == null || produto.getNome().isEmpty()) {
			throw new IllegalArgumentException("Nome do produto é obrigatório");
		}
		if (produto.getDescricao() == null || produto.getDescricao().isEmpty()) {
			throw new IllegalArgumentException("Descrição do produto é obrigatória");
		}
		if (produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Preço do produto deve ser maior que zero");
		}
		if (produto.getCategoria() == null || produto.getCategoria().isEmpty()) {
			throw new IllegalArgumentException("Categoria do produto é obrigatória");
		}
	}

	// Buscar produtos por restaurante
	public List<Produto> buscarPorRestaurante(Long restauranteId) {
		Restaurante restaurante = restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + restauranteId));
		return produtoRepository.findByRestauranteAndDisponivelTrue(restaurante);
	}

	public Produto inativar(Long id) {
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

		if (!produto.getDisponivel()) {
			throw new IllegalArgumentException("Produto já está inativo: " + id);
		}

		produto.setDisponivel(false);
		return produtoRepository.save(produto);
	}

	public List<Produto> buscarDisponiveis() {
		return produtoRepository.findByDisponivelTrue();
	}

	public List<Produto> buscarPorCategoria(String categoria) {
		return produtoRepository.findByCategoriaAndDisponivelTrue(categoria);
	}

	public List<Produto> buscarPorFaixaDePreco(BigDecimal preco) {
		return produtoRepository.findByPrecoLessThanEqualAndDisponivelTrue(preco);
	}
}