/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sumadhura.sumadhuragateway.dto.Result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * EmployeeTicketResponse bean class provides Employee Ticket specific properties.
 * 
 * @author Venkat_Koniki
 * @since 27.04.2019
 * @time 11:08AM
 */
@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class EmployeeTicketResponse extends Result implements Serializable{
	
	private static final long serialVersionUID = -3853006053990602702L;
	
	private List<TicketResponse> ticketResponseList;
	private List<TicketExtendedEscalationApproval> escalationApprovals;
	private List<EmployeeDetails> EmployeeDetailsList;
	private ChangeTicketType changeTicketTypeResponce;
	private List<Long> siteIds;
	private Integer totalTickets;
	private Integer newState;
	private Integer open;
	private Integer inProgress;
	private Integer closed;
	private Integer reOpen;
	private Integer replied;
	private Long pageCount;
	private List<TicketResponse> totalTicketResponseList;
	private Long rowCount;
	private Integer escalated;
	private Integer totalOpen;
}
