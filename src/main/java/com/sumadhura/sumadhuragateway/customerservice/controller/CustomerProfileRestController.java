package com.sumadhura.sumadhuragateway.customerservice.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.CustomerWrapper;
import com.sumadhura.sumadhuragateway.dto.Login;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;

/**
 * CustomerProfileRestController class provides CustomerProfile specific
 * services.
 * 
 * @author Venkat_Koniki
 * @since 05.02.2019
 * @time 06.50 PM
 */

@RestController
@RequestMapping(value = "/customerservice/profile")
public class CustomerProfileRestController {

	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("CustomerSessionHandlerImpl")
	private SessionHandler customerSessionHandlerImpl;

	private final static Logger logger = Logger.getLogger(CustomerProfileRestController.class);

	@RequestMapping(value = "/custProfileDtls.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result getCustProfileDtls(@RequestBody Login login)
			throws SessionTimeoutException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException {
		logger.info(
				"******* The control inside of the getCustProfileDtls service in  CustomerProfileRestController ******"
						+ login.getDeviceId());
		CustomerWrapper customerWrapper = new CustomerWrapper();
		
		if(login.getSessionKey()!=null) {
		logger.info("**** The Customer sessionkey  is ****" + login.getSessionKey());
		String sessionKey = sessionUtils.decryptSessionKey(login.getSessionKey());
		boolean isSessionValid = sessionUtils.validateSession(sessionKey,login.getDeviceId());
		logger.info("**** The session validation info is ******" + isSessionValid);
		if (isSessionValid) {
			CustomerSession customer = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
			login.setDeviceId(customer.getDevice().getDeviceToken());
			login.setCustId(customer.getCustomerId());
			login.setFlatId(customer.getFlat().getFlatId());
			logger.info("*** The customer session object ****"+customer);
			customerWrapper = (CustomerWrapper) RestApiUtility.sendJSONPostRequest(
					CustomerServiceUrls.customerprofiledetails.getUrl(), login, CustomerWrapper.class);
		} else {
			throw new SessionTimeoutException("Your Session has been Timed Out.");
		}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		return customerWrapper;
	}

}
