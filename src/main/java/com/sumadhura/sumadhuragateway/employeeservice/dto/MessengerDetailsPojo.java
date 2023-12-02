/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.dto;


import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * Messenger pojo provides specific properties wto Messenger.
 * 
 * @author Venkat_Koniki
 * @since 16.09.2020
 * @time 11:34PM
 */
@JsonIgnoreProperties
@Data
public class MessengerDetailsPojo{
	private Long messengerId;
	private String messageId;
	private Long createdById;
	private Long createdByType;
	private Long status;
	private String messengerStatus;
	private Long sendTo;
	private Long sendType;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private Timestamp lastChattedDate;
	private Timestamp conversationCreatedDate;
	private String subject;
	private Long flatBookingId;
	private Long custId;
	private String customerName;
	private Long flatId;
	private String flatName;
	private String floorName;
	private String blockName;
	private String siteName;
	private Long messengerConversationId;
	private String createdByName;
	private String messege;
    private Long chatCreatedBy;
	private Long chatCreatedByType;
    private Long viewStatus;
    private String location;
    private Long id;
    private Long levelId;
    private Long siteId;
    private Long deptId;
    private Long employeeId;
    private String employeeName;
    private String departmentName;
	private Long viewCount;
	private List<FileInfo> fileList;
	private String chatType;
	private Long customerViewStatus;
	private List<MessengerDetailsPojo> messengerDetailsPojos;
	private String type;
	private String chatMsgWithoutTags;
	private String requestType;
	private List<ExcelObject> ExcelObject;
	private String flatNo;
	private String appStatus;
	private String viewDetails;
}
