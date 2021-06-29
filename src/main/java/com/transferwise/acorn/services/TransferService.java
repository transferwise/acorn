package com.transferwise.acorn.services;

import com.transferwise.acorn.models.AcornPayload;
import com.transferwise.acorn.models.Transfer;
import com.transferwise.acorn.models.TransferDetails;
import com.transferwise.acorn.models.TransferPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransferClient wiseClient;
    private final QuoteService quoteService;


    public Optional<Transfer> makeTransferToJar(AcornPayload payload) {
        final var quote = quoteService.getQuote(payload.getSourceCurrency(),
                payload.getTargetCurrency(),
                payload.getSourceAmount(),
                payload.getSourceAccount(),
                payload.getToken());
        if (quote.isEmpty()) {
            return Optional.empty();
        }
        final var quoteUUID = quote.get().getId();

        final var transferDetails = TransferDetails.builder()
                .reference("mission days")
                .transferPurpose("2021")
                .sourceOfFunds("wise")
                .build();

        final var customerTransactionId = UUID.randomUUID();

        final var wiseTransfer = TransferPayload.builder()
                .customerTransactionId(customerTransactionId)
                .quoteUuid(quoteUUID)
                .details(transferDetails)
                .sourceAccount(payload.getSourceAccount())
                .targetAccount(payload.getTargetAccount())
                .build();

        return wiseClient.makeTransfer(wiseTransfer, payload.getToken());
    }
}
