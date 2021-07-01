package com.transferwise.acorn.services;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public enum BalanceType {
	STANDARD, SAVINGS
}
