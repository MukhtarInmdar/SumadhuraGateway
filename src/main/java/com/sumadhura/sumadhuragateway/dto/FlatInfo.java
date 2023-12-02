/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author VENKAT
 * DATE 04-MAR-2019
 * TIME 05.06 AM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class FlatInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 521318925197083744L;
	private Long flatId;
	private Long floorDetId;
	private String flatNo;
	private String imageLocation;
	private Long status_Id;
	private Date expectedHandOverDate;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FlatDTO [flatId=" + flatId + ", floorDetId=" + floorDetId + ", flatNo=" + flatNo + ", imageLocation="
				+ imageLocation + ", status_Id=" + status_Id + ", expectedHandOverDate=" + expectedHandOverDate + "]";
	}
	
	
	
}
