/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;

import lombok.ToString;


/**
 * Department class provides Department specific fields.
 * 
 * @author Venkat_Koniki
 * @since 02.05.2019
 * @time 12:45PM
 */
@ToString
public class Department implements Serializable{

	private static final long serialVersionUID = 6671591144079589517L;
	private Long departmentId;
	private String departmentName;
	private String description;
	private String departmentMail;
	private Long statusId;
	/**
	 * @return the departmentId
	 */
	public Long getDepartmentId() {
		return departmentId;
	}
	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}
	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	 * @return the departmentMail
	 */
	public String getDepartmentMail() {
		return departmentMail;
	}
	/**
	 * @param departmentMail the departmentMail to set
	 */
	public void setDepartmentMail(String departmentMail) {
		this.departmentMail = departmentMail;
	}
	
	
}
