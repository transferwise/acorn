package com.transferwise.acorn.services;

import com.transferwise.acorn.models.BalanceResponse;

import java.util.List;
import java.util.Optional;

public interface BalanceAPI {

    Optional<BalanceResponse> makeBalanceToBalanceTransfer(String token,
                                                                  double value,
                                                                  String currency,
                                                                  Long profileId,
                                                                  Long sourceBalanceId,
                                                                  Long targetBalanceId);

    Optional<List<OpenBalanceCommand>> findActiveBalances(String token,
                                                           Long profileId);

    Optional<OpenBalanceCommand> createBalanceJar(com.transferwise.acorn.models.OpenBalanceCommand openBalanceCommand);
}
