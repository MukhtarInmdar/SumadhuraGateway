/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
* 
* @author Venkat_Koniki
* @since 17.04.2019
* @time 10:55AM
*/

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class PaymentDetails extends Result implements Serializable {
	
	private static final long serialVersionUID = -1956161855431347950L;
	private Long paymentDetailsId;
	private String paymentMode;
	private String amount;
	private String milestoneAdjustedAmount;
	private Integer noOfTerms;
	private String bankName;
	private Long paySchedId;
	private String receiptNumber;
	private String referenceNumber;
	private Date createdDate;
	private Date clearenceDate;
	private Long statusId;
	private String status;
	private String uploadedDocs;
	private List<MileStoneTaxDtlsInfo> mileStoneTaxDtlsInfo;
	private String mileStoneName;
	private List<DemandNoteInfo> demandNote;
	private String invoiceDocument;
}
