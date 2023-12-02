package com.sumadhura.sumadhuragateway.util;
/*
* 
* Department enum provides different department codes.
* 
* @author Venkat_Koniki
* @since 30.04.2019
* @time 10:25AM
*/

public enum Department {
	
	CRM(101l,"CRM"),
	TECHCRM(102l,"TECHCRM"),
	PRESLAES(103l,"PRESLAES"),
	SALES(104l,"SLAES"),
	LEGAL(105l,"LEGAL"),
	MIS(106l,"MIS"),
	ACCOUNTS(107l,"ACCOUNTS"),
	ADMIN(108L,"ADMIN"),
	CUSTOMER(7L,"CUSTOMER"),
	DEPARTMENT(9L,"DEPARTMENT"),
	EMPLOYEE(8L,"EMPLOYEE"),
	SYSTEM(6L,"SYSTEM"),
	PM(4L,"PM")
/*	CRM(995l,"CRM"),
	TECHCRM(994l,"TECHCRM"),
	PRESLAES(993l,"PRESLAES"),
	SALES(992l,"SALES"),
	LEGAL(991l,"LEGAL"),
	MIS(990l,"MIS"),
	ACCOUNTS(997l,"ACCOUNTS"),
	ADMIN(989l,"ADMIN"),*/
	
	;
	
	private Department(Long id, String name) {
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
