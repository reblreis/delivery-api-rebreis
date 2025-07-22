package com.deliverytech.delivery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dtos.request.LoginRequest;
import com.deliverytech.delivery.dtos.request.RegisterRequest;
import com.deliverytech.delivery.dtos.response.LoginResponse;
import com.deliverytech.delivery.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Operações de autenticação e autorização")
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	@Operation(summary = "Fazer login", description = "Autentica um usuário e retorna um token JWT", tags = {
			"Autenticação" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Login realizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class), examples = @ExampleObject(name = "Login bem-sucedido", value = """
						{
							"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
							"tipo": "Bearer",
							"expiracao": 86400000,
							"usuario": {
								"id": 1,
								"nome": "João Silva",
								"email": "joao@email.com",
								"role": "CLIENTE"
						}
					}
					"""))),

			@ApiResponse(responseCode = "401", description = "Credenciais inválidas") })

	public ResponseEntity<?> login(
			@Parameter(description = "Credenciais de login") @Valid @RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}

	@PostMapping("/register")
	@Operation(summary = "Registrar novo usuário", description = "Cria uma nova conta de usuário no sistema", tags = {
			"Autenticação" })
	public ResponseEntity<?> register(
			@Parameter(description = "Dados para criação da conta") @Valid @RequestBody RegisterRequest registerRequest) {
		return authService.register(registerRequest);
	}

}