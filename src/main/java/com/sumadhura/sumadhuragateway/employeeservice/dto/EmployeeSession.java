/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * EmployeeSession class provides Employee specific properties.
 *  
 * @author Venkat_Koniki
 * @since 28.06.2019
 * @time 11:49AM
 */
@Data
public class EmployeeSession implements Serializable{
	private static final long serialVersionUID = 3587072270454115612L;
	private LoginResponse loginResponse;
}
