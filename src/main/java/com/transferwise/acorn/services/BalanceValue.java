package com.transferwise.acorn.services;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@JsonSerialize
@Data
public class BalanceValue {

	int id;
	String currency;
	Amount amount;
	ReservedAmount reservedAmount;
	TotalWorth totalWorth;
	String type;
	String name;
	Icon icon;
	String investmentState;
	Date creationTime;
	Date modificationTime;
	boolean visible;

	@JsonSerialize
	@Data
	@NoArgsConstructor
	private static class Amount {
		private int value;
		private String currency;
	}

	@JsonSerialize
	@Data
	@NoArgsConstructor
	private static class ReservedAmount {
		private int value;
		private String currency;
	}

	@JsonSerialize
	@Data
	@NoArgsConstructor
	private static class TotalWorth {
		private int value;
		private String currency;
	}

	@JsonSerialize
	@Data
	@NoArgsConstructor
	private static class Icon {
		private String type;
		private String value;
	}


}
