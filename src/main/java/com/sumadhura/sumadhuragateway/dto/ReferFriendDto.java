package com.sumadhura.sumadhuragateway.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class ReferFriendDto extends Result {
	private String deviceId;
	private Long id;
	private String name;
	private String mobile;
	private String email;
	private String pancard;
	private String interestFlat;
	private Long stateId;
	private Long cityId;
	private String state;
	private String city;
	private Long pincode;
	private Long customerId;
	private String imageLocation;
	private FileInfo fileInfo;
	private Integer statusId;
	private String comments;
	private Long siteId;
	private String siteName;
	private String referenceId;
	private String firstname;
	private List<Long> flatIds;
	private Long flatId;
	private String interestedSiteName;
	private Long flatBookingId;
	private List<String> flatPreferences;
	private String referralStatusName;
	private Long referralStatusValue;
	private List<Long> siteIds;
	private List<String> interestedSiteNames;
	private Flat flat;
    private List<Flat> flats;
    private String customerName;
    private String phoneNo;
    private String employeeId;
    private String crmEmployeeName;
}
