package com.transferwise.acorn.models;

import lombok.Getter;

import java.util.List;

@Getter
public class BalanceTransferPayload {
    String apiToken;
    int profileId;
    int sourceBalanceId;
    String currency;
    double incomingDepositAmount;
    List<Double> payments;
    List<Integer> targetBalanceIds;
}