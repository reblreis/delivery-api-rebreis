package com.deliverytech.delivery.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class PaymentRequest {

	@NotNull(message = "O número do cartão não pode ser nulo")
	@Size(min = 13, max = 19, message = "O número do cartão deve ter entre 13 e 19 dígitos")
	@Pattern(regexp = "\\d+", message = "O número do cartão deve conter apenas dígitos")
	private String cardNumber;

	@NotNull(message = "O valor não pode ser nulo")
	@Positive(message = "O valor deve ser positivo")
	private Double amount;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
