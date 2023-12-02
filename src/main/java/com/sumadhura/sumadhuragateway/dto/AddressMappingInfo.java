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
 * @author Srivenu Aare
 * @since 31.05.2019
 * @time 01:52PM
 */


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AddressMappingInfo implements Serializable {
	
	private static final long serialVersionUID = 7666041682754617517L;
	private Long addressMappingTypeId;
	private Long typeId;
	private Long type;
	private String metaType;
	private String addressType;
	private Long addressId;
	
}
