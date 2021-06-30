package com.transferwise.acorn.models;

import java.util.Date;
import java.util.List;

public class Step{
    public int id;
    public int transactionId;
    public String type;
    public Date creationTime;
    public List<BalancesAfter> balancesAfter;
    public String channelName;
    public String channelReferenceId;
    public Object tracingReferenceCode;
    public SourceAmount sourceAmount;
    public TargetAmount targetAmount;
    public int sourceBalanceId;
    public int targetBalanceId;
    public Fee fee;
    public double rate;
}
