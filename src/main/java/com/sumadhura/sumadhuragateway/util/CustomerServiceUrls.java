/**
 * 
 */
package com.sumadhura.sumadhuragateway.util;

/**
 * CustomerUrls class provides all urls for related to customerservice.
 *  
 * @author Venkat_Koniki
 * @since 25.03.2019
 * @time 01:27PM
 */
public enum CustomerServiceUrls {
	
	/* Live  */
	// commonurl("http://localhost:8090/customerservice"),
	
	/* DEV */
	//commonurl("http://localhost:8080/customerservice"),
	//commonurl("http://localhost:8181/customerservice"),
	
	/* UAT */
	//commonurl("http://localhost:9999/customerservice"),
	
	/* CUG */
	commonurl("http://localhost:8888/customerservice"), 
	
	/* customer login */
	aunticate(commonurl.getUrl()+"/login/authenticate.spring"),
	
	/* customer registration */
	customerregistration(commonurl.getUrl()+"/registration/customerRegistration.spring"),
	sendotp(commonurl.getUrl()+"/registration/sendOTP.spring"),
	verifyotp(commonurl.getUrl()+"/registration/verifyOTP.spring"),
	setmpin(commonurl.getUrl()+"/registration/setMpin.spring"),
	resendOTP(commonurl.getUrl()+"/registration/resendOTP.spring"),
	getPancardSecurityPolicies(commonurl.getUrl()+"/registration/getPancardSecurityPolicies.spring"),
	
	/* customer profile details */
	customerprofiledetails(commonurl.getUrl()+"/profile/custProfileDtls.spring"),
	
	/* customer property details */
	customerpropertydetails(commonurl.getUrl()+"/profile/propertyDetails.spring"),
	customerpropertyuploadeddocs(commonurl.getUrl()+"/profile/propertyUploadedDocs.spring"),
	customerpropertyamenitiescost(commonurl.getUrl()+"/profile/propertyAmenitiesCost.spring"),
	customerpropertymodificationcostdetails(commonurl.getUrl()+"/profile/modificationDetails.spring"),
	
	/* customer financial details */
	customerfinancialdetails(commonurl.getUrl()+"/financial/financialDtls.spring"),
	customerpaymentdetails(commonurl.getUrl()+"/financial/paymentDtls.spring"),
	customerdemandnotedetails(commonurl.getUrl()+"/financial/demandNoteDtls.spring"),
	
	/* customer faq details */
	customerfaqdetails(commonurl.getUrl()+"/faqs/listOfFaqs.spring"),
	
	/* customer dashboard details */
	customerdashboardcarousel(commonurl.getUrl()+"/dashboard/dashboardCarousel.spring"),
	customerdashboardpaymentdetails(commonurl.getUrl()+"/dashboard/paymentDetails.spring"),
	customerdashboardpropertyWork(commonurl.getUrl()+"/dashboard/getPropertyWork.spring"),
	
	/* employee department details */
	departmentdetails(commonurl.getUrl()+"/department/getDepartmentListForCSR.spring"),
	
	/* customer Ticket  details */
	customerTicketTypeDetails(commonurl.getUrl()+"/customerTicket/getTicketTypeDetails.spring"),
	customercreateTicketRequest(commonurl.getUrl()+"/customerTicket/createCustomerServiceRequest.spring"),
	customerticketdetails(commonurl.getUrl()+"/customerTicket/getTicketDetails.spring"),
	customerchatsumbit(commonurl.getUrl()+"/customerTicket/chatSubmit.spring"),
	customerSpecificPropertyInfo(commonurl.getUrl()+"/customerTicket/getCustomerSpecificPropertyInfo.spring"),
	VIEW_COMPANYS_NOTIFY(commonurl.getUrl()+"/notification/companyEmployeeNotifications.spring"),
	VIEW_SITE_NOTIFYS(commonurl.getUrl()+"/notification/listOfNotifications.spring"),
	VIEW_NONCUSTOMER_NOTIFYS(commonurl.getUrl()+"/notification/listOfNonCustomerNotifications.spring"),
	SAVE_CUST_VIEW_NOTIFY(commonurl.getUrl()+"/notification/saveNotifyViewStatus.spring"),
	SAVE_NON_CUST_VIEW_NOTIFY(commonurl.getUrl()+"/notification/saveNonCustomerNotifyViewStatus.spring"),
	// FEED BACK
	GET_NON_FEED_BACK_TKT(commonurl.getUrl()+"/feedback/nonFeedbackTkt.spring"),
	SAVE_TKT_FEED_BACK(commonurl.getUrl()+"/feedback/saveTktFeedback.spring"),
	
	//REFERENCES
	SAVE_REFER_FRIEND(commonurl.getUrl()+"/references/saveReferFriend.spring"),
	GET_REFER_FRIEND_LIST(commonurl.getUrl()+"/references/getReferFriendList.spring"),
	CHANGE_M_PIN(commonurl.getUrl()+"/login/changeMpin.spring"),
	FORGOT_M_PIN(commonurl.getUrl()+"/login/forgotMpin.spring"),
	GET_SITE_LIST(commonurl.getUrl()+"/noncustomer/site/getSiteMenu.spring"),
	GET_STATE_LIST(commonurl.getUrl()+"/references/stateList.spring"),
	GET_FLATBHK_LIST(commonurl.getUrl()+"/references/getFlatBhkList.spring"),
	GET_REFERENCES_SECURITY_POLCIES(commonurl.getUrl()+"/references/getReferencesSecurityPolicies.spring"),
	
	/* IOS Customers Registration Status */
	GET_IOS_CUSTOMERS_REGISTRATION_STATUS(commonurl.getUrl()+"/login/getIosCustomerRegistrationStatus.spring"),
	GET_APP_MEN_DETAILS(commonurl.getUrl()+"/login/getAppMenuDetails.spring"),
	GET_APP_MEN_LIST_DETAILS(commonurl.getUrl()+"/login/getAppMenuListDetails.spring"),
	/* Appointment Booking */
	GET_APPOINTMENT_TYPE_DETAILS(commonurl.getUrl()+"/appointmentBooking/getAppointmentTypeDetails.spring"),
	GET_APPOINTMENT_TIME_SLOT_DETAILS(commonurl.getUrl()+"/appointmentBooking/getAppointmentTimeSlotDetails.spring"),
	SAVE_APPOINTMENT_TIME_SLOT_DETAILS(commonurl.getUrl()+"/appointmentBooking/saveAppointmentTimeSlotDetails.spring"),
	GET_APPOINTMENT_SLOT_DATES_DETAILS(commonurl.getUrl()+"/appointmentBooking/getAppointmentSlotDatesDetails.spring"),


	
	/*apply loan*/
	GET_DOCUMENTS_NAMES_TO_DISPLAY(commonurl.getUrl()+"/loan/getDocumetsNamesToDisplay.spring"),
	GET_BANKER_LIST(commonurl.getUrl()+"/loan/getBankList.spring"),
	SAVE_APPLY_LOAN_DOCUMENTS(commonurl.getUrl()+"/loan/saveApplyLoanDocuments.spring"),
	loadBankInterestLoanDetails(commonurl.getUrl()+"/loan/loadBankInterestLoanDetails.spring"),
	;
	
	private String url;

	private CustomerServiceUrls(String url) {
		this.url = url;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	

}