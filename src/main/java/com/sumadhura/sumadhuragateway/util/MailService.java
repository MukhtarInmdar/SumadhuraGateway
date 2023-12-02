package com.sumadhura.sumadhuragateway.util;

import com.sumadhura.sumadhuragateway.dto.Email;

public interface MailService {

	public void sendEmail(final Email email);
	public void sendErrorMail(final Exception ex);
	
	
}
