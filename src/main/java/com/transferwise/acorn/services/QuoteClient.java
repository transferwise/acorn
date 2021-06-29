package com.transferwise.acorn.services;

import com.transferwise.acorn.models.Quote;
import com.transferwise.acorn.models.QuotePayload;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class QuoteClient {
    private static final String BASE_URL = "https://api.sandbox.transferwise.tech";
    private static final String QUOTE_URL = BASE_URL + "/v2/quotes";


    public Optional<Quote> getQuote(QuotePayload quote, String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);


        var entity = new HttpEntity<>(quote, headers);


        ResponseEntity<Quote> responseEntity = restTemplate.
                postForEntity(QUOTE_URL, entity, Quote.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return Optional.of(responseEntity.getBody());
        }

        return Optional.empty();
    }
}
