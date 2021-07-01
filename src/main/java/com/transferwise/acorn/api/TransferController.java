package com.transferwise.acorn.api;

import com.transferwise.acorn.models.BalanceCreditEvent;
import com.transferwise.acorn.services.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1/acorn")
public class TransferController {

    private final BalanceService transferService;

    @PostMapping("/transfer")
    public ResponseEntity transfer(@RequestBody BalanceCreditEvent payload) {
        if(payload.getEventType().equals("balances#credit") && payload.getSchemaVersion().equals("2.0.0")) {
            transferService.handleIncomingDepositWebhooksEvent(payload.getData());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
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