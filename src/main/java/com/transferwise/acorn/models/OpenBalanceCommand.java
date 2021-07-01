package com.transferwise.acorn.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OpenBalanceCommand {
	private String currency;
	private String type;
	private String name;
	private Icon icon;
}
