package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class CustomerTicketRequest extends Result implements Serializable{

	private static final long serialVersionUID = 5765037254017462889L;
	private long srNo;
	private String deviceToken;
	private String title;
	private String description;
	private Department dept;
	private Long ticketTypeId;
	private Date createdDate;
	private Long ticketId;
	private Long siteId;
	private Long custNo;
	private Long flatNo;
	private String ticketStatus;
	private List<FileInfo> fileInfo; 
	private Long flatBookingId;
	private Long statusId;
	private String requestUrl;
	private Integer resolutionDayTime;
	private Long portNumber;
	
}
