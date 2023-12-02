package com.sumadhura.sumadhuragateway.dto;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class AppointmentDetailsRequest {
	
	private Long type;
	private List<Long> typeIds;
	private List<ApptmtSlotTimesInfo> apptmtSlotTimesInfoList;
	private List<Date> slotDatesList;
	private List<ApptmtSlotTimeRequest> apptmtSlotTimeReqList;
	private Long siteId;
	private String setName;
	
}
