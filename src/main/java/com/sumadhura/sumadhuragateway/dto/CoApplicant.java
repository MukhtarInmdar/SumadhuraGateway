package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class CoApplicant extends Result implements Serializable {

	private static final long serialVersionUID = 8662365699321616091L;
	private Long applicantId;
	private Long custId;
	private Integer addressId;
	private Long applicantNumber;
	private String firstName;
	private String lastName;
	private String middleName;
	private String gender;
	private Integer age;
	private String adaharId;
	private String voterId;
	private String pancard;
	private String phoneNo;
	private String alternatePhoneNo;
	private String email;
	private Date createdDate;
	private Date updatedDate;
	private String dateofBirth;
	private String relationWithCust;
	private String nationality;
	private String relationWith;
	private String relationName;
	private String telephone;
	private CustomerAddressDTO customerAddressDTO;
	private String address;
	
	

}
