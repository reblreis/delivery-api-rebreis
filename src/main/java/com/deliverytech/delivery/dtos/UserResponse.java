package com.deliverytech.delivery.dtos;

import java.time.LocalDateTime;

import com.deliverytech.delivery.entities.Usuario;
import com.deliverytech.delivery.enums.Role;

public class UserResponse {

	private Long id;
	private String nome;
	private String email;
	private Role role;
	private Boolean ativo;
	private LocalDateTime dataCriacao;
	private Long restauranteId;

	// Construtores
	public UserResponse() {
	}

	public UserResponse(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.role = usuario.getRole();
		this.ativo = usuario.getAtivo();
		this.dataCriacao = usuario.getDataCriacao();
		this.restauranteId = usuario.getRestauranteId();
	}

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Long getRestauranteId() {
		return restauranteId;
	}

	public void setRestauranteId(Long restauranteId) {
		this.restauranteId = restauranteId;
	}

}
