package com.sumadhura.sumadhuragateway.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TicketFeedbackDTO extends Result {

	private int rating;
	private String feedbackDesc;
	private String ticketType;
	private long ticketId;
	private String deviceId;
	private String customerName;
	private Long customerId;
	private String emailId;
	private String flatNo;
	private Integer totalRatingPoints;
	
	
	
}
