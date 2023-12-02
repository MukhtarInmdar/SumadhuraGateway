package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CustomerApplicationInfo implements Serializable {

	private static final long serialVersionUID = -7205017382561738561L;
	private Long custAppId;
	private Long siteId;
	private Long unit;	
	private Long leadId;
	private String ackId;	
	//private Long ackId;	
	private Long appNo;	
	private Long blockId;	
	private Long statusId;	
	private Timestamp createdDate;	
	private Timestamp modifiedDate;
	private Long stmId;	
	private Long flatBookId;
	private String reasonForNoKYC;

}
