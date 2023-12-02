/**
 * 
 */
package com.sumadhura.sumadhuragateway.customerservice.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.Customer;
import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.PropertyDetails;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.MessengerRequest;
import com.sumadhura.sumadhuragateway.employeeservice.dto.MessengerResponce;
import com.sumadhura.sumadhuragateway.employeeservice.enums.MetadataId;
import com.sumadhura.sumadhuragateway.employeeservice.enums.Site;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;
import com.sumadhura.sumadhuragateway.util.Util;

import lombok.NonNull;

/**
 * PropertyDetailsRestController class provides Property specific services.
 * 
 * @author Venkat_Koniki
 * @since 16.04.2019
 * @time 03:12PM
 */

@RestController
@RequestMapping(value = "/customerservice/propertyDetails")
public class PropertyDetailsRestController {

	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("CustomerSessionHandlerImpl")
	private SessionHandler customerSessionHandlerImpl;

	private final static Logger logger = Logger.getLogger(PropertyDetailsRestController.class);

	@RequestMapping(value = "/propertyDetails.spring", method = RequestMethod.POST, produces = "application/json")
	public Result getCustomerPropertyDetails(@RequestBody Customer customer) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		logger.info(
				"******* The control inside of the getCustomerPropertyDetails service in  PropertyDetailsRestController ******"
						+ customer.getDeviceId());
		if (customer.getSessionKey() != null) {
			String sessionKey = sessionUtils.decryptSessionKey(customer.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,customer.getDeviceId());
			logger.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				customer.setCustomerId(customerSession.getCustomerId());
				customer.setFlatId(customerSession.getFlat().getFlatId());
				customer.setDeviceId(customer.getDeviceId());
				PropertyDetails propertyDetails = (PropertyDetails) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customerpropertydetails.getUrl(), customer, PropertyDetails.class);
				return propertyDetails;
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}

	@RequestMapping(value = "/getCustomerFlatDocuments.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON,consumes =  MediaType.APPLICATION_JSON)
	public Result getCustomerFlatDocuments(@RequestBody @NonNull  MessengerRequest messengerRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		logger.info("***** Control inside the MessengerRestControlller.getCustomerFlatDocuments() ***** "+messengerRequest);
		Result result = null;
		if(Util.isNotEmptyObject(messengerRequest.getSessionKey()) && Util.isNotEmptyObject(messengerRequest.getRequestUrl()) && Util.isNotEmptyObject(messengerRequest.getFlatId())) {
			logger.info("**** The customer sessionkey  is ****" + messengerRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(messengerRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,messengerRequest.getDeviceToken());
			if (isSessionValid) {
				 CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				 logger.debug("*** The customerSession object ***"+customerSession);
				 /* Taking input parameters from the session  */
				 messengerRequest.setRecipientId(customerSession.getFlatBookingId());
				 messengerRequest.setRecipientType(MetadataId.FLAT_BOOKING.getId());
				 messengerRequest.setType("customer");
				 messengerRequest.setFlatBookingId(customerSession.getFlatBookingId());
				 messengerRequest.setFlatId(customerSession.getFlat().getFlatId());
				 result = (MessengerResponce) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getCustomerFlatDocuments.getUrl(), messengerRequest, MessengerResponce.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}

	
	@RequestMapping(value = "/propertyUploadedDocs.spring", method = RequestMethod.POST, produces = "application/json")
	public Result getPropertyUploadedDocs(@RequestBody Customer customer) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		logger.info(
				"******* The control inside of the getPropertyUploadedDocs service in  PropertyDetailsRestService controller ******");
		if (customer.getSessionKey() != null) {
			String sessionKey = sessionUtils.decryptSessionKey(customer.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,customer.getDeviceId());
			logger.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				customer.setCustomerId(customerSession.getCustomerId());
				customer.setFlatId(customerSession.getFlat().getFlatId());
				customer.setDeviceId(customerSession.getDevice().getDeviceToken());
				PropertyDetails propertyDetails = (PropertyDetails) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customerpropertyuploadeddocs.getUrl(), customer, PropertyDetails.class);
				return propertyDetails;
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}

	@RequestMapping(value = "/propertyAmenitiesCost.spring", method = RequestMethod.POST, produces = "application/json")
	public Result getPropertyAmenitiesCost(@RequestBody Customer customer) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		logger.info(
				"******* The control inside of the getPropertyAmenitiesCost service in  PropertyDetailsRestService controller ******");
		if (customer.getSessionKey() != null) {
			String sessionKey = sessionUtils.decryptSessionKey(customer.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,customer.getDeviceId());
			logger.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				customer.setCustomerId(customerSession.getCustomerId());
				customer.setFlatId(customerSession.getFlat().getFlatId());
				customer.setDeviceId(customerSession.getDevice().getDeviceToken());
				PropertyDetails propertyDetails = (PropertyDetails) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customerpropertyamenitiescost.getUrl(), customer, PropertyDetails.class);
				 Long siteId = customerSession.getFlat().getFloor().getBlock().getSite().getId(); 
				 if(Site.GARDENS_BY_THE_BROOK.getId().equals(siteId)) {
					 propertyDetails.setFlatDetailsInfoList(null);
					 return propertyDetails;
				 }
				return propertyDetails;
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	@RequestMapping(value = "/modificationDetails.spring", method = RequestMethod.POST, produces = "application/json")
	public Result getModificationDetails(@RequestBody Customer customer)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		logger.info(
				"******* The control inside of the getModificationDetails service in  PropertyDetailsRestService controller ******");
		if (customer.getSessionKey() != null) {
		String sessionKey = sessionUtils.decryptSessionKey(customer.getSessionKey());
		boolean isSessionValid = sessionUtils.validateSession(sessionKey,customer.getDeviceId());
		logger.info("**** The session validation info is ******" + isSessionValid);
		if (isSessionValid) {
			CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
			customer.setCustomerId(customerSession.getCustomerId());
			customer.setFlatId(customerSession.getFlat().getFlatId());
			customer.setDeviceId(customerSession.getDevice().getDeviceToken());
			PropertyDetails propertyDetails = (PropertyDetails) RestApiUtility.sendJSONPostRequest(
					CustomerServiceUrls.customerpropertymodificationcostdetails.getUrl(), customer, PropertyDetails.class);
			return propertyDetails;
		} else {
			throw new SessionTimeoutException("Your Session has been Timed Out.");
		}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}

}
