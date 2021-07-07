package com.transferwise.acorn.balance.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BalanceValue {
	private Long id;
	private String currency;
	private String type;
	private String name;
	private boolean visible;
}
