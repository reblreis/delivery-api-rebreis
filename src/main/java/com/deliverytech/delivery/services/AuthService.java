package com.deliverytech.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.dtos.request.LoginRequest;
import com.deliverytech.delivery.dtos.request.RegisterRequest;
import com.deliverytech.delivery.dtos.response.LoginResponse;
import com.deliverytech.delivery.entities.Cliente;
import com.deliverytech.delivery.entities.Usuario;
import com.deliverytech.delivery.enums.Role;
import com.deliverytech.delivery.repositories.ClienteRepository;
import com.deliverytech.delivery.repositories.UsuarioRepository;
import com.deliverytech.delivery.security.JwtUtil;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Usuario loadUserByUsername(String email) throws UsernameNotFoundException {
		return usuarioRepository.findByEmailAndAtivo(email, true)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
	}

	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}

	public Usuario criarUsuario(RegisterRequest request) {

		Usuario usuario = new Usuario();
		usuario.setNome(request.getNome());
		usuario.setEmail(request.getEmail());
		usuario.setSenha(passwordEncoder.encode(request.getSenha()));
		usuario.setRole(request.getRole());
		usuario.setRestauranteId(request.getRestauranteId());

		return usuarioRepository.save(usuario);
	}

	public ResponseEntity<?> login(LoginRequest request) {
		Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

		if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
			return ResponseEntity.badRequest().body("Senha inválida");
		}

		String token = jwtUtil.generateToken(usuario);
		String tipo = "Bearer";
		Long expiracao = jwtUtil.getExpiracao();

		return ResponseEntity.ok(new LoginResponse(token, tipo, expiracao, usuario));
	}

	public ResponseEntity<?> register(RegisterRequest request) {
		if (existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body("E-mail já cadastrado");
		}

		Usuario novoUsuario = criarUsuario(request);

		// Se for cliente, também criar o Cliente (opcional, dependendo do seu fluxo):
		if (request.getRole() == Role.CLIENTE) {
			Cliente cliente = new Cliente();
			cliente.setUsuario(novoUsuario);
			clienteRepository.save(cliente);
		}

		return ResponseEntity.ok("Usuário registrado com sucesso");
	}

	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	}

	public Usuario buscarPorEmail(String email) {
		return usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	}

	public Long getClienteIdLogado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new RuntimeException("Usuário não autenticado");
		}

		String email = authentication.getName();
		Usuario usuario = buscarPorEmail(email);

		Cliente cliente = clienteRepository.findByUsuario(usuario)
				.orElseThrow(() -> new RuntimeException("Cliente não encontrado para o usuário logado"));

		return cliente.getId();
	}
}