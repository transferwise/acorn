package com.transferwise.acorn.models;

import lombok.Builder;

@Builder
public class Icon {
	private final String type = "EMOJI";
	private String value;
}
