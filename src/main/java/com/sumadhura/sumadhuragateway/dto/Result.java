package com.sumadhura.sumadhuragateway.dto;


import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Result class provides common properties for all services.
 * 
 * @author Venkat_Koniki
 * @since 26.03.2019
 * @time 04.50 PM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class Result {
	
	private Integer responseCode;
	private String status;
	private String sessionKey;
	private List<String> errors;
	private String description;
	private Object responseObjList;
	private Object notificationResponses;
	private Boolean isNotificationResponsesAvailable;
	private List<TicketEscalationResponse> ticketEscalationResponses;
	private String deviceToken;
	private Timestamp startDate;
	private Timestamp endDate;
	String internalExcetion;
	private Object ObjList;
		
	public Result() {
		super();
	}

	public Result(Integer responseCode, String status, List<String> errors) {
		super();
		this.responseCode = responseCode;
		this.status = status;
		this.errors = errors;
	}
	public Result(Integer responseCode, String status, List<String> errors,String internalExcetion) {
		super();
		this.responseCode = responseCode;
		this.status = status;
		this.errors = errors;
		this.internalExcetion = internalExcetion;
	}


}
