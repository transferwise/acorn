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
        final Long sourceJarId = getSourceJarId(activeBalances, currency);
        final Long targetJarId = getTargetJarId(profileId,balanceCredit.getCurrency(), activeBalances.get());
        if (targetJarId == null){
            return;
        }

        balanceAPI.makeBalanceToBalanceTransfer(
                token,
                toBeSavedAmount,
                currency,
                profileId,
                sourceJarId,
                targetJarId
        );
    }

    private Long getSourceJarId(java.util.Optional<List<BalanceValue>> activeBalances, String currency) {
        return activeBalances.get().stream().filter(it -> "STANDARD".equals(it.getType()))
                .filter(it -> currency.equals(it.getCurrency()))
                .filter(it -> it.visible).findFirst().map(it -> (Long.valueOf(it.id))).get();
    }

    private Long getTargetJarId(Long profileId, String currency, List<BalanceValue> balances) {
        final var currentTargetJarId = balances.stream()
                .filter(openBalanceCommand -> openBalanceCommand.visible)
                .filter(openBalanceCommand -> openBalanceCommand.currency.equals(currency))
                .filter(openBalanceCommand -> JAR_TYPE.equals(openBalanceCommand.type))
                .filter(openBalanceCommand -> openBalanceCommand.name.startsWith("SAVINGS "))
                .map(openBalanceCommand -> openBalanceCommand.id)
                .findFirst();
        if (command.isPresent()) {
            return Optional.of((long) command.get().id);
        }
        final var newName = "SAVINGS "+currency;
        final var command = OpenBalanceCommand.builder()
                .currency(currency)
                .type(JAR_TYPE)
                .name(newName)
                .icon(new Icon("EMOJI","\uD83C\uDF4D"))
                .build();

        final var newBalance = balanceAPI.createBalanceJar(profileId,token,command);
        if (newBalance.isPresent()){
            return Long.valueOf(newBalance.get().id);
        }
        return null;
    }
}