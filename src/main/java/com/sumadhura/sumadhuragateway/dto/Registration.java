/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Registration class provides Registration specific properties.
 * 
 * @author Venkat_Koniki
 * @since 26.03.2019
 * @time 04.50 PM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class Registration extends Result implements Serializable{

	private static final long serialVersionUID = -3596608887400027813L;
	private String deviceToken;
	private String pancard;
	private List<String> mobileNos;
	private List<String> emails;
	private List<String> originalEmails;
	private String mobileNo;
	private String uuid;
	private Integer otp;
	private String mpin;
	private Device device;
	private Long custId;
	private String emailId;
	private String nationality;
	private String deviceId;
	private String passport;
	private String pancardSecurityPolices;
}
	