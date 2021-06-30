package com.transferwise.acorn.services;

import com.transferwise.acorn.models.BalanceResponse;
import com.transferwise.acorn.models.BalanceTransferPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceAPI balanceAPI;


    public List<BalanceResponse> handleIncomingDepositEvent(BalanceTransferPayload payload) {
        if (payload.getPayments().size() != payload.getTargetBalanceIds().size()) {
            return Collections.emptyList();
        }
        List<BalanceResponse> results = new LinkedList<>();

        for (int i = 0; i < payload.getPayments().size(); i++) {
            final var payment = payload.getPayments().get(i);
            final var targetBalanceId = payload.getTargetBalanceIds().get(i);
            makeBalanceToBalanceTransfer(payload, payment, targetBalanceId).ifPresent(results::add);
        }
        return results;
    }

    private Optional<BalanceResponse> makeBalanceToBalanceTransfer(BalanceTransferPayload payload, double payment, int targetBalanceId) {
        return balanceAPI.makeBalanceToBalanceTransfer(
                payload.getApiToken(),
                payment,
                payload.getCurrency(),
                payload.getProfileId(),
                payload.getSourceBalanceId(),
                targetBalanceId
        );
    }
}
