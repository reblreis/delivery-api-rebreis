package com.deliverytech.delivery.security;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.deliverytech.delivery.services.UsuarioService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final UsuarioService usuarioService;
	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtUtil.extractUsername(jwtToken);
			} catch (IllegalArgumentException e) {
				logger.error("Não foi possível obter o JWT Token", e);
			} catch (ExpiredJwtException e) {
				logger.error("JWT Token expirado", e);
			} catch (MalformedJwtException e) {
				logger.error("JWT Token malformado", e);
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.usuarioService.loadUserByUsername(username);

			if (jwtUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();

		// Sempre permite as rotas públicas
		if (path.startsWith("/api/auth") || path.startsWith("/h2-console") || path.startsWith("/actuator")) {
			return true;
		}

		// Permite Swagger apenas fora do profile "prod"
		String profilesParam = request.getServletContext().getInitParameter("spring.profiles.active");
		String[] profiles = profilesParam != null ? profilesParam.split(",") : new String[0];

		boolean isProd = Arrays.asList(profiles).contains("prod");

		if (!isProd && (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")
				|| path.startsWith("/swagger-resources") || path.startsWith("/webjars"))) {
			return true;
		}

		return false;
	}
}