package com.transferwise.acorn.balance.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BalanceTransferPayload {
    private final MoneyValue amount;
    private final Long sourceBalanceId;
    private final Long targetBalanceId;
}