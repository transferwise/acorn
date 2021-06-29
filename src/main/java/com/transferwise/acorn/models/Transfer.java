package com.transferwise.acorn.models;



public class Transfer{
    int id;
     int user;
    public int targetAccount;
    public Object sourceAccount;
    public Object quote;
    public int quoteUuid;
    public String status;
    public String reference;
    public double rate;
    public String created;
    public int business;
    public Object transferRequest;
    public TransferDetails details;
    public boolean hasActiveIssues;
    public String sourceCurrency;
    public double sourceValue;
    public String targetCurrency;
    public int targetValue;
    public String customerTransactionId;
}