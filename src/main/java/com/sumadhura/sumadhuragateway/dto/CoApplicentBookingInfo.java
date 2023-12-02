package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sumadhura.sumadhuragateway.employeeservice.enums.Status;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CoApplicentBookingInfo implements Serializable {

	private static final long serialVersionUID = -7626673583831483413L;
	private Long coAppBookInfoId;
	private String phoneNo;
	private String alternatePhoneNo;
	//private Long phoneNo;
	//private Long alternatePhoneNo;
	private String email;
	private String telePhone;
	private String maritalStatus;
	private Timestamp dateOfAnniversery;
	private String workExperience;
	private String educationalQualification;
	private String annualHouseHoldIncome;
	private Long custProffisionalId;
	private Long statusId;
	private Status status;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private Long coApplicantId;
	private Long custBookInfoId;
	private String type;
	
}
