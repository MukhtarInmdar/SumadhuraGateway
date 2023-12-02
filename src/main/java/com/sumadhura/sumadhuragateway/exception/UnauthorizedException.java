/**
 * 
 */
package com.sumadhura.sumadhuragateway.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

/**
 * This class is responsible for Thrown Exception when  unauthorized logins.
 * 
 * @author Venkat_Koniki
 * @since 15.11.2019
 * @time 03:25PM
 */

@ToString
public class UnauthorizedException extends Exception implements Serializable{

private static final long serialVersionUID = -6324132473516246647L;
	
	private List<String> messages;

	public UnauthorizedException() {
		super();
	}

	public UnauthorizedException(String errorMsg) {
		super(errorMsg);
	}
	
	public UnauthorizedException(List<String> errorMsgs) {
		super();
		this.messages = errorMsgs;
	}
	
	public UnauthorizedException(Throwable t) {
		super();
		List<String> messages = new ArrayList<String>();
		messages.add(t.getMessage());
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}
	
}
