package com.sumadhura.sumadhuragateway.dto;

import java.util.Date;

/**
 * @author VENKAT DATE 07-FEB-2019 TIME 05.30 PM
 */

public class FlatRegistrationDTO {

	private Long flatRegId;
	private Long flatBookingId;
	private Long customerId;
	private Long flatId;
	private Date regDate;
	private Long paymentId;
	private Long statusId;
	private Long contInfoId;
	private Date createDate;

	/**
	 * @return the statusId
	 */
	public Long getStatusId() {
		return statusId;
	}
	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the flatRegId
	 */
	public Long getFlatRegId() {
		return flatRegId;
	}
	/**
	 * @return the flatBookingId
	 */
	public Long getFlatBookingId() {
		return flatBookingId;
	}
	/**
	 * @param flatBookingId the flatBookingId to set
	 */
	public void setFlatBookingId(Long flatBookingId) {
		this.flatBookingId = flatBookingId;
	}
	/**
	 * @param flatRegId the flatRegId to set
	 */
	public void setFlatRegId(Long flatRegId) {
		this.flatRegId = flatRegId;
	}

	/**
	 * @return the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the flatId
	 */
	public Long getFlatId() {
		return flatId;
	}

	/**
	 * @param flatId the flatId to set
	 */
	public void setFlatId(Long flatId) {
		this.flatId = flatId;
	}

	/**
	 * @return the regDate
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the paymentId
	 */
	public Long getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the contInfoId
	 */
	public Long getContInfoId() {
		return contInfoId;
	}
	/**
	 * @param contInfoId the contInfoId to set
	 */
	public void setContInfoId(Long contInfoId) {
		this.contInfoId = contInfoId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FlatRegistrationDTO [flatRegId=" + flatRegId + ", flatBookingId=" + flatBookingId + ", customerId="
				+ customerId + ", flatId=" + flatId + ", regDate=" + regDate + ", paymentId=" + paymentId
				+ ", statusId=" + statusId + ", contInfoId=" + contInfoId + ", createDate=" + createDate + "]";
	}
	

}
