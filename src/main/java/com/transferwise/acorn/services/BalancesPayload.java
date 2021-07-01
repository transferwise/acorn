package com.transferwise.acorn.services;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.transferwise.acorn.models.BalanceType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@JsonSerialize
@Data
@Builder
public class BalancesPayload {
    private final List<BalanceType> types;
    private final boolean includeHidden;
}
