package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BookingFormSavedStatusResponse extends Result implements Serializable {
	
	private static final long serialVersionUID = -7045099772822493618L;
	
	private List<BookingFormSavedStatus> bookingFormSavedStatuses;

	public List<BookingFormSavedStatus> getBookingFormSavedStatuses() {
		return bookingFormSavedStatuses;
	}

	public void setBookingFormSavedStatuses(List<BookingFormSavedStatus> bookingFormSavedStatuses) {
		this.bookingFormSavedStatuses = bookingFormSavedStatuses;
	}
	
}
