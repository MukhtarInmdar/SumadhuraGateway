/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.List;


/**
 * EmployeeTicketResponse bean class provides Employee Ticket specific properties.
 * 
 * @author Venkat_Koniki
 * @since 27.04.2019
 * @time 11:08AM
 */
public class EmployeeTicketResponse extends Result implements Serializable{
	
	private static final long serialVersionUID = -3853006053990602702L;
	
	private List<TicketResponse> ticketResponseList;
	private Integer totalTickets;
	private Integer open;
	private Integer inProgress;
	private Integer closed;
	/**
	 * @return the ticketResponseList
	 */
	public List<TicketResponse> getTicketResponseList() {
		return ticketResponseList;
	}
	/**
	 * @param ticketResponseList the ticketResponseList to set
	 */
	public void setTicketResponseList(List<TicketResponse> ticketResponseList) {
		this.ticketResponseList = ticketResponseList;
	}
	/**
	 * @return the totalTickets
	 */
	public Integer getTotalTickets() {
		return totalTickets;
	}
	/**
	 * @param totalTickets the totalTickets to set
	 */
	public void setTotalTickets(Integer totalTickets) {
		this.totalTickets = totalTickets;
	}
	/**
	 * @return the open
	 */
	public Integer getOpen() {
		return open;
	}
	/**
	 * @param open the open to set
	 */
	public void setOpen(Integer open) {
		this.open = open;
	}
	/**
	 * @return the inProgress
	 */
	public Integer getInProgress() {
		return inProgress;
	}
	/**
	 * @param inProgress the inProgress to set
	 */
	public void setInProgress(Integer inProgress) {
		this.inProgress = inProgress;
	}
	/**
	 * @return the closed
	 */
	public Integer getClosed() {
		return closed;
	}
	/**
	 * @param closed the closed to set
	 */
	public void setClosed(Integer closed) {
		this.closed = closed;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EmployeeTicketResponse [ticketResponseList=" + ticketResponseList + ", totalTickets=" + totalTickets
				+ ", open=" + open + ", inProgress=" + inProgress + ", closed=" + closed + "]";
	}
		
}
