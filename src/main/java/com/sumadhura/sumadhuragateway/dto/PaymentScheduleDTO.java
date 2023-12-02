package com.sumadhura.sumadhuragateway.dto;


import java.util.Date;

/**
 * @author VENKAT
 *  DATE 08-FEB-2019 
 *  TIME 05.25 PM
 */

public class PaymentScheduleDTO {

	private Long paySchedId;
    private Long flatId;
    private Double termAmount;
	private Long paymentStatusId;
	private Date createdDate;
	private Date updatedDate;
	private Date dueDate;
	private Long taxId;
	private Long payStgMapId;
	private Double  gstAmount;
	private Double totalPaymentPersentage;
	private Double balanceAmount;
	private String demandNote;
	private Date demandNoteCreatedDate;
	
	
	/**
	 * @return the paySchedId
	 */
	public Long getPaySchedId() {
		return paySchedId;
	}
	/**
	 * @param paySchedId the paySchedId to set
	 */
	public void setPaySchedId(Long paySchedId) {
		this.paySchedId = paySchedId;
	}
	/**
	 * @return the flatId
	 */
	public Long getFlatId() {
		return flatId;
	}
	/**
	 * @param flatId the flatId to set
	 */
	public void setFlatId(Long flatId) {
		this.flatId = flatId;
	}
	/**
	 * @return the termAmount
	 */
	public Double getTermAmount() {
		return termAmount;
	}
	/**
	 * @param termAmount the termAmount to set
	 */
	public void setTermAmount(Double termAmount) {
		this.termAmount = termAmount;
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
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}
	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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
	 * @return the taxId
	 */
	public Long getTaxId() {
		return taxId;
	}
	/**
	 * @param taxId the taxId to set
	 */
	public void setTaxId(Long taxId) {
		this.taxId = taxId;
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
	 * @return the gstAmount
	 */
	public Double getGstAmount() {
		return gstAmount;
	}
	/**
	 * @param gstAmount the gstAmount to set
	 */
	public void setGstAmount(Double gstAmount) {
		this.gstAmount = gstAmount;
	}
	/**
	 * @return the totalPaymentPersentage
	 */
	public Double getTotalPaymentPersentage() {
		return totalPaymentPersentage;
	}
	/**
	 * @param totalPaymentPersentage the totalPaymentPersentage to set
	 */
	public void setTotalPaymentPersentage(Double totalPaymentPersentage) {
		this.totalPaymentPersentage = totalPaymentPersentage;
	}
	/**
	 * @return the balanceAmount
	 */
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	/**
	 * @param balanceAmount the balanceAmount to set
	 */
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	/**
	 * @return the demandNote
	 */
	public String getDemandNote() {
		return demandNote;
	}
	/**
	 * @param demandNote the demandNote to set
	 */
	public void setDemandNote(String demandNote) {
		this.demandNote = demandNote;
	}
	/**
	 * @return the demandNoteCreatedDate
	 */
	public Date getDemandNoteCreatedDate() {
		return demandNoteCreatedDate;
	}
	/**
	 * @param demandNoteCreatedDate the demandNoteCreatedDate to set
	 */
	public void setDemandNoteCreatedDate(Date demandNoteCreatedDate) {
		this.demandNoteCreatedDate = demandNoteCreatedDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balanceAmount == null) ? 0 : balanceAmount.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((demandNote == null) ? 0 : demandNote.hashCode());
		result = prime * result + ((demandNoteCreatedDate == null) ? 0 : demandNoteCreatedDate.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + ((flatId == null) ? 0 : flatId.hashCode());
		result = prime * result + ((gstAmount == null) ? 0 : gstAmount.hashCode());
		result = prime * result + ((paySchedId == null) ? 0 : paySchedId.hashCode());
		result = prime * result + ((payStgMapId == null) ? 0 : payStgMapId.hashCode());
		result = prime * result + ((paymentStatusId == null) ? 0 : paymentStatusId.hashCode());
		result = prime * result + ((taxId == null) ? 0 : taxId.hashCode());
		result = prime * result + ((termAmount == null) ? 0 : termAmount.hashCode());
		result = prime * result + ((totalPaymentPersentage == null) ? 0 : totalPaymentPersentage.hashCode());
		result = prime * result + ((updatedDate == null) ? 0 : updatedDate.hashCode());
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
		PaymentScheduleDTO other = (PaymentScheduleDTO) obj;
		if (balanceAmount == null) {
			if (other.balanceAmount != null)
				return false;
		} else if (!balanceAmount.equals(other.balanceAmount))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (demandNote == null) {
			if (other.demandNote != null)
				return false;
		} else if (!demandNote.equals(other.demandNote))
			return false;
		if (demandNoteCreatedDate == null) {
			if (other.demandNoteCreatedDate != null)
				return false;
		} else if (!demandNoteCreatedDate.equals(other.demandNoteCreatedDate))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (flatId == null) {
			if (other.flatId != null)
				return false;
		} else if (!flatId.equals(other.flatId))
			return false;
		if (gstAmount == null) {
			if (other.gstAmount != null)
				return false;
		} else if (!gstAmount.equals(other.gstAmount))
			return false;
		if (paySchedId == null) {
			if (other.paySchedId != null)
				return false;
		} else if (!paySchedId.equals(other.paySchedId))
			return false;
		if (payStgMapId == null) {
			if (other.payStgMapId != null)
				return false;
		} else if (!payStgMapId.equals(other.payStgMapId))
			return false;
		if (paymentStatusId == null) {
			if (other.paymentStatusId != null)
				return false;
		} else if (!paymentStatusId.equals(other.paymentStatusId))
			return false;
		if (taxId == null) {
			if (other.taxId != null)
				return false;
		} else if (!taxId.equals(other.taxId))
			return false;
		if (termAmount == null) {
			if (other.termAmount != null)
				return false;
		} else if (!termAmount.equals(other.termAmount))
			return false;
		if (totalPaymentPersentage == null) {
			if (other.totalPaymentPersentage != null)
				return false;
		} else if (!totalPaymentPersentage.equals(other.totalPaymentPersentage))
			return false;
		if (updatedDate == null) {
			if (other.updatedDate != null)
				return false;
		} else if (!updatedDate.equals(other.updatedDate))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PaymentScheduleDTO [paySchedId=" + paySchedId + ", flatId=" + flatId + ", termAmount=" + termAmount
				+ ", paymentStatusId=" + paymentStatusId + ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + ", dueDate=" + dueDate + ", taxId=" + taxId + ", payStgMapId=" + payStgMapId
				+ ", gstAmount=" + gstAmount + ", totalPaymentPersentage=" + totalPaymentPersentage + ", balanceAmount="
				+ balanceAmount + ", demandNote=" + demandNote + ", demandNoteCreatedDate=" + demandNoteCreatedDate
				+ "]";
	}
	
}

