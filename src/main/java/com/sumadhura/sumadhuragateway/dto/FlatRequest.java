package com.sumadhura.sumadhuragateway.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FlatRequest extends Result {
	
	private Long siteId;
	private List<Long> blockDetIds;
	private List<Long> floorDetIds;
	private Timestamp fromDate;
	private Timestamp toDate;
	
}
