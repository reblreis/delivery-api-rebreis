package com.deliverytech.delivery.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.deliverytech.delivery.dtos.request.PaymentRequest;
import com.deliverytech.delivery.dtos.response.PaymentResponse;

//Consumo de API externa com RestTemplate
@Service
public class PaymentGatewayService {

	private final RestTemplate restTemplate;

	public PaymentGatewayService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public PaymentResponse processPayment(PaymentRequest request) {
		try {
			return restTemplate.postForObject("https://sandbox.api.payment.com/pay", request, PaymentResponse.class);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			// Erros 4xx e 5xx
			throw new RuntimeException(
					"Erro na resposta do gateway: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
		} catch (ResourceAccessException e) {
			// Timeout ou erro de conexão
			throw new RuntimeException("Timeout ou falha de rede ao chamar o gateway de pagamento", e);
		}
	}
}
