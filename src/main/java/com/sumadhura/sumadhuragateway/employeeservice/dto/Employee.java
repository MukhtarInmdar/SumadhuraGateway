/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;


/**
 * EmployeePojo class provides EMPLOYEE Table specific fields.
 * 
 * @author Venkat_Koniki
 * @since 12.06.2019
 * @time 10:14PM
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements Serializable  {

	private static final long serialVersionUID = -2850042567862066227L;
	private Long csEmpId;
	private Long employeeId;
	private String firstName;
	private String lastName;
	private String employeeName;
	private String email;
	private Long statusId;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String mobileNumber;
	private String userProfile;
	private String salesForceEmpName;

}
