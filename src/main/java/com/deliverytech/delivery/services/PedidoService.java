package com.deliverytech.delivery.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery.dtos.request.PedidoRequest;
import com.deliverytech.delivery.dtos.response.PedidoResponse;
import com.deliverytech.delivery.entities.Cliente;
import com.deliverytech.delivery.entities.ItemPedido;
import com.deliverytech.delivery.entities.Pedido;
import com.deliverytech.delivery.entities.Produto;
import com.deliverytech.delivery.entities.Restaurante;
import com.deliverytech.delivery.enums.FormaPagamento;
import com.deliverytech.delivery.enums.StatusPedido;
import com.deliverytech.delivery.repositories.ClienteRepository;
import com.deliverytech.delivery.repositories.PedidoRepository;
import com.deliverytech.delivery.repositories.ProdutoRepository;
import com.deliverytech.delivery.repositories.RestauranteRepository;

@Service
@Transactional
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private AuthService authService;

	/**
	 * Criar novo pedido
	 */
	public Pedido criarPedido(Long clienteId, Long restauranteId) {
		Cliente cliente = clienteRepository.findById(clienteId)
				.orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + clienteId));

		Restaurante restaurante = restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + restauranteId));

		if (!cliente.getAtivo()) {
			throw new IllegalArgumentException("Cliente inativo não pode fazer pedidos");
		}

		if (!restaurante.getAtivo()) {
			throw new IllegalArgumentException("Restaurante não está disponível");
		}

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setStatus(StatusPedido.PENDENTE);

		return pedidoRepository.save(pedido);
	}

	/**
	 * Adicionar item ao pedido
	 */
	public Pedido adicionarItem(Long pedidoId, Long produtoId, Integer quantidade) {
		Pedido pedido = buscarPorId(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + pedidoId));

		Produto produto = produtoRepository.findById(produtoId)
				.orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + produtoId));

		if (!produto.getDisponivel()) {
			throw new IllegalArgumentException("Produto não disponível: " + produto.getNome());
		}

		if (quantidade <= 0) {
			throw new IllegalArgumentException("Quantidade deve ser maior que zero");
		}

		// Verificar se produto pertence ao mesmo restaurante do pedido
		if (!produto.getRestaurante().getId().equals(pedido.getRestaurante().getId())) {
			throw new IllegalArgumentException("Produto não pertence ao restaurante do pedido");
		}

		ItemPedido item = new ItemPedido();
		item.setPedido(pedido);
		item.setProduto(produto);
		item.setQuantidade(quantidade);
		item.setPrecoUnitario(produto.getPreco());
		item.calcularSubtotal();

		pedido.adicionarItem(item);

		return pedidoRepository.save(pedido);
	}

	/**
	 * Confirmar pedido
	 */
	public Pedido confirmarPedido(Long pedidoId) {
		Pedido pedido = buscarPorId(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + pedidoId));

		if (pedido.getStatus() != StatusPedido.PENDENTE) {
			throw new IllegalArgumentException("Apenas pedidos pendentes podem ser confirmados");
		}

		if (pedido.getItens().isEmpty()) {
			throw new IllegalArgumentException("Pedido deve ter pelo menos um item");
		}

		pedido.confirmar();
		return pedidoRepository.save(pedido);
	}

	/**
	 * Buscar por ID
	 */
	@Transactional(readOnly = true)
	public Optional<Pedido> buscarPorId(Long id) {
		return pedidoRepository.findById(id);
	}

	/**
	 * Listar pedidos por cliente
	 */
	@Transactional(readOnly = true)
	public Page<PedidoResponse> listarPorCliente(Pageable pageable) {
		Long clienteId = authService.getClienteIdLogado(); // Você precisa implementar isso
		Page<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId, pageable);
		return pedidos.map(PedidoResponse::new); // ou usar mapper, se preferir
	}

	/**
	 * Buscar por número do pedido
	 */
	@Transactional(readOnly = true)
	public Optional<Pedido> buscarPorNumero(String numeroPedido) {
		return Optional.ofNullable(pedidoRepository.findByNumeroPedido(numeroPedido));
	}

	/**
	 * Cancelar pedido
	 */
	public Pedido cancelarPedido(Long pedidoId, String motivo) {
		Pedido pedido = buscarPorId(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + pedidoId));

		if (pedido.getStatus() == StatusPedido.ENTREGUE) {
			throw new IllegalArgumentException("Pedido já entregue não pode ser cancelado");
		}

		if (pedido.getStatus() == StatusPedido.CANCELADO) {
			throw new IllegalArgumentException("Pedido já está cancelado");
		}

		pedido.setStatus(StatusPedido.CANCELADO);
		if (motivo != null && !motivo.trim().isEmpty()) {
			pedido.setObservacoes(pedido.getObservacoes() + " | Cancelado: " + motivo);
		}

		return pedidoRepository.save(pedido);
	}

	/**
	 * Atualizar status de um pedido
	 */
	public PedidoResponse atualizarStatus(Long pedidoId, StatusPedido status) {
		Pedido pedido = buscarPorId(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + pedidoId));

		pedido.setStatus(status);

		Pedido atualizado = pedidoRepository.save(pedido);
		return new PedidoResponse(atualizado); // Corrigido: converte para DTO
	}

	public PedidoResponse cadastrar(PedidoRequest request) {
		Cliente cliente = clienteRepository.findById(request.getClienteId())
				.orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

		Restaurante restaurante = restauranteRepository.findById(request.getRestauranteId())
				.orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setStatus(StatusPedido.PENDENTE);
		pedido.setEnderecoEntrega(request.getEnderecoEntrega());
		pedido.setCepEntrega(request.getCepEntrega());
		pedido.setFormaPagamento(request.getFormaPagamento());

		// Adiciona itens
		request.getItens().forEach(itemRequest -> {
			Produto produto = produtoRepository.findById(itemRequest.getProdutoId()).orElseThrow(
					() -> new IllegalArgumentException("Produto não encontrado: " + itemRequest.getProdutoId()));

			ItemPedido item = new ItemPedido();
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setQuantidade(itemRequest.getQuantidade());
			item.setPrecoUnitario(produto.getPreco());
			item.calcularSubtotal();

			pedido.adicionarItem(item);
		});

		Pedido salvo = pedidoRepository.save(pedido);
		return new PedidoResponse(salvo); // ou use um mapper, se tiver
	}

	public PedidoResponse cadastrarNovoPedido() {
		Cliente cliente = clienteRepository.findById(1L) // ou qualquer ID real
				.orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

		Restaurante restaurante = restauranteRepository.findById(1L)
				.orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));

		FormaPagamento formaPagamento = FormaPagamento.valueOf("CARTAO".toUpperCase());
		Pedido novoPedido = Pedido.criarNovo(cliente, restaurante, "Rua A", "12345-678", formaPagamento);

		Pedido salvo = pedidoRepository.save(novoPedido);
		return new PedidoResponse(salvo);
	}

}