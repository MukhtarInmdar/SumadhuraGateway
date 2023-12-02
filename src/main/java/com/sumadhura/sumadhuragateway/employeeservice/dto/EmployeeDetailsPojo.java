/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.sql.Timestamp;

import lombok.Data;

/**
 * EmployeeDetailsPojo class provides EMPLOYEE_DETAILS Table specific fields.
 * 
 * @author Venkat_Koniki
 * @since 30.04.2019
 * @time 05:18PM
 */

@Data
public class EmployeeDetailsPojo {
	private Long empDetailsId;
	private String employeeName;
	private String employeeDesignation;
	private Long createdBy;
	private Timestamp createdDate;
	private Long modifiedBy;
	private Timestamp modifiedDate;
	private Long statusId;
	private String email;
	private Long departmentId;
	private String mobileNo;
	private String userProfile;
	private Long siteId;
	private Long employeeId;
	private String employeeNameDesg;
}


