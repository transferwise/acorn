package com.transferwise.acorn.services;

import com.transferwise.acorn.models.BalanceResponse;
import com.transferwise.acorn.models.OpenBalanceCommand;

import java.util.Optional;

public interface BalanceAPI {

    Optional<BalanceResponse> makeBalanceToBalanceTransfer(String token,
                                                                  double value,
                                                                  String currency,
                                                                  Long profileId,
                                                                  Long sourceBalanceId,
                                                                  Long targetBalanceId);

    Optional<BalancesResponse> findActiveBalances(String token,
                                                           Long profileId);

    Optional<BalanceValue> createBalanceJar(OpenBalanceCommand openBalanceCommand);

}
