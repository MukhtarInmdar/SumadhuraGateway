/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.enums;

/*
* Module enum provides Module  codes.
* 
* @author Venkat_Koniki
* @since 05.07.2019
* @time 03:05PM
*/
public enum SubModule {

	VIEW_ALL_TICKETS(1L,"View All Tickets"),
	VIEW_MY_TICKETS(2L,"View My Tickets"),
	VIEW_INFO_REQUEST(3L,"View Info Request"),
	CREATE_NOTIFICATIONS(4L,"Create Notifications"),
	VIEW_APPROVE_NOTIFICATIONS(5L,"View & Approve Notifications"),
	CREATE_CUSTOMER(6L,"Create Customer"),
	APPROVED_CUSTOMER(7L,"Approve Customer"),
	VIEW_ALL_CUSTOMERS(8L,"View All Customers"),
	LEAVE_UPDATE(13L,"Leave Update"),
	APPROVE_ESCALATION_TIME(14L,"Approve Escalation Time"),
	ESCALATION_TICKETS(11L,"Escalation Tickets"),
	CHANGE_TICKET_OWNER(15L, "Change Ticket Owner"),
	CREATE_MILESTONES(17L, "Create Milestones"),
	SEND_NOTIFICATION(18L, "Send Notification"), 
	VIEW_DEMAND_NOTES(19l, "View Demand Notes"), 
	VIEW_MILESTONES(20l, "View Milestones"), 
	SEND_NOTIFICATIONS(21L, "Send Notification"),
	VIEW_NOTIFICATIONS(22L, "View Notifications"), 
	GENERATE_DEMAND_NOTE(23L, "Generate Demand Note"), 
	SUSPENSE_ENTRY(24L, "Suspense Entry"), 
	VIEW_SUSPENSE_ENTRIES(25L, "View Suspense Entries"), 
	RECEIPT_CHEQUE(26L, "Receipt Cheque"), 
	INITIATE_PAYMENT(27L, "Initiate Payment"), 
	LEGAL_INVOICE(28L, "Legal Invoice"), 
	MODIFICATION_INVOICE(29L, "Modification Invoice"), 
	REGENERATE_DEMAND_NOTE(30L, "Regenerate Demand Note"), 
	UPDATE_TDS(31L, "Update TDS"), 
	COMP_SEND_NOTIFICATIONS(32L, "Send Notification"),
	COMP_VIEW_NOTIFICATIONS(33L, "View All Notifications"),
	/* CUG and LIVE */
	VIEW_CLOSED_TICKETS(16L, "View Closed Tickets"),
	/* UAT */
	//VIEW_CLOSED_TICKETS(37L, "View Closed Tickets"),
	COMP_VIEW_NOTIFICATIONS_FOR_APPROVALS(38L, "View Notifications For Approvals"), 
	COMP_VIEW_NOTIFICATIONS_FOR_MODIFICATIONS(39L, "View Notifications For Modifications"), 
	VIEW_NOTIFICATIONS_FOR_APPROVALS(40L, "View Notifications For Approvals"), 
	VIEW_NOTIFICATIONS_FOR_MODIFICATIONS(41L, "View Notifications For Modifications"),
    CHANGE_TICKET_TYPE(40L,"change Ticket Type"),
    CUSTOMER_LEDGER(42L, "Customer Ledger"),
    //UPDATE_INTEREST_RATES(44L, "Update Interest Rates"),
    RATE_OF_INTEREST(44L, "Rate of Interest"),
    VIEW_COMPLETED_TRANSACTIONS(45l,"View Completed Transactions"),
    PENDING_TRANSACTIONS_FOR_APPROVAL(46l,"Pending Transactions For Approval"),
    VIEW_TEMPORARY_TRANSACTIONS_STATUS(47l,"View Pending transaction status"),
    UNCLEARED_CHEQUE(48l,"Uncleared Cheque"),
    VIEW_MPR(49l, "View MPR"),
    VIEW_MY_COMPLAINTS(57L,"View Complaints"),
    MODIFY_TRANSACTION(70L,"Modify Transaction"),
    MODIFY_SUSPENSE_ENTRY(71L,"Modify Suspense Entry"),
	VIEW_INTEREST_LETTER(58l,"View Interest Letter"),
	TICKET_GRAPHS(74l, "Ticket Graphs"),
    VIEW_INTEREST_WAIVER_REQUEST_FOR_APPROVAL(75l,"View Interest Waiver Request for Approval"),
    VIEW_INTEREST_WAIVER_REQUEST_STATUS(76l,"View Interest Waiver Request Status"),
    CLEARED_TRANSACTION_REPORT(84l,"Cleared Transaction Report"),
    CLEARED_UNCLEARED_TRANSACTION_REPORT(86l,"Cleared Transaction Report"),
    VIEW_LEAD_DETAILS(86l,"View Lead Details"),
    Ticket_Avg_Close_Time_Report(88l,"Ticket Avg Closing Time Report"),
	;	
	
	/*VIEW_FORMS_LIST(6L,"Create Customer"),
	VIEW_BOOKING_FORM(7L,"VIEW BOOKING FORM"),
	SAVE_BOOKING_FORM(8L,"SAVE BOOKING FORM"),
	ACTION_BOOKING_FORM(9L,"ACTION BOOKING FORM");*/
	
	
	private Long id;
	private String name;
	
	private SubModule(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
