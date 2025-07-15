package com.deliverytech.delivery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dtos.LoginRequest;
import com.deliverytech.delivery.dtos.LoginResponse;
import com.deliverytech.delivery.dtos.RegisterRequest;
import com.deliverytech.delivery.dtos.UserResponse;
import com.deliverytech.delivery.entities.Usuario;
import com.deliverytech.delivery.security.JwtUtil;
import com.deliverytech.delivery.security.SecurityUtils;
import com.deliverytech.delivery.services.AuthService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthService authService;

	@Autowired
	private JwtUtil jwtUtil;

	@Value("${jwt.expiration}")
	private Long jwtExpiration;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

		try

		{

			// Autenticar usuário
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));

			// Carregar detalhes do usuário
			UserDetails userDetails = authService.loadUserByUsername(loginRequest.getEmail());

			// Gerar token JWT
			String token = jwtUtil.generateToken(userDetails);

			// Criar resposta
			Usuario usuario = (Usuario) userDetails;
			UserResponse userResponse = new UserResponse(usuario);
			LoginResponse loginResponse = new LoginResponse(token, jwtExpiration, userResponse);
			return ResponseEntity.ok(loginResponse);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(401).body("Credenciais inválidas");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erro interno do servidor");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {

		try {

			// Verificar se email já existe
			if (authService.existsByEmail(registerRequest.getEmail())) {
				return ResponseEntity.badRequest().body("Email já está em uso");
			}
			// Criar novo usuário
			Usuario novoUsuario = authService.criarUsuario(registerRequest);

			// Retornar dados do usuário (sem token - usuário deve fazer login)
			UserResponse userResponse = new UserResponse(novoUsuario);
			return ResponseEntity.status(201).body(userResponse);
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Erro ao criar usuário: " + e.getMessage());
		}
	}

	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser() {

		try {

			Usuario usuarioLogado = SecurityUtils.getCurrentUser();
			UserResponse userResponse = new UserResponse(usuarioLogado);
			return ResponseEntity.ok(userResponse);
		} catch (Exception e) {
			return ResponseEntity.status(401).body("Usuário não auten􀆟cado");
		}
	}
}