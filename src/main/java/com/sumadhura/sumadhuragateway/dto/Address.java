package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;

public class Address extends Result implements Serializable{
	
	private static final long serialVersionUID = 8514476226662240951L;
	private Integer id;
	private String name;
	private String plotNo;
	private String floorNo;
	private String surveyNo;
	private String tower;
	private String nearby;
	private String street;
	private String area;
	private String city;
	private String district;
	private String state;
	private String pincode;
	private String contactNo;
	private String email;
	private String website;
	private String gstin;
	private String pan;
	private String cin;
	private String telephoneNo;
	private String crmMail;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlotNo() {
		return plotNo;
	}
	public void setPlotNo(String plotNo) {
		this.plotNo = plotNo;
	}
	public String getTower() {
		return tower;
	}
	public void setTower(String tower) {
		this.tower = tower;
	}
	public String getNearby() {
		return nearby;
	}
	public void setNearby(String nearby) {
		this.nearby = nearby;
	}
	
	public String getSurveyNo() {
		return surveyNo;
	}
	public void setSurveyNo(String surveyNo) {
		this.surveyNo = surveyNo;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStreet() {
		return street;
	}
	
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getGstin() {
		return gstin;
	}
	public void setGstin(String gstin) {
		this.gstin = gstin;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getCrmMail() {
		return crmMail;
	}
	public void setCrmMail(String crmMail) {
		this.crmMail = crmMail;
	}
	@Override
	public String toString() {
		return "Address [id=" + id + ", name=" + name + ", plotNo=" + plotNo + ", floorNo=" + floorNo + ", surveyNo="
				+ surveyNo + ", tower=" + tower + ", nearby=" + nearby + ", street=" + street + ", area=" + area
				+ ", city=" + city + ", district=" + district + ", state=" + state + ", pincode=" + pincode
				+ ", contactNo=" + contactNo + ", email=" + email + ", website=" + website + ", gstin=" + gstin
				+ ", pan=" + pan + ", cin=" + cin + ", telephoneNo=" + telephoneNo + ", crmMail=" + crmMail + "]";
	}
	
}
