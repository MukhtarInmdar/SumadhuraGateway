package com.sumadhura.sumadhuragateway.dto;

import lombok.Data;

@Data
public class FinancialBookingFormAccountsRequest {
	private Long paidAmount;
	private String dueDate;
	private String amountPaidDate;
	private Long bookingFormId;
	private Long flatId;
	private String flatNo; 
}
