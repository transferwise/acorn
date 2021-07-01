package com.transferwise.acorn.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class BalanceCreditEvent {
	@JsonProperty("data")
	private BalanceCredit data;
	@JsonProperty("subscription_id")
	private String subscriptionId;
	@JsonProperty("event_type")
	private String eventType;
	@JsonProperty("schema_version")
	private String schemaVersion;
	@JsonProperty("sent_at")
	private Instant sentAt;
}
