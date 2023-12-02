package com.sumadhura.sumadhuragateway.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class SiteWrapper extends Result{

	private List<Site> siteList;
	
	private List<PropertyDetails> propertyDetailsList;
	
	public List<PropertyDetails> getPropertyDetailsList() {
		return propertyDetailsList;
	}

	public void setPropertyDetailsList(List<PropertyDetails> propertyDetailsList) {
		this.propertyDetailsList = propertyDetailsList;
	}

	public List<Site> getSiteList() {
		return siteList;
	}

	public void setSiteList(List<Site> siteList) {
		this.siteList = siteList;
	}

	
	
}
