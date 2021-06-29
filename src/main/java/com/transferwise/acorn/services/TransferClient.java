package com.transferwise.acorn.services;

import com.transferwise.acorn.models.Transfer;
import com.transferwise.acorn.models.TransferPayload;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class TransferClient {
    private static final String BASE_URL = "https://api.sandbox.transferwise.tech";
    private static final String TRANSFER_URL = BASE_URL + "/v1/transfer";

    public Optional<Transfer> makeTransfer(TransferPayload transfer, String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        var entity = new HttpEntity<>(transfer, headers);

        ResponseEntity<Transfer> responseEntity = restTemplate.
                postForEntity(TRANSFER_URL, entity, Transfer.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return Optional.of(responseEntity.getBody());
        }
        return Optional.empty();
    }
}
