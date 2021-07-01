package com.transferwise.acorn.services;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@JsonSerialize
@Data
public class BalanceValue {

	private Long id;
	private String currency;
	private Amount amount;
	private ReservedAmount reservedAmount;
	private TotalWorth totalWorth;
	private String type;
	private String name;
	private Icon icon;
	private String investmentState;
	private Date creationTime;
	private Date modificationTime;
	private boolean visible;

	@JsonSerialize
	@Data
	@NoArgsConstructor
	private static class Amount {
		private Long value;
		private String currency;
	}

	@JsonSerialize
	@Data
	@NoArgsConstructor
	private static class ReservedAmount {
		private Long value;
		private String currency;
	}

	@JsonSerialize
	@Data
	@NoArgsConstructor
	private static class TotalWorth {
		private Long value;
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
