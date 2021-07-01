package com.transferwise.acorn.configuration;

import com.transferwise.acorn.models.Rule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class RuleSetRepository {

	@Value("${wise.ruleset}")
	private Map<String, Rule> rules;

	@Value("${wise.ruleset.rules}")
	private Rule defaultRules;

	public Optional<Rule> getRuleForCurrency(String currency) {
		return Optional.ofNullable(rules.get(currency));
	}

	public Rule getDefaultRuleForCurrency() {
		return defaultRules;
	}
}
