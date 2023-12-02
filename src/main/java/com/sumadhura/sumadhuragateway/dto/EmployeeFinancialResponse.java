package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * 
 * @author @NIKET CH@V@N
 * @since 11.01.2020
 * @time 06:50 PM
 * @description this class is for holding response data for financial module
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeFinancialResponse implements Serializable {
	private static final long serialVersionUID = 4519169866107540353L;
 
	private Long finMilestoneClassifidesId;
	private Long siteId;
	private String siteName;
	private String mileStoneAliasName;
	private String description;
	private Long statusId;
	private String createdBy;
	private Timestamp createdDate;
	private String modifiedBy;
	private Timestamp modifiedDate;
	private List<FinancialProjectMileStoneResponse> financialProjectMileStoneResponse;
	private List<FinMileStoneCLSMappingBlocksResponse> mappingBlocksResponse;
	List<CustomerPropertyDetails> flatsResponse;
	
	//private static final long serialVersionUID = 4519169866107540353L;
	//private Long finMilestoneClassifidesId;
	//private Long siteId;
	//private String siteName;
	//private String mileStoneAliasName;
	//private String description;
	//private Long statusId;
	//private String createdBy;
	//private Timestamp createdDate;
	//private String modifiedBy;
	//private Timestamp modifiedDate;
	private boolean isShowGstInPDF;
	//private List<FinancialProjectMileStoneResponse> financialProjectMileStoneResponse;
	//private List<FinMileStoneCLSMappingBlocksResponse> mappingBlocksResponse;
	
	//private List<FinBookingFormDemandNoteResponse>  finBookingFormDemandNoteesponse ;
	
	private List<AddressInfo> customerAddressInfoList;
	private List<AddressInfo> siteAddressInfoList;
	//private List<FinProjectAccountResponse> finProjectAccountResponseList;
	//private List<OfficeDtlsResponse> officeDetailsList;
	//private List<CustomerPropertyDetailsInfo> flatsResponse;
	
	private Double totalMileStoneAmount;
	private Double totalmileStoneBasicAmount;
    private Double totalMileStoneTaxAmount;
    private Double totalSgstAmount;
    private Double totalCgstAmount;
    private Double totalPaidAmount;
    private Double totalPenaltyAmount;
    private Double totalPendingPenaltyAmount;
    private Double totalDueAmount;
    private Double totalMilestoneDuePercent;
    private String demandNotePdfFileName;
    private Double totalDemandNoteAmount;
    private String totalFlatCost;
    private String totalCreditAmount;
    private String totalDebitAmount;
    private String totalBalanceAmount;
    private Timestamp bookingDate;
    private Timestamp demandNoteDate;
    private Boolean isInterestOrWithOutInterest;
    private List<CustomerPropertyDetailsInfo> nonSavedDemandNoteDetails;
    
    private List<FileInfo> fileInfoList;
    
    //private List<FinCustomerLedgerResponse> customerLedgerResponses;
    private Object data;

}
