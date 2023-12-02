package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sumadhura.sumadhuragateway.dto.Result;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper=true)
public class DropDownRequest extends Result {
	
	private List<Long> ids;
	private Long loginUserId;
	private List<Long> siteIds;
	private List<Long> blockDetIds;
	private List<Long> floorDetIds;
	private List<String> flatSeriesList;
	private List<Long> sbuaList;
	private List<String> facingList;
	private List<String> bhkTypeList;
	private String requestUrl;
}
