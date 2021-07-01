package com.transferwise.acorn.services;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BalancesResponse {
	List<BalanceValue> balances;
}
