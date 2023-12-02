/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.List;


public class CustomerWrapper extends Result implements Serializable{

	private static final long serialVersionUID = -9176000052395731456L;
	private List<CoApplicant> coApplicants;
	private Customer customer;
	private List<CustomerTicketRequest> customerTickets;

	
	/**
	 * @return the customerTickets
	 */
	public List<CustomerTicketRequest> getCustomerTickets() {
		return customerTickets;
	}
	/**
	 * @param customerTickets the customerTickets to set
	 */
	public void setCustomerTickets(List<CustomerTicketRequest> customerTickets) {
		this.customerTickets = customerTickets;
	}
	/**
	 * @return the coApplicant
	 */
	public List<CoApplicant> getCoApplicants() {
		return coApplicants;
	}
	/**
	 * @param coApplicant the coApplicant to set
	 */
	public void setCoApplicants(List<CoApplicant> coApplicants) {
		this.coApplicants = coApplicants;
	}
	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerWrapper [coApplicants=" + coApplicants + ", customer=" + customer + ", customerTickets="
				+ customerTickets + "]";
	}	
	
}

	
