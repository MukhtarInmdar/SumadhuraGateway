/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class AddressInfo implements Serializable {

	private static final long serialVersionUID = 1015951847997681728L;
	private Long addressId;
	private Long custAddressId;
	private String Hno;
	private String floorNo;
	private String tower;
	private String street;
	private String area;
	private String landmark;
	private String pincode;
	private Long cityId;
	private String city;
	private String cityIcon;
	private Long stateId;
	private String state;
	private String country;
	private Double longitude;
	private Double latitude;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private String addressType;
	private String address1;
	private String address2;
	private String address3;
	private Long statusId;
	private String Foraddress; // applicant or co applicant
	private String surveyNo;
	private String district;
	private String contactNo;
	private String email;
	private String website;
	private String nearBy;
	private AddressMappingInfo addressMappingType;
	private Long countryId;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerAddressPojo [custAddressId=" + custAddressId + ", Hno=" + Hno + ", floorNo=" + floorNo
				+ ", tower=" + tower + ", street=" + street + ", area=" + area + ", landmark=" + landmark + ", pincode="
				+ pincode + ", cityId=" + cityId + ", city=" + city + ", cityIcon=" + cityIcon + ", stateId=" + stateId
				+ ", state=" + state + ", country=" + country + ", langitude=" + longitude + ", latitude=" + latitude
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", addressType=" + addressType
				+ ", address1=" + address1 + ", address2=" + address2 + ", address3=" + address3 + ", statusId="
				+ statusId + "]";
	}
	
}
