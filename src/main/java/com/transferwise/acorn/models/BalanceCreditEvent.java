package com.transferwise.acorn.models;

import lombok.Data;

import java.time.Instant;

@Data
public class BalanceCreditEvent {
	private BalanceCredit data;
	private String subscriptionId;
	private String eventType;
	private String schemaVersion;
	private Instant sentAt;
}
