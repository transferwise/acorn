package com.transferwise.acorn.services;

import com.transferwise.acorn.models.BalanceResponse;
import com.transferwise.acorn.models.BalanceTransferPayload;
import com.transferwise.acorn.models.BalanceValue;
import com.transferwise.acorn.models.OpenBalanceCommand;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@AllArgsConstructor
@Service
public class BalanceApiWebClient implements BalanceAPI {
	private static final String BALANCE_TRANSFER_URL = "/v2/profiles/{profileId}/balance-movements";
	private static final String BALANCES_URL = "/v4/profiles/{profileId}/balances?types=SAVINGS,STANDARD";
	private static final String CREATE_JAR_URL = "/v4/profiles/{profileId}/balances";
	private final WebClient wiseWebClient;

	@Override
	public BalanceResponse makeBalanceToBalanceTransfer(Long profileId, BalanceTransferPayload balanceTransferPayload) {
		return wiseWebClient.post()
				.uri(BALANCE_TRANSFER_URL, profileId)
				.bodyValue(balanceTransferPayload)
				.retrieve()
				.bodyToMono(BalanceResponse.class)
				.block();
	}

	@Override
	public List<BalanceValue> findActiveBalances(Long profileId) {
		return wiseWebClient.get()
				.uri(BALANCES_URL, profileId)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<BalanceValue>>(){})
				.block();
	}

	@Override
	public BalanceValue createBalanceJar(Long profileId, OpenBalanceCommand openBalanceCommand) {
		return wiseWebClient.post()
				.uri(CREATE_JAR_URL, profileId)
				.bodyValue(openBalanceCommand)
				.retrieve()
				.bodyToMono(BalanceValue.class)
				.block();
	}
}
