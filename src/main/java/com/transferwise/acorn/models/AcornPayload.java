package com.transferwise.acorn.models;

import lombok.Getter;

@Getter
public class AcornPayload {
    int sourceAccount;
    int targetAccount;
    String sourceCurrency;
    String targetCurrency;
    int sourceAmount;
    int targetAmount;
    String token;
}
