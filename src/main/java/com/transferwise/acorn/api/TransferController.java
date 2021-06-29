package com.transferwise.acorn.api;

import com.transferwise.acorn.models.AcornPayload;
import com.transferwise.acorn.models.Transfer;
import com.transferwise.acorn.services.TransferService;
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

    private final TransferService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<Optional<Transfer>> transfer(@RequestBody AcornPayload payload) {
        return ResponseEntity.ok(transferService.makeTransferToJar(payload));
    }
}
