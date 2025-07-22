package com.deliverytech.delivery.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deliverytech.delivery.dtos.RelatorioVendas;
import com.deliverytech.delivery.entities.Pedido;

public interface RelatorioRepository extends JpaRepository<Pedido, Long> {

	@Query("""
			    SELECT r.nome AS nomeRestaurante,
			           SUM(p.valorTotal) AS totalVendas,
			           COUNT(p) AS quantidePedidos
			    FROM Pedido p
			    JOIN p.restaurante r
			    WHERE p.dataPedido BETWEEN :dataInicio AND :dataFim
			    GROUP BY r.nome
			""")
	List<RelatorioVendas> buscarRelatorio(@Param("dataInicio") LocalDate dataInicio,
			@Param("dataFim") LocalDate dataFim);
}