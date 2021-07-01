package com.transferwise.acorn.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class BalanceCredit {
    @JsonProperty("resource")
    private Resource resource;
    @JsonProperty("transaction_type")
    private String transactionType;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("post_transaction_balance_amount")
    private BigDecimal postTransactionBalanceAmount;
    @JsonProperty("occurred_at")
    private Instant occurredAt;
}