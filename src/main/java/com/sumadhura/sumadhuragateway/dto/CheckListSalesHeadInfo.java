/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;




/**
 * CustomerAddressPojo class provides ADDRESS Table specific fields.
 * 
 * @author Venkat_Koniki
 * @since 06.05.2019
 * @time 05:52PM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CheckListSalesHeadInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6371233246022294146L;
	private List<CustomerCheckListVerificationInfo> customerCheckListVerification = new ArrayList<CustomerCheckListVerificationInfo>();
	private String name;
	private Long leadId;
	private String stm;
	private String sourceofBooking;
	private String referralBonusStatus;
	private String offersAny;
	private String availability;
	private String availabilityIfOther;
	private String salesHeadcommitments;
	private String remarks;
	private Long projectSalesheadId;
	private String projectSalesheadName;
	private Timestamp projectSalesHeadDate;
	private Long authorizedSignatoryId;
	private String authorizedSignatoryName;
	private Timestamp authorizedSignatoryDate;
	private String erpDetails;
	private Long customerId;
	private String salesTeamCommitments;
	private Long flatBookingId;
	private Long salesHeadId;
	private Timestamp createdDate;
	private Long statusId;
	
}
