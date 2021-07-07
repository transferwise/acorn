package com.transferwise.acorn.rules;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class RuleDecision {
	private boolean passed;
	private BigDecimal amountToForwardToJar;
}
