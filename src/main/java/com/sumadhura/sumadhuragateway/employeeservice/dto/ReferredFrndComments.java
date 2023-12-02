package com.sumadhura.sumadhuragateway.employeeservice.dto;

import com.sumadhura.sumadhuragateway.dto.Result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReferredFrndComments extends Result{
	
	private String refrenceId;
	
	private String comments;
	
    private String referralStatusName;
	
	private Long referralStatusValue;
	
	

}
