/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Site class provides site specific properties.
 *  
 * @author Venkat_Koniki
 * @since 23.03.2019
 * @time 04:27PM
 */
public class Site implements Serializable {
	private static final long serialVersionUID = 8170854815917014561L;
	private Long id;
	private String name;
	private Long cityid;
	private Long statusId;
	private Date createdDate;
	private String imgLoc;
	private Date modifiedDate;
	private String refererMessage;
	private Long stateId;
	private Long addressId;
	private String landmarkImg;
	private String projecrArea;
	private String noofUnits;
	private String rera;
	private String description;
	private String overviewImages;
	private String masterplanImage;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the cityid
	 */
	public Long getCityid() {
		return cityid;
	}
	/**
	 * @param cityid the cityid to set
	 */
	public void setCityid(Long cityid) {
		this.cityid = cityid;
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
	 * @return the refererMessage
	 */
	public String getRefererMessage() {
		return refererMessage;
	}
	/**
	 * @param refererMessage the refererMessage to set
	 */
	public void setRefererMessage(String refererMessage) {
		this.refererMessage = refererMessage;
	}
	/**
	 * @return the stateId
	 */
	public Long getStateId() {
		return stateId;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	/**
	 * @return the addressId
	 */
	public Long getAddressId() {
		return addressId;
	}
	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	/**
	 * @return the landmarkImg
	 */
	public String getLandmarkImg() {
		return landmarkImg;
	}
	/**
	 * @param landmarkImg the landmarkImg to set
	 */
	public void setLandmarkImg(String landmarkImg) {
		this.landmarkImg = landmarkImg;
	}
	/**
	 * @return the projecrArea
	 */
	public String getProjecrArea() {
		return projecrArea;
	}
	/**
	 * @param projecrArea the projecrArea to set
	 */
	public void setProjecrArea(String projecrArea) {
		this.projecrArea = projecrArea;
	}
	/**
	 * @return the noofUnits
	 */
	public String getNoofUnits() {
		return noofUnits;
	}
	/**
	 * @param noofUnits the noofUnits to set
	 */
	public void setNoofUnits(String noofUnits) {
		this.noofUnits = noofUnits;
	}
	/**
	 * @return the rera
	 */
	public String getRera() {
		return rera;
	}
	/**
	 * @param rera the rera to set
	 */
	public void setRera(String rera) {
		this.rera = rera;
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
	 * @return the overviewImages
	 */
	public String getOverviewImages() {
		return overviewImages;
	}
	/**
	 * @param overviewImages the overviewImages to set
	 */
	public void setOverviewImages(String overviewImages) {
		this.overviewImages = overviewImages;
	}
	/**
	 * @return the masterplanImage
	 */
	public String getMasterplanImage() {
		return masterplanImage;
	}
	/**
	 * @param masterplanImage the masterplanImage to set
	 */
	public void setMasterplanImage(String masterplanImage) {
		this.masterplanImage = masterplanImage;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Site [id=" + id + ", name=" + name + ", cityid=" + cityid + ", statusId=" + statusId + ", createdDate="
				+ createdDate + ", imgLoc=" + imgLoc + ", modifiedDate=" + modifiedDate + ", refererMessage="
				+ refererMessage + ", stateId=" + stateId + ", addressId=" + addressId + ", landmarkImg=" + landmarkImg
				+ ", projecrArea=" + projecrArea + ", noofUnits=" + noofUnits + ", rera=" + rera + ", description="
				+ description + ", overviewImages=" + overviewImages + ", masterplanImage=" + masterplanImage + "]";
	}

}
