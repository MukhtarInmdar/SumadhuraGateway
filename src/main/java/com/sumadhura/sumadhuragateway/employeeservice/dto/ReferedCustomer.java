package com.sumadhura.sumadhuragateway.employeeservice.dto;

import com.sumadhura.sumadhuragateway.dto.Result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReferedCustomer extends Result {

	private String referrerName;
	
	private Long refrenceId;
	
	private String customerName;
	
	private String customerFlatNo;
	
	private String customerImg;
	
	private String referrerImg;
	
	private String refrenceSite;
	
	private String referrerEmailId;
	
	private Long mobileNo;
	
	private String empComments;
	
	private String status;
	
	private String cityName;
	
	private String stateName;
	
	private long pincode;
	
	private long siteId;
	
	private String panNumber;
	
	private String interestFlat;
	
	private String comments;
	
	private Long StatusId;
}
