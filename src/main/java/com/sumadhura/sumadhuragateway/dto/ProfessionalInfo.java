/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;




/**
 * CustomerAddressPojo class provides ADDRESS Table specific fields.
 * 
 * @author Srivenu
 * @since 30.05.2019
 * @time 11:49AM
 */


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ProfessionalInfo implements Serializable {
	
	private static final long serialVersionUID = -1219755741229452903L;
	private String designation;
	private String nameOfOrganization;
	//private String addfressfOrganization;
	private String addressOfOrganization;
	private String officeNumber;
	private String officeEmailId;
	private Long custProffisionalId;
	private Long statusId;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private OraganizationDetails oraganizationDetails;
	private SectorDetailsInfo sectorDetailsInfo;
	private WorkFunctionInfo workFunctionInfo;
	private String yearsOfExperience;
	
}
