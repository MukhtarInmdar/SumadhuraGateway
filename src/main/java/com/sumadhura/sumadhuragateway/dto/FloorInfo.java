package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class FloorInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7018925312089289699L;
	private Long floorId;
	private String floorName;
	private Timestamp createdDate;
	
}
