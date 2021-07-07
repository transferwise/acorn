package com.transferwise.acorn.balance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyValue {
	private BigDecimal value;
	private String currency;
}