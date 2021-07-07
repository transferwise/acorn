package com.transferwise.acorn.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BalanceDepositEvent {
	private BalanceDeposit data;
	@JsonProperty("schema_version")
	private String schemaVersion;
}
