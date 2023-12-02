/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;




/**
 * CustomerAddressPojo class provides ADDRESS Table specific fields.
 * 
 * @author Venkat_Koniki
 * @since 06.05.2019
 * @time 05:52PM
 */


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CheckListLegalOfficerInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6820507268183236270L;
	//private CustomerInfo customerInfo;
	private List<CustomerCheckListVerificationInfo> customerCheckListVerification=new ArrayList<CustomerCheckListVerificationInfo>();
	private List<CoApplicantCheckListVerificationInfo> coappCheckListApp = new ArrayList<CoApplicantCheckListVerificationInfo>();
	private String bankerName;
	private String bank;
	private String contact;
	private String bankerEmailAddress;
	private String offersIfAny;
	private String legelOfficerComments;
	private String legalOfficer;
	private Long empId;
	private Timestamp legalOfficeSignedate;
	private Long authorizedSignatoryId;
	private String authorizedSignatoryName;
	private Timestamp authorizedSignatoryDate;
	private Long customerId;
	private Long flatBookingId;
	private Long checkListLegalOfficierId;
	private String bankName;
	private Timestamp createdDate;
	private Long statusId;
	
}
