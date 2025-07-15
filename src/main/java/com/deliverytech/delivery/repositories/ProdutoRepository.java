package com.deliverytech.delivery.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery.entities.Produto;
import com.deliverytech.delivery.entities.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	// Produtos disponíveis
	List<Produto> findByDisponivelTrue();

	// Produtos por restaurante
	List<Produto> findByRestauranteAndDisponivelTrue(Restaurante restaurante);

	List<Produto> findByRestauranteIdAndDisponivelTrue(Long restauranteId);

	List<Produto> findByRestauranteId(Long restauranteId);

	// Por categoria
	List<Produto> findByCategoriaAndDisponivelTrue(String categoria);

	List<Produto> findByCategoria(String categoria);

	// Por nome
	List<Produto> findByNomeContainingIgnoreCaseAndDisponivelTrue(String nome);

	// Por faixa de preço
	List<Produto> findByPrecoBetweenAndDisponivelTrue(BigDecimal precoMin, BigDecimal precoMax);

	List<Produto> findByPrecoLessThanEqualAndDisponivelTrue(BigDecimal preco);

	List<Produto> findByPrecoLessThanEqual(BigDecimal preco);

	// Ordenações
	List<Produto> findByDisponivelTrueOrderByPrecoAsc();

	List<Produto> findByDisponivelTrueOrderByPrecoDesc();
}