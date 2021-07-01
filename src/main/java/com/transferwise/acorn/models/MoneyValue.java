package com.transferwise.acorn.models;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@JsonSerialize
@Data
@AllArgsConstructor
public class MoneyValue {
	private final BigDecimal value;
	private final String currency;
}