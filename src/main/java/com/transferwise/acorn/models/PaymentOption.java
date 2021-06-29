package com.transferwise.acorn.models;

import java.util.Date;
import java.util.List;

public class PaymentOption {
    String sourceCurrency;
    String targetCurrency;
    String payIn;
    String payOut;
    List<String> allowedProfileTypes;
    String payInProduct;
    double feePercentage;
    DisabledReason disabledReason;
    boolean disabled;
    Date estimatedDelivery;
    String formattedEstimatedDelivery;
    List<Object> estimatedDeliveryDelays;
    Fee fee;
    int sourceAmount;
    double targetAmount;
}
