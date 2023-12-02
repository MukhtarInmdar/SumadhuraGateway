package com.sumadhura.sumadhuragateway.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FloorRequest extends Result {
	
	private Long siteId;
	private List<Long> blockDetIds;

}
