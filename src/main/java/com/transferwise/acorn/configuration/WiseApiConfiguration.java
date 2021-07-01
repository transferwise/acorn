package com.transferwise.acorn.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class WiseApiConfiguration {

	@Value("${wise.api-url}")
	private String apiUrl;

	@Value("${wise.oauth-token}")
	private String oauthToken;

	@Value("${wise.public-key}")
	private String publicKey;

	@Bean
	public PublicKey wisePublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getMimeDecoder().decode(publicKey));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(publicKeySpec);
	}

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
