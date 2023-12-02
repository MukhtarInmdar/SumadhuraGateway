package com.sumadhura.sumadhuragateway.employeeservice.enums;

/*
* Status enum provides different Status codes.
* 
* @author Venkat_Koniki
* @since 04.04.2019
* @time 11:12PM
*/
public enum Status {
	
	ALL(0L,"all"),
	INCOMPLETE(1L,"incomplete"),
	COMPLETED(2L,"complete"),
	ENQUIRED(3L,"enquired"),
	INTERESTED(4L,"interested"),
	PARTIAL(5L,"partially intrested"),
	ACTIVE(6L,"active"),
	INACTIVE(7L,"inactive"),
	CLEARED(8L,"Cleared"),
	INPROGRESS(9L,"Inprogress"),
	RESOLVED(10L,"Resolved"),
	CLOSED(11L,"Closed"),
	OPEN(12L,"Open"),
	PUBLIC(13L,"Public"),
	APPROVED(14L,"Approved"),
	NOTAPPROVED(15L,"NotApproved"),
	REJECTED(16L,"Rejected"),
	ESCALATED(17L,"escalated"),
	TRUE(18L,"true"),
	FALSE(19L,"false"),
	PENDING(20L,"pending"),
	YES(21L,"yes"),
	NO(22L,"no")
	;
	public Long status;
	public String description;

	Status(Long status,String description) {
		this.status = status;
		this.description = description;
	}

	/**
	 * @return the status
	 */
	public Long getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
