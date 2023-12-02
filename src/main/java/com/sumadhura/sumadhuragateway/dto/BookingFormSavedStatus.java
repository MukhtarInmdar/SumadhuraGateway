package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BookingFormSavedStatus implements Serializable {

	private static final long serialVersionUID = -1893303725701810373L;

	private Long leadId;
	private String custName;
	private String status;
	private List<String> error;
	public Long getLeadId() {
		return leadId;
	}
	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getError() {
		return error;
	}
	public void setError(List<String> error) {
		this.error = error;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
