package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class LoanRequest extends Result implements Serializable {

	private static final long serialVersionUID = -7934624262306848296L;
	private Long departmentId;
	private Long roleId;
	private Long siteId;
	private String siteName;
	private Long employeeId;
	private String fileLocationType;
	private List<FileInfo> fileInfos;
	private String externalDriveLocation;
	private List<Long> siteIds;
	private Long customerId;
	private Long flatBookingId;
	private String requestUrl;
	private Long appRegId;
	private Long flatId;
	private String flatNo;
	private String EmailId;
	private String mailSentTO;
	private Long intrestedBankId;
	private Long doucumentId;
	private String docLocation;
	private String urlLocation;
	private Long portNumber;
	private Long customerLoanEOIDetailsId;
	private String externalDriveFileLocation;
	
	private Timestamp leadFromDate;
	private Timestamp leadToDate;
	private Long custId;
	private String custName;
	private Long bookingFormId;
	private Long bankerLeadViewStatusId;
	private String bankerLeadViewStatus;
	private Long leadStatusId;
	private String leadStatus;
	private String previousBankerComments;
	private String bankerComment;
	private Long bankerListId;
	private String bankerName;
}
