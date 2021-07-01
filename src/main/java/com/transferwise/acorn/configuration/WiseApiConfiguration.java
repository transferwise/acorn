package com.transferwise.acorn.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WiseApiConfiguration {

	@Value("${wise.api-url}")
	private String apiUrl;

	@Value(("${wise.oauth-token}"))
	private String oauthToken;

	@Bean
	public WebClient wiseWebClient() {
		return WebClient.builder()
				.baseUrl(apiUrl)
				.filter((ClientRequest request, ExchangeFunction next) -> {
						request.headers()
								.add(HttpHeaders.AUTHORIZATION, "Bearer: " + oauthToken);
						return next.exchange(request);
					}
				)
				.build();
	}
}
