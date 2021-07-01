package com.transferwise.acorn.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@JsonSerialize
public class BalanceResponse {
    public Long id;
    public String type;
    public String state;
    public String channelName;
    public String channelReferenceId;
    public List<BalancesAfter> balancesAfter;
    public Date creationTime;
    public List<Step> steps;
    public SourceAmount sourceAmount;
    public TargetAmount targetAmount;
    public BigDecimal rate;
    public List<FeeAmount> feeAmounts;
}