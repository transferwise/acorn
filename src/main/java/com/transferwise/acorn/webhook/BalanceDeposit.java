package com.transferwise.acorn.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.transferwise.acorn.models.Resource;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class BalanceDeposit {
    private Resource resource;
    @JsonProperty("transaction_type")
    private String transactionType;
    private BigDecimal amount;
    private String currency;
    @JsonProperty("post_transaction_balance_amount")
    private BigDecimal postTransactionBalanceAmount;
    @JsonProperty("occurred_at")
    private Instant occurredAt;
}