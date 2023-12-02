/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.dto;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * LoginSubModule  class provides Employee login Sub module specific fields.
 * 
 * @author Venkat_Koniki
 * @since 26.06.2019
 * @time 12:20PM
 */

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@ToString
public class LoginSubModule implements Serializable {
	
	private static final long serialVersionUID = 630376205260657332L;
	private Long subModuleId;
	private String subModuleName;
	private Long createdBy;
	private Timestamp createdDate;
	private Long modifiedBy;
	private Timestamp modifiedDate;
	private Long statusId;
	private String pageLink;
	private String demoVediosLink;
	private List<Site> sites;

}
