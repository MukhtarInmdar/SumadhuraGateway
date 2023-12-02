/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * CustomerAddressPojo class provides ADDRESS Table specific fields.
 * 
 * @author Venkat_Koniki
 * @since 06.05.2019
 * @time 05:52PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WorkFunctionInfo implements Serializable {
	
	private static final long serialVersionUID = -2069363871698060402L;
	private Long workFunctionId;
	private String workFunctionName;
	private String ifOtherworkFunctionName;
	
}
