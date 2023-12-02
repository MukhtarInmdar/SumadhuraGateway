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
public class CheckListCRMInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1032115326711813266L;
	private List<CustomerCheckListVerificationInfo> customerCheckListVerification= new ArrayList<CustomerCheckListVerificationInfo>();
	private Long checkListCrmId;
	private String commitmentsFromSTM;
	private String crmRemarks;
	private String crmPreferenceBankLoan;
	private String comment;
	private Timestamp expectedAgreeDate;
	private String expectedAgreeDateComment;
	private Timestamp crmSignedDate;
	private Long authorizedSignatoryeId;
	private String authorizedSignatoryeName;
	private Timestamp authorizedSignatoryDate;
	private Long crmEmpID;
	private String crmVerifiedByName;
	private Long flatBookingId;
	private String crmSignedName;
	private String welcomeCallRecord;
	private Long customerId;
	private Timestamp createdDate;
	private Long statusId;
	
}
