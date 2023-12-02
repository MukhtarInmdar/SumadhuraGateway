package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarParkingAllotmentRequest extends Result implements Serializable{

	private static final long serialVersionUID = 9177909891115650487L;
	
	private String requestUrl;
	private Long employeeId;
	private Long siteId;
	private Long basementId;
	private Long slotId;
	private Long flatBookId;
	private List<CarParkingAllotmentBasementInfo> carParkingAllotmentBasementInfoList;
	private String slotStatusName;
	private Long allotmentId;
	private String comments;
}
