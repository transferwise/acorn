package com.transferwise.acorn.models;

import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder
public class TransferPayload implements Serializable {
    final int sourceAccount;
    final int targetAccount;
    final String quoteUuid;
    final UUID customerTransactionId;
    final TransferDetails details;
}
