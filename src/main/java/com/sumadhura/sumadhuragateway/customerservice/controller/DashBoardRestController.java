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

import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.DashBoard;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;


/**
 * DashBoardRestController class provides DashBoard specific services.
 * 
 * @author Venkat_Koniki
 * @since 17.04.2019
 * @time 03:18PM
 */



@RestController
@RequestMapping(value = "/customerservice/dashboard")
public class DashBoardRestController {


	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("CustomerSessionHandlerImpl")
	private SessionHandler customerSessionHandlerImpl;
	
	private final  Logger logger = Logger.getLogger(DashBoardRestController.class);
	
	@RequestMapping(value = "/dashboardCarousel.spring", method = RequestMethod.POST, produces = "application/json",consumes="application/json")
	public Result getDashBoardCarousel(@RequestBody DashBoard dashBoard) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		logger.info("******* The control inside of the getDashBoardCarousel service in  DashBoardRestController ******"
				+ dashBoard.getDeviceToken());
		
		if (dashBoard.getSessionKey() != null) {
			String sessionKey = sessionUtils.decryptSessionKey(dashBoard.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,dashBoard.getDeviceToken());
			logger.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				dashBoard.setFlatId(customerSession.getFlat().getFlatId());
				dashBoard.setDeviceToken(dashBoard.getDeviceToken());
				
				 dashBoard = (DashBoard) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customerdashboardcarousel.getUrl(), dashBoard, DashBoard.class);
				return dashBoard;
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		
	}
		
	
	@RequestMapping(value = "/paymentDetails.spring", method = RequestMethod.POST, produces = "application/json",consumes="application/json")
	public Result getPaymentDetails(@RequestBody DashBoard dashBoard) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		logger.info("******* The control inside of the getPaymentDetails service in  DashBoardRestController ******"
				+ dashBoard.getDeviceToken());
		
		if (dashBoard.getSessionKey() != null) {
			String sessionKey = sessionUtils.decryptSessionKey(dashBoard.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,dashBoard.getDeviceToken());
			logger.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				dashBoard.setFlatId(customerSession.getFlat().getFlatId());
				dashBoard.setDeviceToken(dashBoard.getDeviceToken());
				
				 dashBoard = (DashBoard) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customerdashboardpaymentdetails.getUrl(), dashBoard, DashBoard.class);
				return dashBoard;
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value = "/getPropertyWork.spring", method = RequestMethod.POST, produces = "application/json",consumes="application/json")
	public Result getPropertyWork(@RequestBody DashBoard dashBoard) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		logger.info("******* The control inside of the getPropertyWork service in  DashBoardRestController ******"
				+ dashBoard.getDeviceToken());
		
		if (dashBoard.getSessionKey() != null) {
			String sessionKey = sessionUtils.decryptSessionKey(dashBoard.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,dashBoard.getDeviceToken());
			logger.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				dashBoard.setFlatId(customerSession.getFlat().getFlatId());
				dashBoard.setDeviceToken(dashBoard.getDeviceToken());
				
				 dashBoard = (DashBoard) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customerdashboardpropertyWork.getUrl(), dashBoard, DashBoard.class);
				return dashBoard;
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
