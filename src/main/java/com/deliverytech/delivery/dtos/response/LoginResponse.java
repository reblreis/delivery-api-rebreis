package com.deliverytech.delivery.dtos.response;

import com.deliverytech.delivery.entities.Usuario;

public class LoginResponse {

	private String token;
	private String tipo = "Bearer";
	private Long expiracao;
	private UserResponse usuario;

	// Construtores
	public LoginResponse() {

	}

	public LoginResponse(String token, String tipo, Long expiracao, Usuario usuario) {
		super();
		this.token = token;
		this.tipo = tipo;
		this.expiracao = expiracao;
		this.usuario = new UserResponse(usuario);
	}

	// Getters e Setters
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getExpiracao() {
		return expiracao;
	}

	public void setExpiracao(Long expiracao) {
		this.expiracao = expiracao;
	}

	public UserResponse getUsuario() {
		return usuario;
	}

	public void setUsuario(UserResponse usuario) {
		this.usuario = usuario;
	}

}
