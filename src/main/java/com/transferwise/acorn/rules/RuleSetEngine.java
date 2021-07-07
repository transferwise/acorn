package com.transferwise.acorn.rules;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RuleSetEngine {
	private final RuleSetRepository.RuleSet ruleSet;

	public RuleDecision applyRules(String currency, BigDecimal amount) {
		Optional<Rule> currencyRule = Optional.ofNullable(ruleSet.getRuleset().get(currency));

		BigDecimal minAmount = currencyRule.map(Rule::getMinDepositAmount)
				.orElse(ruleSet.getRuleset().get("default").getMinDepositAmount());
		BigDecimal maxAmount = currencyRule.map(Rule::getMaxDepositAmount)
				.orElse(ruleSet.getRuleset().get("default").getMaxDepositAmount());
		BigDecimal percentage = currencyRule.map(Rule::getPercentage)
				.orElse(ruleSet.getRuleset().get("default").getPercentage());

		if (amount.compareTo(minAmount) >= 0 && amount.compareTo(maxAmount) <= 0) {
			return RuleDecision.builder()
					.amountToForwardToJar(amount.multiply(percentage).divide(BigDecimal.valueOf(100), 3, RoundingMode.FLOOR))
					.passed(true)
					.build();
		}
		return RuleDecision.builder()
				.passed(false)
				.amountToForwardToJar(BigDecimal.ZERO)
				.build();
	}
}
