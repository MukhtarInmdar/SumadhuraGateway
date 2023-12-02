package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * TicketResponse bean class provides Employee Ticket Response specific properties.
 * 
 * @author Venkat_Koniki
 * @since 29.04.2019
 * @time 05:53PM
 */

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown=true)
public class TicketResponse implements Serializable {

	private static final long serialVersionUID = 7207319652077854443L;
	@NotNull(message = "Please provide value for ticketId key ")
	private Long ticketId;
	private String title;
	@NotEmpty(message = "Please provide proper value for description key")
	@NotNull(message = "Please provide value for description key ")
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
	@NotNull(message = "Please provide value for ticketTypeId key ")
	private Long ticketTypeId;
	private Long ticketStatusId;
	private Long departmentTicketStatusId;
	private List<TicketEscalationResponse> ticketEscalationResponses;
	private Department department;
	private CustomerPropertyDetails customerPropertyDetails;
	private TicketType ticketType;
	private List<TicketComment> ticketComments;
	private List<String> viewTicketsPdfs;
	private List<FileInfo> fileInfos;
	private String assignedEmployee;
	private Long ticketEscalationId;
	private String pendingDepartmentName;
	private Boolean isTicketOwner;
//	private int rating;
	 private String rating;
	private String feedbackDesc;
	private String ticketOwnerName;
	
	private Long ticketTypeChangeRequest;
    private String ticketClosedBy;
    private Long complaintStatus;
    private Long complaintCreatedBy;
    private Timestamp complaintCreatedDate;
    private String complaintCreatedByEmpName;
    private Long pendingEmpId;
	private Long pendingDeptId;
}
