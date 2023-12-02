package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MprRequest extends Result implements Serializable{

	private static final long serialVersionUID = -7934624262306848296L;
	private String mprName;
	private String remarks;
	private Long siteId;
	private Long employeeId;
	private String fileLocationType;
	private List<FileInfo> fileInfos;
	private String externalDriveLocation;
	private List<Long> siteIds;
	private int pageNo;
	private int pageSize;
	private Long customerId;
	private Long flatBookingId;
	private List<Long> mprIds;
	private String requestUrl;
	private Long mprId;
	private Long appRegId;
	private String devicetoken;
	private String uuid;
}
