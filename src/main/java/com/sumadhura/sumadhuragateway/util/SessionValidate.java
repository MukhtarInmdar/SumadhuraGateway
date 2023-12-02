package com.sumadhura.sumadhuragateway.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;

@Component
public class SessionValidate {

	private final Logger LOGGER = Logger.getLogger(SessionValidate.class);
	
	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("EmployeeSessionHandlerImpl")
	private SessionHandler employeeSessionHandlerImpl;
	

	@Autowired
	@Qualifier("CustomerSessionHandlerImpl")
	private SessionHandler customerSessionHandlerImpl;
	
	public EmployeeSession sessionValidate(String cacheKey) throws SessionTimeoutException,InSufficeientInputException{
		String sessionKey="";
		EmployeeSession employeeSession=null;
		if(Util.isNotEmptyObject(cacheKey)) {
			LOGGER.info("**** The Employee sessionkey  is ****" + cacheKey);
			boolean isSessionValid=true;
			try {
				sessionKey = sessionUtils.decryptSessionKey(cacheKey);
				isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
					|InvalidAlgorithmParameterException | NoSuchPaddingException e) {
				
				e.printStackTrace();
			}
			
			if (!isSessionValid) {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
			
			employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
			//LoginResponse loginResponse = employeeSession.getLoginResponse();
		} else {
				List<String> errors = new ArrayList<String>();
				errors.add("Insufficeint Input is Given.");
				throw new InSufficeientInputException(errors);
		}
		return employeeSession;
	}

	public CustomerSession customerSessionValidate(String cacheKey,String deviceId) throws SessionTimeoutException,InSufficeientInputException{
		CustomerSession customerSession=null;
		if (cacheKey != null && deviceId != null) {
			LOGGER.info("**** The Customer sessionkey  is ****" + cacheKey);
			boolean isSessionValid =false;
			String sessionKey=null;
			try {
			sessionKey = sessionUtils.decryptSessionKey(cacheKey);
			isSessionValid = sessionUtils.validateSession(sessionKey, deviceId);
			LOGGER.info("**** The session validation info is ******" + isSessionValid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (!isSessionValid) {
				LOGGER.info("*** The customer session object ****" + customerSession);
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			} 
			customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		return customerSession;
	}
	
}
