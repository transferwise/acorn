package com.transferwise.acorn.webhook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transferwise.acorn.services.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BalanceDepositController {

    private static final String SIGNATURE_HEADER = "X-Signature-SHA256";
    private static final String SUPPORTED_SCHEMA_VERSION = "2.0.0";

    private final BalanceService transferService;
    private final VerifyWebhookRequest verifyWebhookRequest;
    private final ObjectMapper objectMapper;

    @PostMapping("/balance-deposits")
    public ResponseEntity transfer(@RequestBody String payload, @RequestHeader HttpHeaders httpHeaders) {
        verifyWebhookRequest.payload(payload, httpHeaders.getFirst(SIGNATURE_HEADER));
        return readEvent(payload)
                .map(event -> {
                    transferService.handleIncomingDepositWebhooksEvent(event.getData());
                    return ResponseEntity.status(HttpStatus.OK).build();
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    private Optional<BalanceDepositEvent> readEvent(String payload) {
        BalanceDepositEvent event;
        try {
            event = objectMapper.readValue(payload, BalanceDepositEvent.class);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
        if (event.getSchemaVersion().equals(SUPPORTED_SCHEMA_VERSION)) {
            return Optional.of(event);
        }
        return Optional.empty();
    }
}