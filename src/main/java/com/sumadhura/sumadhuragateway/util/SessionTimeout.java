/**
 * 
 */
package com.sumadhura.sumadhuragateway.util;
/**
 * This enum is used to set the expiryTime for session.
 * 
 *@author Venkat_Koniki
 * @since 18.03.2019
 * @time 04:19PM
 */
public enum SessionTimeout {

	//LOW(10l), MEDIUM(1000l),HIGH(600000l);
	LOW(10l), MEDIUM(1000l),HIGH(18800000l);

	public Long expiryTime;
	
	private SessionTimeout(Long expiryTime) {
		this.expiryTime = expiryTime;
	}
	
}
