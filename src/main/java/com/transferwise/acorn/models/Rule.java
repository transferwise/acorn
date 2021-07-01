package com.transferwise.acorn.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Rule {
	private BigDecimal minDepositAmount;
	private BigDecimal maxDepositAmount;
	private BigDecimal percentage;
}
