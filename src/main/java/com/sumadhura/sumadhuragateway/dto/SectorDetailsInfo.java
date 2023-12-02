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
public class SectorDetailsInfo implements Serializable {
	
	private static final long serialVersionUID = 2935437746931854119L;
	private Long workSectorId;
	private String workSectorName;
	private String ifOtherWorkSectorName;
	
}
