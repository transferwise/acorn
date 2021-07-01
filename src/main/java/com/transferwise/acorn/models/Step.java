package com.transferwise.acorn.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Step {
    public Long id;
    public Long transactionId;
    public String type;
    public Date creationTime;
    public List<BalancesAfter> balancesAfter;
    public String channelName;
    public String channelReferenceId;
    public Object tracingReferenceCode;
    public SourceAmount sourceAmount;
    public TargetAmount targetAmount;
    public Long sourceBalanceId;
    public Long targetBalanceId;
    public Fee fee;
    public BigDecimal rate;
}
