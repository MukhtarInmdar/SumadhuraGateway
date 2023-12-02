package com.sumadhura.sumadhuragateway.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class FAQ {

	private Integer processId;
	private String processName;
	private List<QuestionAndAnswer> qaList;
	public Integer getProcessId() {
		return processId;
	}
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public List<QuestionAndAnswer> getQaList() {
		return qaList;
	}
	public void setQaList(List<QuestionAndAnswer> qaList) {
		this.qaList = qaList;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FAQ [processId=" + processId + ", processName=" + processName + ", qaList=" + qaList + "]";
	}
	
}
