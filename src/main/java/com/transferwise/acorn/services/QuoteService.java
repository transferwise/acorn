package com.transferwise.acorn.services;

import com.transferwise.acorn.models.Quote;
import com.transferwise.acorn.models.QuotePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuoteService {
    private final QuoteClient quoteClient;

    public Optional<Quote> getQuote(String sourceCurrency,
                                    String targetCurrency,
                                    int sourceAmount,
                                    int targetAmount,
                                    int profileId,
                                    String token) {

        final var quote = QuotePayload.builder()
                .sourceCurrency(sourceCurrency)
                .targetCurrency(targetCurrency)
                .sourceAmount(sourceAmount)
                .targetAmount(targetAmount)
                .profileId(profileId)
                .payout("BANK_TRANSFER")
                .preferredPayIn("BANK_TRANSFER")
                .build();
        return quoteClient.getQuote(quote, token);
    }
}
