package com.deliverytech.delivery.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dtos.RelatorioVendas;
import com.deliverytech.delivery.dtos.response.DashboardResponse;
import com.deliverytech.delivery.dtos.response.RelatorioVendasResponse;
import com.deliverytech.delivery.repositories.RelatorioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController
@RequestMapping("/api/admin")
@Tags({ @Tag(name = "Administração", description = "Endpoints exclusivos para administradores"),
		@Tag(name = "Relatórios", description = "Geração de relatórios e estatísticas") })
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	private final RelatorioRepository relatorioRepository;

	public AdminController(RelatorioRepository relatorioRepository) {
		this.relatorioRepository = relatorioRepository;
	}

	@GetMapping("/dashboard")
	@Operation(summary = "Dashboard administrativo", description = "Retorna estatísticas gerais do sistema para o dashboard", tags = {
			"Administração", "Relatórios" })
	public ResponseEntity<DashboardResponse> getDashboard() {
		DashboardResponse response = new DashboardResponse(150L, new BigDecimal("12345.67"), 50L, 10L,
				new BigDecimal("82.30"), 120L, 30L);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/relatorio/vendas")
	@Operation(summary = "Relatório de vendas", description = "Gera relatório detalhado de vendas por período", tags = {
			"Relatórios" })
	public ResponseEntity<RelatorioVendasResponse> getRelatorioVendas(
			@Parameter(description = "Data inicial") @RequestParam LocalDate dataInicio,
			@Parameter(description = "Data final") @RequestParam LocalDate dataFim) {

		List<RelatorioVendas> dados = relatorioRepository.buscarRelatorio(dataInicio, dataFim);

		List<RelatorioVendasResponse.RelatorioVendasDTO> relatorios = dados.stream()
				.map(r -> new RelatorioVendasResponse.RelatorioVendasDTO(r.getNomeRestaurante(), r.getTotalVendas(),
						r.getQuantidePedidos()))
				.toList();

		return ResponseEntity.ok(new RelatorioVendasResponse(relatorios));
	}
}