/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * OTP class provides otp specific properties.
 * 
 * @author Venkat_Koniki
 * @since 05.04.2019
 * @time 11.50 AM
 */
public class OTP extends Result implements Serializable{

	private static final long serialVersionUID = 9155552155890016782L;
	private Integer otp;
	private Boolean active;
	private Date createdDate;
	private String pancard;
	
	public String getPancard() {
		return pancard;
	}
	public void setPancard(String pancard) {
		this.pancard = pancard;
	}
	/**
	 * @return the otp
	 */
	public Integer getOtp() {
		return otp;
	}
	/**
	 * @param otp the otp to set
	 */
	public void setOtp(Integer otp) {
		this.otp = otp;
	}
	
	/**
	 * @return the active
	 */
	public Boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OTP [otp=" + otp + ", otpStatus=" + active + ", createdDate=" + createdDate + ",pancard="+pancard+"]";
	}
	
}
