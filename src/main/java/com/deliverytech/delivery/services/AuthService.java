package com.deliverytech.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.dtos.RegisterRequest;
import com.deliverytech.delivery.entities.Usuario;
import com.deliverytech.delivery.repositories.UsuarioRepository;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
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

	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	}

	public Usuario buscarPorEmail(String email) {
		return usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	}
}