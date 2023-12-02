package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * TicketEscalationResponse bean class provides Employee Ticket escalation Response specific properties.
 * 
 * @author Venkat_Koniki
 * @since 30.04.2019
 * @time 11:50AM
 */
public class TicketEscalationResponse implements Serializable {

	private static final long serialVersionUID = 8841933404917952127L;
	
	private Long ticketEscalationId;
	private Long ticketId;
	private Timestamp escalationDate;
	private Long escalationById;
	private String escalationBy;
	private String comments;
	private Long status;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	
	/**
	 * @return the ticketEscalationId
	 */
	public Long getTicketEscalationId() {
		return ticketEscalationId;
	}
	/**
	 * @param ticketEscalationId the ticketEscalationId to set
	 */
	public void setTicketEscalationId(Long ticketEscalationId) {
		this.ticketEscalationId = ticketEscalationId;
	}
	/**
	 * @return the ticketId
	 */
	public Long getTicketId() {
		return ticketId;
	}
	/**
	 * @param ticketId the ticketId to set
	 */
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	/**
	 * @return the escalationDate
	 */
	public Timestamp getEscalationDate() {
		return escalationDate;
	}
	/**
	 * @param escalationDate the escalationDate to set
	 */
	public void setEscalationDate(Timestamp escalationDate) {
		this.escalationDate = escalationDate;
	}
	/**
	 * @return the escalationById
	 */
	public Long getEscalationById() {
		return escalationById;
	}
	/**
	 * @param escalationById the escalationById to set
	 */
	public void setEscalationById(Long escalationById) {
		this.escalationById = escalationById;
	}
	/**
	 * @return the escalationBy
	 */
	public String getEscalationBy() {
		return escalationBy;
	}
	/**
	 * @param escalationBy the escalationBy to set
	 */
	public void setEscalationBy(String escalationBy) {
		this.escalationBy = escalationBy;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the status
	 */
	public Long getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}
	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TicketEscalationResponse [ticketEscalationId=" + ticketEscalationId + ", ticketId=" + ticketId
				+ ", escalationDate=" + escalationDate + ", escalationById=" + escalationById + ", escalationBy="
				+ escalationBy + ", comments=" + comments + ", status=" + status + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + "]";
	}
	
}
