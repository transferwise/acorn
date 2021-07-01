package com.transferwise.acorn.services;

import com.transferwise.acorn.models.BalanceCredit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private static final String JAR_TYPE = "SAVINGS";
    private final BalanceAPI balanceAPI;
    private final SavingsService savingsService;
    @Value("${api.token}")
    private String token;

    @Async
    public void handleIncomingDepositWebhooksEvent(BalanceCredit balanceCredit) {
        final double toBeSavedAmount = savingsService.savingsAmount(balanceCredit);
        if (toBeSavedAmount == 0) {
            return;
        }
        final Long profileId = balanceCredit.getResource().getProfileId();

        final var activeBalances = balanceAPI.findActiveBalances(token,profileId);

        final String currency = balanceCredit.getCurrency();
        final Long sourceJarId = balanceCredit.getResource().getId();
        final Long targetJarId = getTargetJarId(balanceCredit.getCurrency(), activeBalances.get());


        balanceAPI.makeBalanceToBalanceTransfer(
                token,
                toBeSavedAmount,
                currency,
                profileId,
                sourceJarId,
                targetJarId
        );
    }

    private Long getTargetJarId(String currency, List<BalanceValue> balances) {
        final var currentTargetJarId = balances.stream()
                .filter(openBalanceCommand -> openBalanceCommand.visible)
                .filter(openBalanceCommand -> openBalanceCommand.currency.equals(currency))
                .filter(openBalanceCommand -> JAR_TYPE.equals(openBalanceCommand.type))
                .filter(openBalanceCommand -> openBalanceCommand.name.startsWith("SAVINGS "))
                .map(openBalanceCommand -> openBalanceCommand.id)
                .findFirst();
        if (currentTargetJarId.isPresent()) {
            return Long.valueOf(currentTargetJarId.get());
        }
        // TODO make new jar, return its ID

        return 75555L;
    }
}
