package com.transferwise.acorn.models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class BalanceCredit {
	private Resource resource;
	private String transactionType;
	private BigDecimal amount;
	private String currency;
	private BigDecimal postTransactionBalanceAmount;
	private Instant occurredAt;
}
