package com.sumadhura.sumadhuragateway.util;

/**
 * EmployeeServiceUrls class provides all urls for related to EmployeeService.
 *  
 * @author Venkat_Koniki
 * @since 22.04.2019
 * @time 12:15PM
 */
public enum EmployeeServiceUrls {

	/* common url for employeeservice. */
	
	/* Live  */
	//commonurl("http://localhost:8090/employeeservice"),
	
	/* dev */
   // commonurl("http://localhost:8080/employeeservice"),
   //commonurl("http://localhost:8181/employeeservice"),
	
    /* uat  */
	//commonurl("http://localhost:9999/employeeservice"),
	
	/* cug */
	commonurl("http://localhost:8888/employeeservice"),
	
	/* employee login */
	aunticate(commonurl.getUrl()+"/login/authenticate.spring"),
	departmentSpecificModulesSubmodules(commonurl.getUrl()+"/login/departmentSpecificModulesSubmodules.spring"),
	CHANGE_PASSWORD(commonurl.getUrl()+"/login/changePassword.spring"),
	SEND_OTP_FORGOT_PASSWORD(commonurl.getUrl()+"/login/sendOtpForgotPassword.spring"),
	/* employee Ticketing */
	customerraisedTicketList(commonurl.getUrl()+"/employeeTicket/getCustomerRaisedTicketList.spring"),
	customerTicketDetails(commonurl.getUrl()+"/employeeTicket/getCustomerTicketDetails.spring"),
	customerTicketchatSubmit(commonurl.getUrl()+"/employeeTicket/chatSubmit.spring"),
	closeTicket(commonurl.getUrl()+"/employeeTicket/closeTicket.spring"),
	reOpenTicket(commonurl.getUrl()+"/employeeTicket/reOpenTicket.spring"),
	getTicketList(commonurl.getUrl()+"/employeeTicket/getTicketList.spring"),
	getTicket(commonurl.getUrl()+"/employeeTicket/getTicket.spring"),
	updateTicketConversation(commonurl.getUrl()+"/employeeTicket/updateTicketConversation.spring"),
	getTicketForwardMenuDetails(commonurl.getUrl()+"/employeeTicket/getTicketForwardMenuDetails.spring"),
	forwardTicketDetails(commonurl.getUrl()+"/employeeTicket/forwardTicketDetails.spring"),
	getDepartmentDetails(commonurl.getUrl()+"/employeeTicket/getDepartmentDetails.spring"),
	seekInfoTicketDetails(commonurl.getUrl()+"/employeeTicket/seekInfoTicketDetails.spring"),
	ticketSpecifictviewRequestInfo(commonurl.getUrl()+"/employeeTicket/ticketSpecifictviewRequestInfo.spring"),
	viewRequestInfo(commonurl.getUrl()+"/employeeTicket/viewRequestInfo.spring"),
	insertSeekInfoDetails(commonurl.getUrl()+"/employeeTicket/insertSeekInfoDetails.spring"),
	makeAsPublic(commonurl.getUrl()+"/employeeTicket/makeAsPublic.spring"),
	insertExtendEsacalationTime(commonurl.getUrl()+"/employeeTicket/insertExtendEsacalationTime.spring"),
	getExtendEsacalationTimeDetails(commonurl.getUrl()+"/employeeTicket/getExtendEsacalationTimeDetails.spring"),
	updateExtendEsacalationTimeDetailsStatus(commonurl.getUrl()+"/employeeTicket/updateExtendEsacalationTimeDetailsStatus.spring"),
	getSystemEscalatedTicketDetails(commonurl.getUrl()+"/employeeTicket/getSystemEscalatedTicketDetails.spring"),
	getEmployeeDetails(commonurl.getUrl()+"/employeeTicket/getEmployeeDetails.spring"),
	insertEmployeeLeaveDetails(commonurl.getUrl()+"/employeeTicket/insertEmployeeLeaveDetails.spring"), 
	isEmployeeAvailable(commonurl.getUrl()+"/employeeTicket/isEmployeeAvailable.spring"),
    changeTicketOwnerDropDown(commonurl.getUrl()+"/employeeTicket/changeTicketOwnerDropDown.spring"),
	changeTicketOwner(commonurl.getUrl()+"/employeeTicket/changeTicketOwner.spring"),
    getTicketEscaltionDtls(commonurl.getUrl()+"/employeeTicket/getTicketEscaltionDtls.spring"),
    getExtendedEscalationTimeApprovalLevel(commonurl.getUrl()+"/employeeTicket/getExtendedEscalationTimeApprovalLevel.spring"),
    getChangeTicketTypeMailDetails(commonurl.getUrl()+"/employeeTicket/getChangeTicketTypeMailDetails.spring"),
    sendChangeTicketTypeMail(commonurl.getUrl()+"/employeeTicket/sendChangeTicketTypeMail.spring"),
    changeTicketTypeRemindAction(commonurl.getUrl()+"/employeeTicket/remindAgainAction.spring"),
    actionChangeTicketType(commonurl.getUrl()+"/employeeTicket/actionChangeTicketType.spring"),
    GET_CUSTOMER_TICKET_LIST(commonurl.getUrl()+"/employeeTicket/getCustomerTicketList.spring"),
    SAVE_TICKETS_COMPLAINT(commonurl.getUrl()+"/employeeTicket/saveTicketsComplaint.spring"),
    GET_TICKET_COMPLAINT_LIST(commonurl.getUrl()+"/employeeTicket/getTicketComplaintList.spring"),
    GET_TICKET_ADDITIONAL_DETAILS(commonurl.getUrl()+"/employeeTicket/getTicketAdditionalDetails.spring"),
    GET_TICKET_COUNT_LIST(commonurl.getUrl()+"/employeeTicket/getTicketCountList.spring"),
    GET_CLOSED_TICKET_LIST(commonurl.getUrl()+"/employeeTicket/getClosedTicketList.spring"),
    GET_TICKET_PENDING_DEPT_DTLS(commonurl.getUrl()+"/employeeTicket/getTicketPendingDeptDtls.spring"),
    createTicketTypeDetailsForCRM(commonurl.getUrl()+"/employeeTicket/createTicketTypeDetailsForCRM.spring"),
    getCrmEmployees(commonurl.getUrl()+"/employeeTicket/getCrmEmployees.spring"),
    createTicketEscalationsLevels(commonurl.getUrl()+"/employeeTicket/createTicketEscalationsLevels.spring"),
    getEscalationLevel(commonurl.getUrl()+"/employeeTicket/getEscalationLevel.spring"),
    getEscalationLevelEmployees(commonurl.getUrl()+"/employeeTicket/getEscalationLevelEmployees.spring"),
    createTicketEscalationsEcxtentionLevels(commonurl.getUrl()+"/employeeTicket/createTicketEscalationsEcxtentionLevels.spring"),
    
    
	/* Booking Form */
	bookingFormsList(commonurl.getUrl()+"/bookingFormService/getFormsList.spring"),
	savebookingForms(commonurl.getUrl()+"/bookingFormService/saveBookingDetails.spring"),
	getbookingForms(commonurl.getUrl()+"/bookingFormService/getBookingDetails.spring"),
	generateBookingWelcomeLetter(commonurl.getUrl()+"/bookingFormService/generateBookingAllotmentLetter.spring"),
	getCustomerFlatDocuments(commonurl.getUrl()+"/bookingFormService/getCustomerFaltDocuments.spring"),
	getAgreementTypesList(commonurl.getUrl()+"/bookingFormService/getAgreementTypesList.spring"),
	actionbookingForm(commonurl.getUrl()+"/bookingFormService/actionBookingDetails.spring"),
	insertOrUpdateCheckListDetails(commonurl.getUrl()+"/bookingFormService/insertOrUpdateCheckListDetails.spring"),
	getRegistrationDetails(commonurl.getUrl()+"/bookingFormService/getRegistrationDetails.spring"),
	getCustomerAndCo_AppDetails(commonurl.getUrl()+"/bookingFormService/getCustomerAndCo_AppDetails.spring"),
	getUnitDetails(commonurl.getUrl()+"/bookingFormService/getUnitDetails.spring"),
	inactiveBookings(commonurl.getUrl()+"/bookingFormService/inactiveBookings.spring"),
	generateBookingNOCLetter(commonurl.getUrl()+"/bookingFormService/generateBookingNOCLetter.spring"),
	getNOCDocuments(commonurl.getUrl()+"/bookingFormService/getNOCDocuments.spring"),
	getNOCDocumentsList(commonurl.getUrl()+"/bookingFormService/getNOCDocumentsList.spring"),
	getCustomerData(commonurl.getUrl()+"/bookingFormService/getCustomerData.spring"),
	generateLoanNOCLetter(commonurl.getUrl()+"/bookingFormService/generateLoanNOCLetter.spring"),
	getKycDocumentsList(commonurl.getUrl()+"/bookingFormService/getKycDocumentsList.spring"),
	getFlatDetails(commonurl.getUrl()+"/bookingFormService/getFlatDetails.spring"),
	getNonBookedDetails(commonurl.getUrl()+"/bookingFormService/getNonBookedDetails.spring"),
	getCityList(commonurl.getUrl()+"/bookingFormService/getCityList.spring"),
	getCountryList(commonurl.getUrl()+"/bookingFormService/getCountryList.spring"),
	getFinSchemes(commonurl.getUrl()+"/bookingFormService/getFinSchemes.spring"),
	getOldSalesForesBookingIds(commonurl.getUrl()+"/bookingFormService/getOldSalesForesBookingIds.spring"),
	/*Third party requests*/
	getEmployeeOfMultiplePortal(commonurl.getUrl()+"/thirdParty/getEmployeeOfMultiplePortal.spring"),
	
    /* Employee financial service */
	getMileStoneSetsDtls(commonurl.getUrl()+"/financial/getMileStoneSetsDtls.spring"),
	getMileStoneDemandNoteDetails(commonurl.getUrl()+"/financial/getMileStoneDemandNoteDetails.spring"),
	getMileStoneDetailsForTDS(commonurl.getUrl()+"/financial/getMileStoneDetailsForTDS.spring"),
	updateMileStoneTDSDetails(commonurl.getUrl()+"/financial/updateMileStoneTDSDetails.spring"),
	getDemandNoteBlockSelectionDetails(commonurl.getUrl()+"/financial/getDemandNoteBlockSelectionDetails.spring"),
	generateDemandNote(commonurl.getUrl()+"/financial/generateDemandNote.spring"),
	editDemandNoteDetails(commonurl.getUrl()+"/financial/editDemandNoteDetails.spring"),
	saveFinancialTransactionReceiptRequest(commonurl.getUrl()+"/financial/saveFinancialTransactionReceiptRequest.spring"),
	saveInterestWaiver(commonurl.getUrl()+"/financial/saveInterestWaiver.spring"),
	deleteFinancialTransaction(commonurl.getUrl()+"/financial/deleteFinancialTransaction.spring"),
	editFinancialTransaction(commonurl.getUrl()+"/financial/editFinancialTransaction.spring"),
	uploadDemandNoteMilestones(commonurl.getUrl()+"/financial/uploadDemandNoteMilestones.spring"),
	uploadFinancialTransaction(commonurl.getUrl()+"/financial/uploadFinancialTransaction.spring"),
	getCustomerLedger(commonurl.getUrl()+"/financial/getCustomerLedger.spring"),
	updateInterestRates(commonurl.getUrl()+"/financial/updateInterestRates.spring"),
	loadRequestedData(commonurl.getUrl()+"/financial/loadRequestedData.spring"),
	getCustomerLedgerDetails(commonurl.getUrl()+"/financial/getCustomerLedgerDetails.spring"),
	doOnlinePaymentAnonymousEntry(commonurl.getUrl()+"/financial/doOnlinePaymentAnonymousEntry.spring"),
	updateOnlinePaymentAnonymousEntry(commonurl.getUrl()+"/financial/updateOnlinePaymentAnonymousEntry.spring"),
	doModificationChargesEntry(commonurl.getUrl()+"/financial/doModificationChargesEntry.spring"),
	approveModificationChargesEntry(commonurl.getUrl()+"/financial/approveModificationChargesEntry.spring"),
	getAllIncompletedEmpSitesListForMileStone(commonurl.getUrl()+"/financial/incompletedEmpSitesList.spring"),
	getAllIncompletBlocksListForMileStone(commonurl.getUrl()+"/financial/incompleteBlocksListForMileStone.spring"),
	getAllAliasNamesForMileStone(commonurl.getUrl()+"/financial/aliasNamesForMileStone.spring"),
	getMileStonePercentages(commonurl.getUrl()+"/financial/mileStonePercentages.spring"),
	createMileStoneForDemandNote(commonurl.getUrl()+"/financial/createMileStoneDataForDemandNote.spring"),
	updateMileStoneDataForDemandNote(commonurl.getUrl()+"/financial/updateMileStoneDataForDemandNote.spring"),
	getNonRefundFlats(commonurl.getUrl()+"/financial/getNonRefundFlats.spring"),
	GENERATED_DEMAND_NOTE_PREVIEW(commonurl.getUrl()+"/financial/generatedDemandNotePreview.spring"),
	DELETE_DEMAND_NOTE_ZIP_FILE(commonurl.getUrl()+"/financial/deleteDemandNoteZipFile.spring"),
	LOAD_DEMAND_NOTE_PDF_FILE(commonurl.getUrl()+"/financial/loadDemandNotePDFFile.spring"),
	RAISED_MILESTONE_SITES(commonurl.getUrl()+"/financial/raisedMilestoneSites.spring"),
	GET_ACTIVE_BLOCKS_FLATS(commonurl.getUrl()+"/financial/activeBlocksFlats.spring"),
	VIEW_DEMAND_NOTES(commonurl.getUrl()+"/financial/viewDemandNotes.spring"),
	GET_TRANSACTION_TYPE_MODE_DATA(commonurl.getUrl()+"/financial/viewFinTransactionTypeModeData.spring"),
	GET_FIN_PROJECT_ACCOUNT_DATA(commonurl.getUrl()+"/financial/viewFinProjectAccountData.spring"),
	viewFinProjectAccountDataForInvoices(commonurl.getUrl()+"/financial/viewFinProjectAccountDataForInvoices.spring"),
	GET_PENDING_AMOUNT_DATA(commonurl.getUrl()+"/financial/viewPendingAmount.spring"),
	getCustomerInvoices(commonurl.getUrl()+"/financial/getCustomerInvoices.spring"),
	GET_MIS_PENDING_TRANSACTIONS(commonurl.getUrl()+"/financial/viewMisPendingTransactions.spring"),
	viewInterestWaiverPendingTransactions(commonurl.getUrl()+"/financial/viewInterestWaiverPendingTransactions.spring"),
	GET_MIS_RECEIPT_CHEQUE_ONLINE_DATA(commonurl.getUrl()+"/financial/viewMisReceiptChequeOnlineData.spring"),
	viewInterestWaiverData(commonurl.getUrl()+"/financial/viewInterestWaiverData.spring"), 
	GET_ANONYMOUS_ENTRIES_DATA(commonurl.getUrl()+"/financial/viewAnonymousEntriesData.spring"),
	viewAnonymousEntriesDataReport(commonurl.getUrl()+"/financial/viewAnonymousEntriesDataReport.spring"),
	
	GET_TAX_PERCENTAGE_DATA(commonurl.getUrl()+"/financial/getTaxPercentage.spring"),
	SAVE_LEGAL_CHARGES(commonurl.getUrl()+"/financial/saveLegalCharges.spring"),
	SET_APPROVE_REJECT_MIS_RECEIPT_PAYMENT(commonurl.getUrl()+"/financial/approveOrRejectMisReceiptOrPayment.spring"),
	approveFinancialMultipleTransaction(commonurl.getUrl()+"/financial/approveFinancialMultipleTransaction.spring"),
	GET_CUSTOMER_DETAILS_PENDING_AMOUNTS(commonurl.getUrl()+"/financial/getCustomerDetailsAndPendingAmounts.spring"),
	GET_EXCESS_AMOUNT_DETAILS_FOR_REFUND(commonurl.getUrl()+"/financial/getExcessAmountDetailsForRefund.spring"),
	GENERATE_CONSOLIDATED_RECEIPT(commonurl.getUrl()+"/financial/generateConsolidatedReceipt.spring"),
	deleteUploadedAttachments(commonurl.getUrl()+"/financial/deleteUploadedAttachments.spring"),
	generateClosingBalanceReport(commonurl.getUrl()+"/financial/generateClosingBalanceReport.spring"),
	getCustomerFinancialDetails(commonurl.getUrl()+"/financial/getCustomerFinancialDetails.spring"),
	GET_PENDING_MOD_INVOICES(commonurl.getUrl()+"/financial/getPendingModificationInvoices.spring"),
	GET_PENDING_MOD_INVOICE_DETAILS(commonurl.getUrl()+"/financial/getModificationInvoiceDetails.spring"),
	loadDemandNoteFormats(commonurl.getUrl()+"/financial/loadDemandNoteFormats.spring"),
	GET_INTEREST_WAIVER_REPORT_DETAILS(commonurl.getUrl()+"/financial/getInterestWaiverReportDetails.spring"),
	GET_INTEREST_WAIVED_AND_PAID_DETAILS(commonurl.getUrl()+"/financial/getInterestWaivedAndPaidDetails.spring"),
	getSuspenseEntryReport(commonurl.getUrl()+"/financial/getSuspenseEntryReport.spring"),
	loadTransactionStatusData(commonurl.getUrl()+"/financial/loadTransactionStatusData.spring"),
	getClearedTransactionReport(commonurl.getUrl()+"/financial/getClearedTransactionReport.spring"),
	getPendingTransactionReport(commonurl.getUrl()+"/financial/getPendingTransactionReport.spring"),
	getSuspnesEntryTransactionReport(commonurl.getUrl()+"/financial/getSuspnesEntryTransactionReport.spring"),
	getCleredUnclearedTransactionReport(commonurl.getUrl()+"/financial/getCleredUnclearedTransactionReport.spring"),
	getAccountNumbers(commonurl.getUrl()+"/financial/getAccountNumbers.spring"),
	getBookingStatuses(commonurl.getUrl()+"/financial/getBookingStatuses.spring"),
	checkDuplicateTransactionOrNot(commonurl.getUrl()+"/financial/checkDuplicateTransactionOrNot.spring"),
	getStates(commonurl.getUrl()+"/financial/getStates.spring"),
	/* Malladi Changes */
	GET_ALL_CUSTOMERS_INVOICES(commonurl.getUrl()+"/financial/getAllCustomersInvoices.spring"),
	
	//Site
	getSite(commonurl.getUrl()+"/site/site.spring"),
	getALLSite(commonurl.getUrl()+"/site/allSiteList.spring"),
	getALLSites(commonurl.getUrl()+"/site/allSitesList.spring"),
	getStateWiseSites(commonurl.getUrl()+"/site/stateWiseSites.spring"),
	// Block
	getBlocksNamesBySite(commonurl.getUrl()+"/block/blocks.spring"),
	/*  *************     Floor Service *************  */
	getFloorNamesByBlock(commonurl.getUrl()+"/floor/floor.spring"),
	getFloorNamesBySite(commonurl.getUrl()+"/floor/floorSite.spring"),
	/* *********************  FLAT Service ******** */
    getFlatsNames(commonurl.getUrl()+"/flat/flat.spring"),
    getFlatsNamesBySite(commonurl.getUrl()+"/flat/flatSite.spring"),
    getFlatsNamesByBlock(commonurl.getUrl()+"/flat/flatBlock.spring"),
    getFlatsNamesBySbuaSeries(commonurl.getUrl()+"/flat/flatsbua.spring"),
    GET_CUSTOMER_DETAILS_BY_FLAT_ID(commonurl.getUrl()+"/flat/getCustomerDetailsByFlatId.spring"),
    getFlatsStatusBySite(commonurl.getUrl()+"/flat/getFlatsStatusBySite.spring"),
    /* ************************ NOTIFICATIONS ********** */
    SEND_COMPANY_NOTIFYS(commonurl.getUrl()+"/notification/sendCompanyNotification.spring"),
  	SEND_SITE_NOTIFYS(commonurl.getUrl()+"/notification/siteLevelNotifys.spring"),

  	VIEW_SITE_NOTIFY(commonurl.getUrl()+"/notification/viewSiteNotifys.spring"),
  	
  	/*NOTIFICATIONS*/
  	VIEW_CUST_NOTIFYS(commonurl.getUrl()+"/notification/getCustomersNotifys.spring"),
  	GET_STATES(commonurl.getUrl()+"/notification/getStateList.spring"),
  	GET_ALL_STATES(commonurl.getUrl()+"/notification/getAllStateList.spring"),
  	VIEW_NONCUSTOMER_NOTIFY(commonurl.getUrl()+"/notification/nonCustomerNotifys.spring"),
  	NONCUSTOMER_COMPANY_NOTIFICATIONS(commonurl.getUrl()+"/notification/nonCustomerAndCompanyNotifications.spring"),
  	VIEW_COMPANYS_NOTIFY(commonurl.getUrl()+"/notification/companysNotifys.spring"),
  	VIEW_SITE_NOTIFYS(commonurl.getUrl()+"/notification/sitesNotifys.spring"),
    SEND_PROJECT_NOTIF_FOR_APPROVALS(commonurl.getUrl()+"/notification/sendProjectNotificationsForApprovals.spring"),
  	VIEW_MODF_PROJECT_NOTIF_FOR_APPROVALS(commonurl.getUrl()+"/notification/viewModifiedProjectNotificationsForApprovals.spring"),
  	VIEW_PROJECT_NOTIF_DET_CHANGES(commonurl.getUrl()+"/notification/viewProjectNotificationDetailChanges.spring"),
  	APPROVE_OR_REJECT_PROJECT_NOTIFY(commonurl.getUrl()+"/notification/approveOrRejectProjectNotifications.spring"),
  	SEND_COMPANY_NOTIFYS_FOR_APPROVALS(commonurl.getUrl()+"/notification/sendCompanyNotificationsForApprovals.spring"),
  	APPROVE_OR_REJECT_COMPANY_NOTIFY(commonurl.getUrl()+"/notification/approveOrRejectCompanyNotifications.spring"),
  	GET_FLATS_BY_DATES(commonurl.getUrl()+"/notification/getFlatsByDates.spring"),
	GET_FLATS_COUNT_BY_DATES(commonurl.getUrl()+"/notification/getFlatDetailsCountBydates.spring"),
  	GET_NOTIFICATION_VIEW_REPORT(commonurl.getUrl()+"/notification/getNotificationViewReport.spring"),
	GET_COMPANY_NOTIFICATION_VIEW_REPORT(commonurl.getUrl()+"/notification/getCompanyNotificationViewReport.spring"),
	
  	
  	SEARCH_REFER_FRND(commonurl.getUrl()+"/reference/referedCustomers.spring"),
  	SEARCH_CUSTOMER(commonurl.getUrl()+"/reference/customerSearch.spring"),
	actionbookingFormupdate(commonurl.getUrl()+"/bookingFormService/updateApplicantOrCoapplicant.spring"),
	UPDATE_REFERRER_FRND_COMMENTS(commonurl.getUrl()+"/reference/saveReferredFrndComments.spring"),
  	BOOKING_FORM_APPLICANT_DATA_UPDATE(commonurl.getUrl()+"/bookingFormService/updateApplicantOrCoapplicant.spring"),
	REFERRAL_STATUS_LIST(commonurl.getUrl()+"/reference/referralStatusList.spring"),
	
	/* DROP_DOWN_FOR_NOTIFICATION */
	GET_SBUA_BY_SITE_BLOCK_FLOOR(commonurl.getUrl()+"/notificationdropdown/sbuabysiteblockfloor.spring"),
	GET_SERIES_BY_SITE_BLOCK_FLOOR(commonurl.getUrl()+"/notificationdropdown/seriesbysiteblockfloor.spring"),
	GET_FACING_BY_SITE_BLOCK_FLOOR(commonurl.getUrl()+"/notificationdropdown/facingbysiteblockfloor.spring"),
	GET_BHK_TYPE_BY_SITE_BLOCK_FLOOR(commonurl.getUrl()+"/notificationdropdown/bhktypebysiteblockfloor.spring"), 
	
	/* MESSENGER */
	GET_MESSENGER_LIST(commonurl.getUrl()+"/messenger/getMessagesList.spring"),
	START_NEW_CHAT(commonurl.getUrl()+"/messenger/startNewChat.spring"),
	CHAT_SUBMIT(commonurl.getUrl()+"/messenger/chatSubmit.spring"),
	GET_CHAT_DETAILS(commonurl.getUrl()+"/messenger/getChatDetails.spring"),
	GET_UNVIEW_CHAT_COUNT(commonurl.getUrl()+"/messenger/getUnviewChatCount.spring"),
	UPDATE_VIEW_STATUS_UNREAD(commonurl.getUrl()+"/messenger/updateViewStatusAsUnread.spring"),
    GET_SITES_FOR_INBOX(commonurl.getUrl()+"/messenger/sitesForInbox.spring"),
	/* MPR MODULE */
	SAVE_MPR_DETAILS(commonurl.getUrl()+"/mpr/saveMPRDetails.spring"),
	GET_MPR_DETAILS(commonurl.getUrl()+"/mpr/getMPRDetails.spring"),
	GET_CUSTOMER_MPR_DETAILS(commonurl.getUrl()+"/mpr/getCustomerMPRDetails.spring"),
	CHECK_MPR_NAME_EXISTENCE(commonurl.getUrl()+"/mpr/checkMprNameExistence.spring"),
	INACTIVE_MPR_DETAILS(commonurl.getUrl()+"/mpr/inActiveMPRDetails.spring"),
	GET_MPR_VIEW_REPORT(commonurl.getUrl()+"/mpr/getMPRViewReport.spring"),
	SAVE_CUST_VIEW_MPR(commonurl.getUrl()+"/mpr/saveMPRViewStatus.spring"),
	GET_NON_CUST_MPR_DETAILS(commonurl.getUrl()+"/mpr/getNonCustMPRDetails.spring"),
	SAVE_NON_CUST_VIEW_MPR(commonurl.getUrl()+"/mpr/saveNonCustMPRViewStatus.spring"),
	SEND_MPR_NOTIFY(commonurl.getUrl()+"/mpr/sendNONCUSTMPRDetails.spring"),
	/* TICKET REPORTING */
	GET_PROJECT_WISE_TICKET_COUNT(commonurl.getUrl()+"/ticketreport/getProjectWiseTicketCount.spring"),
	GET_PROJECT_WISE_RAISED_TICKET_TYPE_COUNT(commonurl.getUrl()+"/ticketreport/getProjectWiseRaisedTicketTypeCount.spring"),
	GET_ALL_MAINTICKET_TYPES_PROJECT_WISE_TICKET_COUNT(commonurl.getUrl()+"/ticketreport/getAllMainTicketTypesProjectWiseTicketCount.spring"),
	GET_ALL_TICKET_TYPES_PROJECT_WISE_TICKET_COUNT(commonurl.getUrl()+"/ticketreport/getAllTicketTypesProjectWiseTicketCount.spring"),
	GET_EMPLOYEE_PROJECT_WISE_TICKET_COUNT(commonurl.getUrl()+"/ticketreport/getEmployeeProjectwiseTicketCount.spring"),
	GET_ESCALATION_TICKETS_DETAILS(commonurl.getUrl()+"/ticketreport/getEscalationTicketsDetailsCount.spring"),
	GET_SITE_TICKET_TYPE_LEVEL_WISE_ESCALATION_DETAILS(commonurl.getUrl()+"/ticketreport/getSiteTickettypeLevelWiseEscalationDetails.spring"),
	GET_EMPLOYEE_WISE_TICKET_AVERAGE_CLOSING_TIME(commonurl.getUrl()+"/ticketreport/getEmployeeWiseTicketAverageClosingTime.spring"),
	GET_EMPLOYEE_WISE_TICKET_AVERAGE_CLOSING_TIME_DETAILS(commonurl.getUrl()+"/ticketreport/getEmployeeWiseTicketAverageClosingAndReplyTimeDtls.spring"),
	GET_UNRESPONDING_TICKETS(commonurl.getUrl()+"/ticketreport/getUnRespondingTickets.spring"),
	GET_TICKETING_DASHBORARD_DETAILS(commonurl.getUrl()+"/ticketreport/getTicketingDashBoardDetails.spring"),
	GET_ALL_FEEDBACK_WISE_TICKETS(commonurl.getUrl()+"/ticketreport/getAllFeedbackWiseTickets.spring"),
	GET_TICKET_OWNERS(commonurl.getUrl()+"/ticketreport/getTicketOwners.spring"),
	GET_SPECIFIC_TICKET_REPORT(commonurl.getUrl()+"/ticketreport/getSpecificTicketReport.spring"),
	GET_TICKET_DETAILS_REPORT(commonurl.getUrl()+"/ticketreport/getTicketDetailsReport.spring"),
	GET_ESCALATION_LEVEL_DETAILS(commonurl.getUrl()+"/ticketreport/getEscalationLevelDetails.spring"),
	GET_PROJECT_WISE_CLOSED_TICKETS_COUNT(commonurl.getUrl()+"/ticketreport/getProjectWiseClosedTicketCount.spring"),
	
	/* Appointment Booking */
	GET_APPOINTMENT_BOOKING_DETAILS(commonurl.getUrl()+"/appointmentBooking/getAppointmentBookingDetails.spring"),
	SAVE_APPOINTMENT_TIME_SLOTS(commonurl.getUrl()+"/appointmentBooking/saveAppointmentTimeslots.spring"),
	GET_APPOINTMENT_TIME_SLOTS(commonurl.getUrl()+"/appointmentBooking/getAppointmentTimeslots.spring"),
	GET_ALL_APPOINTMENT_BOOKING_DETAILS(commonurl.getUrl()+"/appointmentBooking/getAllAppointmentBookingDetails.spring"),
	UPDATE_APPOINTMENT_BOOKING_STATUS(commonurl.getUrl()+"/appointmentBooking/updateAppointmentBookingStatus.spring"),
	UPDATE_APPOINTMENT_SUMMARY(commonurl.getUrl()+"/appointmentBooking/updateAppointmentSummary.spring"),
	GET_COMPLETED_APPOINTMENT_BOOKING_DETAILS(commonurl.getUrl()+"/appointmentBooking/getCompletedAppointmentBookingDetails.spring"),
	
	/*ApplyLoan API*/
	getLoanAppliedLeadDetailsList(commonurl.getUrl()+"/loan/getLoanAppliedLeadDetailsList.spring"),
	loadLoanAppliedLeadDetailsById(commonurl.getUrl()+"/loan/loadLoanAppliedLeadDetailsById.spring"),
	updateBankerLeadSeenStatus(commonurl.getUrl()+"/loan/updateBankerLeadSeenStatus.spring"),
	updateApplyLoanLeadDetails(commonurl.getUrl()+"/loan/updateApplyLoanLeadDetails.spring"),
	//GET_BANKER_LIST(commonurl.getUrl()+"/loan/GET_BANKER_LIST.spring"),
	
	/* Project Details */
	GET_BLOCK_DETAILS(commonurl.getUrl()+"/block/getBlocks.spring"),
	GET_FLOOR_DETAILS(commonurl.getUrl()+"/floor/getFloors.spring"),
	GET_FLAT_DETAILS(commonurl.getUrl()+"/flat/getFlats.spring"),
	GET_BOOKING_FLATS(commonurl.getUrl()+"/flat/getBookingFlats.spring"),
	
	/* Car Parking Allotment */
	GET_CAR_PARKING_BASEMENT_DETAILS(commonurl.getUrl()+"/carParkingAllotment/getCarParkingBasementDetails.spring"),
	SAVE_CAR_PARKING_ALLOTMENT_DETAILS(commonurl.getUrl()+"/carParkingAllotment/saveCarParkingAllotmentDetails.spring"),
	GET_ALL_CAR_PARKING_ALLOTMENT_DETAILS(commonurl.getUrl()+"/carParkingAllotment/getAllCarParkingAllotmentDetails.spring"),
	GET_CUSTOMER_CAR_PARKING_ALLOTMENT_DETAILS(commonurl.getUrl()+"/carParkingAllotment/getCustomerCarParkingAllotmentDetails.spring"),
	UPDATE_CAR_PARKING_ALLOTMENT_STATUS(commonurl.getUrl()+"/carParkingAllotment/updateCarParkingAllotmentStatus.spring"),
	APPROVE_OR_REJECT_CAR_PARKING_ALLOTMENT(commonurl.getUrl()+"/carParkingAllotment/approveOrRejectAllotment.spring"),
	CAR_PARKING_ALLOTMENT_LETTER_PDF_PREVIEW(commonurl.getUrl()+"/carParkingAllotment/allotmentLetterPdfPreview.spring"),
	
	/* Lead Customer start service */
	GET_CUSTOMER_LEAD_VIEW(commonurl.getUrl()+"/customerLead/customerLeadView.spring"),
	SAVE_CUSTOMER_LEAD(commonurl.getUrl()+"/customerLead/customerLeadSave.spring"),
	SAVE_CUSTOMER_LEAD_SITE_VISIT(commonurl.getUrl()+"/customerLead/customerLeadSiteVisitSave.spring"),
	DELETE_CUSTOMER_LEAD(commonurl.getUrl()+"/customerLead/customerLeadDelete.spring"),
	GET_CUSTOMER_SOURCE(commonurl.getUrl()+"/customerLead/customerSource.spring"),
	GET_CUSTOMER_PROJECT_PREFERRED_LOCATION(commonurl.getUrl()+"/customerLead/customerProjectPreferedLocation.spring"),
	GET_CUSTOMER_TIME_FRAME_TO_PURCHASE(commonurl.getUrl()+"/customerLead/customerTimeFrameToPurchase.spring"),
	GET_CUSTOMER_HOUSING_REQUIREMENT(commonurl.getUrl()+"/customerLead/customerHousingRequirement.spring"),
	GET_CUSTOMER_LEAD_SUB_STATUS(commonurl.getUrl()+"/customerLead/customerLeadSubStatus.spring"),
	GET_CUSTOMER_MARKETING_TYPE(commonurl.getUrl()+"/customerLead/customerMarketingType.spring"),
	GET_CUSTOMER_INACTIVE_REMARKS(commonurl.getUrl()+"/customerLead/customerInactiveRemarks.spring"),
	GET_CUSTOMER_LEAD_COMMENTS(commonurl.getUrl()+"/customerLead/customerLeadComments.spring"),
	GET_CUSTOMER_LEAD_SITE_VISIT_LIST(commonurl.getUrl()+"/customerLead/customerLeadSiteVisitList.spring"),
	GET_CUSTOMER_LEAD_MIS_COUNT(commonurl.getUrl()+"/customerLead/customerLeadMISCount.spring"),
	GET_CUSTOMER_LEAD_MIS(commonurl.getUrl()+"/customerLead/customerLeadMIS.spring"),
	
	;
	/* Lead Customer End service */
	
	private String url;

	private EmployeeServiceUrls(String url) {
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
