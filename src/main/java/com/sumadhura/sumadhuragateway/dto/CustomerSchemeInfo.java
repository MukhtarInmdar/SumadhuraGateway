package com.sumadhura.sumadhuragateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerSchemeInfo {
	private String schemeName;
	 private Double percentageValue;
}

