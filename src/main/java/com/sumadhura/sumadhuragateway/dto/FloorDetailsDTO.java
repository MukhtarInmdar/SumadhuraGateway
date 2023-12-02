package com.sumadhura.sumadhuragateway.dto;


import java.util.Date;

public class FloorDetailsDTO {
	
	private Long floorDetailsId;
	private Long blockDetailsId;
	private Long floorId;
	private Long statusId;
	private String imageLocation;
	private Date createdDate;
	
	
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
	 * @return the floorDetailsId
	 */
	public Long getFloorDetailsId() {
		return floorDetailsId;
	}
	/**
	 * @param floorDetailsId the floorDetailsId to set
	 */
	public void setFloorDetailsId(Long floorDetailsId) {
		this.floorDetailsId = floorDetailsId;
	}
	/**
	 * @return the blockDetailsId
	 */
	public Long getBlockDetailsId() {
		return blockDetailsId;
	}
	/**
	 * @param blockDetailsId the blockDetailsId to set
	 */
	public void setBlockDetailsId(Long blockDetailsId) {
		this.blockDetailsId = blockDetailsId;
	}
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
	 * @return the imageLocation
	 */
	public String getImageLocation() {
		return imageLocation;
	}
	/**
	 * @param imageLocation the imageLocation to set
	 */
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
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
		return "FloorDetailsDTO [floorDetailsId=" + floorDetailsId + ", blockDetailsId=" + blockDetailsId + ", floorId="
				+ floorId + ", statusId=" + statusId + ", imageLocation=" + imageLocation + ", createdDate="
				+ createdDate + "]";
	}
	

}
