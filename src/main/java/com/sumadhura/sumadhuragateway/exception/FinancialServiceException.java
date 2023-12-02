package com.sumadhura.sumadhuragateway.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FinancialServiceException extends Exception implements Serializable{
 
	private static final long serialVersionUID = 6724483167103839500L;

	private List<String> messages;
	private String errorMsg;

	public FinancialServiceException() {
		super();
	}
	public FinancialServiceException(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}
	
	public FinancialServiceException(List<String> errorMsgs) {
		super();
		this.messages = errorMsgs;
	}
	
	public FinancialServiceException(Throwable t) {
		super();
		List<String> messages = new ArrayList<String>();
		messages.add(t.getMessage());
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
