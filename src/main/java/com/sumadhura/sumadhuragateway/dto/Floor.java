/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;

/**
 * Floor class provides Floor specific properties.
 *  
 * @author Venkat_Koniki
 * @since 23.03.2019
 * @time 04:27PM
 */
public class Floor implements Serializable{

	private static final long serialVersionUID = 6217999709402868579L;
	private Long floorId;
	private String floorName;
	private Block block;
	/**
	 * @return the floorId
	 */
	public Long getFloorId() {
		return floorId;
	}
	/**
	 * @param floorId the floorId to set
	 */
	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}
	/**
	 * @return the floorName
	 */
	public String getFloorName() {
		return floorName;
	}
	/**
	 * @param floorName the floorName to set
	 */
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	/**
	 * @return the block
	 */
	public Block getBlock() {
		return block;
	}
	/**
	 * @param block the block to set
	 */
	public void setBlock(Block block) {
		this.block = block;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Floor [floorId=" + floorId + ", floorName=" + floorName + ", block=" + block + "]";
	}
	
	
}
