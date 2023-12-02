/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.List;

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
public class CoApplicentDetailsInfo implements Serializable {
	
	private static final long serialVersionUID = -8274465695394806701L;
	private Co_ApplicantInfo co_ApplicantInfo ;
	private CoApplicentBookingInfo coApplicentBookingInfo ;
	private List<AddressInfo> addressInfos ;
	private ProfessionalInfo professionalInfo ;
}
