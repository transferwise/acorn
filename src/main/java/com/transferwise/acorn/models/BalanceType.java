package com.transferwise.acorn.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public enum BalanceType {
	STANDARD, SAVINGS
}
