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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.MessengerRequest;
import com.sumadhura.sumadhuragateway.employeeservice.dto.MessengerResponce;
import com.sumadhura.sumadhuragateway.employeeservice.enums.MetadataId;
import com.sumadhura.sumadhuragateway.employeeservice.enums.Site;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.HttpStatus;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;
import com.sumadhura.sumadhuragateway.util.Util;

import lombok.NonNull;

/**
 * MessengerRestControlller class provides customer Messenger specific services.
 * 
 * @author Venkat_Koniki
 * @since 07.10.2020
 * @time 03:22PM
 */

@RestController("CustomerMessengerRestControlller")
@RequestMapping(value = "/customerservice/messenger")
public class MessengerRestControlller {
	
	@Value("${INBOX_ACROPOLIS_ALERT_MSG}")
	private String inboxAcropolisAlertMsg;

	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("CustomerSessionHandlerImpl")
	private SessionHandler customerSessionHandlerImpl;
	
	private final static Logger LOGGER = Logger.getLogger(MessengerRestControlller.class);
	
	
	@RequestMapping(value = "/getMessagesList.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON,consumes =  MediaType.APPLICATION_JSON)
	public Result getMessagesList(@RequestBody @NonNull  MessengerRequest messengerRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getMessagesList  in  MessengerRestControlller ********");
		Result result = null;
		if(Util.isNotEmptyObject(messengerRequest.getSessionKey()) && Util.isNotEmptyObject(messengerRequest.getRequestUrl()) && Util.isNotEmptyObject(messengerRequest.getFlatIds())) {
			LOGGER.info("**** The customer sessionkey  is ****" + messengerRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(messengerRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,messengerRequest.getDeviceToken());
			LOGGER.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				 CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The customerSession object ***"+customerSession);
				 /* Taking input parameters from the session  */
				 messengerRequest.setRecipientId(customerSession.getFlatBookingId());
				 messengerRequest.setRecipientType(MetadataId.FLAT_BOOKING.getId());
				 messengerRequest.setType("customer");
				 LOGGER.debug("*** The MessengerRequest obj ***"+messengerRequest);
				 result = (MessengerResponce) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_MESSENGER_LIST.getUrl(), messengerRequest, MessengerResponce.class);
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
	
	@RequestMapping(value = "/chatSubmit.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON,consumes =  MediaType.APPLICATION_JSON)
	public Result chatSubmit(@RequestBody @NonNull  MessengerRequest messengerRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the chatSubmit  in  MessengerRestControlller ********");
		Result result = null;
		if(Util.isNotEmptyObject(messengerRequest.getSessionKey())){
			LOGGER.info("**** The customer sessionkey  is ****" + messengerRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(messengerRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,messengerRequest.getDeviceToken());
			LOGGER.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				 CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				 /* We are sending alert message to Acropolis customers and Not allowing them to reploy */
				 Long siteId = customerSession.getFlat().getFloor().getBlock().getSite().getId(); 
				 if(Site.ACROPOLIS.getId().equals(siteId)) {
					 result = new Result();
					 result.setResponseCode(HttpStatus.failure.getResponceCode());
					 result.setDescription(inboxAcropolisAlertMsg);
					 return result;
				 }
				 /* Taking input parameters from the session  */
				 messengerRequest.setCreatedById(customerSession.getFlatBookingId());
				 messengerRequest.setCreatedByType(MetadataId.FLAT_BOOKING.getId());
				 messengerRequest.setFlatBookingId(customerSession.getFlatBookingId());
				 messengerRequest.setFlag(Boolean.TRUE);
				 /* Setting chat messge to chat message without tags variable */
				 messengerRequest.setChatMsgWithoutTags(messengerRequest.getMessage());
				 LOGGER.debug("*** The MessengerRequest obj ***"+messengerRequest);
				 result = (MessengerResponce) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.CHAT_SUBMIT.getUrl(), messengerRequest, MessengerResponce.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		result.setStatus(result.getDescription());
		return result;
	}
	
	@RequestMapping(value = "/getChatDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON,consumes =  MediaType.APPLICATION_JSON)
	public Result getChatDetails(@RequestBody @NonNull  MessengerRequest messengerRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getChatDetails  in  MessengerRestControlller ********");
		Result result = null;
		if(Util.isNotEmptyObject(messengerRequest.getSessionKey())){
			LOGGER.info("**** The customer sessionkey  is ****" + messengerRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(messengerRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,messengerRequest.getDeviceToken());
			if (isSessionValid) {
				 CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The customerSession object ***"+customerSession);
				 /* Taking input parameters from the session  */
				 messengerRequest.setRecipientId(customerSession.getFlatBookingId());
				 messengerRequest.setRecipientType(MetadataId.FLAT_BOOKING.getId());
				 LOGGER.debug("*** The MessengerRequest obj ***"+messengerRequest);
				 result = (MessengerResponce) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CHAT_DETAILS.getUrl(), messengerRequest, MessengerResponce.class);
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
	
	@RequestMapping(value = "/getUnviewChatCount.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON,consumes =  MediaType.APPLICATION_JSON)
	public Result getUnviewChatCount(@RequestBody @NonNull  MessengerRequest messengerRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getUnviewChatCount  in  MessengerRestControlller ********");
		Result result = null;
		if(Util.isNotEmptyObject(messengerRequest.getSessionKey())){
			LOGGER.info("**** The customer sessionkey  is ****" + messengerRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(messengerRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,messengerRequest.getDeviceToken());
			if (isSessionValid) {
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The customerSession object ***"+customerSession);
			    /* Taking input parameters from the session  */
				 messengerRequest.setRecipientId(customerSession.getFlatBookingId());
				 messengerRequest.setRecipientType(MetadataId.FLAT_BOOKING.getId());
				 LOGGER.debug("*** The MessengerRequest obj ***"+messengerRequest);
				 result = (MessengerResponce) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_UNVIEW_CHAT_COUNT.getUrl(), messengerRequest, MessengerResponce.class);
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
}
