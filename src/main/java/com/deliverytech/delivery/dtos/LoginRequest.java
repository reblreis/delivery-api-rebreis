package com.deliverytech.delivery.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message = "Email é obrigatório")
	@Email(message = "Email deve ter formato válido")
	private String email;

	@NotBlank(message = "Senha é obrigatória")
	private String senha;

	// Construtores
	public LoginRequest() {

	}

	public LoginRequest(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	// Getters e Setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
