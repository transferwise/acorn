package com.transferwise.acorn.rules;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Rule {
	private final BigDecimal minDepositAmount;
	private final BigDecimal maxDepositAmount;
	private final BigDecimal percentage;
}
