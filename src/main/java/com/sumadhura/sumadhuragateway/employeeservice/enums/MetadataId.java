package com.sumadhura.sumadhuragateway.employeeservice.enums;

public enum MetadataId {

	SITE(1L,"SITE"),
	BLOCK(2L,"BLOCK"),
	FLOOR(3L,"FLOOR"),
	FLAT_BOOKING(4L,"FLAT_BOOKING"),
	NONCOUSTOMER(5L,"NONCOUSTOMER"),
	COMPANY_UPDATE(6L,"COMPANY_UPDATE"),
	CUSTOMER(7L,"CUSTOMER"),
	EMPLOYEE(8L,"EMPLOYEE"),
	DEPARTMENT(9L,"DEPARTMENT"),
	REFERENCES_CUSTOMER(12L,"REFERENCES_CUSTOMER"),
	CHANEL_PARTNER(10L,"CHANEL_PARTNER"),
	REFERENCES_FRIEND(11L,"REFERENCES_FRIEND"),
	APPLICANT1(13L,"APPLICANT1"),
	APPLICANT2(14L,"APPLICANT2"),
	APPLICANT3(16L,"APPLICANT3"),
	APPLICANT4(17L,"APPLICANT4"),
	APPLICANT5(18L,"APPLICANT5"),
	APPLICANT6(19L,"APPLICANT6"),
	APPLICANT7(20L,"APPLICANT7"),
	APPLICANT8(21L,"APPLICANT8"),
	APPLICANT9(22L,"APPLICANT9"),
	APPLICANT10(23L,"APPLICANT10"),
	REFRENCE_MASTER(15L,"REFRENCE_MASTER"),
	DEVICE(43L,"device"),
	COMPANY_NOTIFICATIONS(16L,"COMPANY_NOTIFICATIONS"),
	NONCUSTOMER_COMPANY_NOTIFICATIONS(48L,"NONCUSTOMER_COMPANY_NOTIFICATIONS")
	;
	
	private MetadataId(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	private Long id;
	private String name;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
