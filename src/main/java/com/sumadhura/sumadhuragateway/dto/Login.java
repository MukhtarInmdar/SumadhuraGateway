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
 * @author VENKAT
 * DATE 05-FEB-2019
 * TIME 02.57 PM
 */

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Login extends Result implements Serializable{

	private static final long serialVersionUID = 5187548645660379033L;
	private Long appRegId;
	private String deviceId;
	private String uuid;
	private String serialNo;
	private Long flatId;
	private Long custId;
	private List<Long> flatIds;
	private List<Flat> flats;
	private String username;
	private String password;
	private Long siteId;
	
}
