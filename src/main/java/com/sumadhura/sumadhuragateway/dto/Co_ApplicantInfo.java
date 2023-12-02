package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sumadhura.sumadhuragateway.employeeservice.enums.Status;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Co_ApplicantInfo implements Serializable {

	private static final long serialVersionUID = -6697727024692598764L;
	private Long coApplicantId;
	private String coApplicantNumber;
	private String namePrefix;
	private String firstName;
	private String lastName;
	private String middleName;
	private String gender;
	private Timestamp dateOfBirth;
	private Long age;
	private String aadharId;
	private String voterId;
	private String passport;
	private String pancard;
	private String nationality;
	private String relationWith;
	private String relationWithCust;
	private String relationName;
	private String relationNamePrefix;
	private Long statusId;
	private Status status;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private Timestamp handingOverDate;
	private Timestamp registrationDate;
	
}
