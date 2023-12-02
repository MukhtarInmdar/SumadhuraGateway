package com.sumadhura.sumadhuragateway.dto;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class DemandNoteInfo {

	private String demandNoteName;
	private String demandNoteUrl;
	private Date demandNoteCreatedDate;
	private Timestamp createdDate;
	private Double interestAmount;
	private String interestAmountWithFormat;
	private String docName;
}
