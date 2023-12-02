/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author Srivenu
 * DATE 15-June-2019
 * TIME 05.30 PM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AminitiesInfraCostInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7425113448425081260L;
	private Long aminititesInfraCostId;
	private Long aminititesInfraFlatWiseId;
	private Long aminititesInfraId;
	private String aminititesInfraName;
	private Long flatCostId;
	private Double perSqftCost;
	private Double aminititesInfraCost;
	private Double totalCost;
	private Timestamp creationDate;
	private Timestamp modifyDate;
	private Long statusId;
	private Long createdBy;
	private Long modifyBy;
	
}

