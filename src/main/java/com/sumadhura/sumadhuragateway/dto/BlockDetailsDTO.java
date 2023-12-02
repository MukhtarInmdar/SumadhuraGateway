package com.sumadhura.sumadhuragateway.dto;

import java.util.Date;

/**
 * @author VENKAT
 * DATE 11-MAR-2019
 * TIME 11.11 AM
 */

public class BlockDetailsDTO {

	private Long blockDetId;
	private String imgLoc;
    private Long siteId;
    private Long blockId;
    private Long statusId;
	private Date createdDate;
    private Date modifiedDate;
	/**
	 * @return the blockDetId
	 */
	public Long getBlockDetId() {
		return blockDetId;
	}
	/**
	 * @param blockDetId the blockDetId to set
	 */
	public void setBlockDetId(Long blockDetId) {
		this.blockDetId = blockDetId;
	}
	/**
	 * @return the imgLoc
	 */
	public String getImgLoc() {
		return imgLoc;
	}
	/**
	 * @param imgLoc the imgLoc to set
	 */
	public void setImgLoc(String imgLoc) {
		this.imgLoc = imgLoc;
	}
	/**
	 * @return the siteId
	 */
	public Long getSiteId() {
		return siteId;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
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
	 * @return the statusId
	 */
	public Long getStatusId() {
		return statusId;
	}
	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
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
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BlockDetailsDTO [blockDetId=" + blockDetId + ", imgLoc=" + imgLoc + ", siteId=" + siteId + ", blockId="
				+ blockId + ", statusId=" + statusId + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ "]";
	}
    
	
}

