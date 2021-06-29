package com.transferwise.acorn.models;

import lombok.Getter;

import java.util.Date;
import java.util.List;


@Getter
public class Quote {
    String id;
    String sourceCurrency;
    String targetCurrency;
    int sourceAmount;
    String payOut;
    String preferredPayIn;
    double rate;
    Date createdTime;
    int user;
    int profile;
    String rateType;
    Date rateExpirationTime;
    boolean guaranteedTargetAmountAllowed;
    boolean targetAmountAllowed;
    boolean guaranteedTargetAmount;
    String providedAmountType;
    List<PaymentOption> paymentOptions;
    String status;
    Date expirationTime;
    List<Notice> notices;
}