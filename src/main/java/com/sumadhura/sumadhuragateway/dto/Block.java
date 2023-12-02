/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;

/**
 * Block class provides Block specific properties.
 *  
 * @author Venkat_Koniki
 * @since 23.03.2019
 * @time 04:27PM
 */

public class Block implements Serializable {
	private static final long serialVersionUID = 7560918470808105465L;
	private Long blockId;
	private String blockName;
	private Site site;
	/**
	 * @return the blockId
	 */
	public Long getBlockId() {
		return blockId;
	}
	/**
	 * @param blockId the blockId to set
	 */
	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}
	/**
	 * @return the blockName
	 */
	public String getBlockName() {
		return blockName;
	}
	/**
	 * @param blockName the blockName to set
	 */
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	/**
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}
	/**
	 * @param site the site to set
	 */
	public void setSite(Site site) {
		this.site = site;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Block [blockId=" + blockId + ", blockName=" + blockName + ", site=" + site + "]";
	}
	
}
