package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MileStone implements Serializable{
	private static final long serialVersionUID = -90111482896543171L;
	private Long milStoneNo;
	private String mileStoneName;
	private String mileStoneAmount;
	private Date dueDate;
	private Long msStatusId;
	private Double milStonePercentage;
	private String mileStoneStatus;
	private Double paymentPercentageInMileStone;
	private String totalPenalityAmount;
	//private String totalPenalityAmount;
	private String totalPendingPenaltyAmount;
	private String totalPenalityPaidAmount;
	private String interestWaiverAdjAmount;
	private String interestWaiverPendingAmount;//interest waiver amount to approve
	private Long paymentScheduleId;
	private Long paymentStatusId;
	private Long payStgMapId;
	private Long projectMilestoneId;
	private Timestamp mileStoneDate;
	private Timestamp demandNoteDate;
	private String mileStonePaidAmount;
	private String milestoneAmountDue;
	private Timestamp milestoneLastReceiptDate;
	private Integer daysDiffInDN_Date_AndLastReceipt;
	private Long finMilestoneClassifidesId;
	private String documentLocation;
	private String documentName;
	private String daysDelayed;
	private Long siteId;
	private String siteName;
	


	public String getInterestWaiverPendingAmount() {
		return interestWaiverPendingAmount;
	}
	public void setInterestWaiverPendingAmount(String interestWaiverPendingAmount) {
		this.interestWaiverPendingAmount = interestWaiverPendingAmount;
	}
	public Long getMsStatusId() {
		return msStatusId;
	}
	public void setMsStatusId(Long msStatusId) {
		this.msStatusId = msStatusId;
	}
	public String getTotalPendingPenaltyAmount() {
		return totalPendingPenaltyAmount;
	}
	public void setTotalPendingPenaltyAmount(String totalPendingPenaltyAmount) {
		this.totalPendingPenaltyAmount = totalPendingPenaltyAmount;
	}
	public String getTotalPenalityPaidAmount() {
		return totalPenalityPaidAmount;
	}
	public void setTotalPenalityPaidAmount(String totalPenalityPaidAmount) {
		this.totalPenalityPaidAmount = totalPenalityPaidAmount;
	}
	public String getInterestWaiverAdjAmount() {
		return interestWaiverAdjAmount;
	}
	public void setInterestWaiverAdjAmount(String interestWaiverAdjAmount) {
		this.interestWaiverAdjAmount = interestWaiverAdjAmount;
	}
	public String getDaysDelayed() {
		return daysDelayed;
	}
	public void setDaysDelayed(String daysDelayed) {
		this.daysDelayed = daysDelayed;
	}
	public Long getMilStoneNo() {
		return milStoneNo;
	}
	public String getTotalPenalityAmount() {
		return totalPenalityAmount;
	}
	public void setTotalPenalityAmount(String totalPenalityAmount) {
		this.totalPenalityAmount = totalPenalityAmount;
	}
	public Long getProjectMilestoneId() {
		return projectMilestoneId;
	}
	public void setProjectMilestoneId(Long projectMilestoneId) {
		this.projectMilestoneId = projectMilestoneId;
	}
	public Timestamp getMileStoneDate() {
		return mileStoneDate;
	}
	public void setMileStoneDate(Timestamp mileStoneDate) {
		this.mileStoneDate = mileStoneDate;
	}
	public Timestamp getDemandNoteDate() {
		return demandNoteDate;
	}
	public void setDemandNoteDate(Timestamp demandNoteDate) {
		this.demandNoteDate = demandNoteDate;
	}
	public String getMileStonePaidAmount() {
		return mileStonePaidAmount;
	}
	public void setMileStonePaidAmount(String mileStonePaidAmount) {
		this.mileStonePaidAmount = mileStonePaidAmount;
	}
	public String getMilestoneAmountDue() {
		return milestoneAmountDue;
	}
	public void setMilestoneAmountDue(String milestoneAmountDue) {
		this.milestoneAmountDue = milestoneAmountDue;
	}
	public Timestamp getMilestoneLastReceiptDate() {
		return milestoneLastReceiptDate;
	}
	public void setMilestoneLastReceiptDate(Timestamp milestoneLastReceiptDate) {
		this.milestoneLastReceiptDate = milestoneLastReceiptDate;
	}
	public Integer getDaysDiffInDN_Date_AndLastReceipt() {
		return daysDiffInDN_Date_AndLastReceipt;
	}
	public void setDaysDiffInDN_Date_AndLastReceipt(Integer daysDiffInDN_Date_AndLastReceipt) {
		this.daysDiffInDN_Date_AndLastReceipt = daysDiffInDN_Date_AndLastReceipt;
	}
	public Long getFinMilestoneClassifidesId() {
		return finMilestoneClassifidesId;
	}
	public void setFinMilestoneClassifidesId(Long finMilestoneClassifidesId) {
		this.finMilestoneClassifidesId = finMilestoneClassifidesId;
	}
	public String getDocumentLocation() {
		return documentLocation;
	}
	public void setDocumentLocation(String documentLocation) {
		this.documentLocation = documentLocation;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setMilStoneNo(Long milStoneNo) {
		this.milStoneNo = milStoneNo;
	}
	/**
	 * @return the payStgMapId
	 */
	public Long getPayStgMapId() {
		return payStgMapId;
	}
	/**
	 * @param payStgMapId the payStgMapId to set
	 */
	public void setPayStgMapId(Long payStgMapId) {
		this.payStgMapId = payStgMapId;
	}
	/**
	 * @return the paymentScheduleId
	 */
	public Long getPaymentScheduleId() {
		return paymentScheduleId;
	}
	/**
	 * @param paymentScheduleId the paymentScheduleId to set
	 */
	public void setPaymentScheduleId(Long paymentScheduleId) {
		this.paymentScheduleId = paymentScheduleId;
	}
	/**
	 * @return the paymentStatusId
	 */
	public Long getPaymentStatusId() {
		return paymentStatusId;
	}
	/**
	 * @param paymentStatusId the paymentStatusId to set
	 */
	public void setPaymentStatusId(Long paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}
	public Double getPaymentPercentageInMileStone() {
		return paymentPercentageInMileStone;
	}
	public void setPaymentPercentageInMileStone(Double paymentPercentageInMileStone) {
		this.paymentPercentageInMileStone = paymentPercentageInMileStone;
	}
	
	public String getMileStoneStatus() {
		return mileStoneStatus;
	}
	public void setMileStoneStatus(String mileStoneStatus) {
		this.mileStoneStatus = mileStoneStatus;
	}
	/**
	 * @return the mileStoneAmount
	 */
	public String getMileStoneAmount() {
		return mileStoneAmount;
	}
	/**
	 * @param mileStoneAmount the mileStoneAmount to set
	 */
	public void setMileStoneAmount(String mileStoneAmount) {
		this.mileStoneAmount = mileStoneAmount;
	}
	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	/**
	 * @return the milStonePercentage
	 */
	public Double getMilStonePercentage() {
		return milStonePercentage;
	}
	/**
	 * @param milStonePercentage the milStonePercentage to set
	 */
	public void setMilStonePercentage(Double milStonePercentage) {
		this.milStonePercentage = milStonePercentage;
	}
	
	/**
	 * @return the mileStoneName
	 */
	public String getMileStoneName() {
		return mileStoneName;
	}
	/**
	 * @param mileStoneName the mileStoneName to set
	 */
	public void setMileStoneName(String mileStoneName) {
		this.mileStoneName = mileStoneName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((milStonePercentage == null) ? 0 : milStonePercentage.hashCode());
		result = prime * result + ((mileStoneAmount == null) ? 0 : mileStoneAmount.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MileStone other = (MileStone) obj;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (milStonePercentage == null) {
			if (other.milStonePercentage != null)
				return false;
		} else if (!milStonePercentage.equals(other.milStonePercentage))
			return false;
		if (mileStoneAmount == null) {
			if (other.mileStoneAmount != null)
				return false;
		} else if (!mileStoneAmount.equals(other.mileStoneAmount))
			return false;
		return true;
	}
	
	
	
	
}
