package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * @author @NIKET CH@V@N
 * @since 11.01.2020
 * @time 06:25 PM
 * @description this class provides basic properties for controller financial module
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@ToString
@Getter
@Setter
public class EmployeeFinancialRequest  extends Result implements Serializable{ 
	private static final long serialVersionUID = 2908943929161565147L;
	private Long empId;
	private String employeeName;
	//private Long createdBy;
	private Long siteId;
	private String siteName;//already added
	private String mileStoneAliasName;
	private Long finMilestoneClassifidesId;
	private String demandNoteSelectionType;
	private String isNewCustomer;
	private Timestamp demandNoteDate;
	private String isInterestOrWithOutInterest;
	private String actionUrl;
	private String requestUrl;
	private String condition;
	private Long bookingFormId;
	private Long portNumber;
	private Long finBookingFormDemandNoteId;
	private String isReGenerateDemandNote;
	private String isThisUpdateDemandNote;
	private String isShowGstInPDF;
	private List<Long> flatBookingIds;
	//private List<Long> demandNoteIds;
	private List<Long> bookingFormIds;
	private List<Long> flatIds;
	private List<Long> floorIds;
	private List<Long> blockIds;
	private List<Long> siteIds;
	private List<Long> projectMileStoneIds;
	private List<CustomerPropertyDetails> demandNoteSelectionTypeValues;//CustomerPropertyDetails
	private List<FinancialProjectMileStoneRequest> financialProjectMileStoneRequests;
	private List<FinancialBookingFormAccountsRequest> bookingFormAccountsRequests;
	private List<FinancialGstDetailsRequest> financialGstDetailsRequests;
	private List<FileInfo> fileInfos;
	private List<FinancialDemandNoteMS_TRN_Request> demandNoteMSRequests;
	private List<FinancialDemandNoteMS_TRN_Request> demandNoteTransactionRequests;
	private List<FinancialSchemeRequest> financialSchemeRequests;
	//private List<FinancialFlatCostDetailsRequest> financialFlatCostDetailsRequests;
	private List<FinancialUploadDataRequest> financialUploadDataRequests;
	//used for interest waiver
	private String comment;
	private List<Map<String, String>> comments;
	private Long finBookingFormModiCostId;
	
	/* Malladi */
	private Long flatId;
	private Timestamp startDate;
	private Timestamp endDate;
	
}
