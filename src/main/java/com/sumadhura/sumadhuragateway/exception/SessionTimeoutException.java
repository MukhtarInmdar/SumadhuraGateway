/**
 * 
 */
package com.sumadhura.sumadhuragateway.exception;

import java.io.Serializable;

/**
 * This class is responsible for Thrown when receiving an session TimeOut situation.
 * 
 * @author Venkat_Koniki
 * @since 08.04.2019
 * @time 10:25AM
 */

public class SessionTimeoutException extends Exception implements Serializable {
      
	
	private static final long serialVersionUID = 3495006359451996600L;

	public SessionTimeoutException() {
		super();
	}
	
	public SessionTimeoutException(String errorMsg) {
		super(errorMsg);
	}
	
}
