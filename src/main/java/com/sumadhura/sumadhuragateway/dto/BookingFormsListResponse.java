package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingFormsListResponse extends Result implements Serializable {
	
	private static final long serialVersionUID = 6543789364174785005L;

	private List<CustomerPropertyDetailsInfo> applicantInfo;

	public List<CustomerPropertyDetailsInfo> getApplicantInfo() {
		return applicantInfo;
	}

	public void setApplicantInfo(List<CustomerPropertyDetailsInfo> applicantInfo) {
		this.applicantInfo = applicantInfo;
	}

}
