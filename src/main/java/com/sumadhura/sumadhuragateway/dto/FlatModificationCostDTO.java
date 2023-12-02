package com.sumadhura.sumadhuragateway.dto;


import java.util.Date;

/**
 * @author VENKAT
 * DATE 13-MAR-2019
 * TIME 05.51 PM
 */

public class FlatModificationCostDTO {

	private Long flatModificationCostId;
	private String name;
	private String description;
	private Double cost;
	private Date createdDate;
	private Long createdBy;
	private Date modifiedDate;
	private Long modifiedBy;
	private Long flatCostId;
	private String imgLoc;
	/**
	 * @return the flatModificationCostId
	 */
	public Long getFlatModificationCostId() {
		return flatModificationCostId;
	}
	/**
	 * @param flatModificationCostId the flatModificationCostId to set
	 */
	public void setFlatModificationCostId(Long flatModificationCostId) {
		this.flatModificationCostId = flatModificationCostId;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the cost
	 */
	public Double getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(Double cost) {
		this.cost = cost;
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
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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
	/**
	 * @return the modifiedBy
	 */
	public Long getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the flatCostId
	 */
	public Long getFlatCostId() {
		return flatCostId;
	}
	/**
	 * @param flatCostId the flatCostId to set
	 */
	public void setFlatCostId(Long flatCostId) {
		this.flatCostId = flatCostId;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FlatModificationCostDTO [flatModificationCostId=" + flatModificationCostId + ", name=" + name
				+ ", description=" + description + ", cost=" + cost + ", createdDate=" + createdDate + ", createdBy="
				+ createdBy + ", modifiedDate=" + modifiedDate + ", modifiedBy=" + modifiedBy + ", flatCostId="
				+ flatCostId + "]";
	}
	
}

