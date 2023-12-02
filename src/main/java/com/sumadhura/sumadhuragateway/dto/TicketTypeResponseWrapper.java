/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * TicketTypeResponse class provides TicketType specific fields.
 * 
 * @author Venkat_Koniki
 * @since 28.05.2019
 * @time 5:05PM
 */
@Getter
@Setter
@ToString
public class TicketTypeResponseWrapper extends Result implements Serializable {

	private static final long serialVersionUID = 1162527485368865670L;
	private List<TicketTypeResponse>  ticketTypeResponses;
	
}
