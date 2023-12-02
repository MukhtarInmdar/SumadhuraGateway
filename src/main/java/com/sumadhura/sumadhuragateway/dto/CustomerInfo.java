package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CustomerInfo implements Serializable {
	
	private static final long serialVersionUID = -9010478128188925776L;
	private Long customerId;
	private String namePrefix;
	private String firstName;
	private String lastName;
	private String gender;
	private Long age;
	private Timestamp dob;
	private String profilePic;
	private String nationality;
	private String adharNumber;
	private String pancard;
	private String passport;
	private String voterId;
	private String relationNamePrefix;
	private String relationName;
	private String relationWith;
	private Long statusId;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	
}
