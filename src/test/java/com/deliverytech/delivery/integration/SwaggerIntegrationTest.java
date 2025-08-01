package com.deliverytech.delivery.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.deliverytech.delivery.security.JwtAuthenticationFilter;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SwaggerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Test
	@DisplayName("Swagger UI deve estar acessível sem autenticação")
	public void testSwaggerUIAccessible() {
		String url = "http://localhost:" + port + "/swagger-ui/index.html";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		if (response.getStatusCode() != HttpStatus.OK) {
			// fallback
			url = "http://localhost:" + port + "/swagger-ui.html";
			response = restTemplate.getForEntity(url, String.class);
		}

		System.out.println("Swagger UI response status: " + response.getStatusCode());
		System.out.println("Swagger UI body: " + response.getBody());

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("Swagger UI"));
	}

	@Test
	public void testApiDocsAccessible() {
		String url = "http://localhost:" + port + "/v3/api-docs";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		System.out.println("Status: " + response.getStatusCode());
		System.out.println("Body: " + response.getBody()); // <-- aqui vemos se veio null
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("openapi"));
	}

	@Test
	public void testApiDocsContainsExpectedEndpoints() {
		String url = "http://localhost:" + port + "/v3/api-docs";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String body = response.getBody();
		assertTrue(body.contains("/api/restaurantes"));
		assertTrue(body.contains("/api/produtos"));
		assertTrue(body.contains("/api/pedidos"));
		assertTrue(body.contains("/api/auth"));
	}
}