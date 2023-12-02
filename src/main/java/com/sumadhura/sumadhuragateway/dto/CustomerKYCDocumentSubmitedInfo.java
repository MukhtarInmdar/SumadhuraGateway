package com.sumadhura.sumadhuragateway.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author Srivenu
 * DATE 30-MAY-2019
 * TIME 11:11 AM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CustomerKYCDocumentSubmitedInfo {

	private Long  documentId;
	private String  docName;
	private Long flatBookId;
	private Long custBookInfoId;
	private Long submittedDocId;
	private Long empId;
	private Long statusId;
	private String status;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	
}
