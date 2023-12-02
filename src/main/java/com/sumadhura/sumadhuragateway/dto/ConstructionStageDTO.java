/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.util.Date;

/**
 * @author VENKAT
 *  DATE 28-FEB-2019 
 *  TIME 04.00 PM
 */
public class ConstructionStageDTO {

private Long consStageId;
private String name;
private String description;
private Date createdDate;

/**
 * @return the consStageId
 */
public Long getConsStageId() {
	return consStageId;
}
/**
 * @param consStageId the consStageId to set
 */
public void setConsStageId(Long consStageId) {
	this.consStageId = consStageId;
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
 * @see java.lang.Object#hashCode()
 */
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((consStageId == null) ? 0 : consStageId.hashCode());
	result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	ConstructionStageDTO other = (ConstructionStageDTO) obj;
	if (consStageId == null) {
		if (other.consStageId != null)
			return false;
	} else if (!consStageId.equals(other.consStageId))
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
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	return true;
}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "ConstructionStage [consStageId=" + consStageId + ", name=" + name + ", description=" + description
			+ ", createdDate=" + createdDate + "]";
}


}

