package com.transferwise.acorn.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

import java.io.Serializable;

@Builder
@JsonSerialize
public class QuotePayload implements Serializable {
    final String sourceCurrency;
    final String targetCurrency;
    final int sourceAmount;
    final int targetAmount;
    final int profileId;
    final String payout;
    final String preferredPayIn;
}
