package com.sumadhura.sumadhuragateway.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for Thrown when receiving an Incorrect OTP situation.
 * 
 * @author Venkat_Koniki
 * @since 08.04.2019
 * @time 10:25AM
 */
public class IncorrectOtpException extends Exception implements Serializable {

	private static final long serialVersionUID = -6324132473516246647L;
	
	private List<String> messages;

	public IncorrectOtpException() {
		super();
	}

	public IncorrectOtpException(String errorMsg) {
		super(errorMsg);
	}
	
	public IncorrectOtpException(List<String> errorMsgs) {
		super();
		this.messages = errorMsgs;
	}
	
	public IncorrectOtpException(Throwable t) {
		super();
		List<String> messages = new ArrayList<String>();
		messages.add(t.getMessage());
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}

}
