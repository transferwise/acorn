package com.transferwise.acorn.services;

import com.transferwise.acorn.models.BalanceResponse;
import com.transferwise.acorn.models.BalanceTransferPayload;
import com.transferwise.acorn.models.OpenBalanceCommand;

import java.util.List;

public interface BalanceAPI {

    BalanceResponse makeBalanceToBalanceTransfer(Long profileId, BalanceTransferPayload balanceTransferPayload);

    List<BalanceValue> findActiveBalances(Long profileId);

    BalanceValue createBalanceJar(Long profileId, OpenBalanceCommand openBalanceCommand);
}
