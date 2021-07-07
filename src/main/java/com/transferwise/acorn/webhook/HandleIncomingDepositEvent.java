package com.transferwise.acorn.webhook;

import com.transferwise.acorn.balance.BalanceAPI;
import com.transferwise.acorn.balance.InvalidBalanceException;
import com.transferwise.acorn.balance.dto.*;
import com.transferwise.acorn.rules.RuleDecision;
import com.transferwise.acorn.rules.RuleSetEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@Service
@Slf4j
@RequiredArgsConstructor
public class HandleIncomingDepositEvent {

    public static final String EMOJI_PINEAPPLE = "\uD83C\uDF4D";
    public static final String EMOJI = "EMOJI";
    public static final String SAVINGS_JAR_NAME_PREFIX = "Savings ";
    private final BalanceAPI balanceAPI;
    private final RuleSetEngine ruleSetEngine;

    @Async
    public void execute(BalanceDeposit balanceDeposit) {
        RuleDecision ruleDecision = ruleSetEngine.applyRules(balanceDeposit.getCurrency(), balanceDeposit.getAmount());
        if (!ruleDecision.isPassed()) {
            log.info("Deposit amount less than set in rules. No Balance Transfer Required");
            return;
        }
        Long profileId = balanceDeposit.getResource().getProfileId();
        List<BalanceValue> activeBalances = balanceAPI.findActiveBalances(profileId);


        if (activeBalances.isEmpty()) {
            throw new InvalidBalanceException("No available balances found");
        }

        String currency = balanceDeposit.getCurrency();
        Long sourceJarId = getSourceJarId(activeBalances, currency);
        Long targetJarId = getTargetJarId(profileId, balanceDeposit.getCurrency(), activeBalances, balanceDeposit.getOccurredAt());

        BalanceTransferPayload balanceTransferPayload = BalanceTransferPayload.builder()
                .amount(new MoneyValue(ruleDecision.getAmountToForwardToJar(), currency))
                .sourceBalanceId(sourceJarId)
                .targetBalanceId(targetJarId)
                .build();

        balanceAPI.makeBalanceToBalanceTransfer(profileId, balanceTransferPayload);
    }

    private Long getSourceJarId(List<BalanceValue> balances, String currency) {
        var predicates = getFilterPredicates(BalanceType.STANDARD, currency);
        return balances.stream()
                .filter(predicates.stream().reduce(x->true, Predicate::and))
                .findFirst().map(BalanceValue::getId).orElseThrow(() -> new InvalidBalanceException("No Regular Visible Balance Available for currency " + currency));
    }

    private Long getTargetJarId(Long profileId, String currency, List<BalanceValue> balances, Instant time) {
        var predicates = getFilterPredicates(BalanceType.SAVINGS, currency);
        Optional<Long> currentTargetJarId = balances.stream()
                .filter(predicates.stream().reduce(x->true, Predicate::and))
                .filter(balance -> balance.getName().startsWith(SAVINGS_JAR_NAME_PREFIX))
                .map(BalanceValue::getId)
                .findFirst();
        if (currentTargetJarId.isPresent()) {
            return currentTargetJarId.get();
        }
        String newName = SAVINGS_JAR_NAME_PREFIX  + currency;
        OpenBalanceCommand command = OpenBalanceCommand.builder()
                .currency(currency)
                .type(BalanceType.SAVINGS.name())
                .name(newName)
                .icon(new Icon(EMOJI, EMOJI_PINEAPPLE))
                .build();
        return balanceAPI.createBalanceJar(profileId, command, generateIdempotenceUUID("OPEN_BALANCE", time)).getId();
    }

    private List<Predicate<BalanceValue>> getFilterPredicates(BalanceType type, String currency) {
        Predicate<BalanceValue> visibilityPredicate = BalanceValue::isVisible;
        Predicate<BalanceValue> currencyPredicate = balance -> type.name().equals(balance.getType());
        Predicate<BalanceValue> typePredicate = balance -> currency.equals(balance.getCurrency());
        return Arrays.asList(visibilityPredicate, currencyPredicate, typePredicate);
    }

    private UUID generateIdempotenceUUID(String action, Instant time) {
        String seed = action + "|" + time.getEpochSecond() + "|" + time.getNano();
        return UUID.nameUUIDFromBytes(seed.getBytes());
    }
}