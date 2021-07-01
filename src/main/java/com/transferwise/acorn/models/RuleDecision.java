package com.transferwise.acorn.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Data
public class RuleDecision {
	private Boolean passed;
	private BigDecimal amountToForwardToJar;
}
