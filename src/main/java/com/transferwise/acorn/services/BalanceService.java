package com.transferwise.acorn.services;

import com.transferwise.acorn.models.BalanceCredit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceAPI balanceAPI;
    private final SavingsService savingsService;
    @Value("${api.token}")
    private String token;

    @Async
    public void handleIncomingDepositWebhooksEvent(BalanceCredit balanceCredit) {
        final double toBeSavedAmount = savingsService.savingsAmount(balanceCredit);
        if (toBeSavedAmount == 0) {
            return;
        }
        final Long profileId = balanceCredit.getResource().getProfileId();
        final String currency = balanceCredit.getCurrency();
        // TODO: is this correct?
        final Long sourceJarId = balanceCredit.getResource().getId();
        final Long targetJarId = getTargetJarId();

        balanceAPI.makeBalanceToBalanceTransfer(
                token,
                toBeSavedAmount,
                currency,
                profileId,
                sourceJarId,
                targetJarId
        );
    }


    private Long getTargetJarId() {
        return 75555L;
    }

    /*
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
     */
}
