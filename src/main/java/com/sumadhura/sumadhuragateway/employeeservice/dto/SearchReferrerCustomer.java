package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SearchReferrerCustomer extends CommonRequest {

	private String referrerName;

	private String refrenceId;

	private Long custId;

	private String customerName;

	private String customerFlatNo;

	private String siteId;

	private Long empId;

	private List<Long> siteIds;

	private int pageNo;

	private int pageSize;
	
	private Long flatBookingId;
	
	private String requestUrl;
	
	private List<Long> blockIds;
	private List<Long> floorIds;
	private List<Long> flatIds;

}
