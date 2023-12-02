package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * @author VENKAT 
 * DATE 26-FEB-2019 
 * TIME 11.02 AM
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class DashBoard extends Result implements Serializable{

	private static final long serialVersionUID = 1819337284827879338L;
	private String deviceToken;
	private Long FlatId;
	private String completedAmount;
	private String incompletedAmount;
	//private Integer noOfTerms;
	//private Integer noOfTermsCompleted;
	private List<PaymentScheduleDTO> noOfTerms;
	private List<PaymentScheduleDTO> noOfTermsCompleted;
	private Date nextExpectedDate;
	private Double paymentPercentage;
	private String dashBoardCarousel;
	private List<String> dashBoardCarouselImages;
	private Integer constructionPercentage;
	List<ConstructionStageDTO> completedConstructionStageDTOLIST ;
	List<ConstructionStageDTO> incompletedConstructionStageDTOLIST;

	
	/**
	 * @return the deviceToken
	 */
	public String getDeviceToken() {
		return deviceToken;
	}
	/**
	 * @param deviceToken the deviceToken to set
	 */
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	/**
	 * @return the flatId
	 */
	public Long getFlatId() {
		return FlatId;
	}
	/**
	 * @param flatId the flatId to set
	 */
	public void setFlatId(Long flatId) {
		FlatId = flatId;
	}
	/**
	 * @return the completedAmount
	 */
	public String getCompletedAmount() {
		return completedAmount;
	}
	/**
	 * @param completedAmount the completedAmount to set
	 */
	public void setCompletedAmount(String completedAmount) {
		this.completedAmount = completedAmount;
	}
	/**
	 * @return the incompletedAmount
	 */
	public String getIncompletedAmount() {
		return incompletedAmount;
	}
	/**
	 * @param incompletedAmount the incompletedAmount to set
	 */
	public void setIncompletedAmount(String incompletedAmount) {
		this.incompletedAmount = incompletedAmount;
	}

	/**
	 * @return the nextExpectedDate
	 */
	public Date getNextExpectedDate() {
		return nextExpectedDate;
	}
	/**
	 * @param nextExpectedDate the nextExpectedDate to set
	 */
	public void setNextExpectedDate(Date nextExpectedDate) {
		this.nextExpectedDate = nextExpectedDate;
	}
	/**
	 * @return the paymentPercentage
	 */
	public Double getPaymentPercentage() {
		return paymentPercentage;
	}
	/**
	 * @param paymentPercentage the paymentPercentage to set
	 */
	public void setPaymentPercentage(Double paymentPercentage) {
		this.paymentPercentage = paymentPercentage;
	}
	
	/**
	 * @return the noOfTerms
	 */
	public List<PaymentScheduleDTO> getNoOfTerms() {
		return noOfTerms;
	}
	/**
	 * @param noOfTerms the noOfTerms to set
	 */
	public void setNoOfTerms(List<PaymentScheduleDTO> noOfTerms) {
		this.noOfTerms = noOfTerms;
	}
	/**
	 * @return the noOfTermsCompleted
	 */
	public List<PaymentScheduleDTO> getNoOfTermsCompleted() {
		return noOfTermsCompleted;
	}
	/**
	 * @param noOfTermsCompleted the noOfTermsCompleted to set
	 */
	public void setNoOfTermsCompleted(List<PaymentScheduleDTO> noOfTermsCompleted) {
		this.noOfTermsCompleted = noOfTermsCompleted;
	}
	/**
	 * @return the dashBoardCarousel
	 */
	public String getDashBoardCarousel() {
		return dashBoardCarousel;
	}
	/**
	 * @param dashBoardCarousel the dashBoardCarousel to set
	 */
	public void setDashBoardCarousel(String dashBoardCarousel) {
		this.dashBoardCarousel = dashBoardCarousel;
	}
	/**
	 * @return the dashBoardCarouselImages
	 */
	public List<String> getDashBoardCarouselImages() {
		return dashBoardCarouselImages;
	}
	/**
	 * @param dashBoardCarouselImages the dashBoardCarouselImages to set
	 */
	public void setDashBoardCarouselImages(List<String> dashBoardCarouselImages) {
		this.dashBoardCarouselImages = dashBoardCarouselImages;
	}
	
	/**
	 * @return the constructionPercentage
	 */
	public Integer getConstructionPercentage() {
		return constructionPercentage;
	}
	/**
	 * @param constructionPercentage the constructionPercentage to set
	 */
	public void setConstructionPercentage(Integer constructionPercentage) {
		this.constructionPercentage = constructionPercentage;
	}
	/**
	 * @return the completedConstructionStageDTOLIST
	 */
	public List<ConstructionStageDTO> getCompletedConstructionStageDTOLIST() {
		return completedConstructionStageDTOLIST;
	}
	/**
	 * @param completedConstructionStageDTOLIST the completedConstructionStageDTOLIST to set
	 */
	public void setCompletedConstructionStageDTOLIST(List<ConstructionStageDTO> completedConstructionStageDTOLIST) {
		this.completedConstructionStageDTOLIST = completedConstructionStageDTOLIST;
	}
	/**
	 * @return the incompletedConstructionStageDTOLIST
	 */
	public List<ConstructionStageDTO> getIncompletedConstructionStageDTOLIST() {
		return incompletedConstructionStageDTOLIST;
	}
	/**
	 * @param incompletedConstructionStageDTOLIST the incompletedConstructionStageDTOLIST to set
	 */
	public void setIncompletedConstructionStageDTOLIST(List<ConstructionStageDTO> incompletedConstructionStageDTOLIST) {
		this.incompletedConstructionStageDTOLIST = incompletedConstructionStageDTOLIST;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DashBoard [deviceToken=" + deviceToken + ", FlatId=" + FlatId + ", completedAmount=" + completedAmount
				+ ", incompletedAmount=" + incompletedAmount + ", noOfTerms=" + noOfTerms + ", noOfTermsCompleted="
				+ noOfTermsCompleted + ", nextExpectedDate=" + nextExpectedDate + ", paymentPercentage="
				+ paymentPercentage + ", dashBoardCarousel=" + dashBoardCarousel + ", dashBoardCarouselImages="
				+ dashBoardCarouselImages + ", constructionPercentage=" + constructionPercentage
				+ ", completedConstructionStageDTOLIST=" + completedConstructionStageDTOLIST
				+ ", incompletedConstructionStageDTOLIST=" + incompletedConstructionStageDTOLIST + "]";
	}
	
	
}
