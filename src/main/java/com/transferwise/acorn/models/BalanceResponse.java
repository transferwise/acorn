package com.transferwise.acorn.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;


@JsonSerialize
public class BalanceResponse {
    public int id;
    public String type;
    public String state;
    public String channelName;
    public String channelReferenceId;
    public List<BalancesAfter> balancesAfter;
    public Date creationTime;
    public List<Step> steps;
    public SourceAmount sourceAmount;
    public TargetAmount targetAmount;
    public double rate;
    public List<FeeAmount> feeAmounts;
}