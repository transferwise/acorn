package com.transferwise.acorn.services;

import com.transferwise.acorn.balance.InvalidBalanceException;
import com.transferwise.acorn.models.BalanceCredit;
import com.transferwise.acorn.models.Icon;
import com.transferwise.acorn.models.OpenBalanceCommand;
import com.transferwise.acorn.models.RuleDecision;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private static final String JAR_TYPE = "SAVINGS";
    public static final String EMOJI = "EMOJI";
    public static final String SAVINGS_JAR_NAME_PREFIX = "SAVINGS ";
    public static final String STANDARD_JAR_TYPE = "STANDARD";
    private final RestTemplateBalanceAPI balanceAPI;
    private final RuleSetEngine ruleSetEngine;
    @Value("${wise.oauth-token}")
    private String token;

    @Async
    public void handleIncomingDepositWebhooksEvent(BalanceCredit balanceCredit) {
        RuleDecision ruleDecision = ruleSetEngine.applyRules(balanceCredit.getCurrency(), balanceCredit.getAmount());
        if (Boolean.FALSE.equals(ruleDecision.getPassed())) {
            return;
        }
        final Long profileId = balanceCredit.getResource().getProfileId();
        final var activeBalances = balanceAPI.findActiveBalances(token, profileId).orElseThrow(() -> new InvalidBalanceException("No Available Balances for your profile"));

        final String currency = balanceCredit.getCurrency();
        final Long sourceJarId = getSourceJarId(activeBalances, currency);
        final Long targetJarId = getTargetJarId(profileId, balanceCredit.getCurrency(), activeBalances);
        if (targetJarId == null) {
            return;
        }

        balanceAPI.makeBalanceToBalanceTransfer(
                token,
                ruleDecision.getAmountToForwardToJar().doubleValue(),
                currency,
                profileId,
                sourceJarId,
                targetJarId
        );
    }

    private Long getSourceJarId(List<BalanceValue> balances, String currency) {
        var predicates = getFilterPredicates(STANDARD_JAR_TYPE, currency);
        return balances.stream()
                .filter(predicates.stream().reduce(x->true, Predicate::and))
                .findFirst().map(it -> (Long.valueOf(it.id))).orElseThrow(() -> new InvalidBalanceException("No Regular Visible Balance Available for currency " + currency));
    }

    private Long getTargetJarId(Long profileId, String currency, List<BalanceValue> balances) {
        final var currentTargetJarId = balances.stream()
                .filter(openBalanceCommand -> openBalanceCommand.visible)
                .filter(openBalanceCommand -> openBalanceCommand.currency.equals(currency))
                .filter(openBalanceCommand -> JAR_TYPE.equals(openBalanceCommand.type))
                .filter(openBalanceCommand -> openBalanceCommand.name.startsWith(SAVINGS_JAR_NAME_PREFIX))
                .map(openBalanceCommand -> openBalanceCommand.id)
                .findFirst();
        if (currentTargetJarId.isPresent()) {
            return Long.valueOf(currentTargetJarId.get());
        }
        final var newName = "SAVINGS " + currency;
        final var command = OpenBalanceCommand.builder()
                .currency(currency)
                .type(JAR_TYPE)
                .name(newName)
                .icon(new Icon(EMOJI, "\uD83C\uDF4D"))
                .build();

        final var newBalance = balanceAPI.createBalanceJar(profileId, token, command);
        return newBalance.map(balanceValue -> Long.valueOf(balanceValue.id)).orElseThrow(() -> new InvalidBalanceException("New JAR could not be created"));
    }

    private List<Predicate<BalanceValue>> getFilterPredicates(String type, String currency) {
        Predicate<BalanceValue> visibilityPredicate =  balance -> balance.visible;
        Predicate<BalanceValue> currencyPredicate =  balance -> type.equals(balance.getType());
        Predicate<BalanceValue> typePredicate =   balance -> type.equals(balance.getType());
        return Arrays.asList(visibilityPredicate, currencyPredicate, typePredicate);
    }

    private Stream<BalanceValue> filterVisibleBalancesByCurrencyAndType1(List<BalanceValue> balances, String type, String currency) {
        return balances.stream()
                .filter(balance -> balance.visible)
                .filter(balance -> type.equals(balance.getType()))
                .filter(balance -> currency.equals(balance.getCurrency()));
    }
}