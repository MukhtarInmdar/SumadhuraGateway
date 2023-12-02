package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//@Setter
//@Getter
@EqualsAndHashCode(callSuper=false)
public class SiteLevelNotifyRequestDTO extends NotificationRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6899127449843450218L;
	/*
	 * @NotNull
	 * 
	 * @NotEmpty
	 */
	private List<Long> siteIds=new ArrayList<>();
	
	private List<Long> blockIds=new ArrayList<>();
	
	private List<Long> floorIds=new ArrayList<>();
	
	private List<Long> flatIds=new ArrayList<>();
	
    private List<Long> sbuaList;
	
	private List<String> facingList;
	
	private List<String> bhkTypeList;
	
	private List<String> flatSeriesList;
	
	private List<Long> selectedFlatIds=new ArrayList<>();
	
}
