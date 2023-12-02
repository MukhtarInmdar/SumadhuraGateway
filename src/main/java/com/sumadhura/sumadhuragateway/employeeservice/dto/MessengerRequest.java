/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.enums.ServiceRequestEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Venkat_Koniki
 * @since 07.10.2020
 * @time 03:34PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class MessengerRequest extends Result implements Serializable{

	private static final long serialVersionUID = 4171222515534574256L;
	private List<Long> siteIds;
	private List<Long> blockIds;
	private List<Long> floorIds;
	private List<Long> flatIds;
	private Timestamp startDate;
	private Timestamp endDate;
	private String requestUrl;
	private Long flatId;
	private String subject;
	private String message;
	private List<FileInfo> files;
	private Long employeeId;
	private Long flatBookingId;
	private Long messengerId;
	private Long createdById;
	private Long createdByType;
	private Long sendType;
	private Long sendTo;
	private List<Long> employeeIds;
	private Long messengerConversationId;
	private Map<Long, Map<Long, Long>> reciepientMap;
	private Map<Long, Map<Long, Long>> managementMap;
	private Long recipientType;
	private Long recipientId;
	private Boolean flag;
	private List<Long> deptIds;
	private String type;
	private Set<Long> conversationIds;
	private String deviceToken;
	private List<String> googleDriveLinks;
	private List<Long> messengerIds;
	private ServiceRequestEnum requestEnum;
	private String chatMsgWithoutTags;
	private Long conversationId;
	private String requestType;
	private List<ExcelObject> ExcelObject;
	private String flatNo;
	private String appStatus;
	private String subModuleName;
}
