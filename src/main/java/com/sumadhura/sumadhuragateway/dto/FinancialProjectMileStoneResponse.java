package com.sumadhura.sumadhuragateway.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinancialProjectMileStoneResponse {

	private Long projectMilestoneId;
	private String milestoneName;
	private Long finMilestoneClassifidesId;
	private Long finBokFomDmdNoteId;
	private Long percentagesId;
	private Double mileStonePercentage;
	private Timestamp milestoneDate;
	private Timestamp mileStoneDueDate;
	private Long mileStoneNo;
	private String createdBy;
	private Timestamp createdDate;
	private String modifiedBy;
	private Timestamp modifiedDate;
	private Double mileStoneBasicAmount;
	private Double mileStoneTotalAmount;
	private Double mileStoneTaxAmount;
	private Double mileStoneTdsAmount;
	private Double payAmount;
	private Double sgstAmount;
	private Double cgstAmount;
	private Double gstAmount;
	private Double paidAmount;
	private Double totalPenaltyAmount;
	private Double totalPendingPenaltyAmount;
	private Double totalPenalityPaidAmount;
	private Double interestWaiverAdjAmount;
	private String interestWaiverPendingAmount;//interest waiver amount to approve
	private Double totalDueAmount;
	private Double paidTaxAmount;
	private String customerName;
	private Double setOffAmount;
	private Long submitedById;
	private String submitedByName;
	private Long msStatusId;
	private Long statusId;
	private String statusName;
	private Long tdsStatusId;
	private String tdsStatusName;
	private Long finBookingFormTdsDtlsId;
	private String demandNoteNo;
	private Timestamp demandNoteDate;
	private String documentLocation;
	private String documentName;
	private Long daysDelayed;
	// private Long tdsDetailsStatusId;
	//private List<FinBookingFormAccountsResponse> finBookingFormAccountsResponseList;
	//private List<FinBookingFormMstSchTaxMapInfo> bokFrmDemNteSchTaxMapInfos;
	//private List<CustomerPropertyDetailsInfo> flatsResponse;
	//private List<FinBookingFormModiCostDtlsResponse> bookingFormModiCostDtlsResponses;
	//private List<FinBookingFormLglCostDtlsResponse> bookingFormLglCostDtlsResponses;

}