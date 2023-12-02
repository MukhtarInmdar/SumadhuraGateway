package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationResponse {

	private Long id;
	private String name;
	private String message;
	private String description;
	private Date recivedDate;
	private Long typeOf;
	private Long genericid;
	private Long empId;
	private String image;
	private List<String> imageList;
	private Date createdDate;
	private Date modifiedDate;
	private Long statusId;
	private String linkFileLoc;
	private String notificationText;
	private boolean isViewed;

	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getRecivedDate() {
		return recivedDate;
	}
	public void setRecivedDate(Date recivedDate) {
		this.recivedDate = recivedDate;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<String> getImageList() {
		return imageList;
	}
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
	/**
	 * @return the typeOf
	 */
	public Long getTypeOf() {
		return typeOf;
	}
	/**
	 * @param typeOf the typeOf to set
	 */
	public void setTypeOf(Long typeOf) {
		this.typeOf = typeOf;
	}
	/**
	 * @return the genericid
	 */
	public Long getGenericid() {
		return genericid;
	}
	/**
	 * @param genericid the genericid to set
	 */
	public void setGenericid(Long genericid) {
		this.genericid = genericid;
	}
	/**
	 * @return the empId
	 */
	public Long getEmpId() {
		return empId;
	}
	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(Long empId) {
		this.empId = empId;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NotificationResponse [id=" + id + ", message=" + message + ", description=" + description
				+ ", recivedDate=" + recivedDate + ", typeOf=" + typeOf + ", genericid=" + genericid + ", empId="
				+ empId + ", image=" + image + ", imageList=" + imageList + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", statusId=" + statusId + "]";
	}
	/**
	 * @return the linkFileLoc
	 */
	public String getLinkFileLoc() {
		return linkFileLoc;
	}
	/**
	 * @param linkFileLoc the linkFileLoc to set
	 */
	public void setLinkFileLoc(String linkFileLoc) {
		this.linkFileLoc = linkFileLoc;
	}
	/**
	 * @return the notificationText
	 */
	public String getNotificationText() {
		return notificationText;
	}
	/**
	 * @param notificationText the notificationText to set
	 */
	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}
	
	public boolean isViewed() {
		return isViewed;
	}
	public void setViewed(boolean isViewed) {
		this.isViewed = isViewed;
	}
}
