package com.sumadhura.sumadhuragateway.employeeservice.controller;


import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.Department;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.employeeservice.dto.MessengerRequest;
import com.sumadhura.sumadhuragateway.employeeservice.dto.MessengerResponce;
import com.sumadhura.sumadhuragateway.employeeservice.enums.MetadataId;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.HttpStatus;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

import lombok.NonNull;

/**
 * MessengerRestControlller class provides Employee Messenger specific services.
 * 
 * @author Venkat_Koniki
 * @since 07.10.2020
 * @time 03:22PM
 */

@RestController("EmployeeMessengerRestControlller")
@RequestMapping(value = "/employeeservice/messenger")
public class MessengerRestControlller extends SessionValidate {

	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("EmployeeSessionHandlerImpl")
	private SessionHandler employeeSessionHandlerImpl;
	
	private final static Logger LOGGER = Logger.getLogger(MessengerRestControlller.class);
	
	@RequestMapping(value = "/getMessagesList.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON,consumes =  MediaType.APPLICATION_JSON)
	public Result getMessagesList(@RequestBody @NonNull  MessengerRequest messengerRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getMessagesList  in  MessengerRestControlller ********");
		Result result = null;
		if(Util.isNotEmptyObject(messengerRequest.getSessionKey()) && Util.isNotEmptyObject(messengerRequest.getRequestUrl())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + messengerRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(messengerRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 messengerRequest.setRecipientId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 messengerRequest.setRecipientType(MetadataId.EMPLOYEE.getId());
				 // messengerRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
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
	
	@RequestMapping(value = "/startNewChat.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON,consumes =  MediaType.APPLICATION_JSON)
	public Result startNewChat(@RequestBody @NonNull  MessengerRequest messengerRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the startNewChat  in  MessengerRestControlller ********");
		Result result = null;
		List<Long> userAccessSiteList = null;
		if(Util.isNotEmptyObject(messengerRequest.getSessionKey()) && Util.isNotEmptyObject(messengerRequest.getFlatIds())){
			LOGGER.info("**** The Employee sessionkey  is ****" + messengerRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(messengerRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			Result results = null;
			List<Department> departments = null;
			List<Long> sites = null;
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 messengerRequest.setRecipientId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 messengerRequest.setRecipientType(MetadataId.EMPLOYEE.getId());
				 messengerRequest.setFlatIds(messengerRequest.getFlatIds());
				 //checking he can create chat or not
				departments = employeeSession.getLoginResponse().getDepartments();
				userAccessSiteList = Util.getUserAccessSiteList(departments, "Inbox", "Start a Chat");
				sites = messengerRequest.getSiteIds();
				results = new Result();
				if (Util.isNotEmptyObject(sites)) {
					for (Long site : sites) {
						if (!userAccessSiteList.contains(site)) {
							results.setResponseCode(HttpStatus.failure.getResponceCode());
							results.setDescription(HttpStatus.failure.getStatus());
							results.setErrors(Arrays.asList("You don't have the access to create a new chat option to selected project."));
							return results;
						}
					}
				}
				// messengerRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				// messengerRequest.setEmpSiteId(employeeTicketRestController.getSiteIds(employeeSession,Module.VIEW_CUSTOMERS,SubModule.VIEW_ALL_CUSTOMERS));
				 LOGGER.debug("*** The MessengerRequest obj ***"+messengerRequest);
				 result = (MessengerResponce) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.START_NEW_CHAT.getUrl(), messengerRequest, MessengerResponce.class);
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
			LOGGER.info("**** The Employee sessionkey  is ****" + messengerRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(messengerRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 messengerRequest.setCreatedById(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 messengerRequest.setCreatedByType(MetadataId.EMPLOYEE.getId());
				 if(Util.isEmptyObject(messengerRequest.getDeptIds())){
				 messengerRequest.setDeptIds(Arrays.asList(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l));
				 }
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
		return result;
	}
	
	@RequestMapping(value = "/getChatDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON,consumes =  MediaType.APPLICATION_JSON)
	public Result getChatDetails(@RequestBody @NonNull  MessengerRequest messengerRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getChatDetails  in  MessengerRestControlller ********");
		Result result = null;
		if(Util.isNotEmptyObject(messengerRequest.getSessionKey())){
			LOGGER.info("**** The Employee sessionkey  is ****" + messengerRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(messengerRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 messengerRequest.setRecipientId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 messengerRequest.setRecipientType(MetadataId.EMPLOYEE.getId());
				 messengerRequest.setDeptIds(Arrays.asList(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l));
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
			LOGGER.info("**** The Employee sessionkey  is ****" + messengerRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(messengerRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				 EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 messengerRequest.setRecipientId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 messengerRequest.setRecipientType(MetadataId.EMPLOYEE.getId());
				 messengerRequest.setDeptIds(Arrays.asList(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l));
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
	
	@RequestMapping(value = "/getCustomerMessagesList.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON,consumes =  MediaType.APPLICATION_JSON)
	public Result getCustomersMessagesList(@RequestBody @NonNull  MessengerRequest messengerRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getCustomersMessagesList  in  MessengerRestControlller ********");
		Result result = null;
		if(Util.isNotEmptyObject(messengerRequest.getSessionKey()) && Util.isNotEmptyObject(messengerRequest.getFlatIds())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + messengerRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(messengerRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 //messengerRequest.setRecipientId(customerSession.getFlatBookingId());
				 messengerRequest.setRecipientType(MetadataId.FLAT_BOOKING.getId());
				 messengerRequest.setType("customer");
				 messengerRequest.setRequestUrl("getMessagesList");
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
	
	@RequestMapping(value = "/updateViewStatusAsUnread.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON,consumes =  MediaType.APPLICATION_JSON)
	public Result updateViewStatusAsUnread(@RequestBody @NonNull  MessengerRequest messengerRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the updateViewStatusAsUnread  in  MessengerRestControlller ********");
		Result result = null;
		if(Util.isNotEmptyObject(messengerRequest.getSessionKey())){
			LOGGER.info("**** The Employee sessionkey  is ****" + messengerRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(messengerRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 messengerRequest.setRecipientId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 messengerRequest.setRecipientType(MetadataId.EMPLOYEE.getId());
				 messengerRequest.setDeptIds(Arrays.asList(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l));
				 LOGGER.debug("*** The MessengerRequest obj ***"+messengerRequest);
				 result = (MessengerResponce) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.UPDATE_VIEW_STATUS_UNREAD.getUrl(), messengerRequest, MessengerResponce.class);
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

    @RequestMapping(value="/sitesForInbox.spring",method=RequestMethod.POST)
	public Result getSiteList(@RequestBody  MessengerRequest messengerRequest ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
    	LOGGER.info("******* The control inside of the getSiteList  in  MessengerRestControlller ********");
		EmployeeSession sessionValidate = sessionValidate(messengerRequest.getSessionKey());
		List<Long> userAccessSiteList = null;
		List<Department> departments = sessionValidate.getLoginResponse().getDepartments();
		messengerRequest.setEmployeeId(sessionValidate.getLoginResponse().getEmployee().getEmployeeId());
		if (Util.isNotEmptyObject(messengerRequest.getSiteIds())) {
		} else {
			if ("startAChat".equals(messengerRequest.getSubModuleName())) {
				userAccessSiteList = Util.getUserAccessSiteList(departments, "Inbox", "Start a Chat");
			} else {
				userAccessSiteList = Util.getUserAccessSiteList(departments, "Inbox", "View All Chats");
			}
			if (userAccessSiteList == null || userAccessSiteList.isEmpty()) {
				return null;
			}
			messengerRequest.setSiteIds(userAccessSiteList);
		}
		Result sendJSONGetRequest = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_SITES_FOR_INBOX.getUrl(),messengerRequest, Result.class);
		return sendJSONGetRequest;
	}

}
