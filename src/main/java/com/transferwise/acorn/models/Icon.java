package com.transferwise.acorn.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@JsonSerialize
@Data
public class Icon {
	private final String type = "EMOJI";
	private String value;
}
