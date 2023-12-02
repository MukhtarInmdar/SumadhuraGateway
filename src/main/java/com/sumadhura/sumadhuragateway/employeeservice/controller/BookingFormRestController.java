/**
 * 
 */
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
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumadhura.sumadhuragateway.dto.BookingFormRequest;
import com.sumadhura.sumadhuragateway.dto.BookingFormSavedStatusResponse;
import com.sumadhura.sumadhuragateway.dto.BookingFormsListResponse;
import com.sumadhura.sumadhuragateway.dto.Customer;
import com.sumadhura.sumadhuragateway.dto.CustomerBookingFormInfo;
import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.ReferFriendWrapper;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.employeeservice.dto.MessengerRequest;
import com.sumadhura.sumadhuragateway.employeeservice.enums.MetadataId;
import com.sumadhura.sumadhuragateway.employeeservice.enums.Module;
import com.sumadhura.sumadhuragateway.employeeservice.enums.SubModule;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.exception.UnauthorizedException;
import com.sumadhura.sumadhuragateway.util.AESEncryptDecrypt;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

import lombok.NonNull;


/**
 * BookingFormRestController class provides Booking Form specific services.
 * 
 * @author Srivenu_Aare
 * @since 7.7.2019
 * @time 05:59AM
 */
@RestController("BookingFormRestController")
@RequestMapping(value = "/employeeservice/bookingFormService")
public class BookingFormRestController extends SessionValidate {

	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("EmployeeSessionHandlerImpl")
	private SessionHandler employeeSessionHandlerImpl;
	
	@Autowired
	@Qualifier("EmployeeTicketRestController")
	private EmployeeTicketRestController employeeTicketRestController;
	
	@Autowired
    private HttpServletRequest httpServletRequest;
	
	@Value("${SALES_FORCE_IPS}")
	private  String salesForceIps;
	
	@Value("${encryptedKey}")
	private  String encryptedKey ;
	
	@Value("${SALESFORCE_MERCHANT_ID}")
	private  String salesForceMerchantId;
	
	@Value("${CUSTOMERAPP_MERCHANT_ID}")
	private  String customerAppMerchantId;
	
	
	
	private final static Logger LOGGER = Logger.getLogger(BookingFormRestController.class);
	
	/**
	 * This is responsible to return FormsList associated with the given employeeId and departmentId and SiteId.
	 * @param BookingFormRequest  holding existing employeeId and departmentId and SiteId. values (can be {@code NonNull})
	 * @return Result BookingFormsListResponse associated with the given given employeeId and departmentId and SiteId.
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	@RequestMapping(value = "/getFormsList.spring", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON)
	public Result getFormsList(@RequestBody @NonNull  BookingFormRequest bookingFormRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getFormsList  in  BookingFormRestController ********");
		Result result = null;
		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey()) && Util.isNotEmptyObject(bookingFormRequest.getRequestUrl())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 bookingFormRequest.setMenuId(Long.valueOf(2l));
				 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 if(Util.isNotEmptyObject(bookingFormRequest.getSiteId())) {
					 bookingFormRequest.setEmpSiteId(Arrays.asList(bookingFormRequest.getSiteId()));
				 }else {
					 bookingFormRequest.setEmpSiteId(employeeTicketRestController.getSiteIds(employeeSession,Module.CUSTOMER_DETAILS,SubModule.VIEW_ALL_CUSTOMERS)); 
				 }
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+bookingFormRequest);
				 result = (BookingFormsListResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.bookingFormsList.getUrl(), bookingFormRequest, BookingFormsListResponse.class);
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
	 * This is responsible to return FormsList associated with the given employeeId and departmentId and SiteId.
	 * @param BookingFormRequest  holding existing employeeId and departmentId and SiteId. values (can be {@code NonNull})
	 * @return Result BookingFormsListResponse associated with the given given employeeId and departmentId and SiteId.
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws UnauthorizedException 
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/saveBookingDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Result saveBookingDetails(@RequestBody @NonNull BookingFormRequest bookingFormRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException, UnauthorizedException{	
		LOGGER.info("******* The control inside of the getFormsList  in  BookingFormRestController ********");
		Result result = null;
		int portNumber = request.getLocalPort();
		bookingFormRequest.setPortNumber((long)portNumber);
		try {
			ObjectMapper mapper = new ObjectMapper();
			//Java objects to JSON string - compact-print
	        String jsonString = mapper.writeValueAsString(bookingFormRequest);
	        //System.out.println(jsonString);
			/*int PRETTY_PRINT_INDENT_FACTOR = 4;
			JSONObject xmlJSONObj = XML.toJSONObject(bookingFormRequest);
			String 	responseMsg = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);*/
			LOGGER.info("New Booking Reqeust Object \n"+jsonString); 
			//saveBookingJsonInFile(jsonString);
		}catch (Exception ex) {
			LOGGER.info(ex.getMessage(), ex);
		}
		
		/*  If request is coming from SalesForce  */
		if(Util.isNotEmptyObject(bookingFormRequest.getMerchantId())){
			String remoteAddress = httpServletRequest.getRemoteAddr();
	    	LOGGER.info("** The Client remote address is **"+remoteAddress); 
	    	String decryptedSessionKey = AESEncryptDecrypt.decrypt(bookingFormRequest.getMerchantId(),AESEncryptDecrypt.convertKeyToHex(encryptedKey));	
	    	if((true || Arrays.asList(salesForceIps.split(",")).contains(remoteAddress)) && (salesForceMerchantId.equalsIgnoreCase(decryptedSessionKey))) {
	    		 LOGGER.debug("*** The employeeTicketRequest obj ***"+bookingFormRequest);
	    		 /* Adding menu level Id */
	    		 bookingFormRequest.setMenuId(Long.valueOf(2l));
				 result = (BookingFormSavedStatusResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.savebookingForms.getUrl(), bookingFormRequest, BookingFormSavedStatusResponse.class);
				 return result;
	    	
	    	}else {
			   List<String> errors = new ArrayList<String>();
			   errors.add("The Unauthorized Ip address or Invalid SalesForce Merchant Id.");
			   throw new UnauthorizedException(errors);
			 }
	    /*  If the request is coming from AMS  */	
		}else {
		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey()) && Util.isNotEmptyObject(bookingFormRequest.getRequestUrl())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 bookingFormRequest.setMenuId(Long.valueOf(2l));
				 bookingFormRequest.setEmpSiteId(employeeTicketRestController.getSiteIds(employeeSession,Module.CUSTOMER_DETAILS,SubModule.CREATE_CUSTOMER));
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+bookingFormRequest);
				 result = (BookingFormSavedStatusResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.savebookingForms.getUrl(), bookingFormRequest, BookingFormSavedStatusResponse.class);
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
	
//	@Path("/getRegistrationDetails.spring")
	//@Path("/getCustomerAndCo_AppDetails.spring")
	//@Path("/getUnitDetails.spring")
	/**
	 * @author Aniket Chavan 
	 * @Description loading Flat registration details from flat booking table
	 * @param bookingFormRequest
	 * @return
	 * @throws SessionTimeoutException
	 * @throws InSufficeientInputException
	 */
	@RequestMapping(value ="/getRegistrationDetails.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getRegistrationDetails(@RequestBody BookingFormRequest bookingFormRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getCustomerDetailsAndPendingAmounts in EmployeeFinancialRestController ***");	
		String sessionKey = bookingFormRequest.getSessionKey();
		sessionValidate(sessionKey);
		//log.debug("** The EmployeeSession Object is ***"+employeeSession);
		//bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		//bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getRegistrationDetails.getUrl(), bookingFormRequest, Result.class);	
	}
	
	/**
	 * @author Aniket Chavan 
	 * @Description loading customer and it's co-applicant details
	 * @param bookingFormRequest
	 * @return
	 * @throws SessionTimeoutException
	 * @throws InSufficeientInputException
	 */
	@RequestMapping(value ="/getCustomerAndCo_AppDetails.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getCustomerAndCo_AppDetails(@RequestBody BookingFormRequest bookingFormRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getCustomerDetailsAndPendingAmounts in EmployeeFinancialRestController ***");	
		String sessionKey = bookingFormRequest.getSessionKey();
		sessionValidate(sessionKey);
		//log.debug("** The EmployeeSession Object is ***"+employeeSession);
		//bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		//bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (CustomerBookingFormInfo) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getCustomerAndCo_AppDetails.getUrl(), bookingFormRequest, CustomerBookingFormInfo.class);	
	}
	
	/**
	 * @author Aniket Chavan 
	 * @Description loading Flat(Unit) details
	 * @param bookingFormRequest
	 * @return
	 * @throws SessionTimeoutException
	 * @throws InSufficeientInputException
	 */
	@RequestMapping(value ="/getUnitDetails.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getUnitDetails(@RequestBody BookingFormRequest bookingFormRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getCustomerDetailsAndPendingAmounts in EmployeeFinancialRestController ***");	
		String sessionKey = bookingFormRequest.getSessionKey();
		sessionValidate(sessionKey);
		//log.debug("** The EmployeeSession Object is ***"+employeeSession);
		//bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		//bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getUnitDetails.getUrl(), bookingFormRequest, Result.class);	
	}
	
	/**
	 * This is responsible to return FormsList associated with the given employeeId and departmentId and SiteId.
	 * @param BookingFormRequest  holding existing employeeId and departmentId and SiteId. values (can be {@code NonNull})
	 * @return Result BookingFormsListResponse associated with the given given employeeId and departmentId and SiteId.
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	@RequestMapping(value = "/getBookingDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Result getBookingDetails(@RequestBody @NonNull BookingFormRequest bookingFormRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getFormsList  in  BookingFormRestController ********");
		Result result = null;
		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey()) && Util.isNotEmptyObject(bookingFormRequest.getRequestUrl())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 bookingFormRequest.setEmpSiteId(employeeTicketRestController.getSiteIds(employeeSession,Module.CUSTOMER_DETAILS,SubModule.VIEW_ALL_CUSTOMERS));
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+bookingFormRequest);
				 result = (CustomerBookingFormInfo) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getbookingForms.getUrl(), bookingFormRequest, CustomerBookingFormInfo.class);
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
	 * This is responsible to return FormsList associated with the given employeeId and departmentId and SiteId.
	 * @param BookingFormRequest  holding existing employeeId and departmentId and SiteId. values (can be {@code NonNull})
	 * @return Result BookingFormsListResponse associated with the given given employeeId and departmentId and SiteId.
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws UnauthorizedException 
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/actionBookingDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Result actionBookingDetails(@RequestBody @NonNull BookingFormRequest bookingFormRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException, UnauthorizedException{	
		LOGGER.info("******* The control inside of the actionBookingDetails  in  BookingFormRestController ********");
		Result result = null;
		/*  If request is coming from SalesForce  */
		int portNumber = request.getLocalPort();
		bookingFormRequest.setPortNumber((long)portNumber);
		if(Util.isNotEmptyObject(bookingFormRequest.getMerchantId())){
			String remoteAddress = httpServletRequest.getRemoteAddr();
	    	LOGGER.info("** The Client remote address is **"+remoteAddress); 
	    	String decryptedSessionKey = AESEncryptDecrypt.decrypt(bookingFormRequest.getMerchantId(),AESEncryptDecrypt.convertKeyToHex(encryptedKey));	
	    	if((true || Arrays.asList(salesForceIps.split(",")).contains(remoteAddress)) && (salesForceMerchantId.equalsIgnoreCase(decryptedSessionKey)) ||(customerAppMerchantId.equalsIgnoreCase(decryptedSessionKey))) {
	    	
	    	if (Util.isNotEmptyObject(bookingFormRequest.getSiteName()) && Util.isNotEmptyObject(bookingFormRequest.getBlockName())
					&& Util.isNotEmptyObject(bookingFormRequest.getFloorName()) && Util.isNotEmptyObject(bookingFormRequest.getFlatNo())
					//&& bookingFormRequest.getBookingformCanceledDate() != null && Util.isNotEmptyObject(bookingFormRequest.getComments()) 
					&& Util.isNotEmptyObject(bookingFormRequest.getRequestUrl()) && Util.isNotEmptyObject(bookingFormRequest.getActionStr()) 
					&& Util.isNotEmptyObject(bookingFormRequest.getEmployeeName()) ) {
	    			
	    			if(bookingFormRequest.getActionStr().equalsIgnoreCase("cancel")) {//acp
	    				//this condition required only for cancel status of booking
	    				if(bookingFormRequest.getBookingformCanceledDate() == null || Util.isEmptyObject(bookingFormRequest.getComments())) {
	    					 /*List<String> errors = new ArrayList<String>();
		   					 errors.add("Insufficeint Input is Given.");*/
		   					 throw new InSufficeientInputException(Arrays.asList("Insufficeint Input is Given."));
	    				}
	    			}
	    		
		    		 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.actionbookingForm.getUrl(), bookingFormRequest, Result.class);
		    	}else {
		    		 List<String> errors = new ArrayList<String>();
					 errors.add("Insufficeint Input is Given.");
					 throw new InSufficeientInputException(errors);
		    	}
		   }else {
				List<String> errors = new ArrayList<String>();
				errors.add("The Unauthorized Ip address or Invalid SalesForce Merchant Id.");
				throw new UnauthorizedException(errors);
		   }
		}else {
		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey()) && Util.isNotEmptyObject(bookingFormRequest.getRequestUrl())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 bookingFormRequest.setEmpSiteId(employeeTicketRestController.getSiteIds(employeeSession,Module.CUSTOMER_DETAILS,SubModule.APPROVED_CUSTOMER));
				 LOGGER.debug("*** The employeeTicketRequest obj ***"+bookingFormRequest);
				 result = (BookingFormSavedStatusResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.actionbookingForm.getUrl(), bookingFormRequest, BookingFormSavedStatusResponse.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		  }
		}
		return result;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateApplicantOrCoapplicant.spring")
	public Result updateApplicantOrCoApplicantData(@RequestBody CustomerBookingFormInfo customerBookingFormInfo) throws IllegalAccessException, InSufficeientInputException, SessionTimeoutException {
		String cacheKey = customerBookingFormInfo.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		customerBookingFormInfo.setEmpId(employee.getLoginResponse().getEmpId());

		try {
			ObjectMapper mapper = new ObjectMapper();
			//Java objects to JSON string - compact-print
	        String jsonString = mapper.writeValueAsString(customerBookingFormInfo);
	        //System.out.println(jsonString);
			/*int PRETTY_PRINT_INDENT_FACTOR = 4;
			JSONObject xmlJSONObj = XML.toJSONObject(bookingFormRequest);
			String 	responseMsg = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);*/
			LOGGER.info("Update Booking Reqeust Object \n"+jsonString); 
		}catch (Exception ex) {
			LOGGER.info("Update Booking Reqeust Object Exception "+ex.getMessage(), ex);
		}

		//LOGGER.debug("*** The EmployeeSession object ***" + employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.actionbookingFormupdate.getUrl(),
				customerBookingFormInfo, Result.class);
		return result;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/insertOrUpdateCheckListDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Result insertOrUpdateCheckListDetails(@RequestBody @NonNull BookingFormRequest bookingFormRequest) throws InSufficeientInputException, UnauthorizedException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		LOGGER.info("****** The control inside of the insertOrUpdateCheckListDetails  in  BookingFormRestController *******");
		final CustomerBookingFormInfo customerBookingFormsInfo = bookingFormRequest.getCustomerBookingFormsInfo();
		
		if ( Util.isNotEmptyObject(bookingFormRequest.getMerchantId()) && Util.isNotEmptyObject(customerBookingFormsInfo.getFlatBookingInfo().getFlatInfo().getFlatNo())
				&& Util.isNotEmptyObject(customerBookingFormsInfo.getFlatBookingInfo().getFloorInfo().getFloorName())
				&& Util.isNotEmptyObject(customerBookingFormsInfo.getFlatBookingInfo().getBlockInfo().getName())
				&& Util.isNotEmptyObject(customerBookingFormsInfo.getFlatBookingInfo().getSiteInfo().getName())
				&& Util.isNotEmptyObject(bookingFormRequest.getRequestUrl())
				&& Util.isNotEmptyObject(bookingFormRequest.getActionStr())
				&& Util.isNotEmptyObject(Util.isNotEmptyObject(customerBookingFormsInfo.getCustomerBookingInfo().getSalesTeamLeadId()))
				&& (Util.isNotEmptyObject(customerBookingFormsInfo.getCustomerInfo().getFirstName())
						|| Util.isNotEmptyObject(customerBookingFormsInfo.getCustomerInfo().getLastName()))) {
			
			    String remoteAddress = httpServletRequest.getRemoteAddr();
		    	LOGGER.info("** The Client remote address is **"+remoteAddress); 
		    	
		    	 String decryptedSessionKey = AESEncryptDecrypt.decrypt(bookingFormRequest.getMerchantId(),AESEncryptDecrypt.convertKeyToHex(encryptedKey));	
	    		 LOGGER.info("**** The salesForce merchant key ******" + decryptedSessionKey);
		    	if((true || Arrays.asList(salesForceIps.split(",")).contains(remoteAddress)) && (salesForceMerchantId.equalsIgnoreCase(decryptedSessionKey))) {
		    		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.insertOrUpdateCheckListDetails.getUrl(), bookingFormRequest, Result.class);	
		    	}else {
		    		List<String> errors = new ArrayList<String>();
					errors.add("The Unauthorized Ip address or Invalid SalesForce Merchant Id.");
					throw new UnauthorizedException(errors);
		    	}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/updateApplicantOrCoapplicant.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Result updateApplicantOrCoApplicantData(@RequestBody BookingFormRequest bookingFormRequest,HttpServletRequest request) throws IllegalAccessException, InSufficeientInputException, SessionTimeoutException, UnauthorizedException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		if ( Util.isNotEmptyObject(bookingFormRequest.getMerchantId()) && Util.isNotEmptyObject(bookingFormRequest.getCustomerBookingFormsInfos())){
			    String remoteAddress = httpServletRequest.getRemoteAddr();
		    	LOGGER.info("** The Client remote address is **"+remoteAddress); 
				try {
					ObjectMapper mapper = new ObjectMapper();
					//Java objects to JSON string - compact-print
			        String jsonString = mapper.writeValueAsString(bookingFormRequest);
			        //System.out.println(jsonString);
					/*int PRETTY_PRINT_INDENT_FACTOR = 4;
					JSONObject xmlJSONObj = XML.toJSONObject(bookingFormRequest);
					String 	responseMsg = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);*/
					LOGGER.info("Update Booking Reqeust Object \n"+jsonString); 
				}catch (Exception ex) {
					LOGGER.info(ex.getMessage(), ex);
				}
				int portNumber = request.getLocalPort();
				bookingFormRequest.setPortNumber((long)portNumber);
		    	String decryptedMerchantKey = AESEncryptDecrypt.decrypt(bookingFormRequest.getMerchantId(),AESEncryptDecrypt.convertKeyToHex(encryptedKey));	
	    		LOGGER.info("**** The salesForce merchant key ******" + decryptedMerchantKey);
		    	if((true || Arrays.asList(salesForceIps.split(",")).contains(remoteAddress)) && (salesForceMerchantId.equalsIgnoreCase(decryptedMerchantKey))) {
		    		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.BOOKING_FORM_APPLICANT_DATA_UPDATE.getUrl(),bookingFormRequest, Result.class);	
		    	}else {
		    		List<String> errors = new ArrayList<String>();
					errors.add("The Unauthorized Ip address or Invalid SalesForce Merchant Id.");
					throw new UnauthorizedException(errors);
		    	}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}

	@RequestMapping(value = "/getAgreementTypesList.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Result getAggrementTypesList(@RequestBody @NonNull BookingFormRequest bookingFormRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the getAggrementTypesList  in  BookingFormRestController ********");
		Result result = null;
/*		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey()) && Util.isNotEmptyObject(bookingFormRequest.getRequestUrl())
				 && Util.isNotEmptyObject(bookingFormRequest.getSiteId())) {
*/			
			if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey()) && Util.isNotEmptyObject(bookingFormRequest.getRequestUrl())
					&& Util.isNotEmptyObject(bookingFormRequest.getFlatBookingId()) && Util.isNotEmptyObject(bookingFormRequest.getSiteId())){

			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 /* Taking input parameters from the session  */
				 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 //bookingFormRequest.setEmpSiteId(employeeTicketRestController.getSiteIds(employeeSession,Module.CUSTOMER_DETAILS,SubModule.VIEW_ALL_CUSTOMERS));
				 int portNumber = request.getLocalPort();
				 bookingFormRequest.setPortNumber((long)portNumber);
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getAgreementTypesList.getUrl(), bookingFormRequest, Result.class);
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
	 * This is responsible to return FormsList associated with the given employeeId and departmentId and SiteId.
	 * @param BookingFormRequest  holding existing employeeId and departmentId and SiteId. values (can be {@code NonNull})
	 * @return Result BookingFormsListResponse associated with the given given employeeId and departmentId and SiteId.
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	@RequestMapping(value = "/generateBookingWelcomeLetter.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Result generateAllotmentLetter(@RequestBody @NonNull BookingFormRequest bookingFormRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the generateBookingWelcomeLetter  in  BookingFormRestController ********");
		Result result = null;
		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey()) && Util.isNotEmptyObject(bookingFormRequest.getRequestUrl())
				&& Util.isNotEmptyObject(bookingFormRequest.getFlatBookingId()) && Util.isNotEmptyObject(bookingFormRequest.getSiteId())
			//	 && Util.isNotEmptyObject(bookingFormRequest.getAgreementType())
				) {
			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 ///LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 //bookingFormRequest.setEmpSiteId(employeeTicketRestController.getSiteIds(employeeSession,Module.CUSTOMER_DETAILS,SubModule.VIEW_ALL_CUSTOMERS));
				 int portNumber = request.getLocalPort();
				 bookingFormRequest.setPortNumber((long)portNumber);
				 LOGGER.debug("*** The generateBookingWelcomeLetter obj ***"+bookingFormRequest);
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.generateBookingWelcomeLetter.getUrl(), bookingFormRequest, Result.class);
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
	 * @Description this method will load the customer welcome letter documents
	 * @param bookingFormRequest
	 * @param request
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws SessionTimeoutException
	 * @throws InSufficeientInputException
	 * @throws IOException
	 */
	@RequestMapping(value = "/getCustomerFlatDocuments.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Result getCustomerFlatDocuments(@RequestBody @NonNull BookingFormRequest bookingFormRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the generateAllotmentLetter  in  BookingFormRestController ********");
		Result result = null;
		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey()) && Util.isNotEmptyObject(bookingFormRequest.getRequestUrl())
				&& Util.isNotEmptyObject(bookingFormRequest.getFlatBookingId()) && Util.isNotEmptyObject(bookingFormRequest.getSiteId())
				) {
			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				MessengerRequest messengerRequest = new MessengerRequest(); 
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 //LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 
				 messengerRequest.setRecipientId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 messengerRequest.setRecipientType(MetadataId.EMPLOYEE.getId());
				 messengerRequest.setFlatBookingId(bookingFormRequest.getFlatBookingId());
				 messengerRequest.setSiteIds(Arrays.asList(bookingFormRequest.getSiteId()));
				 messengerRequest.setRequestUrl(bookingFormRequest.getRequestUrl());
				 messengerRequest.setFlatId(bookingFormRequest.getFlatId());
				 //bookingFormRequest.setEmpSiteId(employeeTicketRestController.getSiteIds(employeeSession,Module.CUSTOMER_DETAILS,SubModule.VIEW_ALL_CUSTOMERS));
				 int portNumber = request.getLocalPort();
				 bookingFormRequest.setPortNumber((long)portNumber);
				 LOGGER.debug("*** The generateAllotmentLetter obj ***"+bookingFormRequest);
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getCustomerFlatDocuments.getUrl(), messengerRequest, Result.class);
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
	@RequestMapping(value = "/generateBookingNOCLetter.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Result generateNOCLetter(@RequestBody @NonNull BookingFormRequest bookingFormRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the generateNOCLetter  in  BookingFormRestController ********");
		Result result = null;
		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey()) && Util.isNotEmptyObject(bookingFormRequest.getRequestUrl())
				&& Util.isNotEmptyObject(bookingFormRequest.getFlatBookingId()) && Util.isNotEmptyObject(bookingFormRequest.getSiteId())
				) {
			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 ///LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 //bookingFormRequest.setEmpSiteId(employeeTicketRestController.getSiteIds(employeeSession,Module.CUSTOMER_DETAILS,SubModule.VIEW_ALL_CUSTOMERS));
				 int portNumber = request.getLocalPort();
				 bookingFormRequest.setPortNumber((long)portNumber);
				 LOGGER.debug("*** The generateBookingNOCLetter obj ***"+bookingFormRequest);
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.generateBookingNOCLetter.getUrl(), bookingFormRequest, Result.class);
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
	@RequestMapping(value ="/getNOCDocuments.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getNOCShowStatus(@RequestBody BookingFormRequest bookingFormRequest,HttpServletRequest request)  throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException {
		LOGGER.info("*** The Control is inside the getNOCDocuments in BookingFormRestController ***");	
		Result result = null;
		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey()) 
				&& Util.isNotEmptyObject(bookingFormRequest.getFlatBookingId()) 
				) {
			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 ///LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 int portNumber = request.getLocalPort();
				 bookingFormRequest.setPortNumber((long)portNumber);
				 LOGGER.debug("*** The generateBookingNOCLetter obj ***"+bookingFormRequest);
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getNOCDocuments.getUrl(), bookingFormRequest, Result.class);	
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
	@RequestMapping(value = "/inactiveBookings.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Result inactiveBookings(@RequestBody @NonNull BookingFormRequest bookingFormRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the generateAllotmentLetter  in  BookingFormRestController ********");
		Result result = null;
		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey()) && Util.isNotEmptyObject(bookingFormRequest.getSiteId())
				) {
			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				 //bookingFormRequest.setEmpSiteId(employeeTicketRestController.getSiteIds(employeeSession,Module.CUSTOMER_DETAILS,SubModule.VIEW_ALL_CUSTOMERS));
				 int portNumber = request.getLocalPort();
				 bookingFormRequest.setPortNumber((long)portNumber);
				 LOGGER.debug("*** The generateAllotmentLetter obj ***"+bookingFormRequest);
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.inactiveBookings.getUrl(), bookingFormRequest, Result.class);
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

	
	
	@RequestMapping(value ="/getNOCDocumentsList.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getNOCDocumentsList(@RequestBody BookingFormRequest bookingFormRequest,HttpServletRequest request)  throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException {
		LOGGER.info("*** The Control is inside the getNOCDocumentsList in BookingFormRestController ***");	
		Result result = null;
		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 ///LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 int portNumber = request.getLocalPort();
				 bookingFormRequest.setPortNumber((long)portNumber);
				 LOGGER.debug("*** The generateBookingNOCLetter obj ***"+bookingFormRequest);
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getNOCDocumentsList.getUrl(), bookingFormRequest, Result.class);	
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
	
	
	@RequestMapping(value ="/getCustomerData.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getCustomerData(@RequestBody BookingFormRequest bookingFormRequest,HttpServletRequest request)  throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException {
		LOGGER.info("*** The Control is inside the getCustomerData in BookingFormRestController ***");	
		Result result = null;
		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 ///LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 int portNumber = request.getLocalPort();
				 bookingFormRequest.setPortNumber((long)portNumber);
				 LOGGER.debug("*** The getCustomerData obj ***"+bookingFormRequest);
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getCustomerData.getUrl(), bookingFormRequest, Result.class);	
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
	@RequestMapping(value = "/generateLoanNOCLetter.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public Result generateLoanNOCLetter(@RequestBody @NonNull BookingFormRequest bookingFormRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException{	
		LOGGER.info("******* The control inside of the generateLoanNOCLetter  in  BookingFormRestController ********");
		Result result = null;
		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey()) && Util.isNotEmptyObject(bookingFormRequest.getRequestUrl())
				&& Util.isNotEmptyObject(bookingFormRequest.getFlatBookingId()) && Util.isNotEmptyObject(bookingFormRequest.getSiteId())
				) {
			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 ///LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 //bookingFormRequest.setEmpSiteId(employeeTicketRestController.getSiteIds(employeeSession,Module.CUSTOMER_DETAILS,SubModule.VIEW_ALL_CUSTOMERS));
				 int portNumber = request.getLocalPort();
				 bookingFormRequest.setPortNumber((long)portNumber);
				 LOGGER.debug("*** The generateLoanNOCLetter obj ***"+bookingFormRequest);
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.generateLoanNOCLetter.getUrl(), bookingFormRequest, Result.class);
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
	
	@RequestMapping(value ="/getKycDocumentsList.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getKycDocumentsList(@RequestBody BookingFormRequest bookingFormRequest,HttpServletRequest request)  throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException {
		LOGGER.info("*** The Control is inside the getKycDocumentsList in BookingFormRestController ***");	
		Result result = null;
		if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey())) {
			LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				 ///LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
				 /* Taking input parameters from the session  */
				 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				 bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
				 int portNumber = request.getLocalPort();
				 bookingFormRequest.setPortNumber((long)portNumber);
				 LOGGER.debug("*** The getKycDocumentsList obj ***"+bookingFormRequest);
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getKycDocumentsList.getUrl(), bookingFormRequest, Result.class);	
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
	   @RequestMapping(value="/stateList.spring",method=RequestMethod.POST)
	    public Result getStateList(@RequestBody BookingFormRequest bookingFormRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException  {
			Result result = null;
			 Customer customer =new Customer();
			if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey())) {
				LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
				String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
				boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
				if (isSessionValid) {
					EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
					 ///LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
					 /* Taking input parameters from the session  */
					 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
					 bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
					 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
					 LOGGER.debug("*** The getKycDocumentsList obj ***"+bookingFormRequest);
					 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getKycDocumentsList.getUrl(), bookingFormRequest, Result.class);	
				} else {
					throw new SessionTimeoutException("Your Session has been Timed Out.");
				}
			}else {
				List<String> errors = new ArrayList<String>();
				errors.add("Insufficeint Input is Given.");
				throw new InSufficeientInputException(errors);
			}
	    	 ReferFriendWrapper referFriendWrapper = (ReferFriendWrapper)RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_STATE_LIST.getUrl(), customer, ReferFriendWrapper.class);
	         return referFriendWrapper;
	    }
	   

		@RequestMapping(value ="/getFlatDetails.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
		public Result getFlatDetails(@RequestBody BookingFormRequest bookingFormRequest,HttpServletRequest request)  throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException {
			LOGGER.info("*** The Control is inside the getFlatDetails in BookingFormRestController ***");	
			Result result = null;
			if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey())) {
				LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
				String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
				boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
				if (isSessionValid) {
					EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
					 ///LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
					 /* Taking input parameters from the session  */
					 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
					 bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
					 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
					 int portNumber = request.getLocalPort();
					 bookingFormRequest.setPortNumber((long)portNumber);
					 LOGGER.debug("*** The getFlatDetails obj ***"+bookingFormRequest);
					 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getFlatDetails.getUrl(), bookingFormRequest, Result.class);	
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
		
		
		
		@RequestMapping(value ="/getNonBookedDetails.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
		public Result getNonBookedDetails(@RequestBody BookingFormRequest bookingFormRequest,HttpServletRequest request)  throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException {
			LOGGER.info("*** The Control is inside the getNonBookedDetails in BookingFormRestController ***");	
			Result result = null;
			if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey())) {
				LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
				String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
				boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
				if (isSessionValid) {
					EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
					 ///LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
					 /* Taking input parameters from the session  */
					 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
					 bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
					 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
					 int portNumber = request.getLocalPort();
					 bookingFormRequest.setPortNumber((long)portNumber);
					 LOGGER.debug("*** The getCustomerData obj ***"+bookingFormRequest);
					 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getNonBookedDetails.getUrl(), bookingFormRequest, Result.class);	
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
		
	   @RequestMapping(value="/getCityList.spring",method=RequestMethod.POST)
	    public Result getCityList(@RequestBody BookingFormRequest bookingFormRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException  {
				Result result = null;
				
				if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey())) {
					LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
					String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
					boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
					if (isSessionValid) {
						EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
					
						 LOGGER.debug("*** The getCityList obj ***"+bookingFormRequest);
						 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getCityList.getUrl(), bookingFormRequest, Result.class);	
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
	   
	   
	   @RequestMapping(value="/getCountryList.spring",method=RequestMethod.POST)
	    public Result getCountryList(@RequestBody BookingFormRequest bookingFormRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException  {
				Result result = null;
				
				if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey())) {
					LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
					String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
					boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
					if (isSessionValid) {
						EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
						
						 LOGGER.debug("*** The getCountryList obj ***"+bookingFormRequest);
						 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getCountryList.getUrl(), bookingFormRequest, Result.class);	
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
	   @RequestMapping(value ="/getFinSchemes.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
		public Result getFinSchemes(@RequestBody BookingFormRequest bookingFormRequest,HttpServletRequest request)  throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException {
			LOGGER.info("*** The Control is inside the getFinSchemes in BookingFormRestController ***");	
			Result result = null;
			if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey())) {
				LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
				String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
				boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
				if (isSessionValid) {
					EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
					 ///LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
					 /* Taking input parameters from the session  */
					 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
					 bookingFormRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
					 bookingFormRequest.setDeptId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l);
					 int portNumber = request.getLocalPort();
					 bookingFormRequest.setPortNumber((long)portNumber);
					 LOGGER.debug("*** The getCustomerData obj ***"+bookingFormRequest);
					 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getFinSchemes.getUrl(), bookingFormRequest, Result.class);	
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
	   
	   @RequestMapping(value ="/getOldSalesForesBookingIds.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
		public Result getOldSalesForesBookingIds(@RequestBody BookingFormRequest bookingFormRequest,HttpServletRequest request)  throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException, IOException {
			LOGGER.info("*** The Control is inside the getOldSalesForesBookingIds in BookingFormRestController ***");	
			Result result = null;
			if(Util.isNotEmptyObject(bookingFormRequest.getSessionKey())) {
				LOGGER.info("**** The Employee sessionkey  is ****" + bookingFormRequest.getSessionKey());
				String sessionKey = sessionUtils.decryptSessionKey(bookingFormRequest.getSessionKey());
				boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
				if (isSessionValid) {
					EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
					 ///LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
					 /* Taking input parameters from the session  */
					 bookingFormRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
					
				if(Util.isNotEmptyObject(bookingFormRequest.getSiteId())) {	
				 bookingFormRequest.setSiteId(bookingFormRequest.getSiteId());
				}	
					 LOGGER.debug("*** The getCustomerData obj ***"+bookingFormRequest);
					 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getOldSalesForesBookingIds.getUrl(), bookingFormRequest, Result.class);	
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
	   
	   
}

