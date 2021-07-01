package com.transferwise.acorn.services;

import com.transferwise.acorn.models.BalanceCredit;
import org.springframework.stereotype.Service;

@Service
public class SavingsService {
    private static final double SAVINGS_PERCENT = 0.1;
    private static final double MINIMUM_SAVINGS_AMOUNT = 1.0;

    public double savingsAmount(BalanceCredit balanceCredit){
        final double incomingAmount = balanceCredit.getAmount().doubleValue();
        if (incomingAmount < MINIMUM_SAVINGS_AMOUNT/SAVINGS_PERCENT){
            return 0;
        }
        return Math.round(incomingAmount*SAVINGS_PERCENT * 100.0) / 100.0;
    }
}
