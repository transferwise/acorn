package com.transferwise.acorn.models;

import lombok.Builder;

@Builder
public class TransferDetails {
    final String reference;
    final String transferPurpose;
    final String sourceOfFunds;
}
