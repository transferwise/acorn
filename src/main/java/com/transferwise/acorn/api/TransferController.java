package com.transferwise.acorn.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.transferwise.acorn.models.BalanceResponse;
import com.transferwise.acorn.services.BalanceService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/acorn")
public class TransferController {

    private final BalanceService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<Optional<BalanceResponse>> transfer(@RequestBody Payload payload) throws JsonProcessingException {
        return ResponseEntity.ok(transferService.makeBalanceToBalanceTransfer(payload.getValue(), payload.getCurrency()));
    }

    @Getter
    private static class Payload {
        double value;
        String currency;
    }
}