/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sumadhura.sumadhuragateway.dto.Result;

/**
 * @author VENKAT
 * DATE 12-MAR-2019
 * TIME 01.57 PM
 */

@JsonIgnoreProperties
public class NotificationWrapper extends Result implements Serializable{

	private static final long serialVersionUID = 3039450008844703632L;
	private List<NotificationResponse> notificationResponses;
	private Boolean isNotificationResponsesAvailable;

	/**
	 * @return the notificationResponses
	 */
	public List<NotificationResponse> getNotificationResponses() {
		return notificationResponses;
	}

	/**
	 * @param notificationResponses the notificationResponses to set
	 */
	public void setNotificationResponses(List<NotificationResponse> notificationResponses) {
		this.notificationResponses = notificationResponses;
	}

	public Boolean getIsNotificationResponsesAvailable() {
		return isNotificationResponsesAvailable;
	}

	public void setIsNotificationResponsesAvailable(Boolean isNotificationResponsesAvailable) {
		this.isNotificationResponsesAvailable = isNotificationResponsesAvailable;
	}

	@Override
	public String toString() {
		return "NotificationWrapper [notificationResponses=" + notificationResponses
				+ ", isNotificationResponsesAvailable=" + isNotificationResponsesAvailable + "]";
	}
	
}
