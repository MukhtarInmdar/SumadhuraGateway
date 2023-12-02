package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ReferencesCustomerInfo implements Serializable {

	private static final long serialVersionUID = -6400871139119814928L;
	private Long referencesCustomerId;
	private Long customerId;
	private String customerName;
	private String projectName;
	private String unitNo;
	private Long id;

	
}
