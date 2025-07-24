package com.deliverytech.delivery.validations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CpfValidatorTest {

	@Test
	@DisplayName("Deve validar CPF corretamente")
	void should_ValidateCpf_When_ValidFormat() {
		assertTrue(CpfValidator.isValid("12345678901"));
		assertFalse(CpfValidator.isValid("123"));
		assertFalse(CpfValidator.isValid("abc"));
	}

}
