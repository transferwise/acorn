package com.transferwise.acorn.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.transferwise.acorn.balance.dto.Resource;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class BalanceDeposit {
    private Resource resource;
    private BigDecimal amount;
    private String currency;
    @JsonProperty("occurred_at")
    private Instant occurredAt;
}