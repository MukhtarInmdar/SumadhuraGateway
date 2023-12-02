package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ReferenceMaster implements Serializable {

	private static final long serialVersionUID = -1061996293789590902L;
	private Long referenceId;
	private String referenceType;
	private Long statusId;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
		
}
