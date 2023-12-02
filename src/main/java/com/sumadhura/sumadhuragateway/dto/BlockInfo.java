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
 * DATE 11-MAR-2019
 * TIME 11.11 AM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BlockInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6448798843095699456L;
	private Long blockId;
	private String name;
	private Date createdDate;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BlockDTO [blockId=" + blockId + ", name=" + name + ", createdDate=" + createdDate + "]";
	}
	
	
}
