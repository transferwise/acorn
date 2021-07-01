package com.transferwise.acorn.services;

import com.transferwise.acorn.models.BalanceCredit;
import com.transferwise.acorn.models.Icon;
import com.transferwise.acorn.models.OpenBalanceCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private static final String JAR_TYPE = "SAVINGS";
    private final RestTemplateBalanceAPI balanceAPI;
    private final SavingsService savingsService;
    @Value("${wise.oauth-token}")
    private String token;

    @Async
    public void handleIncomingDepositWebhooksEvent(BalanceCredit balanceCredit) {
        final double toBeSavedAmount = savingsService.savingsAmount(balanceCredit);
        if (toBeSavedAmount == 0) {
            return;
        }
        final Long profileId = balanceCredit.getResource().getProfileId();
        final var activeBalances = balanceAPI.findActiveBalances(token, profileId);

        if (activeBalances.isEmpty()) {
            return;
        }

        final String currency = balanceCredit.getCurrency();
        final Long sourceJarId = balanceCredit.getResource().getId();
        final Optional<Long> targetJarId = getTargetJarId(profileId, balanceCredit.getCurrency(), activeBalances.get());
        targetJarId.ifPresent
                (aLong -> balanceAPI.makeBalanceToBalanceTransfer
                        (token, toBeSavedAmount, currency, profileId, sourceJarId, aLong));
    }

    private Optional<Long> getTargetJarId(Long profileId, String currency, List<BalanceValue> balances) {
        var command = balances.stream()
                .filter(balanceValue -> balanceValue.visible)
                .filter(balanceValue -> balanceValue.currency.equals(currency))
                .filter(balanceValue -> JAR_TYPE.equals(balanceValue.type))
                .filter(balanceValue -> balanceValue.name.startsWith("SAVINGS "))
                .findFirst();
        if (command.isPresent()) {
            return Optional.of((long) command.get().id);
        }

        String newBalanceName = "SAVINGS " + currency;

        final var newCommand = OpenBalanceCommand.builder()
                .currency(currency)
                .type(JAR_TYPE)
                .name(newBalanceName)
                .icon(new Icon("\uD83C\uDF4D"))
                .build();

        final var newJar = balanceAPI.createBalanceJar(profileId, token, newCommand);
        return newJar.map(balanceValue -> (long) balanceValue.id);
    }
}