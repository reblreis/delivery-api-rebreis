package com.deliverytech.delivery.filter;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CorrelationIdFilter implements Filter {

	private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

	private static final String CORRELATION_ID_MDC_KEY = "correlationId";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		try {

			// Obter ou gerar correla􀆟on ID
			String correlationId = httpRequest.getHeader(CORRELATION_ID_HEADER);
			if (correlationId == null || correlationId.trim().isEmpty()) {
				correlationId = generateCorrelationId();
			}

			// Adicionar ao MDC para logs
			MDC.put(CORRELATION_ID_MDC_KEY, correlationId);

			// Adicionar informações extras ao MDC
			MDC.put("clientIp", getClientIpAddress(httpRequest));
			MDC.put("userAgent", httpRequest.getHeader("User-Agent"));
			MDC.put("sessionId", httpRequest.getSession().getId());
			MDC.put("requestUri", httpRequest.getRequestURI());
			MDC.put("httpMethod", httpRequest.getMethod());

			// Adicionar correlation ID ao header de resposta
			httpResponse.setHeader(CORRELATION_ID_HEADER, correlationId);

			// Continuar com a cadeia de filtros
			chain.doFilter(request, response);

		} finally {

			// Limpar MDC após processamento
			MDC.clear();
		}
	}

	private String generateCorrelationId() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
	}

	private String getClientIpAddress(HttpServletRequest request) {
		String xForwardedFor = request.getHeader("X-Forwarded-For");
		if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
			return xForwardedFor.split(",")[0].trim();
		}

		String xRealIp = request.getHeader("X-Real-IP");
		if (xRealIp != null && !xRealIp.isEmpty()) {
			return xRealIp;
		}

		return request.getRemoteAddr();
	}
}
