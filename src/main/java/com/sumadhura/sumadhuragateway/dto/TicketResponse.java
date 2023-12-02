/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

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
public class TicketResponse extends Result {

	private Long ticketId;
	private String title;
	private String description;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private Long statusId;
	private String status;
	private Long ticketTypeDetailsId;
	private Long departmentId;
	private Long assignmentTo;
	private Long assignedBy;
	private Timestamp assignedDate;
	private Long flatBookingId;
	private Timestamp estimatedResolvedDate;
	private Long estimatedResolvedDateStatus;
	private Timestamp extendedEstimatedResolvedDate;
	private Timestamp resolvedDate;
	private Long statusUpdateBy;
	private Long statusUpdateType;
	private String documentLocation;
	private Long ticketTypeId;
	private Long ticketStatusId;
	private Long departmentTicketStatusId;
	private List<TicketEscalationResponse> ticketEscalationResponses;
	private CustomerPropertyDetails customerPropertyDetails;
	private Department department;
	private TicketType ticketType;
	private List<TicketComment> ticketComments;
	private List<String> viewTicketsPdfs;
	private List<FileInfo> fileInfos;
	private String assignedEmployee;
   // private int rating;
	 private String rating;
    private String feedbackDesc;
    private Boolean isTicketReopenEnable;
    private Timestamp reopenenDate;
    private Long complaintStatus;
    private Long complaintCreatedBy;
    private Timestamp complaintCreatedDate;
    private String complaintCreatedByEmpName;
	
}
	
