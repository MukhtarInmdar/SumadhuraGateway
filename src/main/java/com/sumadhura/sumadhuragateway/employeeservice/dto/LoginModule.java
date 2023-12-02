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
 * LoginModule class provides Employee login module specific fields.
 * 
 * @author Venkat_Koniki
 * @since 26.06.2019
 * @time 12:20PM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class LoginModule implements Serializable {

	private static final long serialVersionUID = 5707692407028521382L;
	private Long moduleId;
	private String moduleName;
	private Long createdBy;
	private Timestamp createdDate;
	private Long modifiedBy;
	private Timestamp modifiedDate;
	private Long statusId;
	private String moduleIcon;
	private List<LoginSubModule> loginSubModules;

}
