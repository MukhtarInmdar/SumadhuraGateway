/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.util.List;


/**
 * @author VENKAT DATE 08-FEB-2019 TIME 03.39 PM
 */
public class PaymentDetailsWrapper extends Result {

	private List<PaymentDetails> paymentDetailsList;
	private Address address;
	private CustomerWrapper customerWrapper;
	private CustomerPropertySpecificInfo customerPropertySpecificInfo;
	private List<String> paymentDetails;

	/**
	 * @return the paymentDetails
	 */
	public List<String> getPaymentDetails() {
		return paymentDetails;
	}

	/**
	 * @param paymentDetails the paymentDetails to set
	 */
	public void setPaymentDetails(List<String> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	/**
	 * @return the paymentDetailsList
	 */
	public List<PaymentDetails> getPaymentDetailsList() {
		return paymentDetailsList;
	}

	/**
	 * @param paymentDetailsList the paymentDetailsList to set
	 */
	public void setPaymentDetailsList(List<PaymentDetails> paymentDetailsList) {
		this.paymentDetailsList = paymentDetailsList;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the customerWrapper
	 */
	public CustomerWrapper getCustomerWrapper() {
		return customerWrapper;
	}

	/**
	 * @param customerWrapper the customerWrapper to set
	 */
	public void setCustomerWrapper(CustomerWrapper customerWrapper) {
		this.customerWrapper = customerWrapper;
	}

	/**
	 * @return the customerPropertySpecificInfo
	 */
	public CustomerPropertySpecificInfo getCustomerPropertySpecificInfo() {
		return customerPropertySpecificInfo;
	}

	/**
	 * @param customerPropertySpecificInfo the customerPropertySpecificInfo to set
	 */
	public void setCustomerPropertySpecificInfo(CustomerPropertySpecificInfo customerPropertySpecificInfo) {
		this.customerPropertySpecificInfo = customerPropertySpecificInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PaymentDetailsWrapper [paymentDetailsList=" + paymentDetailsList + ", address=" + address
				+ ", customerWrapper=" + customerWrapper + ", customerPropertySpecificInfo="
				+ customerPropertySpecificInfo + "]";
	}

}
