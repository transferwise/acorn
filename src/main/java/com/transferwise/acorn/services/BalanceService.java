package com.transferwise.acorn.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.transferwise.acorn.models.BalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private static final String API_TOKEN = "979999c9-10cf-4da2-9f58-77a7edb57d03";
    private static final int PROFILE_ID = 25;
    private static final int SOURCE_BALANCE_ID = 3324;
    private static final int TARGET_BALANCE_ID = 75555;
    private final BalanceAPI balanceAPI;

    public Optional<BalanceResponse> makeBalanceToBalanceTransfer(double value, String currency) throws JsonProcessingException {
        return balanceAPI.makeBalanceToBalanceTransfer(API_TOKEN, value, currency, PROFILE_ID, SOURCE_BALANCE_ID, TARGET_BALANCE_ID);
    }
}
