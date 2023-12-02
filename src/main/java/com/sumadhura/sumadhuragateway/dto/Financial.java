package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Financial  extends Result implements Serializable{
	private static final long serialVersionUID = -1999102389315863568L;
	private String TotoalAmount;
	private String nextMileStoneAmount;
	private String modificationAmount;
	private Date dueDate;
	private List<MileStone> mileStones;
	private String totalMilestonePaidAmount;
	private String totalMilestoneDueAmount;
	private String totalPenaltyAmount;
	
	private String sumOfTotalPendingPenaltyAmount;
	private String sumOfTotalPenalityPaidAmount;
	private String sumOfInterestWaiverAdjAmount;
	
	private String totalDaysDelayed;
	private Boolean showInterestDetailsButton;
	private String interestCalDateMsg;
	private List<Map<String,String>> financialAmtDetails;
	
}
