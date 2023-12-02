package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sumadhura.sumadhuragateway.dto.Result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessengerResponce extends Result{
	private List<MessengerDetailsPojo> messengerDetailsPojos; 
	private List<EmployeeDetailsPojo> employeeDetailsPojos;
	private List<MessengerDetailsPojo> departmentwisemessengerDetailsPojos;
	private boolean isEditable;
	private Long unviewedChatCount;
	private Long messengerId;
	private List<Long> messengerIds;
}
