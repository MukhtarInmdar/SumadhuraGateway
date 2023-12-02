package com.sumadhura.sumadhuragateway.dto;

public class MileStoneTaxDtlsInfo {
	
	private String name;
	private String amount;
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
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MileStoneTaxDtlsInfo [name=" + name + ", amount=" + amount + "]";
	}
	
	

}
