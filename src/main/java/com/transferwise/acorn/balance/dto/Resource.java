package com.transferwise.acorn.balance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Resource {
    private String type;
    private Long id;
    @JsonProperty("profile_id")
    private Long profileId;
}