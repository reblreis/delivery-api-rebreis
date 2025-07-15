package com.deliverytech.delivery.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.deliverytech.delivery.entities.Usuario;

public class SecurityUtils {

	public static Usuario getCurrentUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			return (Usuario) authentication.getPrincipal();
		}
		throw new RuntimeException("Usuário não autenticado");
	}

	public static Long getCurrentUserId() {
		return getCurrentUser().getId();
	}

	public static String getCurrentUserEmail() {
		return getCurrentUser().getEmail();
	}

	public static String getCurrentUserRole() {
		return getCurrentUser().getRole().name();
	}

	public static Long getCurrentRestauranteId() {
		Usuario usuario = getCurrentUser();
		return usuario.getRestauranteId();
	}

	public static boolean hasRole(String role) {

		try {
			Usuario usuario = getCurrentUser();
			return usuario.getRole().name().equals(role);
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isAdmin() {
		return hasRole("ADMIN");
	}

	public static boolean isCliente() {
		return hasRole("CLIENTE");
	}

	public static boolean isRestaurante() {
		return hasRole("RESTAURANTE");
	}

	public static boolean isEntregador() {
		return hasRole("ENTREGADOR");
	}
}