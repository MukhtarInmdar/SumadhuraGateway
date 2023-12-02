/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Result class provides common properties for all services.
 * 
 * @author Venkat_Koniki
 * @since 26.03.2019
 * @time 04.50 PM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeResult extends ResponseAdapter{

	Integer responseCode;
	String sessionKey;
	List<String> errors;
	String description;
		
	public EmployeeResult() {
		super();
	}

	/**
	 * @return the responseCode
	 */
	public Integer getResponseCode() {
		return responseCode;
	}
	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}
	
	/**
	 * @return the sessionKey
	 */
	public String getSessionKey() {
		return sessionKey;
	}
	/**
	 * @param sessionKey the sessionKey to set
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	/**
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EmployeeResult [responseCode=" + responseCode + ", sessionKey=" + sessionKey + ", errors=" + errors
				+ ", description=" + description + "]";
	}

	
}
