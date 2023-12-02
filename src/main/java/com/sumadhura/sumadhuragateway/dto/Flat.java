/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * Flat class provides Flat specific properties.
 * @author Venkat_Koniki
 * @since 23.03.2019
 * @time 12:06PM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class Flat implements Serializable{

	private static final long serialVersionUID = 1032474455095941010L;
	private Long flatId;
	private String flatNo;
	private Floor floor;
	private String bhk;
	private String bhkWithSbua;
	private Long sitePropertyId;
	
}
