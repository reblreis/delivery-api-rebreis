package com.deliverytech.delivery.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.deliverytech.delivery.entities.Cliente;
import com.deliverytech.delivery.entities.ItemPedido;
import com.deliverytech.delivery.entities.Pedido;
import com.deliverytech.delivery.entities.Produto;
import com.deliverytech.delivery.entities.Restaurante;
import com.deliverytech.delivery.entities.Usuario;
import com.deliverytech.delivery.enums.FormaPagamento;
import com.deliverytech.delivery.enums.Role;
import com.deliverytech.delivery.enums.StatusPedido;
import com.deliverytech.delivery.repositories.ClienteRepository;
import com.deliverytech.delivery.repositories.PedidoRepository;
import com.deliverytech.delivery.repositories.ProdutoRepository;
import com.deliverytech.delivery.repositories.RestauranteRepository;
import com.deliverytech.delivery.repositories.UsuarioRepository;

@Component
public class DataLoader implements CommandLineRunner {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	private PasswordEncoder passwordEncoder;

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("=== INICIANDO CARGA DE DADOS DE TESTE ===");

		// Limpar dados existentes
		pedidoRepository.deleteAll();
		produtoRepository.deleteAll();
		restauranteRepository.deleteAll();
		usuarioRepository.deleteAll();
		clienteRepository.deleteAll();

		// Inserir usuário administrador primeiro
		inserirUsuarioAdmin();

		// Inserir dados de teste
		inserirClientes();
		inserirRestaurantes();
		inserirProdutos();
		inserirPedidos();

		// Executar testes das consultas
		testarConsultas();

		System.out.println("=== CARGA DE DADOS CONCLUÍDA ===");
	}

	// Método inserirClientes
	private void inserirClientes() {
		System.out.println("--- Inserindo Clientes ---");

		Usuario usuario1 = usuarioRepository.findByEmail("joao@email.com").orElseGet(() -> {
			Usuario u = new Usuario();
			u.setNome("João");
			u.setEmail("joao@email.com");
			u.setSenha(passwordEncoder.encode("123456"));
			u.setRole(Role.CLIENTE);
			return usuarioRepository.save(u);
		});

		Usuario usuario2 = usuarioRepository.findByEmail("maria@email.com").orElseGet(() -> {
			Usuario u = new Usuario();
			u.setNome("Maria");
			u.setEmail("maria@email.com");
			u.setSenha(passwordEncoder.encode("123"));
			u.setRole(Role.CLIENTE);
			return usuarioRepository.save(u);
		});

		Usuario usuario3 = usuarioRepository.findByEmail("pedro@email.com").orElseGet(() -> {
			Usuario u = new Usuario();
			u.setNome("Pedro");
			u.setEmail("pedro@email.com");
			u.setSenha(passwordEncoder.encode("123"));
			u.setRole(Role.CLIENTE);
			return usuarioRepository.save(u);
		});

		Cliente cliente1 = new Cliente();
		cliente1.setNome("João Silva");
		cliente1.setEmail("joao@email.com");
		cliente1.setTelefone("11999999999");
		cliente1.setEndereco("Rua A, 123");
		cliente1.setAtivo(true);
		cliente1.setUsuario(usuario1); // associação

		Cliente cliente2 = new Cliente();
		cliente2.setNome("Maria Santos");
		cliente2.setEmail("maria@email.com");
		cliente2.setTelefone("11888888888");
		cliente2.setEndereco("Rua B, 456");
		cliente2.setAtivo(true);
		cliente2.setUsuario(usuario2); // associação

		Cliente cliente3 = new Cliente();
		cliente3.setNome("Pedro Oliveira");
		cliente3.setEmail("pedro@email.com");
		cliente3.setTelefone("11777777777");
		cliente3.setEndereco("Rua C, 789");
		cliente3.setAtivo(false);
		cliente3.setUsuario(usuario3); // associação

		clienteRepository.saveAll(Arrays.asList(cliente1, cliente2, cliente3));
		System.out.println("✓ 3 clientes inseridos com usuários associados");
	}

	// Método inserirRestaurantes
	private void inserirRestaurantes() {
		System.out.println("--- Inserindo Restaurantes ---");

		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Pizza Express");
		restaurante1.setCategoria("Italiana");
		restaurante1.setEndereco("Av. Principal, 100");
		restaurante1.setTelefone("1133333333");
		restaurante1.setTaxaEntrega(new BigDecimal("3.50"));
		restaurante1.setAtivo(true);

		Restaurante restaurante2 = new Restaurante();
		restaurante2.setNome("Burger King");
		restaurante2.setCategoria("Fast Food");
		restaurante2.setEndereco("Rua Central, 200");
		restaurante2.setTelefone("1144444444");
		restaurante2.setTaxaEntrega(new BigDecimal("5.00"));
		restaurante2.setAtivo(true);

		restauranteRepository.saveAll(Arrays.asList(restaurante1, restaurante2));
		System.out.println("✓ 2 restaurantes inseridos");
	}

	// Método testarConsultas
	private void testarConsultas() {
		System.out.println("\n=== TESTANDO CONSULTAS DOS REPOSITORIES ===");

		// Teste ClienteRepository
		System.out.println("\n--- Testes ClienteRepository ---");

		var clientePorEmail = clienteRepository.findByEmail("joao@email.com");
		System.out.println("Cliente por email: "
				+ (clientePorEmail.isPresent() ? clientePorEmail.get().getNome() : "Não encontrado"));

		var clientesAtivos = clienteRepository.findByAtivoTrue();
		System.out.println("Clientes ativos: " + clientesAtivos.size());

		var clientesPorNome = clienteRepository.findByNomeContainingIgnoreCase("silva");
		System.out.println("Clientes com 'silva' no nome: " + clientesPorNome.size());

		boolean emailExiste = clienteRepository.existsByEmail("maria@email.com");
		System.out.println("Email maria@email.com existe: " + emailExiste);

		// ... continuar com outros testes
	}

	private void inserirProdutos() {
		System.out.println("--- Inserindo Produtos ---");

		var restaurantes = restauranteRepository.findAll();

		if (restaurantes.isEmpty()) {
			System.out.println("Nenhum restaurante encontrado para associar produtos.");
			return;
		}

		Restaurante restaurante1 = restaurantes.get(0);
		Restaurante restaurante2 = restaurantes.size() > 1 ? restaurantes.get(1) : restaurante1;

		Produto produto1 = new Produto();
		produto1.setNome("Pizza Margherita");
		produto1.setDescricao("Pizza tradicional com mussarela, tomate e manjericão");
		produto1.setPreco(new BigDecimal("30.00"));
		produto1.setCategoria("Pizza");
		produto1.setDisponivel(true);
		produto1.setRestaurante(restaurante1);

		Produto produto2 = new Produto();
		produto2.setNome("Cheeseburger");
		produto2.setDescricao("Hambúrguer com queijo cheddar, alface e tomate");
		produto2.setPreco(new BigDecimal("20.00"));
		produto2.setCategoria("Sanduíche");
		produto2.setDisponivel(true);
		produto2.setRestaurante(restaurante2);

		Produto produto3 = new Produto();
		produto3.setNome("Refrigerante");
		produto3.setDescricao("Lata 350ml");
		produto3.setPreco(new BigDecimal("6.00"));
		produto3.setCategoria("Bebida");
		produto3.setDisponivel(true);
		produto3.setRestaurante(restaurante2);

		Produto produto4 = new Produto();
		produto4.setNome("Pizza Calabresa");
		produto4.setDescricao("Pizza com calabresa, cebola e mussarela");
		produto4.setPreco(new BigDecimal("35.00"));
		produto4.setCategoria("Pizza");
		produto4.setDisponivel(true);
		produto4.setRestaurante(restaurante1);

		Produto produto5 = new Produto();
		produto5.setNome("Batata Frita");
		produto5.setDescricao("Porção média de batata frita");
		produto5.setPreco(new BigDecimal("12.00"));
		produto5.setCategoria("Acompanhamento");
		produto5.setDisponivel(true);
		produto5.setRestaurante(restaurante2);

		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5));

		System.out.println("✓ 5 produtos inseridos");
	}

	private void inserirPedidos() {
		System.out.println("--- Inserindo Pedidos ---");

		var clientes = clienteRepository.findAll();
		var restaurantes = restauranteRepository.findAll();
		var produtos = produtoRepository.findAll();

		if (clientes.isEmpty() || restaurantes.isEmpty() || produtos.size() < 2) {
			System.out.println("Necessário ter clientes, restaurantes e produtos para criar pedidos.");
			return;
		}

		Cliente cliente1 = clientes.get(0);
		Cliente cliente2 = clientes.size() > 1 ? clientes.get(1) : cliente1;

		Restaurante restaurante1 = restaurantes.get(0);
		Restaurante restaurante2 = restaurantes.get(1);

		Produto produto1 = produtos.get(0);
		Produto produto2 = produtos.get(1);
		Produto produto3 = produtos.get(2);

		// Pedido 1
		Pedido pedido1 = new Pedido();
		pedido1.setDataPedido(LocalDate.now());
		pedido1.setEnderecoEntrega("Rua X, 123");
		pedido1.setCepEntrega("12345-678");
		pedido1.setNumeroPedido("PED-0001");
		pedido1.setSubtotal(produto1.getPreco());
		pedido1.setTaxaEntrega(restaurante1.getTaxaEntrega());
		pedido1.setValorTotal(produto1.getPreco().add(restaurante1.getTaxaEntrega()));
		pedido1.setObservacoes("Entregar sem contato");
		pedido1.setStatus(StatusPedido.ENTREGUE);
		pedido1.setCliente(cliente1);
		pedido1.setRestaurante(restaurante1);

		ItemPedido item1 = new ItemPedido();
		item1.setProduto(produto1);
		item1.setQuantidade(1);
		item1.setPrecoUnitario(produto1.getPreco());
		item1.setSubtotal(produto1.getPreco());
		item1.setPedido(pedido1);

		pedido1.getItens().add(item1);
		pedido1.calcularTotais();

		// Pedido 2
		Pedido pedido2 = new Pedido();
		pedido2.setDataPedido(LocalDate.now());
		pedido2.setEnderecoEntrega("Av. Y, 456");
		pedido2.setCepEntrega("98765-432");
		pedido2.setNumeroPedido("PED-0002");
		BigDecimal subtotal2 = produto2.getPreco().add(produto3.getPreco());
		pedido2.setSubtotal(subtotal2);
		pedido2.setTaxaEntrega(restaurante2.getTaxaEntrega());
		pedido2.setValorTotal(subtotal2.add(restaurante2.getTaxaEntrega()));
		pedido2.setObservacoes("Chamar no interfone");
		pedido2.setStatus(StatusPedido.ENTREGUE);
		pedido2.setCliente(cliente2);
		pedido2.setRestaurante(restaurante2);

		ItemPedido item2 = new ItemPedido();
		item2.setProduto(produto2);
		item2.setQuantidade(2);
		item2.setPrecoUnitario(produto2.getPreco());
		item2.setSubtotal(produto2.getPreco().multiply(new BigDecimal(2)));
		item2.setPedido(pedido2);

		ItemPedido item3 = new ItemPedido();
		item3.setProduto(produto3);
		item3.setQuantidade(1);
		item3.setPrecoUnitario(produto3.getPreco());
		item3.setSubtotal(produto3.getPreco());
		item3.setPedido(pedido2);

		pedido2.getItens().add(item2);
		pedido2.getItens().add(item3);
		pedido2.calcularTotais();

		pedido1.setFormaPagamento(FormaPagamento.DINHEIRO);
		pedido2.setFormaPagamento(FormaPagamento.CARTAO_CREDITO);
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));

		System.out.println("✓ 2 pedidos inseridos");
	}

	private void inserirUsuarioAdmin() {
		System.out.println("--- Inserindo usuário ADMIN ---");

		usuarioRepository.findByEmail("admin@delivery.com").orElseGet(() -> {
			Usuario admin = new Usuario();
			admin.setNome("Administrador");
			admin.setEmail("admin@delivery.com");
			admin.setSenha(passwordEncoder.encode("admin123"));
			admin.setRole(Role.ADMIN);
			admin.setAtivo(true);
			Usuario salvo = usuarioRepository.save(admin);
			usuarioRepository.flush(); // opcional: garante persistência imediata
			return salvo;
		});
	}
}