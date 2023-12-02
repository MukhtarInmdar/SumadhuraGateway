/**
 * 
 */
package com.sumadhura.sumadhuragateway.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * @author Venkat_Koniki
 * @since 17.04.2019
 * @time 12:43PM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FaqWrapper extends Result implements Serializable{

	private static final long serialVersionUID = -2369544246998483445L;
	private List<FAQ> faqList;
	/**
	 * @return the faqList
	 */
	public List<FAQ> getFaqList() {
		return faqList;
	}
	/**
	 * @param faqList the faqList to set
	 */
	public void setFaqList(List<FAQ> faqList) {
		this.faqList = faqList;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FaqWrapper [faqList=" + faqList + "]";
	}
	
}
