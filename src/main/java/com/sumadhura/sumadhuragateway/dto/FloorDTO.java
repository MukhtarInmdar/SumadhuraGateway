package com.sumadhura.sumadhuragateway.dto;

import java.util.Date;

public class FloorDTO {

	private Long floorId;
	private String floorName;
	private Date createdDate;
	
	
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
		return "FloorDTO [floorId=" + floorId + ", floorName=" + floorName + ", createdDate=" + createdDate + "]";
	}
	
}
