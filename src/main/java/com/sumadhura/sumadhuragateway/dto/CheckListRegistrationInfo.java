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
 * CheckListRegistrationInfo class provides CHECKLIST_REGISTRATION Table specific fields.
 * 
 * @author Srivenu
 * @since 30.05.2019
 * @time 12:00PM
 */


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CheckListRegistrationInfo implements Serializable {
	
	private static final long serialVersionUID = 8418465345733053908L;
	//private CustomerInfo customerInfo;
	private List<CustomerCheckListVerificationInfo> customerCheckListVerification= new ArrayList<CustomerCheckListVerificationInfo>();
	//private SiteInfo siteInfo;
	//private FlatInfo flatInfo;
	private String agValue;
	private String sdValue;
	private String legalComments;
	private String accountsComments;
	private Long legalOfficerEmpId;
	private String legalOfficerEmpName;
	private Timestamp legalOfficerDate;
	private Long accountsExecutiveEmpid;
	private String accountsExecutiveEmpName;
	private Timestamp accountsExecutiveDate;
	private Long authorizedSignatureId;
	private String authorizedSignatureName;
	private Timestamp authorizedDate;
	private Long sdNumber;
	private Long customerId;
    private Long flatBookingId;
    
}
