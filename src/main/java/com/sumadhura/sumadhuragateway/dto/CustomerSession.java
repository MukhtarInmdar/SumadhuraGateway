/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Customer class provides Customer specific properties.
 *  
 * @author Venkat_Koniki
 * @since 23.03.2019
 * @time 04:27PM
 */
public class CustomerSession extends Result implements Serializable {

	private static final long serialVersionUID = 990521885989700969L;
	private Long customerId;
	private String username;
	private String name;
	private String password;
	private Long appRegId;
	private String namePrefix;
	private String firstName;
	private String lastName;
	private String gender;
	private Integer age;
	private Date dateOfBirth;
	private String aadharNumber;
	private Date createdDate;
	private Date updatedDate;
	private String phoneNo;
	private String alternatePhoneNo;
	private String telephone;
	private String pancard;
	private String voterId;
	private String email;
	private Integer statusId;
	private Integer addressId;
	private String relationWith;
	private String relationName;
	private String nationality;
	private String maritalStatus;
	private String documentsUpload;
	private String profilePic;
	private Integer customerProfileId;
	private List<Long> flatIds;
	private Flat flat;
	private Long flatBookingId;
	private Device device;
	/**
	 * @return the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the flatIds
	 */
	public List<Long> getFlatIds() {
		return flatIds;
	}
	/**
	 * @param flatIds the flatIds to set
	 */
	public void setFlatIds(List<Long> flatIds) {
		this.flatIds = flatIds;
	}
	/**
	 * @return the appRegId
	 */
	public Long getAppRegId() {
		return appRegId;
	}
	/**
	 * @param appRegId the appRegId to set
	 */
	public void setAppRegId(Long appRegId) {
		this.appRegId = appRegId;
	}
	/**
	 * @return the namePrefix
	 */
	public String getNamePrefix() {
		return namePrefix;
	}
	/**
	 * @param namePrefix the namePrefix to set
	 */
	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @return the aadharNumber
	 */
	public String getAadharNumber() {
		return aadharNumber;
	}
	/**
	 * @param aadharNumber the aadharNumber to set
	 */
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}
	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	/**
	 * @return the alternatePhoneNo
	 */
	public String getAlternatePhoneNo() {
		return alternatePhoneNo;
	}
	/**
	 * @param alternatePhoneNo the alternatePhoneNo to set
	 */
	public void setAlternatePhoneNo(String alternatePhoneNo) {
		this.alternatePhoneNo = alternatePhoneNo;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the pancard
	 */
	public String getPancard() {
		return pancard;
	}
	/**
	 * @param pancard the pancard to set
	 */
	public void setPancard(String pancard) {
		this.pancard = pancard;
	}
	/**
	 * @return the voterId
	 */
	public String getVoterId() {
		return voterId;
	}
	/**
	 * @param voterId the voterId to set
	 */
	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the statusId
	 */
	public Integer getStatusId() {
		return statusId;
	}
	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	/**
	 * @return the addressId
	 */
	public Integer getAddressId() {
		return addressId;
	}
	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	/**
	 * @return the relationWith
	 */
	public String getRelationWith() {
		return relationWith;
	}
	/**
	 * @param relationWith the relationWith to set
	 */
	public void setRelationWith(String relationWith) {
		this.relationWith = relationWith;
	}
	/**
	 * @return the relationName
	 */
	public String getRelationName() {
		return relationName;
	}
	/**
	 * @param relationName the relationName to set
	 */
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}
	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}
	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	/**
	 * @return the documentsUpload
	 */
	public String getDocumentsUpload() {
		return documentsUpload;
	}
	/**
	 * @param documentsUpload the documentsUpload to set
	 */
	public void setDocumentsUpload(String documentsUpload) {
		this.documentsUpload = documentsUpload;
	}
	/**
	 * @return the profilePic
	 */
	public String getProfilePic() {
		return profilePic;
	}
	/**
	 * @param profilePic the profilePic to set
	 */
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	/**
	 * @return the customerProfileId
	 */
	public Integer getCustomerProfileId() {
		return customerProfileId;
	}
	/**
	 * @param customerProfileId the customerProfileId to set
	 */
	public void setCustomerProfileId(Integer customerProfileId) {
		this.customerProfileId = customerProfileId;
	}	
	/**
	 * @return the flat
	 */
	public Flat getFlat() {
		return flat;
	}
	/**
	 * @param flat the flat to set
	 */
	public void setFlat(Flat flat) {
		this.flat = flat;
	}
	/**
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}
	/**
	 * @param device the device to set
	 */
	public void setDevice(Device device) {
		this.device = device;
	}
	/**
	 * @return the flatBookingId
	 */
	public Long getFlatBookingId() {
		return flatBookingId;
	}
	/**
	 * @param flatBookingId the flatBookingId to set
	 */
	public void setFlatBookingId(Long flatBookingId) {
		this.flatBookingId = flatBookingId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerSession [customerId=" + customerId + ", username=" + username + ", password=" + password
				+ ", appRegId=" + appRegId + ", namePrefix=" + namePrefix + ", firstName=" + firstName + ", lastName="
				+ lastName + ", gender=" + gender + ", age=" + age + ", dateOfBirth=" + dateOfBirth + ", aadharNumber="
				+ aadharNumber + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", phoneNo="
				+ phoneNo + ", alternatePhoneNo=" + alternatePhoneNo + ", telephone=" + telephone + ", pancard="
				+ pancard + ", voterId=" + voterId + ", email=" + email + ", statusId=" + statusId + ", addressId="
				+ addressId + ", relationWith=" + relationWith + ", relationName=" + relationName + ", nationality="
				+ nationality + ", maritalStatus=" + maritalStatus + ", documentsUpload=" + documentsUpload
				+ ", profilePic=" + profilePic + ", customerProfileId=" + customerProfileId + ", flatIds=" + flatIds
				+ ", flat=" + flat + ", flatBookingId=" + flatBookingId + ", device=" + device + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
