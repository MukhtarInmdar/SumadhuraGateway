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
public class Customer extends Result implements Serializable {
	
	private static final long serialVersionUID = -3012082155044954556L;
	private long id;
	private Long siteId;
	private Long flatId;
	private long custNo;
	private String addaharNo;
	private String name;
	private String city;
	private Long cityId;
	private Long stateId;
	private String state;
	private Integer pincode;
	private Long mobile;
	private String email;
	private String flag;
	private Flat flat;
	private Device device;
	private String siteName;
	private String firstName;
	private String lastName;
	private String address;
	private int phone;
	private Long customerId;
	private String namePrefix;
	private String gender;
	private Integer age;
	private String dateOfBirth;
	private String aadharNumber;
	private Date createdDate;
	private Date updatedDate;
	private String phoneNo;
	private String alternatePhoneNo;
	private String telephone;
	private String pancard;
	private String voterId;
	private Integer statusId;
	private Integer addressId;
	private String relationWith;
	private String relationName;
	private String nationality;
	private String maritalStatus;
	private String documentsUpload;
	private String profilePic;
	private Integer customerProfileId;
	private String deviceId;
	private CustomerAddressDTO customerAddressDTO;
	private Long paymentScheduleId;
	private String deviceToken;
	private Long flatBookingId;
	private List<Long> siteIds;
    private List<String> interestedSiteNames;
    private List<Flat> flats;
    private String uuid;
    private String serialNo;
    private Long notificationId;
    private List<Long> stateIds;
    private Long nonCustomerNotificationSentDetailsId ;
    private String requestUrl;
    private Long portNumber;
    private List<Long> departmentIds;
	private Long appRegId;
}
