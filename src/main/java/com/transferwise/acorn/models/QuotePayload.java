package com.transferwise.acorn.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;


@Builder
@JsonSerialize
public class QuotePayload {
    final String sourceCurrency;
    final String targetCurrency;
    final int sourceAmount;
    final int targetAmount;
    final int profileId;
    final String payout;
    final String preferredPayIn;
}
