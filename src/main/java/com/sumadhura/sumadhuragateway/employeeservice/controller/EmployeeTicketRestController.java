/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.controller;


import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.Customer;
import com.sumadhura.sumadhuragateway.dto.CustomerTicketRequest;
import com.sumadhura.sumadhuragateway.dto.CustomerWrapper;
import com.sumadhura.sumadhuragateway.dto.EmployeeTicketRequest;
import com.sumadhura.sumadhuragateway.dto.Login;
import com.sumadhura.sumadhuragateway.dto.PropertyDetails;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.dto.TicketTypeResponseWrapper;
import com.sumadhura.sumadhuragateway.employeeservice.dto.ChangeTicketType;
import com.sumadhura.sumadhuragateway.employeeservice.dto.DepartmentResponse;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeTicketResponse;
import com.sumadhura.sumadhuragateway.employeeservice.dto.LoginModule;
import com.sumadhura.sumadhuragateway.employeeservice.dto.LoginSubModule;
import com.sumadhura.sumadhuragateway.employeeservice.dto.Site;
import com.sumadhura.sumadhuragateway.employeeservice.dto.TicketForwardMenuResponse;
import com.sumadhura.sumadhuragateway.employeeservice.dto.TicketSeekInfoWrapper;
import com.sumadhura.sumadhuragateway.employeeservice.enums.Module;
import com.sumadhura.sumadhuragateway.employeeservice.enums.ServiceRequestEnum;
import com.sumadhura.sumadhuragateway.employeeservice.enums.SubModule;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.AESEncryptDecrypt;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.Department;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.HttpStatus;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;
import com.sumadhura.sumadhuragateway.util.Util;

import lombok.NonNull;


/**
 * EmployeeTicketRestController class provides Employee Ticketing specific services.
 * 
 * @author Venkat_Koniki
 * @since 28.06.2019
 * @time 06:56PM
 */
@RestController("EmployeeTicketRestController")
@RequestMapping(value = "/employeeservice/employeeTicket")
public class EmployeeTicketRestController {

	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("EmployeeSessionHandlerImpl")
	private SessionHandler employeeSessionHandlerImpl;
	
	@Value("${encryptedKey}")
	private  String encryptedKey ;
	
	@Value("${PM_MERCHANT_ID}")
	private  String pmMerchantId;
	
	@Value("${ACTION_MAIL_SUCCESS}")
	private  String actionMailSuccess;
	
	@Value("${ACTION_MAIL_FAILED}")
	private  String actionMailFailed;
	
	@Value("${ACTION_MAIL_FAILED_FORWARDED}")
	private  String actionMailFailedForwarded;
	
	@Value("${ACTION_MAIL_FAILED_INVALID_MERCHANT_ID}")
	private  String actionMailFailedInvalidMerchantId;
	
	@Value("${ACTION_MAIL_FAILED_INSUFFICIENT_INPUT}")
	private  String actionMailFailedInsufficientInput;
	
	private final static Logger LOGGER = Logger.getLogger(EmployeeTicketRestController.class);
	
	/**
	 * This is responsible to return TicketList associated with the given employeeId and departmentId and SiteId.
	 * @param employeeTicketRequest  holding existing employeeId and departmentId and SiteId. values (can be {@code NonNull})
	 * @return Result TicketList assosiated with the given given employeeId and departmentId and SiteId.
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	@RequestMapping(value = "/getTicketList.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getTicketList(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getTicketList  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				  /* Taking input parameters from the session  */
				    employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				  /* Taking department role mapping id from session */
				    if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
				      if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentRoleMappingId())) {	
				         employeeTicketRequest.setDepartmentRoleMappingId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentRoleMappingId());
				      } else {
				    	  employeeTicketRequest.setDepartmentRoleMappingId(0l);
				      }
				      if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
				    	  employeeTicketRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
				    	  employeeTicketRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				      }else {
				    	  employeeTicketRequest.setDepartmentId(0l);
				    	  employeeTicketRequest.setRoleId(0l);
				      }
				    }else {
				    	 employeeTicketRequest.setDepartmentRoleMappingId(0l);	
				    }
				    /* pagination dummy inputs */ 
				   if(Util.isEmptyObject(employeeTicketRequest.getPageNo())) {
				       employeeTicketRequest.setPageNo(1L);
				   } if(Util.isEmptyObject(employeeTicketRequest.getPageSize())) {
				       employeeTicketRequest.setPageSize(100000L);
				   }
				    employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				  
				 /* if siteIds is not given from front end */
				 if(Util.isEmptyObject(employeeTicketRequest.getSiteIds()) && ServiceRequestEnum.TICKET_GRAPHS.equals(employeeTicketRequest.getRequestEnum())) {
				    employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.TICKET_GRAPHS));
				 }else if(Util.isEmptyObject(employeeTicketRequest.getSiteIds())) {
					 // For getting closed tickets
					 if("closedTickets".equalsIgnoreCase(employeeTicketRequest.getType())) {
						 employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_CLOSED_TICKETS));
					 // For getting view all tickets
					 }else {
						 employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_ALL_TICKETS));
					 }
				 }   
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				 result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.getTicketList.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
			
				// ((EmployeeTicketResponse)result).setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_ALL_TICKETS));
			
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
	
	/**
	 * This is responsible to return TicketList associated with the given employeeId and departmentId and SiteId.
	 * @param employeeTicketRequest  holding existing employeeId and departmentId and SiteId. values (can be {@code NonNull})
	 * @return Result TicketList assosiated with the given given employeeId and departmentId and SiteId.
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	@RequestMapping(value = "/getMyTicketList.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getMyTicketList(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getTicketList  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				    employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				    employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				   
				    /* if siteIds is not given from front end */
				    if(Util.isEmptyObject(employeeTicketRequest.getSiteIds())) {
				    employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_MY_TICKETS));
				    }
				    
				    /* Taking department role mapping id from session */
				    if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
				      if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentRoleMappingId())) {	
				         employeeTicketRequest.setDepartmentRoleMappingId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentRoleMappingId());
				      }else {
				    	  employeeTicketRequest.setDepartmentRoleMappingId(0l);
				      }
				      if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
				    	  employeeTicketRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
				    	  employeeTicketRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				      }else {
				    	  employeeTicketRequest.setDepartmentId(0l);
				    	  employeeTicketRequest.setRoleId(0l);
				      }
				    }else {
				    	 employeeTicketRequest.setDepartmentRoleMappingId(0l);	
				    }
				    /* pagination dummy inputs */ 
					   if(Util.isEmptyObject(employeeTicketRequest.getPageNo())) {
					       employeeTicketRequest.setPageNo(1L);
					   } if(Util.isEmptyObject(employeeTicketRequest.getPageSize())) {
					       employeeTicketRequest.setPageSize(100000L);
					   }
				    LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				 result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.getTicketList.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	
	@RequestMapping(value = "/getChangeTicketTypeTicketList.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getChangeTicketTypeTicketList(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getChangeTicketTypeTicketList  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if(isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				    employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				    employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				   
				    /* if siteIds is not given from front end */
				    if(Util.isEmptyObject(employeeTicketRequest.getSiteIds())) {
				    employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.CHANGE_TICKET_TYPE));
				    }	
				   /* pagination dummy inputs */ 
					  if(Util.isEmptyObject(employeeTicketRequest.getPageNo())) {
					       employeeTicketRequest.setPageNo(1L);
					  } if(Util.isEmptyObject(employeeTicketRequest.getPageSize())) {
					       employeeTicketRequest.setPageSize(100000L);
					  }
				    LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				    result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.getTicketList.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	
	/**
	 * This is responsible to return TicketDetails associated with the given TicketId.
	 * @param employeeTicketRequest  holding existing TicketId values (can be {@code NonNull})
	 * @return Result Ticket assosiated with the given  TicketId.
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws SessionTimeoutException 
	 * @throws InSufficeientInputException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getTicket.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getTicketDtls(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException {
		LOGGER.info("******* The control inside of the getTicketDtls  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketId())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				/* Taking input parameters from the session  */
 			       employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
			       employeeTicketRequest.setFromId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
			       employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
			      LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				 result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.getTicket.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	/**
	 * This is responsible to updateTicketConversation associated with the given TicketId.
	 * @param employeeTicketRequest  holding existing TicketId values (can be {@code NonNull})
	 * @return Result Ticket updating status with the given  TicketId.
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws SessionTimeoutException 
	 * @throws InSufficeientInputException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/updateTicketConversation.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result updateTicketConversation(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, IOException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the updateTicketConversation  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketId()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl()) && Util.isNotEmptyObject(employeeTicketRequest.getToId()) && Util.isNotEmptyObject(employeeTicketRequest.getToType()) && Util.isNotEmptyObject(employeeTicketRequest.getMessage())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				 employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 employeeTicketRequest.setFromDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 employeeTicketRequest.setFromId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setFromType(Department.EMPLOYEE.getId());
				 result = (Result) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.updateTicketConversation.getUrl(), employeeTicketRequest, Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}
	/**
	 * This is responsible to closeTicket associated with the given TicketId.
	 * @param employeeTicketRequest  holding existing TicketId values (can be {@code NonNull})
	 * @return Result Ticket updating status with the given  TicketId.
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws SessionTimeoutException 
	 * @throws InSufficeientInputException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/closeTicket.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result closeTicket(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, IOException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the closeTicket  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketId()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				// employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 employeeTicketRequest.setFromDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 employeeTicketRequest.setFromId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setFromType(Department.EMPLOYEE.getId());
				 employeeTicketRequest.setDeptName(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentName());
				 result = (Result) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.closeTicket.getUrl(), employeeTicketRequest, Result.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		
		//result.setStatus(result.getDescription());
		//if(result.getResponseCode().equals(200)) {
		//	result.setStatus("Your ticket has been Closed");
		//}
		
		return result;
	}
	/**
	 * This is responsible to getTicketForwardMenuDetails.
	 * @param employeeTicketRequest  holding existing EmployeeId values (can be {@code NonNull})
	 * @return Result TicketForwardMenu with the given EmployeeId.
	 * @throws IOException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 * @throws InSufficeientInputException 
	 */
	@RequestMapping(value = "/getTicketForwardMenuDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getTicketForwardMenuDetails(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the getTicketForwardMenuDetails  in  EmployeeTicketRestService ********");    		
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setFromId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setFromDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (TicketForwardMenuResponse) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.getTicketForwardMenuDetails.getUrl(), employeeTicketRequest, TicketForwardMenuResponse.class);
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
	
	/**
	 * This is responsible to forwardTicket associated with the given TicketId.
	 * @param employeeTicketRequest  holding existing TicketId values (can be {@code NonNull})
	 * @return Result Ticket Forwarding status with the given  TicketId.
	 * @throws IOException 
	 * @throws SessionTimeoutException 
	 * @throws InSufficeientInputException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	@RequestMapping(value = "/forwardTicketDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result forwardTicketDetails(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws IOException, SessionTimeoutException, InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		LOGGER.info("******* The control inside of the forwardTicketDetails  in  EmployeeTicketRestService ********"); 
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketId()) && Util.isNotEmptyObject(employeeTicketRequest.getMessage()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 employeeTicketRequest.setFromDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 employeeTicketRequest.setFromId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setFromType(Department.EMPLOYEE.getId());
				 employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (Result) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.forwardTicketDetails.getUrl(), employeeTicketRequest, Result.class);
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
	/**
	 * This is responsible to get DepartmentDetails.
	 * @param employeeTicketRequest  holding existing EmployeeId values (can be {@code NonNull})
	 * @return Result DepartmentDetails.
	 * @throws IOException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 * @throws InSufficeientInputException 
	 */
	@RequestMapping(value = "/getDepartmentDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getDepartmentDetails(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the getDepartmentDetails  in  EmployeeTicketRestService ********"); 
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (DepartmentResponse) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.getDepartmentDetails.getUrl(), employeeTicketRequest, DepartmentResponse.class);
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
	/**
	 * This is responsible to get submit seekInfo.
	 * @param employeeTicketRequest  holding existing EmployeeId  & DepartmentId & TicketId values (can be {@code NonNull})
	 * @return Result seekInfo status with the given  TicketId.
	 * @throws IOException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 * @throws InSufficeientInputException 
	 */
	@RequestMapping(value = "/seekInfoTicketDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result seekInfoTicketDetails(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the seekInfoTicketDetails  in  EmployeeTicketRestService ********"); 
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketId()) && Util.isNotEmptyObject( employeeTicketRequest.getRequestUrl()) && Util.isNotEmptyObject(employeeTicketRequest.getMessage()) && (Util.isNotEmptyObject(employeeTicketRequest.getToId()) || Util.isNotEmptyObject(employeeTicketRequest.getToDeptId()))){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setFromDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 employeeTicketRequest.setFromId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setFromType(Department.EMPLOYEE.getId());
				 employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (Result) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.seekInfoTicketDetails.getUrl(), employeeTicketRequest, Result.class);
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
	
	/**
	 * This is responsible to get submit seekInfo.
	 * @param employeeTicketRequest  holding existing EmployeeId  & DepartmentId & TicketId values (can be {@code NonNull})
	 * @return Result seekInfo status with the given  TicketId.
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 * @throws InSufficeientInputException 
	 */
	@RequestMapping(value = "/ticketSpecifictviewRequestInfo.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result ticketViewRequestInfo(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		Result result = null;
		LOGGER.info("******* The control inside of the ticketViewRequestInfo  in  EmployeeTicketRestService ********"); 
		 assert(employeeTicketRequest!=null):"employeeTicketRequest is not null";
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketId()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl()) ){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setFromDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 employeeTicketRequest.setFromId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setFromType(Department.EMPLOYEE.getId());
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (TicketSeekInfoWrapper) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.ticketSpecifictviewRequestInfo.getUrl(), employeeTicketRequest, TicketSeekInfoWrapper.class);
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
	/**
	 * This is responsible to get viewRequestInfo.
	 * @param employeeTicketRequest  holding existing EmployeeId  & DepartmentId & TicketId values (can be {@code NonNull})
	 * @return Result viewRequestInfo status with the given  TicketId.
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 * @throws InSufficeientInputException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/viewRequestInfo.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result viewRequestInfo(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException {
		LOGGER.info("******* The control inside of the viewRequestInfo  in  EmployeeTicketRestService ********"); 
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketId()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl()) ){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setFromDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 employeeTicketRequest.setFromId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setFromType(Department.EMPLOYEE.getId());
				 employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_INFO_REQUEST));
				  /* pagination dummy inputs */ 
				   if(Util.isEmptyObject(employeeTicketRequest.getPageNo())) {
				       employeeTicketRequest.setPageNo(1L);
				   } if(Util.isEmptyObject(employeeTicketRequest.getPageSize())) {
				       employeeTicketRequest.setPageSize(100000L);
				   }
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.viewRequestInfo.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	
	/**
	 * This is responsible to insert SeekInfoDetails.
	 * @param employeeTicketRequest  holding existing EmployeeId  & DepartmentId & TicketId values (can be {@code NonNull})
	 * @return Result insertSeekInfo status with the given  TicketId.
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 * @throws InSufficeientInputException 
     */
	@RequestMapping(value = "/insertSeekInfoDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result insertSeekInfoDetails(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the insertSeekInfoDetails  in  EmployeeTicketRestService ********"); 
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketId()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl()) && Util.isNotEmptyObject(employeeTicketRequest.getMessage()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketSeekInforequestId()) && (Util.isNotEmptyObject(employeeTicketRequest.getToId()) || Util.isNotEmptyObject(employeeTicketRequest.getToDeptId())) && Util.isNotEmptyObject(employeeTicketRequest.getToType()) ){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setFromDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 employeeTicketRequest.setFromId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setFromType(Department.EMPLOYEE.getId());
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (Result) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.insertSeekInfoDetails.getUrl(), employeeTicketRequest, Result.class);
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
	/**
	 * This is responsible to make Documents As Public.
	 * @param employeeTicketRequest  holding existing EmployeeId  & DepartmentId & TicketConversationDocumentId values (can be {@code NonNull})
	 * @return Result makeAsPublic status with the given TicketConversationDocumentId.
	 * @throws IOException 
	 * @throws InSufficeientInputException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws SessionTimeoutException 
	 */
	@RequestMapping(value = "/makeAsPublic.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result makeAsPublic(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws IOException, InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the makeAsPublic  in  EmployeeTicketRestService ********"); 
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketId()) && Util.isNotEmptyObject(employeeTicketRequest.getFileInfos()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (Result) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.makeAsPublic.getUrl(), employeeTicketRequest, Result.class);
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
	/**
	 * This is responsible to insert ExtendEsacalation Time Details.
	 * @param employeeTicketRequest  holding existing EmployeeId  & TicketId & extendedEscalationTime & message (can be {@code NonNull})
	 * @return Result insert ExtendEsacalation Time  status with the given TicketId.
	 * @throws IOException 
	 * @throws SessionTimeoutException 
	 * @throws InSufficeientInputException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 */
	@RequestMapping(value = "/insertExtendEsacalationTime.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result insertExtendEsacalationTime(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws IOException, SessionTimeoutException, InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		LOGGER.info("******* The control inside of the insertExtendEsacalationTime  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketId()) && Util.isNotEmptyObject(employeeTicketRequest.getExtendedEscalationTime()) && Util.isNotEmptyObject(employeeTicketRequest.getMessage())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (Result) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.insertExtendEsacalationTime.getUrl(), employeeTicketRequest, Result.class);
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
	/**
	 * This is responsible to get ExtendEsacalation Time Details.
	 * @param employeeTicketRequest  holding existing EmployeeId(can be {@code NonNull})
	 * @return Result get ExtendEsacalation Time  Details with the given EmployeeId.
	 * @throws InSufficeientInputException 
	 * @throws IOException
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 */
	@RequestMapping(value = "/getExtendEsacalationTimeDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getExtendEsacalationTimeDetails(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws InSufficeientInputException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the getExtendEsacalationTimeDetails  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				 employeeTicketRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.getExtendEsacalationTimeDetails.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	
	/**
	 * This is responsible to update ExtendEsacalation TimeDetails Status Details.
	 * @param employeeTicketRequest  holding existing TicketId & TicketExtendedEscalationApprovalId(can be {@code NonNull})
	 * @return Result update ExtendEsacalation TimeDetails Status.
	 * @throws IOException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 * @throws InSufficeientInputException 
	 */
	@RequestMapping(value = "/updateExtendEsacalationTimeDetailsStatus.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result updateExtendEsacalationTimeDetailsStatus(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the updateExtendEsacalationTimeDetailsStatus  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketId()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketExtendedEscalationApprovalId()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (Result) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.updateExtendEsacalationTimeDetailsStatus.getUrl(), employeeTicketRequest, Result.class);
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
	/**
	 * This is responsible to get System Escalated TicketDetails Details.
	 * @param employeeTicketRequest  holding existing TicketId & TicketExtendedEscalationApprovalId(can be {@code NonNull})
	 * @return Result update ExtendEsacalation TimeDetails Status.
	 * @throws IOException
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 * @throws InSufficeientInputException 
	 */
	@RequestMapping(value = "/getSystemEscalatedTicketDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getSystemEscalatedTicketDetails(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the getSystemEscalatedTicketDetails  in  EmployeeTicketRestService ********");	
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) &&  Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				  /* pagination dummy inputs */ 
				   if(Util.isEmptyObject(employeeTicketRequest.getPageNo())) {
				       employeeTicketRequest.setPageNo(1L);
				   } if(Util.isEmptyObject(employeeTicketRequest.getPageSize())) {
				       employeeTicketRequest.setPageSize(100000L);
				   }
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.getSystemEscalatedTicketDetails.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	/**
	 * This is responsible to get Employee Details.
	 * @param employeeTicketRequest  holding existing EmployeeId(can be {@code NonNull})
	 * @return Result get ExtendEsacalation Time  Details with the given EmployeeId.
	 * @throws IOException 
	 * @throws InSufficeientInputException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 */
	@RequestMapping(value = "/getEmployeeDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getEmployeeDetails(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws IOException, InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the getEmployeeDetails  in  EmployeeTicketRestService ********");
		Result result = null;	
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getEmployeeName()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.getEmployeeDetails.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	
	/**
	 * This is responsible to insert EmployeeLeave Details.
	 * @param employeeTicketRequest  holding existing EmployeeId(can be {@code NonNull})
	 * @return Result get ExtendEsacalation Time  Details with the given EmployeeId.
	 * @throws IOException 
	 * @throws InSufficeientInputException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 */
	@RequestMapping(value = "/insertEmployeeLeaveDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result insertEmployeeLeaveDetails(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws IOException, InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the insertEmployeeLeaveDetails  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl()) && Util.isNotEmptyObject(employeeTicketRequest.getStartDate()) && Util.isNotEmptyObject(employeeTicketRequest.getEndDate()) && Util.isNotEmptyObject(employeeTicketRequest.getApprovedBy())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmployee().getEmployeeId())?employeeSession.getLoginResponse().getEmployee().getEmployeeId():0l);
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (Result) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.insertEmployeeLeaveDetails.getUrl(), employeeTicketRequest, Result.class);
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
	/** 
	 * This is responsible to is Employee Available Details.
	 * @param employeeTicketRequest  holding existing EmployeeId(can be {@code NonNull})
	 * @return Result get ExtendEsacalation Time  Details with the given EmployeeId.
	 * @throws InSufficeientInputException 
	 * @throws IOException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 */
	@RequestMapping(value = "/isEmployeeAvailable.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result isEmployeeAvailable(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws InSufficeientInputException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the isEmployeeAvailable  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && (Util.isNotEmptyObject(employeeTicketRequest.getEmployeeId()) || Util.isNotEmptyObject(employeeTicketRequest.getDepartmentId())) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);	
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (Result) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.isEmployeeAvailable.getUrl(), employeeTicketRequest, Result.class);
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
	
	/** 
	 * This is responsible to get Customer Profile Details Available Details.
	 * @param employeeTicketRequest  holding existing CustomerId and flatId(can be {@code NonNull})
	 * @return Result get CustomerWrapper contains Details with the given customerId and FlatId.
	 * @throws InSufficeientInputException 
	 * @throws IOException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 */
	@RequestMapping(value = "/getCustomerProfileDetails.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result getCustProfileDtls(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest)
			throws SessionTimeoutException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the getCustProfileDtls  in  EmployeeTicketRestService ********");
		CustomerWrapper customerWrapper = new CustomerWrapper();
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getCustomerId()) && Util.isNotEmptyObject(employeeTicketRequest.getFlatId()) ) {
		LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
		String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
		boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
		LOGGER.info("**** The session validation info is ******" + isSessionValid);
		if (isSessionValid) {
			EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
			Login login = new Login();
			login.setCustId(employeeTicketRequest.getCustomerId());
			login.setFlatId(employeeTicketRequest.getFlatId());
			LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
			LOGGER.info("*** The login  object ****"+login);
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
	/** 
	 * This is responsible to get Customer property Details  Details.
	 * @param employeeTicketRequest  holding existing CustomerId and flatId(can be {@code NonNull})
	 * @return Result get PropertyDetails contains Details with the given customerId and FlatId.
	 * @throws InSufficeientInputException 
	 * @throws IOException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 */
	@RequestMapping(value = "/propertyDetails.spring", method = RequestMethod.POST, produces = "application/json")
	public Result getCustomerPropertyDetails(@RequestBody @NonNull  EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the getCustomerPropertyDetails  in  EmployeeTicketRestService ********");
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getCustomerId()) && Util.isNotEmptyObject(employeeTicketRequest.getFlatId()) ) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			LOGGER.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				Customer customer = new Customer();
				customer.setCustomerId(employeeTicketRequest.getCustomerId());
				customer.setFlatId(employeeTicketRequest.getFlatId());
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				LOGGER.info("*** The customer  object ****"+customer);
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
	
	/** 
	 * This is responsible to get Customer PropertyUploadedDocs Details.
	 * @param employeeTicketRequest  holding existing CustomerId and flatId(can be {@code NonNull})
	 * @return Result get PropertyDetails contains Details with the given customerId and FlatId.
	 * @throws InSufficeientInputException 
	 * @throws IOException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 */
	@RequestMapping(value = "/propertyUploadedDocs.spring", method = RequestMethod.POST, produces = "application/json")
	public Result getPropertyUploadedDocs(@RequestBody @NonNull  EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the getPropertyUploadedDocs  in  EmployeeTicketRestService ********");
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getCustomerId()) && Util.isNotEmptyObject(employeeTicketRequest.getFlatId()) ) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			LOGGER.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				Customer customer = new Customer();
				customer.setCustomerId(employeeTicketRequest.getCustomerId());
				customer.setFlatId(employeeTicketRequest.getFlatId());
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				LOGGER.info("*** The customer  object ****"+customer);
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
	/** 
	 * This is responsible to get Customer PropertyAmenitiesCost Details.
	 * @param employeeTicketRequest  holding existing CustomerId and flatId(can be {@code NonNull})
	 * @return Result get PropertyDetails contains Details with the given customerId and FlatId.
	 * @throws InSufficeientInputException 
	 * @throws IOException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 */
	@RequestMapping(value = "/propertyAmenitiesCost.spring", method = RequestMethod.POST, produces = "application/json")
	public Result getPropertyAmenitiesCost(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		    LOGGER.info("******* The control inside of the getPropertyAmenitiesCost  in  EmployeeTicketRestService ********");
		    if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getCustomerId()) && Util.isNotEmptyObject(employeeTicketRequest.getFlatId()) ) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			LOGGER.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				Customer customer = new Customer();
				customer.setCustomerId(employeeTicketRequest.getCustomerId());
				customer.setFlatId(employeeTicketRequest.getFlatId());
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				LOGGER.info("*** The customer  object ****"+customer);
				PropertyDetails propertyDetails = (PropertyDetails) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customerpropertyamenitiescost.getUrl(), customer, PropertyDetails.class);
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
	/** 
	 * This is responsible to get Customer ModificationDetails.
	 * @param employeeTicketRequest  holding existing CustomerId and flatId(can be {@code NonNull})
	 * @return Result get PropertyDetails contains Details with the given customerId and FlatId.
	 * @throws InSufficeientInputException 
	 * @throws IOException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws SessionTimeoutException 
	 */
	@RequestMapping(value = "/modificationDetails.spring", method = RequestMethod.POST, produces = "application/json")
	public Result getModificationDetails(@RequestBody @NonNull  EmployeeTicketRequest employeeTicketRequest)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the getModificationDetails  in  EmployeeTicketRestService ********");
		 if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getCustomerId()) && Util.isNotEmptyObject(employeeTicketRequest.getFlatId()) ) {
		String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
		boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
		LOGGER.info("**** The session validation info is ******" + isSessionValid);
		if (isSessionValid) {
			EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
			Customer customer = new Customer();
			customer.setCustomerId(employeeTicketRequest.getCustomerId());
			customer.setFlatId(employeeTicketRequest.getFlatId());
			LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
			LOGGER.info("*** The customer  object ****"+customer);
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
	/**
	 * This is responsible to return siteIds associated with the given Module and SubModules.
	 * @param employeeSession  holding existing employee Modules and SubModules values (can be {@code NonNull})
     * @param module  holding  Module  value (can be {@code NonNull})
     * @param subModule  holding  subModule  value (can be {@code NonNull})
	 * @return List<Long> siteIds assosiated with the subModule.
	 */
	public List<Long> getSiteIds(@NonNull EmployeeSession employeeSession, @NonNull Module module,@NonNull SubModule subModule) {
		LOGGER.info("*** The Control is inside the getSiteIds in EmployeeTicketRestController ***"+employeeSession);
		Set<Long> siteIdSet = new HashSet<Long>();
		List<Long> siteIds = new ArrayList<Long>();
		if (Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())) {
			for (LoginModule loginModule : employeeSession.getLoginResponse().getDepartments().get(0).getLoginModule()) {
				if (loginModule.getModuleId().equals(module.getId())) {
					for (LoginSubModule loginSubModule : loginModule.getLoginSubModules()) {
						if (loginSubModule.getSubModuleId().equals(subModule.getId())) {
							for (Site site : loginSubModule.getSites()) {
								siteIdSet.add(site.getSiteId());
							}
							break;
						}
					}
					break;
				}
			}
		}
		LOGGER.info("*** The SiteSet object is ****"+siteIdSet);
		siteIds.addAll(siteIdSet);
		return siteIds;
	}
@RequestMapping(value = "/ticketForwardActionMail.spring", method = RequestMethod.POST, produces = MediaType.TEXT_HTML)
	public String ticketForwardActionMail(@RequestParam("merchantId") String merchantId,
			                              @RequestParam("employeeId") Long employeeId,
			                              @RequestParam("ticketId") Long ticketId,
			                              @RequestParam("departmentId") Long departmentId,
			                              @RequestParam("requestUrl") String requestUrl,
			                              @RequestParam("ticketEscalationId") Long ticketEscalationId,
			                              @RequestParam("typeOf") Long typeOf,
			                              @RequestParam("toId") Long toId,
			                              @RequestParam("toDeptId") Long toDeptId,
			                              @RequestParam("mailOtpApproval") String mailOtpApproval,
			                              @RequestParam("type") String type,
			                              @RequestParam("comment") String msg
			                              )throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException,NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the getModificationDetails  in  EmployeeTicketRestService ********"+merchantId);
		Result result = null;
		if(Util.isNotEmptyObject(merchantId) 
		&& Util.isNotEmptyObject(employeeId)
		&& Util.isNotEmptyObject(ticketId)
		&& Util.isNotEmptyObject(departmentId)
		&& Util.isNotEmptyObject(ticketEscalationId)
		&& Util.isNotEmptyObject(requestUrl)
		&& Util.isNotEmptyObject(typeOf) 
		&& Util.isNotEmptyObject(mailOtpApproval)
		&& Util.isNotEmptyObject(type)){
		
	   String decryptedSessionKey = AESEncryptDecrypt.decrypt(merchantId,AESEncryptDecrypt.convertKeyToHex(encryptedKey));	
	   LOGGER.info("**** The pm merchant key ******" + decryptedSessionKey);	
	   
	   EmployeeTicketRequest employeeTicketRequest = new EmployeeTicketRequest();
	   employeeTicketRequest.setTicketEscalationId(ticketEscalationId);
	   result = (Result) RestApiUtility.sendJSONPostRequest(
				 EmployeeServiceUrls.getTicketEscaltionDtls.getUrl(), employeeTicketRequest, Result.class);
	   
	   Date date = null;
	   if(Util.isNotEmptyObject(result)) {
		   if(Util.isNotEmptyObject(result.getTicketEscalationResponses())) {
			   if(Util.isNotEmptyObject(result.getTicketEscalationResponses().get(0).getCreatedDate())) {
				   date = new Date(result.getTicketEscalationResponses().get(0).getCreatedDate().getTime());
			   }else {
				   date=new Date(); 
			   }
		   }else {
			   date=new Date(); 
		   }
	   }else {
		   date=new Date(); 
	   }
	   
	   if(Util.isNotEmptyObject(decryptedSessionKey)?decryptedSessionKey.equalsIgnoreCase(pmMerchantId+Util.getDate("dd-MM-yyyy",date))?true:false:false){
		   
		   if(result.getResponseCode().equals(HttpStatus.success.getResponceCode()) && Util.isNotEmptyObject(result.getTicketEscalationResponses().get(0).getMailOtpApproval()) && result.getTicketEscalationResponses().get(0).getMailOtpApproval().equalsIgnoreCase(mailOtpApproval)){
			
			 employeeTicketRequest.setDepartmentId(departmentId);
			 employeeTicketRequest.setFromDeptId(departmentId);
			 employeeTicketRequest.setFromId(employeeId);
			 employeeTicketRequest.setFromType(Department.EMPLOYEE.getId());
			 employeeTicketRequest.setEmployeeId(employeeId);
			 employeeTicketRequest.setTicketId(ticketId);
			 employeeTicketRequest.setRequestUrl(requestUrl); 
			 employeeTicketRequest.setMessage(msg);
			 if(typeOf.equals(9l)) {
			 employeeTicketRequest.setToDeptId(toDeptId);
			 }else {
			 employeeTicketRequest.setToId(toId);
			 }
			 employeeTicketRequest.setType(type);
			
			LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
		      result = (Result) RestApiUtility.sendJSONPostRequest(
					 EmployeeServiceUrls.forwardTicketDetails.getUrl(), employeeTicketRequest, Result.class);
		   
		   if(result.getResponseCode().equals(HttpStatus.success.getResponceCode())) {
			   /* send success html code */
			   return actionMailSuccess ;
			   
		   }else {
			   /* send failure html code */ 
			   return actionMailFailed;
		   }   
		   }else {
			 /* alredy approved  */  
			//  "Ticket is Already forwarded";
			   return actionMailFailedForwarded;
			  
		   }
	   }else {
		  /* "InValid Merchant Id is Given" */
		  return actionMailFailedInvalidMerchantId;
		} 
	}else {
		 /* "Insufficeint Input is Given." */
		 return actionMailFailedInsufficientInput;
	}
}
	
/**
	 * This is responsible to get ticket owner associated with the given employeeId and departmentId and SiteId.
	 * @param employeeTicketRequest holding existing employeeId and departmentId and SiteId. values (can be {@code NonNull})
	 * @return Result TicketList assosiated with the given given employeeId and departmentId and SiteId.
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	@RequestMapping(value = "/changeTicketOwnerDropDown.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result changeTicketOwnerDropDown(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getTicketOwners  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				  /* Taking input parameters from the session  */
				    employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				    employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				    employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.CHANGE_TICKET_OWNER));
				  LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				 result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.changeTicketOwnerDropDown.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	
	/**
	 * This is responsible to change Ticket Owner associated with the given Ticket Id employeeId and departmentId and SiteId.
	 * @param employeeTicketRequest holding existing employeeId and departmentId and SiteId. values (can be {@code NonNull})
	 * @return Result TicketList assosiated with the given given employeeId and departmentId and SiteId.
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	@RequestMapping(value = "/changeTicketOwner.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result changeTicketOwner(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the changeTicketOwner in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketIds()) 
		 && Util.isNotEmptyObject(employeeTicketRequest.getDepartmentId()) && Util.isNotEmptyObject(employeeTicketRequest.getEmpDetailsId())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.changeTicketOwner.getUrl(), employeeTicketRequest, Result.class);
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
	/* Dummy Method for read values from application.properties File */
	@RequestMapping(value = "/actionMailSuccessTest.spring", method = RequestMethod.GET, produces = MediaType.TEXT_HTML)
	public String actionMailSuccessTest() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the actionMailSuccessTest in  EmployeeTicketRestService ********");
		Result result = new Result();
		result.setDescription(actionMailSuccess);
		result.setResponseCode(HttpStatus.success.getResponceCode());
		LOGGER.info("**** Success Code *****"+actionMailSuccess);
		return actionMailSuccess /*+ actionMailFailed + actionMailFailedForwarded  +actionMailFailedInvalidMerchantId +actionMailFailedInsufficientInput  */;
	}	
	
	@RequestMapping(value = "/getExtendedEscalationTimeApprovalLevel.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getExtendedEscalationTimeApprovalLevel(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the getExtendedEscalationTimeApprovalLevel in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketId())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				 employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 employeeTicketRequest.setRequestUrl("getExtendedEscalationTimeApprovalLevel");
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getExtendedEscalationTimeApprovalLevel.getUrl(), employeeTicketRequest, Result.class);
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
	
	@RequestMapping(value = "/getChangeTicketTypeMailDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getChangeTicketTypeMailDetails(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the getChangeTicketTypeMailDetails in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketId())){
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				 employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 employeeTicketRequest.setRequestUrl("getChangeTicketTypeMailDetails");
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getChangeTicketTypeMailDetails.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	
	@RequestMapping(value = "/sendChangeTicketTypeMail.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result sendChangeTicketTypeMail(@Valid @RequestBody ChangeTicketType changeTicketTypeRequest) throws InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the sendChangeTicketTypeMail in  EmployeeTicketRestService ********");
		Result result = null;
			LOGGER.info("**** The Employee sessionkey  is ****" + changeTicketTypeRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(changeTicketTypeRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 LOGGER.debug("*** The ChangeTicketType obj ***"+changeTicketTypeRequest);
				 changeTicketTypeRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 changeTicketTypeRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.sendChangeTicketTypeMail.getUrl(), changeTicketTypeRequest, Result.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		return result;
	}
	
	@RequestMapping(value = "/remindAgainAction.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result changeTicketTypeRemindAgain(@RequestBody ChangeTicketType changeTicketTypeRequest) throws InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the changeTicketTypeRemindAgain in  EmployeeTicketRestService ********");
		Result result = null;
		LOGGER.info("**** The Employee sessionkey  is ****" + changeTicketTypeRequest.getSessionKey());
		if(Util.isNotEmptyObject(changeTicketTypeRequest.getSessionKey()) && Util.isNotEmptyObject(changeTicketTypeRequest.getTicketId())){
		String sessionKey = sessionUtils.decryptSessionKey(changeTicketTypeRequest.getSessionKey());
		boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
		if (isSessionValid) {
			EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
			 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
			 LOGGER.debug("*** The ChangeTicketType obj ***"+changeTicketTypeRequest);
			 changeTicketTypeRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
			 changeTicketTypeRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
			 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.changeTicketTypeRemindAction.getUrl(), changeTicketTypeRequest, Result.class);
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
	
	@RequestMapping(value = "/getTicketTypeDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getTicketTypeDetails(@RequestBody ChangeTicketType changeTicketTypeRequest) throws InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the getTicketTypeDetails in  EmployeeTicketRestService ********");
		Result result = null;
		LOGGER.info("**** The Employee sessionkey  is ****" + changeTicketTypeRequest.getSessionKey());
		if(Util.isNotEmptyObject(changeTicketTypeRequest.getSessionKey())){
		String sessionKey = sessionUtils.decryptSessionKey(changeTicketTypeRequest.getSessionKey());
		boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
		if (isSessionValid) {
			EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
			 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
			 LOGGER.debug("*** The ChangeTicketType obj ***"+changeTicketTypeRequest);
				CustomerTicketRequest customerTicketRequest = new CustomerTicketRequest();
				/* setting dummy custmoer noumber and siteIds */
				customerTicketRequest.setCustNo(100l);
				customerTicketRequest.setSiteId(111l);
				LOGGER.info("*** The customer session object ****" + customerTicketRequest);
				result = (TicketTypeResponseWrapper) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customerTicketTypeDetails.getUrl(), customerTicketRequest, TicketTypeResponseWrapper.class);
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
	
	@RequestMapping(value = "/actionChangeTicketType.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result changeTicketTypeAction(@RequestBody ChangeTicketType changeTicketTypeRequest) throws InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the changeTicketTypeAction in  EmployeeTicketRestService ********");
		Result result = null;
		LOGGER.info("**** The Employee sessionkey  is ****" + changeTicketTypeRequest.getSessionKey());
		if(Util.isNotEmptyObject(changeTicketTypeRequest.getSessionKey())){
			String sessionKey = sessionUtils.decryptSessionKey(changeTicketTypeRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 changeTicketTypeRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 changeTicketTypeRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 result = (Result) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.actionChangeTicketType.getUrl(), changeTicketTypeRequest, Result.class);
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
	
	@RequestMapping(value = "/getCustomerTicketList.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getCustomerTicketList(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the getCustomerTicketList in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getFlatBookingId())) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.info("*** The employeeSession obj ***"+employeeSession);
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_TICKET_LIST.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
	    }else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given."+employeeTicketRequest);
			throw new InSufficeientInputException(errors);
		}
		return result;
	}
	
	@RequestMapping(value = "/saveTicketsComplaint.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result saveTicketsComplaint(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the saveTicketsComplaint in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getTicketIds())
			&& Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl())) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.info("*** The employeeSession obj ***"+employeeSession);
				employeeTicketRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
				employeeTicketRequest.setDepartmentId((employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()));
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SAVE_TICKETS_COMPLAINT.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
	    }else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given."+employeeTicketRequest);
			throw new InSufficeientInputException(errors);
		}
		return result;
	}
	
	@RequestMapping(value = "/getTicketComplaintList.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getTicketComplaintList(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getTicketComplaintList in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				  /* Taking input parameters from the session  */
				    employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				  /* Taking department role mapping id from session */
				    if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
				      if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentRoleMappingId())) {	
				         employeeTicketRequest.setDepartmentRoleMappingId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentRoleMappingId());
				      }else {
				    	  employeeTicketRequest.setDepartmentRoleMappingId(0l);
				      }
				      if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
				    	  employeeTicketRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
				    	  employeeTicketRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				      }else {
				    	  employeeTicketRequest.setDepartmentId(0l);
				    	  employeeTicketRequest.setRoleId(0l);
				      }
				    }else {
				    	 employeeTicketRequest.setDepartmentRoleMappingId(0l);	
				    }
				    /* pagination dummy inputs */ 
				   if(Util.isEmptyObject(employeeTicketRequest.getPageNo())) {
				       employeeTicketRequest.setPageNo(1L);
				   } if(Util.isEmptyObject(employeeTicketRequest.getPageSize())) {
				       employeeTicketRequest.setPageSize(100000L);
				   }
				    employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				  
				    /* if siteIds is not given from front end */
				    if(Util.isEmptyObject(employeeTicketRequest.getSiteIds())) {
				      employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_MY_COMPLAINTS));
				    }
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				 result = (Result) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.GET_TICKET_COMPLAINT_LIST.getUrl(), employeeTicketRequest, Result.class);
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
	
	@RequestMapping(value = "/getTicketAdditionalDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getTicketAdditionalDetails(@RequestBody @NonNull EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException {
		LOGGER.info("******* The control inside of the getTicketAdditionalDetails  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl()) && Util.isNotEmptyObject(employeeTicketRequest.getType()) 
			&& Util.isNotEmptyObject(employeeTicketRequest.getTicketId()) && Util.isNotEmptyObject(employeeTicketRequest.getFlatBookingId())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				/* Taking input parameters from the session  */
 			    employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
			    employeeTicketRequest.setFromId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
			    employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
			    LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_TICKET_ADDITIONAL_DETAILS.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	
	/**
	 * This is responsible to return Ticket count list associated with the given employeeId and departmentId and SiteId.
	 * @param employeeTicketRequest  holding existing employeeId and departmentId and SiteId. values (can be {@code NonNull})
	 * @return Result Ticket count list assosiated with the given employeeId and departmentId and SiteId.
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	@RequestMapping(value = "/getTicketCountList.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getTicketCountList(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getTicketCountList  in  EmployeeTicketRestService ********");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestUrl())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				  /* Taking input parameters from the session  */
				    employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				  /* Taking department role mapping id from session */
				    if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
				      if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentRoleMappingId())) {	
				         employeeTicketRequest.setDepartmentRoleMappingId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentRoleMappingId());
				      }else {
				    	  employeeTicketRequest.setDepartmentRoleMappingId(0l);
				      }
				      if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
				    	  employeeTicketRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
				    	  employeeTicketRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				      }else {
				    	  employeeTicketRequest.setDepartmentId(0l);
				    	  employeeTicketRequest.setRoleId(0l);
				      }
				    }else {
				    	 employeeTicketRequest.setDepartmentRoleMappingId(0l);	
				    }
				    /* pagination dummy inputs */ 
				   if(Util.isEmptyObject(employeeTicketRequest.getPageNo())) {
				       employeeTicketRequest.setPageNo(1L);
				   } if(Util.isEmptyObject(employeeTicketRequest.getPageSize())) {
					   employeeTicketRequest.setPageSize(100000L);
				   }
				    employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				  
				 /* if siteIds is not given from front end */
				 if(Util.isEmptyObject(employeeTicketRequest.getSiteIds()) && ServiceRequestEnum.TICKET_GRAPHS.equals(employeeTicketRequest.getRequestEnum())) {
					 employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.TICKET_GRAPHS));
				 }else if(Util.isEmptyObject(employeeTicketRequest.getSiteIds())) {
					 employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_ALL_TICKETS));
				 } 
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				 result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(
						 EmployeeServiceUrls.GET_TICKET_COUNT_LIST.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	@RequestMapping(value = "/getClosedTicketList.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getClosedTicketList(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {	
		LOGGER.info("*** The control inside of the getClosedTicketList in  EmployeeTicketRestService ***");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey()) && Util.isNotEmptyObject(employeeTicketRequest.getRequestEnum())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				  /* Taking input parameters from the session  */
				    employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				  /* Taking department role mapping id from session */
				    if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
				      if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentRoleMappingId())) {	
				         employeeTicketRequest.setDepartmentRoleMappingId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentRoleMappingId());
				      }else {
				    	  employeeTicketRequest.setDepartmentRoleMappingId(0l);
				      }
				      if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
				    	  employeeTicketRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
				    	  employeeTicketRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				      }else {
				    	  employeeTicketRequest.setDepartmentId(0l);
				    	  employeeTicketRequest.setRoleId(0l);
				      }
				    }else {
				    	 employeeTicketRequest.setDepartmentRoleMappingId(0l);	
				    }
				    /* pagination dummy inputs */ 
				   if(Util.isEmptyObject(employeeTicketRequest.getPageNo())) {
				       employeeTicketRequest.setPageNo(1L);
				   } 
				   if(Util.isEmptyObject(employeeTicketRequest.getPageSize())) {
				       employeeTicketRequest.setPageSize(100000L);
				   }
				    employeeTicketRequest.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				  
				 /* if siteIds is not given from front end */
				 if(Util.isEmptyObject(employeeTicketRequest.getSiteIds())) {
					 employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_CLOSED_TICKETS));
				 }   
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				 result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CLOSED_TICKET_LIST.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	
	@RequestMapping(value = "/getTicketPendingDeptDtls.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getTicketPendingDeptDtls(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {	
		LOGGER.info("*** The control inside of the getTicketPendingDeptDtls in EmployeeTicketRestController ***");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				/* Taking input parameters from the session  */
				employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				/* Taking department role mapping id from session */
				if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
					if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
						employeeTicketRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
				    	employeeTicketRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				    }else {
				    	employeeTicketRequest.setDepartmentId(0l);
				    	employeeTicketRequest.setRoleId(0l);
				    }
				}else {
					 employeeTicketRequest.setDepartmentId(0l);
				     employeeTicketRequest.setRoleId(0l); 
				}
				/* if siteIds is not given from front end */
				if(Util.isEmptyObject(employeeTicketRequest.getSiteIds())) {
					employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_MY_TICKETS));
				} 
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_TICKET_PENDING_DEPT_DTLS.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	@RequestMapping(value = "/getCrmEmployees.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getCrmEmployees(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {	
		LOGGER.info("*** The control inside of the getCrmEmployees in EmployeeTicketRestController ***");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				/* Taking input parameters from the session  */
				employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				/* Taking department role mapping id from session */
				if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
					if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
						employeeTicketRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
				    	employeeTicketRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				    }else {
				    	employeeTicketRequest.setDepartmentId(0l);
				    	employeeTicketRequest.setRoleId(0l);
				    }
				}else {
					 employeeTicketRequest.setDepartmentId(0l);
				     employeeTicketRequest.setRoleId(0l); 
				}
				/* if siteIds is not given from front end */
//				if(Util.isEmptyObject(employeeTicketRequest.getSiteIds())) {
//					employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_MY_TICKETS));
//				} 
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getCrmEmployees.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	
	@RequestMapping(value = "/createTicketTypeDetailsForCRM.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result createTicketTypeDetailsForCRM(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {	
		LOGGER.info("*** The control inside of the createTicketTypeDetailsForCRM in EmployeeTicketRestController ***");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				/* Taking input parameters from the session  */
				employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				/* Taking department role mapping id from session */
				if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
					if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
						employeeTicketRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
				    	employeeTicketRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				    }else {
				    	employeeTicketRequest.setDepartmentId(0l);
				    	employeeTicketRequest.setRoleId(0l);
				    }
				}else {
					 employeeTicketRequest.setDepartmentId(0l);
				     employeeTicketRequest.setRoleId(0l); 
				}
				/* if siteIds is not given from front end */
//				if(Util.isEmptyObject(employeeTicketRequest.getSiteIds())) {
//					employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_MY_TICKETS));
//				} 
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.createTicketTypeDetailsForCRM.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	
	
	

	@RequestMapping(value = "/createTicketEscalationsLevels.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result createTicketEscalationsLevels(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {	
		LOGGER.info("*** The control inside of the createTicketEscalationsLevels in EmployeeTicketRestController ***");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				/* Taking input parameters from the session  */
				employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				/* Taking department role mapping id from session */
				if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
					if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
						employeeTicketRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
				    	employeeTicketRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				    }else {
				    	employeeTicketRequest.setDepartmentId(0l);
				    	employeeTicketRequest.setRoleId(0l);
				    }
				}else {
					 employeeTicketRequest.setDepartmentId(0l);
				     employeeTicketRequest.setRoleId(0l); 
				}
				/* if siteIds is not given from front end */
//				if(Util.isEmptyObject(employeeTicketRequest.getSiteIds())) {
//					employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_MY_TICKETS));
//				} 
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.createTicketEscalationsLevels.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	@RequestMapping(value = "/getEscalationLevel.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getEscalationLevel(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {	
		LOGGER.info("*** The control inside of the getEscalationLevel in EmployeeTicketRestController ***");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				/* Taking input parameters from the session  */
				employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				/* Taking department role mapping id from session */
				if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
					if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
						employeeTicketRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
				    	employeeTicketRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				    }else {
				    	employeeTicketRequest.setDepartmentId(0l);
				    	employeeTicketRequest.setRoleId(0l);
				    }
				}else {
					 employeeTicketRequest.setDepartmentId(0l);
				     employeeTicketRequest.setRoleId(0l); 
				}
				/* if siteIds is not given from front end */
//				if(Util.isEmptyObject(employeeTicketRequest.getSiteIds())) {
//					employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_MY_TICKETS));
//				} 
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getEscalationLevel.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	@RequestMapping(value = "/getEscalationLevelEmployees.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getEscalationLevelEmployees(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {	
		LOGGER.info("*** The control inside of the getEscalationLevelEmployees in EmployeeTicketRestController ***");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				/* Taking input parameters from the session  */
				employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				/* Taking department role mapping id from session */
				if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
					if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
						employeeTicketRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
				    	employeeTicketRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				    }else {
				    	employeeTicketRequest.setDepartmentId(0l);
				    	employeeTicketRequest.setRoleId(0l);
				    }
				}else {
					 employeeTicketRequest.setDepartmentId(0l);
				     employeeTicketRequest.setRoleId(0l); 
				}
				/* if siteIds is not given from front end */
//				if(Util.isEmptyObject(employeeTicketRequest.getSiteIds())) {
//					employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_MY_TICKETS));
//				} 
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getEscalationLevelEmployees.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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
	
	
	
	@RequestMapping(value = "/createTicketEscalationsEcxtentionLevels.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result createTicketEscalationsEcxtentionLevels(@RequestBody EmployeeTicketRequest employeeTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {	
		LOGGER.info("*** The control inside of the createTicketEscalationsEcxtentionLevels in EmployeeTicketRestController ***");
		Result result = null;
		if(Util.isNotEmptyObject(employeeTicketRequest.getSessionKey())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + employeeTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(employeeTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				/* Taking input parameters from the session  */
				employeeTicketRequest.setEmployeeId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				/* Taking department role mapping id from session */
				if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
					if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
						employeeTicketRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
				    	employeeTicketRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				    }else {
				    	employeeTicketRequest.setDepartmentId(0l);
				    	employeeTicketRequest.setRoleId(0l);
				    }
				}else {
					 employeeTicketRequest.setDepartmentId(0l);
				     employeeTicketRequest.setRoleId(0l); 
				}
				/* if siteIds is not given from front end */
//				if(Util.isEmptyObject(employeeTicketRequest.getSiteIds())) {
//					employeeTicketRequest.setSiteIds(getSiteIds(employeeSession,Module.TICKETING,SubModule.VIEW_MY_TICKETS));
//				} 
				LOGGER.debug("*** The employeeTicketRequest obj ***"+employeeTicketRequest);
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.createTicketEscalationsEcxtentionLevels.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
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