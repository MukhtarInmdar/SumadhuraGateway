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
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SiteInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5481243344088056910L;
	private Long siteId;
	private String name;
	private Long cityId;
	private Long statusId;
	private Date createdDate;
	private String imageLocation;
	private Date modifiedDate;
	private String referMessage;
	private Long stateId;
	private Long addressId;
	private String landmarkImage;
	private String projectArea;
	private String noofUnits;
	private String rera;
	private String description;
	private String overviewImage;
	private String masterplanImage;
	private String refererDescription;

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((addressId == null) ? 0 : addressId.hashCode());
	result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
	result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + ((siteId == null) ? 0 : siteId.hashCode());
	result = prime * result + ((imageLocation == null) ? 0 : imageLocation.hashCode());
	result = prime * result + ((landmarkImage == null) ? 0 : landmarkImage.hashCode());
	result = prime * result + ((masterplanImage == null) ? 0 : masterplanImage.hashCode());
	result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((noofUnits == null) ? 0 : noofUnits.hashCode());
	result = prime * result + ((overviewImage == null) ? 0 : overviewImage.hashCode());
	result = prime * result + ((projectArea == null) ? 0 : projectArea.hashCode());
	result = prime * result + ((referMessage == null) ? 0 : referMessage.hashCode());
	result = prime * result + ((rera == null) ? 0 : rera.hashCode());
	result = prime * result + ((stateId == null) ? 0 : stateId.hashCode());
	result = prime * result + ((statusId == null) ? 0 : statusId.hashCode());
	result = prime * result + ((refererDescription == null) ? 0 : refererDescription.hashCode());
	return result;
}
/* (non-Javadoc)
 * @see java.lang.Object#equals(java.lang.Object)
 */
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	SiteInfo other = (SiteInfo) obj;
	if (addressId == null) {
		if (other.addressId != null)
			return false;
	} else if (!addressId.equals(other.addressId))
		return false;
	if (cityId == null) {
		if (other.cityId != null)
			return false;
	} else if (!cityId.equals(other.cityId))
		return false;
	if (createdDate == null) {
		if (other.createdDate != null)
			return false;
	} else if (!createdDate.equals(other.createdDate))
		return false;
	if (description == null) {
		if (other.description != null)
			return false;
	} else if (!description.equals(other.description))
		return false;
	if (siteId == null) {
		if (other.siteId != null)
			return false;
	} else if (!siteId.equals(other.siteId))
		return false;
	if (imageLocation == null) {
		if (other.imageLocation != null)
			return false;
	} else if (!imageLocation.equals(other.imageLocation))
		return false;
	if (landmarkImage == null) {
		if (other.landmarkImage != null)
			return false;
	} else if (!landmarkImage.equals(other.landmarkImage))
		return false;
	if (masterplanImage == null) {
		if (other.masterplanImage != null)
			return false;
	} else if (!masterplanImage.equals(other.masterplanImage))
		return false;
	if (modifiedDate == null) {
		if (other.modifiedDate != null)
			return false;
	} else if (!modifiedDate.equals(other.modifiedDate))
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (noofUnits == null) {
		if (other.noofUnits != null)
			return false;
	} else if (!noofUnits.equals(other.noofUnits))
		return false;
	if (overviewImage == null) {
		if (other.overviewImage != null)
			return false;
	} else if (!overviewImage.equals(other.overviewImage))
		return false;
	if (projectArea == null) {
		if (other.projectArea != null)
			return false;
	} else if (!projectArea.equals(other.projectArea))
		return false;
	if (referMessage == null) {
		if (other.referMessage != null)
			return false;
	} else if (!referMessage.equals(other.referMessage))
		return false;
	if (rera == null) {
		if (other.rera != null)
			return false;
	} else if (!rera.equals(other.rera))
		return false;
	if (stateId == null) {
		if (other.stateId != null)
			return false;
	} else if (!stateId.equals(other.stateId))
		return false;
	if (statusId == null) {
		if (other.statusId != null)
			return false;
	} else if (!statusId.equals(other.statusId))
		return false;
	if (refererDescription == null) {
		if (other.refererDescription != null)
			return false;
	} else if (!refererDescription.equals(other.refererDescription))
		return false;
	return true;
}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "SiteDTO [siteId=" + siteId + ", name=" + name + ", cityId=" + cityId + ", statusId=" + statusId + ", createdDate="
			+ createdDate + ", imageLocation=" + imageLocation + ", modifiedDate=" + modifiedDate + ", refererMessage="
			+ referMessage + ", stateId=" + stateId + ", addressId=" + addressId + ", landmarkImg=" + landmarkImage
			+ ", projectArea=" + projectArea + ", noofUnits=" + noofUnits + ", rera=" + rera + ", description="
			+ description + ", overviewImage=" + overviewImage + ", masterplanImage=" + masterplanImage + ", refererDescription="+ refererDescription +"]";
}	
	

}


