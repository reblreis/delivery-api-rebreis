package com.deliverytech.delivery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.deliverytech.delivery.dtos.request.PaymentRequest;
import com.deliverytech.delivery.dtos.response.PaymentResponse;

@SpringBootTest
public class PaymentGatewayServiceTest {

	@Autowired
	private PaymentGatewayService paymentGatewayService;

	@Autowired
	private RestTemplate restTemplate;

	private MockRestServiceServer mockServer;

	@BeforeEach
	void setup() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	void testProcessPaymentSuccess() {
		// Arrange
		PaymentRequest request = new PaymentRequest();
		request.setAmount(100.0);
		request.setCardNumber("1234567890123456");

		String fakeResponse = """
				{
				  "status": "SUCCESS",
				  "transactionId": "abc123"
				}
				""";

		mockServer.expect(requestTo("https://sandbox.api.payment.com/pay")).andExpect(method(HttpMethod.POST))
				.andRespond(withSuccess(fakeResponse, MediaType.APPLICATION_JSON));

		// Act
		PaymentResponse response = paymentGatewayService.processPayment(request);

		// Assert
		assertEquals("SUCCESS", response.getStatus());
		assertEquals("abc123", response.getTransactionId());
	}
}
