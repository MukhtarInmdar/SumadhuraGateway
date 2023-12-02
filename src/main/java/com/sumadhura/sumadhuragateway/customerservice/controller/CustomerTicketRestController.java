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
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.CustomerTicketRequest;
import com.sumadhura.sumadhuragateway.dto.Device;
import com.sumadhura.sumadhuragateway.dto.EmployeeTicketRequest;
import com.sumadhura.sumadhuragateway.dto.EmployeeTicketResponse;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.dto.TicketTypeResponseWrapper;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.Department;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * CustomerTicketRestController class provides Customer Ticket specific
 * services.
 * 
 * @author Venkat_Koniki
 * @since 23.04.2019
 * @time 12.56 PM
 */

@RestController
@RequestMapping(value = "/customerservice/customerTicket")
public class CustomerTicketRestController {

	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("CustomerSessionHandlerImpl")
	private SessionHandler customerSessionHandlerImpl;

	private final Logger LOGGER = Logger.getLogger(CustomerTicketRestController.class);

	@RequestMapping(value = "/getTicketTypeDetails.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result getTicketTypeDetails(@RequestBody Device device) throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
			SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the getDepartments in CustomerTicketRestController **********"
				+ device.getDeviceToken());
		Result result = null;
		if (device.getSessionKey() != null && device.getDeviceToken() != null) {
			LOGGER.info("**** The Customer sessionkey  is ****" + device.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(device.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey, device.getDeviceToken());
			LOGGER.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				CustomerSession customer = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				device.setDeviceToken(customer.getDevice().getDeviceToken());
				CustomerTicketRequest customerTicketRequest = new CustomerTicketRequest();
				customerTicketRequest.setFlatNo(customer.getFlat().getFlatId());
				customerTicketRequest.setDeviceToken(customer.getDevice().getDeviceToken());
				customerTicketRequest.setCustNo(customer.getCustomerId());
				customerTicketRequest.setSiteId(customer.getFlat().getFloor().getBlock().getSite().getId());
				LOGGER.info("*** The customer session object ****" + customer);
				result = (TicketTypeResponseWrapper) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customerTicketTypeDetails.getUrl(), customerTicketRequest, TicketTypeResponseWrapper.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}
	@RequestMapping(value = "/createCustomerServiceRequest.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result createRequest(@RequestBody CustomerTicketRequest srRequest,HttpServletRequest request) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("***** The control is inside the createTicketRequest method in CustomerTicketRestController *****"
				+ srRequest.getDeviceToken());
		Result result = null;
		if (srRequest.getSessionKey() != null && srRequest.getDeviceToken() != null) {
			LOGGER.info("**** The Customer sessionkey  is ****" + srRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(srRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey, srRequest.getDeviceToken());
			LOGGER.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				CustomerSession customer = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				LOGGER.info("*** The customer session object ****" + customer);
				/* set inputs to object from session */
				srRequest.setDeviceToken(customer.getDevice().getDeviceToken());
				srRequest.setCustNo(customer.getCustomerId());
				srRequest.setFlatNo(customer.getFlat().getFlatId());
				srRequest.setSiteId(customer.getFlat().getFloor().getBlock().getSite().getId());
				
				int portNumber = request.getLocalPort();
				srRequest.setPortNumber((long)portNumber);
				result = (Result) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customercreateTicketRequest.getUrl(), srRequest, Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}
	@RequestMapping(value = "/getCustomerRaisedTicketList.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result getCustomerRaisedTicketList(@RequestBody CustomerTicketRequest customerTicketRequest) throws InSufficeientInputException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("**** The control inside of the getCustomerRaisedTicketList CustomerTicketRestController *****"
				+ customerTicketRequest.getDeviceToken());
		Result result = null;
		if (customerTicketRequest.getSessionKey() != null && customerTicketRequest.getDeviceToken() != null) {

			LOGGER.info("**** The Customer sessionkey  is ****" + customerTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(customerTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey, customerTicketRequest.getDeviceToken());
			LOGGER.info("**** The session validation info is ******" + isSessionValid);

			if (isSessionValid) {
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				LOGGER.info("*** The customer session object ****" + customerSession);
				/* set inputs to object from session */
				customerTicketRequest.setCustNo(customerSession.getCustomerId());
				customerTicketRequest.setFlatNo(customerSession.getFlat().getFlatId());
				customerTicketRequest.setFlatBookingId(customerSession.getFlatBookingId());
				
				EmployeeTicketRequest employeeTicketRequest = new EmployeeTicketRequest();
				employeeTicketRequest.setFlatBookingId(customerSession.getFlatBookingId());
				employeeTicketRequest.setRequestUrl("getTicket");
				 /* pagination dummy inputs */ 
				   if(Util.isEmptyObject(employeeTicketRequest.getPageNo())) {
				       employeeTicketRequest.setPageNo(1L);
				   } if(Util.isEmptyObject(employeeTicketRequest.getPageSize())) {
				       employeeTicketRequest.setPageSize(10000L);
				   }
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(
						EmployeeServiceUrls.customerraisedTicketList.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}
	@RequestMapping(value = "/getTicketDetails.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result getTicketDetails(@RequestBody CustomerTicketRequest ticketRequest) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info(
				"***** The control is inside the getTicketDetails controller *******" + ticketRequest.getDeviceToken());
		Result result = null;

		if (ticketRequest.getSessionKey() != null && ticketRequest.getDeviceToken() != null) {
			LOGGER.info("**** The Customer sessionkey  is ****" + ticketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(ticketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey, ticketRequest.getDeviceToken());
			LOGGER.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				LOGGER.info("*** The customer session object ****" + customerSession);
				
				EmployeeTicketRequest employeeTicketRequest = new EmployeeTicketRequest();
				employeeTicketRequest.setTicketId(ticketRequest.getTicketId());
				employeeTicketRequest.setRequestUrl("getTicket");
				employeeTicketRequest.setStatusId(ticketRequest.getStatusId());
				
				result = (EmployeeTicketResponse) RestApiUtility.sendJSONPostRequest(
						EmployeeServiceUrls.customerTicketDetails.getUrl(), employeeTicketRequest, EmployeeTicketResponse.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		result.setStatus(result.getDescription());
		return result;
	}
	@RequestMapping(value = "/chatSubmit.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result chatSubmit(@RequestBody CustomerTicketRequest customerTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("***** The control is inside the chatSubmit service in  CustomerTicketRestController *******"+customerTicketRequest.getDeviceToken());
		Result result = null;

		if (customerTicketRequest.getSessionKey() != null && customerTicketRequest.getDeviceToken() != null) {
			LOGGER.info("**** The Customer sessionkey  is ****" + customerTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(customerTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey, customerTicketRequest.getDeviceToken());
			LOGGER.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				LOGGER.info("*** The customer session object ****" + customerSession);
				/* set inputs to object from session */
				customerTicketRequest.setCustNo(customerSession.getCustomerId());
				customerTicketRequest.setFlatNo(customerSession.getFlat().getFlatId());
				
				EmployeeTicketRequest employeeTicketRequest = new EmployeeTicketRequest();
				employeeTicketRequest.setTicketId(customerTicketRequest.getTicketId());
				employeeTicketRequest.setMessage(customerTicketRequest.getTitle());
				employeeTicketRequest.setCustomerId(customerTicketRequest.getCustNo());
				employeeTicketRequest.setFileInfos(customerTicketRequest.getFileInfo());
				
				result = (Result) RestApiUtility.sendJSONPostRequest(
						EmployeeServiceUrls.customerTicketchatSubmit.getUrl(), employeeTicketRequest, Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		
		result.setStatus(result.getDescription());
		
	   return result;
	}
	@RequestMapping(value = "/closeTicket.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result closeTicket(@RequestBody CustomerTicketRequest customerTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("***** The control is inside the closeTicket service in  CustomerTicketRestController *******"+customerTicketRequest.getDeviceToken());
		Result result = null;
		if (customerTicketRequest.getSessionKey() != null && customerTicketRequest.getDeviceToken() != null) {
			LOGGER.info("**** The Customer sessionkey  is ****" + customerTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(customerTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey, customerTicketRequest.getDeviceToken());
			LOGGER.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				LOGGER.info("*** The customer session object ****" + customerSession);
				/* set inputs to object from session */
				customerTicketRequest.setCustNo(customerSession.getCustomerId());
				customerTicketRequest.setFlatNo(customerSession.getFlat().getFlatId());
				
				EmployeeTicketRequest employeeTicketRequest = new EmployeeTicketRequest();
				employeeTicketRequest.setTicketId(customerTicketRequest.getTicketId());
				//employeeTicketRequest.setCustomerId(customerTicketRequest.getCustNo());
				employeeTicketRequest.setFromDeptId(Long.valueOf("0"));
				employeeTicketRequest.setFromId(customerTicketRequest.getCustNo());
				employeeTicketRequest.setFromType(Department.CUSTOMER.getId());
				employeeTicketRequest.setRequestUrl(customerTicketRequest.getRequestUrl());
				
				result = (Result) RestApiUtility.sendJSONPostRequest(
						EmployeeServiceUrls.closeTicket.getUrl(), employeeTicketRequest, Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		
		result.setStatus(result.getDescription());
		
		if(result.getResponseCode().equals(200)) {
			result.setStatus("Your ticket has been Closed");	
		}
		
	   return result;
	}
	
	@RequestMapping(value = "/reOpenTicket.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result reOpenTicket(@RequestBody CustomerTicketRequest customerTicketRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("***** The control is inside the reOpenTicket service in  CustomerTicketRestController *******"+customerTicketRequest.getDeviceToken());
		Result result = null;

		if (customerTicketRequest.getSessionKey() != null && customerTicketRequest.getDeviceToken() != null) {
			LOGGER.info("**** The Customer sessionkey  is ****" + customerTicketRequest.getSessionKey());
			String sessionKey = sessionUtils.decryptSessionKey(customerTicketRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey, customerTicketRequest.getDeviceToken());
			LOGGER.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				LOGGER.info("*** The customer session object ****" + customerSession);
				/* set inputs to object from session */
				customerTicketRequest.setCustNo(customerSession.getCustomerId());
				customerTicketRequest.setFlatNo(customerSession.getFlat().getFlatId());
				
				EmployeeTicketRequest employeeTicketRequest = new EmployeeTicketRequest();
				employeeTicketRequest.setTicketId(customerTicketRequest.getTicketId());
				//employeeTicketRequest.setCustomerId(customerTicketRequest.getCustNo());
				employeeTicketRequest.setFromId(customerTicketRequest.getCustNo());
				employeeTicketRequest.setFromType(Department.CUSTOMER.getId());
				employeeTicketRequest.setRequestUrl(customerTicketRequest.getRequestUrl());
				result = (Result) RestApiUtility.sendJSONPostRequest(
						EmployeeServiceUrls.reOpenTicket.getUrl(), employeeTicketRequest, Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		
		result.setStatus(result.getDescription());
		if(result.getResponseCode().equals(200)) {
			result.setStatus("Your ticket has been Reopened");
	    }
		
	   return result;
	}
}
