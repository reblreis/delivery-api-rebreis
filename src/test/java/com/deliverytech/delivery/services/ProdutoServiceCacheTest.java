package com.deliverytech.delivery.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.DirtiesContext;

import com.deliverytech.delivery.entities.Produto;
import com.deliverytech.delivery.repositories.ProdutoRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // limpa contexto/caches
public class ProdutoServiceCacheTest {

	@Autowired
	private ProdutoService produtoService;

	@MockBean
	private ProdutoRepository produtoRepository;

	@Autowired
	private CacheManager cacheManager;

	@Test
	void deveInvalidarCacheQuandoProdutoForSalvo() {
		// Arrange: simula um produto de categoria "Pizza"
		String categoria = "Pizza";

		Produto produtoMock = new Produto("Pizza Margherita", categoria, new BigDecimal("45.00"));
		produtoMock.setDisponivel(true);
		produtoMock.setId(1L);

		when(produtoRepository.findByCategoriaAndDisponivelTrue(categoria)).thenReturn(List.of(produtoMock));

		// Primeira chamada
		produtoService.buscarPorCategoria(categoria);
		verify(produtoRepository, times(1)).findByCategoriaAndDisponivelTrue(categoria);

		// Segunda chamada (cache)
		produtoService.buscarPorCategoria(categoria);
		verify(produtoRepository, times(1)).findByCategoriaAndDisponivelTrue(categoria);

		// Salva o produto e invalida o cache
		produtoService.salvarProduto(produtoMock);

		// definição de retorno do mock ANTES da próxima chamada
		when(produtoRepository.findByCategoriaAndDisponivelTrue(categoria)).thenReturn(Collections.emptyList());

		// Terceira chamada (sem cache)
		List<Produto> resultado = produtoService.buscarPorCategoria(categoria);
		assertThat(resultado).isEmpty();

		verify(produtoRepository, times(2)).findByCategoriaAndDisponivelTrue(categoria);
	}
}