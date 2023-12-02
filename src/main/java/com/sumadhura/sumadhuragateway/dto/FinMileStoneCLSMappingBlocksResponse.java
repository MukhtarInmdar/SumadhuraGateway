package com.sumadhura.sumadhuragateway.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class FinMileStoneCLSMappingBlocksResponse {
	private Long projectMileStoneClsMappingBlocksId;
	private Long blockId;
	private String blockName;
	private Long finMilestoneClassifidesId;
	private String createdBy;
	private Timestamp createdDate;
	private String modifiedBy;
	private Timestamp modifiedDate;
	List<FlatInfo> flatInfos;
}
