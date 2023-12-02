package com.sumadhura.sumadhuragateway.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Email extends Result{

	private String fromMail;
	private String toMail;
	private String fromMailPassword;
	private String subject;
	private Exception exception;
	private String TemplateName;
	private String name;
	private Long mobileNo;
	private String custData;
	private String siteName;
	private boolean status;
	
	
	public String getCustData() {
		return custData;
	}
	public void setCustData(String custData) {
		this.custData = custData;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long  getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getTemplateName() {
		return TemplateName;
	}
	public void setTemplateName(String templateName) {
		TemplateName = templateName;
	}
	
	public String getSiteName() {
		return siteName;
	}
	
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getFromMail() {
		return fromMail;
	}
	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}
	public String getToMail() {
		return toMail;
	}
	public void setToMail(String toMail) {
		this.toMail = toMail;
	}
	public String getFromMailPassword() {
		return fromMailPassword;
	}
	public void setFromMailPassword(String fromMailPassword) {
		this.fromMailPassword = fromMailPassword;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Email [fromMail=" + fromMail + ", toMail=" + toMail + ", fromMailPassword=" + fromMailPassword
				+ ", subject=" + subject + ", exception=" + exception + ", TemplateName=" + TemplateName + ", name="
				+ name + ", mobileNo=" + mobileNo + ", custData=" + custData + ", siteName=" + siteName + ", status="
				+ status + "]";
	}

}
