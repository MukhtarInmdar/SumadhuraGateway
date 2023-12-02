package com.sumadhura.sumadhuragateway.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * CustomerPropertyDetailsPojo class provides CUSTOMER Property specific fields.
 * 
 * @author Srivenu
 * @since 18.06.2019
 * @time 1:50PM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CustomerPropertyDetailsInfo {
	
	private Long customerId;
	private String customerName;
	private Long flatBookingId;
	private Long salesTeamLeadId;
	private Long flatId;
	private String flatNo;
	private Long flooId;
	private String floorName;
	private Long blockId;
	private String blockName;
	private Long siteId;
	private String siteName;
	private Double totalAgreementCost;
	
	//properties for financial module 
	private Long finProjectDemandNoteId;
	private Long statusId;
	private Timestamp startDate;
	private Timestamp endDate;
	private String demandNoteSelectionType;
	private String condition;
	private Long type;
	private Long typeId;
	private String custFullName;
	private String coAppFullName;
	private String pancard;
	private String customerEmail;
	private String contactNumber;
	private String alternatePhoneNo;
	
	private Timestamp bookingDate;
	private Timestamp agreementDate;
	private Long sbua;
	private String bhk;
	private String appStatus;
	private Timestamp lastLoginTime;
}
