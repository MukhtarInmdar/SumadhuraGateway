/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.enums;


/*
* Module enum provides Module  codes.
* 
* @author Venkat_Koniki
* @since 05.07.2019
* @time 02:59PM
*/

public enum Module {
	TICKETING(1L,"TICKETING"),
	LOGIN(2L,"LOGIN"),
	CUSTOMER_DETAILS(3L,"Customer Details"),
	//VIEW_CUSTOMERS(4l,"View Customers"),
	MILESTONES(5l, "Milestones"), 
	COMPANY_NOTIFICATIONS(6l, "Company  Notifications"), 
	DEMAND_NOTES(7l, "Demand Note"), 
	PROJECT_NOTIFICATIONS(8l, "Project Notifications"),
	TRANSACTIONS(9l, "Transactions"), 
	//TDS(10l, "Tds"),
	//INVOICES(11l, "Invoices"),
	REPORT(12l, "Report"),
	//UPDATE(13l, "Update"),
	MPR(14l, "MPR"),
	APPLY_LOAN(14l, "Apply Loan"),
	;
	
	private Long id;
	private String name;
	
	private Module(Long id, String name) {
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
