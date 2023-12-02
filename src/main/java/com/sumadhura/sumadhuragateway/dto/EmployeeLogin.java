/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * EmployeeLogin  class provides Employee login specific fields.
 * 
 * @author Venkat_Koniki
 * @since 22.04.2019
 * @time 12:20PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class EmployeeLogin extends Result implements Serializable {

	private static final long serialVersionUID = 4587857093810452548L;
	private String username;
	private String password;
	private String requestUrl;

}
