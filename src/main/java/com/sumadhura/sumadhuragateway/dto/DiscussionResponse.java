package com.sumadhura.sumadhuragateway.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscussionResponse {

	private String customerName;
	private String employeeName;
	private String customerMessage;
	private String employeeMessage;
	private Date customerTimestamp;
	private Date employeeTimestamp;
	
	public String getCustomerMessage() {
		return customerMessage;
	}
	public void setCustomerMessage(String customerMessage) {
		this.customerMessage = customerMessage;
	}
	public String getEmployeeMessage() {
		return employeeMessage;
	}
	public void setEmployeeMessage(String employeeMessage) {
		this.employeeMessage = employeeMessage;
	}
	public Date getCustomerTimestamp() {
		return customerTimestamp;
	}
	public void setCustomerTimestamp(Date customerTimestamp) {
		this.customerTimestamp = customerTimestamp;
	}
	public Date getEmployeeTimestamp() {
		return employeeTimestamp;
	}
	public void setEmployeeTimestamp(Date employeeTimestamp) {
		this.employeeTimestamp = employeeTimestamp;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	@Override
	public String toString() {
		return "DiscussionResponse [customerName=" + customerName + ", employeeName=" + employeeName
				+ ", customerMessage=" + customerMessage + ", employeeMessage=" + employeeMessage
				+ ", customerTimestamp=" + customerTimestamp + ", employeeTimestamp=" + employeeTimestamp + "]";
	}
}	
	