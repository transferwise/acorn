package com.transferwise.acorn.services;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.transferwise.acorn.models.BalanceResponse;
import com.transferwise.acorn.models.OpenBalanceCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestTemplateBalanceAPI implements BalanceAPI {

	private static final String BASE_URL = "https://sandbox.transferwise.tech";

	@Override
	public Optional<BalanceResponse> makeBalanceToBalanceTransfer(String token,
	                                                              double value,
	                                                              String currency,
	                                                              Long profileId,
	                                                              Long sourceBalanceId,
	                                                              Long targetBalanceId) {
		final String BALANCE_TRANSFER_URL = BASE_URL + "/gateway/v2/profiles/" + profileId + "/balance-movements";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.set("X-idempotence-uuid", String.valueOf(UUID.randomUUID()));
		headers.setContentType(MediaType.APPLICATION_JSON);

		var payload = BalanceTransferPayload.builder()
				.amount(new MoneyValue(value, currency))
				.sourceBalanceId(sourceBalanceId)
				.targetBalanceId(targetBalanceId)
				.build();

		var entity = new HttpEntity<>(payload, headers);

		ResponseEntity<BalanceResponse> responseEntity = restTemplate.
				postForEntity(BALANCE_TRANSFER_URL, entity, BalanceResponse.class);

		if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
			return Optional.of(responseEntity.getBody());
		}
		return Optional.empty();
	}

	@Override
	public Optional<List<BalanceValue>> findActiveBalances(String token,
	                                                                                             Long profileId) {

		final String BALANCES_URL = BASE_URL + "/gateway/v4/profiles/" + profileId + "/balances?types=SAVINGS,STANDARD";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.set("X-idempotence-uuid", String.valueOf(UUID.randomUUID()));
		headers.setContentType(MediaType.APPLICATION_JSON);


		var entity = new HttpEntity<>(headers);

		ResponseEntity<List<BalanceValue>> responseEntity = restTemplate.exchange(BALANCES_URL, HttpMethod.GET, entity, new ParameterizedTypeReference<List<BalanceValue>>() {});

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			List<BalanceValue> body = responseEntity.getBody();
			return Optional.of(body);
		}
		return Optional.empty();
	}

	@Override
	public Optional<BalanceValue> createBalanceJar(OpenBalanceCommand openBalanceCommand) {
		return Optional.empty();
	}

	@JsonSerialize
	@Data
	@Builder
	private static class BalanceTransferPayload {
		private final MoneyValue amount;
		private final Long sourceBalanceId;
		private final Long targetBalanceId;
	}

	@JsonSerialize
	@Data
	@AllArgsConstructor
	private static class MoneyValue {
		private final double value;
		private final String currency;
	}
}
