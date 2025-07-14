package com.deliverytech.delivery.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.deliverytech.delivery")
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.group("public")
				.packagesToScan("com.deliverytech.delivery")
				.pathsToMatch("/**")
				.build();
	}

	// A URL padrão é /v3/api-docs/
	// O SpringDoc automaticamente gera o Swagger UI
}