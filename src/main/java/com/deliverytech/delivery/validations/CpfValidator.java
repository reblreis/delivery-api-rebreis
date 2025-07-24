package com.deliverytech.delivery.validations;

public class CpfValidator {
	public static boolean isValid(String cpf) {
		
		//Validação simples: apenas verifica se tem 11 dígitos numéricos
		return cpf != null && cpf.matches("\\d{11}");
	}

}
