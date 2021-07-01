package com.transferwise.acorn.services;

import com.transferwise.acorn.models.BalanceResponse;
import com.transferwise.acorn.models.OpenBalanceCommand;

import java.util.List;
import java.util.Optional;

public interface BalanceAPI {

    Optional<BalanceResponse> makeBalanceToBalanceTransfer(String token,
                                                                  double value,
                                                                  String currency,
                                                                  Long profileId,
                                                                  Long sourceBalanceId,
                                                                  Long targetBalanceId);

    Optional<List<BalanceValue>> findActiveBalances(String token,
                                                    Long profileId);

    Optional<BalanceValue> createBalanceJar(Long profileId, String token, OpenBalanceCommand openBalanceCommand);
}
