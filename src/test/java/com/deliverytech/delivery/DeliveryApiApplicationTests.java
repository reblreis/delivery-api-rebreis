package com.deliverytech.delivery;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DeliveryApiApplicationTests {

	@Test
	void somaSimples() {
		int resultado = 2 + 3;
		org.junit.jupiter.api.Assertions.assertEquals(5, resultado);
	}

}
