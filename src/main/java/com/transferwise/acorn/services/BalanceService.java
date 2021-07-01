package com.transferwise.acorn.services;

import com.transferwise.acorn.models.BalanceTransferPayload;
import com.transferwise.acorn.models.Icon;
import com.transferwise.acorn.models.MoneyValue;
import com.transferwise.acorn.models.OpenBalanceCommand;
import com.transferwise.acorn.models.RuleDecision;
import com.transferwise.acorn.webhook.BalanceDeposit;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private static final String JAR_TYPE = "SAVINGS";
    private final BalanceAPI balanceAPI;
    private final RuleSetEngine ruleSetEngine;

    @Async
    public void handleIncomingDepositWebhooksEvent(BalanceDeposit balanceDeposit) {
        RuleDecision ruleDecision = ruleSetEngine.applyRules(balanceDeposit.getCurrency(), balanceDeposit.getAmount());
        if (Boolean.FALSE.equals(ruleDecision.getPassed())) {
            return;
        }
        Long profileId = balanceDeposit.getResource().getProfileId();
        List<BalanceValue> activeBalances = balanceAPI.findActiveBalances(profileId);


        if (activeBalances.isEmpty()) {
            return;
        }

        String currency = balanceDeposit.getCurrency();
        Long sourceJarId = getSourceJarId(activeBalances, currency);
        Long targetJarId = getTargetJarId(profileId, balanceDeposit.getCurrency(), activeBalances);
        if (targetJarId == null) {
            return;
        }

        BalanceTransferPayload balanceTransferPayload = BalanceTransferPayload.builder()
                .amount(new MoneyValue(ruleDecision.getAmountToForwardToJar(), currency))
                .sourceBalanceId(sourceJarId)
                .targetBalanceId(targetJarId)
                .build();

        balanceAPI.makeBalanceToBalanceTransfer(profileId, balanceTransferPayload);
    }

    private Long getSourceJarId(List<BalanceValue> activeBalances, String currency) {
        return activeBalances.stream()
                .filter(it -> "STANDARD".equals(it.getType()))
                .filter(it -> currency.equals(it.getCurrency()))
                .filter(it -> it.visible)
                .findFirst()
                .map(it -> (it.id))
                .orElse(null);
    }

    private Long getTargetJarId(Long profileId, String currency, List<BalanceValue> balances) {
        String savingsJarName = "Savings " + currency;

        Optional<Long> currentTargetJarId = balances.stream()
                .filter(openBalanceCommand -> JAR_TYPE.equals(openBalanceCommand.type))
                .filter(openBalanceCommand -> openBalanceCommand.name.equals(savingsJarName))
                .filter(openBalanceCommand -> openBalanceCommand.visible)
                .filter(openBalanceCommand -> openBalanceCommand.currency.equals(currency))
                .map(openBalanceCommand -> openBalanceCommand.id)
                .findFirst();
        if (currentTargetJarId.isPresent()) {
            return currentTargetJarId.get();
        }

        OpenBalanceCommand command = OpenBalanceCommand.builder()
                .currency(currency)
                .type(JAR_TYPE)
                .name(savingsJarName)
                .icon(new Icon("EMOJI", "\uD83C\uDF4D"))
                .build();

        BalanceValue newBalance = balanceAPI.createBalanceJar(profileId, command);
        return newBalance.getId();
    }
}