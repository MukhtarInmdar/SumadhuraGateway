/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sumadhura.sumadhuragateway.dto.Result;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown=true)
public class NotificationRequest extends Result implements Serializable {

	private static final long serialVersionUID = 1301346993582872423L;
	
	private Long id;
	
	private String message;
	
	//@Phone
	private String description;
	
	private String imgLoc;
	
	private Long employeeId;
	
	private List<String> types;
	
	private Long typeOf;
	
	private List<Long> stateIds;
	
	private List<FileInfo> fileInfos;

	private List<FileInfo> linkFiles;
	
	private String notificationText;
	
	private String linkFileLoc;
	
	/* @NotNull */
	private String osType;
	
	private int pageNo;
	
	private int pageSize;
	
	private List<Long> empSiteList;
	
	private Timestamp startDate;
	
	private Timestamp endDate;
	
	private String comments;
	
	private String action;
	
	private String notificationType;
	
	private String requestUrl;
	
	private Long flatId;
	
	private List<Long> typeIds;
	private List<Long> siteIds;
	
	private List<Long> blockIds;
	
	private Long flatCount;
	private List<String> osTypes;
	
    private String requestAction;
	
	private String requestPurpose;
	
	private String sendTypeName;
	
	private Object siteobjList;
	
	private Date sentDate;
	
	private String strcreatedDate;
	private String strsentDate;
		
}
