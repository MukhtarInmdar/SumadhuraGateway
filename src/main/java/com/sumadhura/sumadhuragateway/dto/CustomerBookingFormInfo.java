/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.ToString;

/**
 * CustomerBookingFormInfo class provides CustomerBookingForm specific fields.
 * 
 * @author Srivenu
 * @since 30.05.2019
 * @time 11:55AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class CustomerBookingFormInfo extends Result implements Serializable {
	
	private static final long serialVersionUID = 3025681126550993608L;
	private CustomerInfo customerInfo;
	//private CustomerSchemeInfo customerSchemeInfo;
	private List<CustomerSchemeInfo> customerSchemeInfos;
	private CustomerBookingInfo customerBookingInfo;
	private List<AddressInfo> addressInfos ;
	private ProfessionalInfo professionalInfo ;
	private CustomerOtherDetailsInfo customerOtherDetailsInfo;
	private List<CoApplicentDetailsInfo> coApplicentDetails;
	private FlatBookingInfo  flatBookingInfo ;
	private CheckListSalesHeadInfo  checkListSalesHead ;
	private CheckListCRMInfo  checkListCRM ;
	private CheckListLegalOfficerInfo  checkListLegalOfficer ;
	private CheckListRegistrationInfo  checkListRegistration ;
	private CustomerApplicationInfo customerApplicationInfo;
	private List<CustomerKYCDocumentSubmitedInfo> customerKYCSubmitedInfo;
	private Long empId;
	private Long empDeptId;
	private List<Long> empSiteId;
	private Long userId;
	private Boolean customerAppBookingApproval;
	private List<Map<String, Object>> customerFinancialDetails;
	
	public List<Map<String, Object>> getCustomerFinancialDetails() {
		return customerFinancialDetails;
	}

	public void setCustomerFinancialDetails(List<Map<String, Object>> customerFinancialDetails) {
		this.customerFinancialDetails = customerFinancialDetails;
	}

	public List<CustomerSchemeInfo> getCustomerSchemeInfos() {
		return customerSchemeInfos;
	}
	
    public void setCustomerSchemeInfos(List<CustomerSchemeInfo> customerSchemeInfos) {
		this.customerSchemeInfos = customerSchemeInfos;
	}	
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public Long getEmpDeptId() {
		return empDeptId;
	}
	public void setEmpDeptId(Long empDeptId) {
		this.empDeptId = empDeptId;
	}
	public List<Long> getEmpSiteId() {
		return empSiteId;
	}
	public void setEmpSiteId(List<Long> empSiteId) {
		this.empSiteId = empSiteId;
	}
	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}
	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}
	public List<AddressInfo> getAddressInfos() {
		return addressInfos;
	}
	public void setAddressInfos(List<AddressInfo> addressInfos) {
		this.addressInfos = addressInfos;
	}
	public ProfessionalInfo getProfessionalInfo() {
		return professionalInfo;
	}
	public void setProfessionalInfo(ProfessionalInfo professionalInfo) {
		this.professionalInfo = professionalInfo;
	}
	public CustomerOtherDetailsInfo getCustomerOtherDetailsInfo() {
		return customerOtherDetailsInfo;
	}
	public void setCustomerOtherDetailsInfo(CustomerOtherDetailsInfo customerOtherDetailsInfo) {
		this.customerOtherDetailsInfo = customerOtherDetailsInfo;
	}
	public List<CoApplicentDetailsInfo> getCoApplicentDetails() {
		return coApplicentDetails;
	}
	public void setCoApplicentDetails(List<CoApplicentDetailsInfo> coApplicentDetails) {
		this.coApplicentDetails = coApplicentDetails;
	}
	public FlatBookingInfo getFlatBookingInfo() {
		return flatBookingInfo;
	}
	public void setFlatBookingInfo(FlatBookingInfo flatBookingInfo) {
		this.flatBookingInfo = flatBookingInfo;
	}
	public CheckListSalesHeadInfo getCheckListSalesHead() {
		return checkListSalesHead;
	}
	public void setCheckListSalesHead(CheckListSalesHeadInfo checkListSalesHead) {
		this.checkListSalesHead = checkListSalesHead;
	}
	public CheckListCRMInfo getCheckListCRM() {
		return checkListCRM;
	}
	public void setCheckListCRM(CheckListCRMInfo checkListCRM) {
		this.checkListCRM = checkListCRM;
	}
	public CheckListLegalOfficerInfo getCheckListLegalOfficer() {
		return checkListLegalOfficer;
	}
	public void setCheckListLegalOfficer(CheckListLegalOfficerInfo checkListLegalOfficer) {
		this.checkListLegalOfficer = checkListLegalOfficer;
	}
	public CheckListRegistrationInfo getCheckListRegistration() {
		return checkListRegistration;
	}
	public void setCheckListRegistration(CheckListRegistrationInfo checkListRegistration) {
		this.checkListRegistration = checkListRegistration;
	}
	public CustomerApplicationInfo getCustomerApplicationInfo() {
		return customerApplicationInfo;
	}
	public void setCustomerApplicationInfo(CustomerApplicationInfo customerApplicationInfo) {
		this.customerApplicationInfo = customerApplicationInfo;
	}
	public List<CustomerKYCDocumentSubmitedInfo> getCustomerKYCSubmitedInfo() {
		return customerKYCSubmitedInfo;
	}
	public void setCustomerKYCSubmitedInfo(List<CustomerKYCDocumentSubmitedInfo> customerKYCSubmitedInfo) {
		this.customerKYCSubmitedInfo = customerKYCSubmitedInfo;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public CustomerBookingInfo getCustomerBookingInfo() {
		return customerBookingInfo;
	}
	public void setCustomerBookingInfo(CustomerBookingInfo customerBookingInfo) {
		this.customerBookingInfo = customerBookingInfo;
	}
	public Boolean getCustomerAppBookingApproval() {
		return customerAppBookingApproval;
	}
	public void setCustomerAppBookingApproval(Boolean customerAppBookingApproval) {
		this.customerAppBookingApproval = customerAppBookingApproval;
	}
	
}
