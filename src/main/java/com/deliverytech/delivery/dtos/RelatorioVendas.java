package com.deliverytech.delivery.dtos;

import java.math.BigDecimal;

public interface RelatorioVendas {

	String getNomeRestaurante();

	BigDecimal getTotalVendas();

	Long getQuantidePedidos();

}