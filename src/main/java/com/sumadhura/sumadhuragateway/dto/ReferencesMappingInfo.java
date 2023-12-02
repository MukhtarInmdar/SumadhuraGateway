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
public class ReferencesMappingInfo implements Serializable {

	private static final long serialVersionUID = -3936019277514258593L;
	private Long typeId;
	private Long type;
	private Long custOtherId;
	private Long referencesMappingId;
}
