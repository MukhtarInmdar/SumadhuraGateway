package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.io.Serializable;
import java.util.Date;

import com.sumadhura.sumadhuragateway.dto.Result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OTP extends Result implements Serializable{

	private static final long serialVersionUID = 9155552155890016782L;
	private Integer otp;
	private Boolean active;
	private Date createdDate;
	private String username;
	private String employeeName;
	private String mobileNo;
	private String email;
	private Long employeeId;
}
