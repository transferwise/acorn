package com.transferwise.acorn.balance;

import com.transferwise.acorn.balance.dto.BalanceResponse;
import com.transferwise.acorn.balance.dto.BalanceTransferPayload;
import com.transferwise.acorn.balance.dto.BalanceValue;
import com.transferwise.acorn.balance.dto.OpenBalanceCommand;

import java.util.List;
import java.util.UUID;

public interface BalanceAPI {

    BalanceResponse makeBalanceToBalanceTransfer(Long profileId, BalanceTransferPayload balanceTransferPayload);

    List<BalanceValue> findActiveBalances(Long profileId);

    BalanceValue createBalanceJar(Long profileId, OpenBalanceCommand openBalanceCommand, UUID idempotenceId);
}
