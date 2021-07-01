package com.transferwise.acorn.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.transferwise.acorn.models.MoneyValue;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@JsonSerialize
@Data
public class BalanceValue {

	private Long id;
	private String currency;
	private MoneyValue amount;
	private MoneyValue reservedAmount;
	private MoneyValue totalWorth;
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
	private static class Icon {
		private String type;
		private String value;
	}


}
