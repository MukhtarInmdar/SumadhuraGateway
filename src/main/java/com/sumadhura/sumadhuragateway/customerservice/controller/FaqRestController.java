/**
 * 
 */
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

import com.sumadhura.sumadhuragateway.dto.Device;
import com.sumadhura.sumadhuragateway.dto.FaqWrapper;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;

/**
 * FaqRestController class provides Frequently Asking Questions specific services.
 * 
 * @author Venkat_Koniki
 * @since 17.04.2019
 * @time 12:43PM
 */

@RestController
@RequestMapping(value = "/customerservice/faqs")
public class FaqRestController {

	
	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("CustomerSessionHandlerImpl")
	private SessionHandler customerSessionHandlerImpl;
	
	
	private final  Logger logger = Logger.getLogger(FaqRestController.class);
	
	@RequestMapping(value = "/listOfFaqs.spring", method = RequestMethod.POST, produces = "application/json")
	public Result getlistOfFaqs(@RequestBody Device device) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {

		logger.info("******* The control inside of the getlistOfFaqs service in FaqRestController **********"+device.getDeviceToken());
		
		if (device.getSessionKey() != null) {
			String sessionKey = sessionUtils.decryptSessionKey(device.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,device.getDeviceToken());
			logger.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				FaqWrapper faqWrapper = (FaqWrapper) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customerfaqdetails.getUrl(), device, FaqWrapper.class);
				return faqWrapper;
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		
	}
	
}
