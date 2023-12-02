package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ReferencesFriendInfo implements Serializable{
	
	private static final long serialVersionUID = -7851652867959562223L;
	private Long referencesFriendId;
	private String referenceFreindsorFamilyName;
	
}
