package com.sumadhura.sumadhuragateway.dto;

import java.util.Date;

/**
 * @author VENKAT
 * DATE 11-MAR-2019
 * TIME 11.11 AM
 */
public class BlockDTO {

	private Long blockId;
	private String name;
	private Date createdDate;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BlockDTO [blockId=" + blockId + ", name=" + name + ", createdDate=" + createdDate + "]";
	}
	
	
}

