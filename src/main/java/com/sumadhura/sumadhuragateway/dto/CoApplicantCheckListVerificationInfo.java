package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CoApplicantCheckListVerificationInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5472743005664468020L;
	private Long  checkListVerfiId;  
	private CheckListInfo  checkListInfo=new CheckListInfo();
	private Long  coApplicantId;
	private Long  checkListDeptMappingId;
	private String CoapplicentPanCard;
	private String CoapplicentPassport;
	private Long custId;
	private Long is_verified;
	private Long flatBookId;
	private String checkListStatus;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	
}
