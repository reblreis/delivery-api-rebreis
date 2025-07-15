package com.deliverytech.delivery.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@ComponentScan(basePackages = "com.deliverytech.delivery")
public class SwaggerConfig {
	
	 // Grupo de APIs - define quais pacotes e paths vão para a doc

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.group("delivery-api")
				.packagesToScan("com.deliverytech.delivery")
				.pathsToMatch("/**")
				.build();
	}

	// Personalização das informações da documentação
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("API Delivery")
                    .description("Sistema para controle de pedidos, clientes e restaurantes.")
                    .version("1.0")
                    .contact(new Contact()
                        .name("Regina Reis")
                        .url("http://www.deliverytech.com.br")
                        .email("contato@deliverytech.com.br"))
                );
    }
}