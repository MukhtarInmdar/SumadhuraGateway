/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 * TicketTypeResponse class provides TicketType specific fields.
 * 
 * @author Venkat_Koniki
 * @since 28.05.2019
 * @time 4:51PM
 */

@Getter
@Setter
public class TicketTypeResponse {
	private Long ticketTypeId;
	private String ticketType;
	private String ticketMainType;
	private String description;
	private Float resolutionDayTime;
	private Long statusId;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private List<TicketTypeResponse> ticketTypeResponseList;
}
