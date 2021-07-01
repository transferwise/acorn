package com.transferwise.acorn.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class Rule {
	private BigDecimal minDepositAmount;
	private BigDecimal maxDepositAmount;
	private BigDecimal percentage;
}
