package com.transferwise.acorn.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.ToString;

import java.util.UUID;

@Builder
@JsonSerialize
@ToString
public class TransferPayload {
    final int sourceAccount;
    final int targetAccount;
    final String quoteUuid;
    final UUID customerTransactionId;
    final TransferDetails details;
}
