package com.transferwise.acorn.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BalanceTransferPayload {
    private final MoneyValue amount;
    private final Long sourceBalanceId;
    private final Long targetBalanceId;
}