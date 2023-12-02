package com.sumadhura.sumadhuragateway.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ChangePasswordReq extends Login {
	
	
	private static final long serialVersionUID = 1L;
	private String newMpin;
	private String oldMpin;
	
	
}
