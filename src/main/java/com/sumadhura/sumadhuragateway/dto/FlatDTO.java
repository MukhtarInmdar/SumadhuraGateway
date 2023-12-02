package com.sumadhura.sumadhuragateway.dto;

import java.util.Date;

/**
 * @author VENKAT
 * DATE 04-MAR-2019
 * TIME 05.06 AM
 */

public class FlatDTO {

	private Long flatId;
	private Long floorDetId;
	private String flatNo;
	private String imageLocation;
	private Long status_Id;
	private Date expectedHandOverDate;
	/**
	 * @return the flatId
	 */
	public Long getFlatId() {
		return flatId;
	}
	/**
	 * @param flatId the flatId to set
	 */
	public void setFlatId(Long flatId) {
		this.flatId = flatId;
	}
	/**
	 * @return the floorDetId
	 */
	public Long getFloorDetId() {
		return floorDetId;
	}
	/**
	 * @param floorDetId the floorDetId to set
	 */
	public void setFloorDetId(Long floorDetId) {
		this.floorDetId = floorDetId;
	}
	/**
	 * @return the flatNo
	 */
	public String getFlatNo() {
		return flatNo;
	}
	/**
	 * @param flatNo the flatNo to set
	 */
	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
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
	 * @return the status_Id
	 */
	public Long getStatus_Id() {
		return status_Id;
	}
	/**
	 * @param status_Id the status_Id to set
	 */
	public void setStatus_Id(Long status_Id) {
		this.status_Id = status_Id;
	}
	/**
	 * @return the expectedHandOverDate
	 */
	public Date getExpectedHandOverDate() {
		return expectedHandOverDate;
	}
	/**
	 * @param expectedHandOverDate the expectedHandOverDate to set
	 */
	public void setExpectedHandOverDate(Date expectedHandOverDate) {
		this.expectedHandOverDate = expectedHandOverDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FlatDTO [flatId=" + flatId + ", floorDetId=" + floorDetId + ", flatNo=" + flatNo + ", imageLocation="
				+ imageLocation + ", status_Id=" + status_Id + ", expectedHandOverDate=" + expectedHandOverDate + "]";
	}
	
	
	
}
