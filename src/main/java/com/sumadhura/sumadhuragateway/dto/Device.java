/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Device class provides Device specific properties.
 *  
 * @author Venkat_Koniki
 * @since 23.03.2019
 * @time 04:53PM
 */
public class Device extends Result implements Serializable {

	private static final long serialVersionUID = -5734398400705769564L;
	private Integer id;
	private String deviceToken;
	private String deviceModel;
	private String appVersion;
	private String osType;
	private String osVersion;
	private String macId;
	private String imeiNo;
	private String uuid;
	private String serialNo;
	private String deviceStatus;
	private String userAgent;
	private String userXid;
	private Date createdDate;
	private Date updatedDate;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the deviceToken
	 */
	public String getDeviceToken() {
		return deviceToken;
	}
	/**
	 * @param deviceToken the deviceToken to set
	 */
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	/**
	 * @return the deviceModel
	 */
	public String getDeviceModel() {
		return deviceModel;
	}
	/**
	 * @param deviceModel the deviceModel to set
	 */
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	/**
	 * @return the appVersion
	 */
	public String getAppVersion() {
		return appVersion;
	}
	/**
	 * @param appVersion the appVersion to set
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	/**
	 * @return the osType
	 */
	public String getOsType() {
		return osType;
	}
	/**
	 * @param osType the osType to set
	 */
	public void setOsType(String osType) {
		this.osType = osType;
	}
	/**
	 * @return the osVersion
	 */
	public String getOsVersion() {
		return osVersion;
	}
	/**
	 * @param osVersion the osVersion to set
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	/**
	 * @return the macId
	 */
	public String getMacId() {
		return macId;
	}
	/**
	 * @param macId the macId to set
	 */
	public void setMacId(String macId) {
		this.macId = macId;
	}
	/**
	 * @return the imeiNo
	 */
	public String getImeiNo() {
		return imeiNo;
	}
	/**
	 * @param imeiNo the imeiNo to set
	 */
	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}
	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}
	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	/**
	 * @return the deviceStatus
	 */
	public String getDeviceStatus() {
		return deviceStatus;
	}
	/**
	 * @param deviceStatus the deviceStatus to set
	 */
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}
	/**
	 * @param userAgent the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	/**
	 * @return the userXid
	 */
	public String getUserXid() {
		return userXid;
	}
	/**
	 * @param userXid the userXid to set
	 */
	public void setUserXid(String userXid) {
		this.userXid = userXid;
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
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Device [id=" + id + ", deviceToken=" + deviceToken + ", deviceModel=" + deviceModel + ", appVersion="
				+ appVersion + ", osType=" + osType + ", osVersion=" + osVersion + ", macId=" + macId + ", imeiNo="
				+ imeiNo + ", uuid=" + uuid + ", serialNo=" + serialNo + ", deviceStatus=" + deviceStatus
				+ ", userAgent=" + userAgent + ", userXid=" + userXid + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + "]";
	}
	
}
