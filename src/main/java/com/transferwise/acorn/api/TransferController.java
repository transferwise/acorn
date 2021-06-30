package com.transferwise.acorn.api;

import com.transferwise.acorn.models.BalanceResponse;
import com.transferwise.acorn.models.BalanceTransferPayload;
import com.transferwise.acorn.services.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/acorn")
public class TransferController {

    private final BalanceService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<List<BalanceResponse>> transfer(@RequestBody BalanceTransferPayload payload) {
        return ResponseEntity.ok(transferService.handleIncomingDepositEvent(payload));
    }
}
/*
{
    "apiToken": "979999c9-10cf-4da2-9f58-77a7edb57d03",
    "profileId": 25,
    "sourceBalanceId": 3324,
    "currency": "EUR",
    "incomingDepositAmount": 100,
    "payments": [
        1.1
    ],
    "targetBalanceIds": [
        75555
    ]
}
 */