package com.sumadhura.sumadhuragateway.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinancialDemandNoteMS_TRN_Request {
	//Demand notes
	private String recordType;
	private String siteId;
	private String siteName;
	private String demandNoteNo;
	private String blockName;
	private String flatNo;
	private Long flatId;
	private Long flatBookingId;
	private Timestamp demandNoteDate;
	private String isWithInterestOrWithOut;
	private Long projectMilestoneId;
	private Long mileStoneNo;
	private String milestoneName;
	private Double mileStonePercentage;
	private Timestamp milestoneDate;
	private Timestamp milestoneCompletedDate;
	private Timestamp mileStoneDueDate;
	private Double totalFlatAmountIncludeGST;
	private Double amountReceivedIncludeGST;
	private Double dueAmountExcludinggst;
	private Double cgst;
	private Double sgst;
	private Double totalDueAmount;
	private String isShowGstInPDF;
	//Transactions
	private String transactionReceiptNo;
	private String salesFrTransactionReceiptNo;
	private String transactionTypeName;
	private String transactionModeName;
	private String transferModeName;
	private String transactionFor;
	
	private Long transactionTypeId;
	private Long transactionModeId;
	private Long transferModeId;
	private Long transactionForId;
	
	private String transactionNo;
	private String chequeNumber;
	private String referenceNo;
	private Double receivedAmount;
	//private Timestamp chequeDate;
	private Double transactionAmount;	
	private Timestamp transactionDate;
	private Timestamp transactionReceiveDate;
	private Timestamp chequeDate;
	private Timestamp onlineReceiveDate;
	private Timestamp chequeReceiveDate;
	private Timestamp chequeClearanceDate;
	private Timestamp chequeDepositedDate;
	private Timestamp transactionClosedDate;
	private String receiptStage;
	//private String isShowGstInPDF;
	private String excelRecordNo;
	private String dataUploadCondition;
	private Long bankId;
	private String bankName;
	private Long siteAccountId;
	private String siteBankAccountNumber;
	private Long bankAccountId;
	private String bankAccountNumber;
	private String comment;
	private String sourceOfFunds;
	private String typeOfDemandNoteFormat;
	private List<EmployeeFinTranPaymentSetOffRequest> paymentSetOffDetails;	
}