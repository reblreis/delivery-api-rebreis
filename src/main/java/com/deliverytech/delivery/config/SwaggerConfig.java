package com.deliverytech.delivery.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	private final String serverPort;

	public SwaggerConfig(@Value("${server.port:8080}") String serverPort) {
		this.serverPort = serverPort;
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(apiInfo())
				.servers(List.of(
						new Server().url("http://localhost:" + serverPort).description("Servidor de Desenvolvimento"),
						new Server().url("https://api.deliverytech.com").description("Servidor de Produção")))
				.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
				.components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("Bearer Authentication",
						createAPIKeyScheme()));
	}

	private Info apiInfo() {
		return new Info().title("DeliveryTech API").description("API REST para sistema de delivery de comida")
				.version("1.0.0")
				.contact(new Contact().name("Equipe DeliveryTech").email("dev@deliverytech.com")
						.url("https://deliverytech.com"))
				.license(new License().name("MIT License").url("https://opensource.org/licenses/MIT"));
	}

	private SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer")
				.description("Insira o token JWT obtido no endpoint /api/auth/login");
	}

}