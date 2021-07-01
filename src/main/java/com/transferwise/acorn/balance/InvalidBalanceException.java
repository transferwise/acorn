package com.transferwise.acorn.balance;

public class InvalidBalanceException extends RuntimeException {
	public InvalidBalanceException(String message) {
		super(message);
	}
}
