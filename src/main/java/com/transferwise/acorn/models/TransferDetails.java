package com.transferwise.acorn.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

@Builder
@JsonSerialize
public class TransferDetails {
    final String reference;
    final String transferPurpose;
    final String sourceOfFunds;
}
