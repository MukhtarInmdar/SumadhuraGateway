package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class FlatBookingInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2347242304619373716L;
	private FlatInfo flatInfo;
	private FloorInfo floorInfo;
	private BlockInfo blockInfo;
	private SiteInfo siteInfo;
	//private String SBUA;
	private Double carpetArea;
	//private String isEOAApplicable;
	//private String EOASeqNumber;
	private String facing;
	private Double sbua;
	private Double uds;
	private String eoiApplicable;
	private String eoiSequenceNumber;
	//private FlatCost FlatCostDTO;
	private FlatCostInfo flatCost ;
	private List<AminitiesInfraCostInfo> AminitiesInfraCostInfo ;
	private Long CustomerId;
	private Long flatBookingId;
    private Long flatId;
	private Timestamp registrationDate;
	private Long paymentId;
	private Long statusId;
	private Timestamp createdDate;
	private String bookingRecieptFront;
	private String bookingRecieptBack;
	private Timestamp bookingDate;
	// ACP
	private Timestamp agreementDate;
	private Long milestoneDueDays;
	private String saleDeedNo;
	private Timestamp saleDeedDate;
	private String saleDeedValue;
	private String registrationStatus;
	private String saleDeedCDno;
	private String balconyArea;
	private String bhk;
	private String numberOfBeds;
	
	/* Salesforce new parameters */
	@JsonProperty("OldBookingName")
	private String oldBookingName;
	@JsonProperty("NewBookingReason")
    private String newBookingReason;
	@JsonProperty("BookingId")
    private String bookingId;
	private Timestamp handingOverDate;
	
	private String propertySlNo;
	private String carParkingSpaces;
	
	@JsonProperty("salesforceTransactionId")
	private String salesforceTransactionId;
	private String customerLoanBank;
	private String rrNo;

}
