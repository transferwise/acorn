package com.transferwise.acorn.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;


@Builder
@JsonSerialize
public class QuotePayload {
    final int profileId;
    final String sourceCurrency;
    final String targetCurrency;
    final double sourceAmount;
}
