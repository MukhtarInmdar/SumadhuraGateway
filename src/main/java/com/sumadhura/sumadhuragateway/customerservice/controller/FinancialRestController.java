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

import com.sumadhura.sumadhuragateway.dto.Customer;
import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.EmployeeFinancialRequest;
import com.sumadhura.sumadhuragateway.dto.EmployeeFinancialTransactionRequest;
import com.sumadhura.sumadhuragateway.dto.Financial;
import com.sumadhura.sumadhuragateway.dto.PaymentDetails;
import com.sumadhura.sumadhuragateway.dto.PaymentDetailsWrapper;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.enums.Site;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.HttpStatus;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * FinancialRestController class provides Financial specific services.
 * 
 * @author Venkat_Koniki
 * @since 17.04.2019
 * @time 10:12AM
 */

@RestController
@RequestMapping(value = "/customerservice/financial")
public class FinancialRestController {

	
	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("CustomerSessionHandlerImpl")
	private SessionHandler customerSessionHandlerImpl;
	
	
	private final  Logger logger = Logger.getLogger(FinancialRestController.class);
	
	@RequestMapping(value = "/financialDtls.spring", method = RequestMethod.POST, produces = "application/json")
	public Result getFinancialDtls(@RequestBody Customer customer,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		logger.info("******* The control inside of the getFinancialDtls service in  FinancialRestController ******"+ customer);
		
		if (customer.getSessionKey() != null) {
			String sessionKey = sessionUtils.decryptSessionKey(customer.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,customer.getDeviceId());
			logger.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				customer.setCustomerId(customerSession.getCustomerId());
				customer.setFlatId(customerSession.getFlat().getFlatId());
				customer.setDeviceId(customer.getDeviceId());
				customer.setFlatBookingId(customerSession.getFlatBookingId());
				int portNumber = request.getLocalPort();
				customer.setPortNumber((long)portNumber);
				
				Financial financial = (Financial) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customerfinancialdetails.getUrl(), customer, Financial.class);
				 Long siteId = customerSession.getFlat().getFloor().getBlock().getSite().getId(); 
				 if(Site.GARDENS_BY_THE_BROOK.getId().equals(siteId)) {
					 Result result = new Result();
					 result.setResponseCode(700);
					 result.setStatus("Under Maitainence.");
					 return result;
				 }
				return financial;
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value = "/paymentDtls.spring", method = RequestMethod.POST, produces = "application/json",consumes="application/json")
	public Result getPaymentDtls(@RequestBody Customer customer) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		logger.info("******* The control inside of the getFinancialDtls service in  FinancialRestController ******"+ customer.getDeviceId());
		
		if (customer.getSessionKey() != null) {
			String sessionKey = sessionUtils.decryptSessionKey(customer.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,customer.getDeviceId());
			logger.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				customer.setCustomerId(customerSession.getCustomerId());
				customer.setFlatId(customerSession.getFlat().getFlatId());
				customer.setDeviceId(customer.getDeviceId());
				customer.setFlatBookingId(customerSession.getFlatBookingId());
				PaymentDetailsWrapper paymentDetailsWrapper = (PaymentDetailsWrapper) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customerpaymentdetails.getUrl(), customer, PaymentDetailsWrapper.class);
				return paymentDetailsWrapper;
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value = "/demandNoteDtls.spring", method = RequestMethod.POST, produces = "application/json",consumes="application/json")
	public Result getDemandNoteDtls(@RequestBody Customer customer) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		logger.info("******* The control inside of the getDemandNoteDtls service in  FinancialRestController ******"
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
				customer.setFlatBookingId(customerSession.getFlatBookingId());
				PaymentDetails paymentDetails = (PaymentDetails) RestApiUtility.sendJSONPostRequest(
						CustomerServiceUrls.customerdemandnotedetails.getUrl(), customer, PaymentDetails.class);
				return paymentDetails;
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value = "/getCustomerInvoices.spring", method = RequestMethod.POST, produces = "application/json",consumes="application/json")
	public Result getCustomerInvoices(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		logger.info("******* The control inside of the getCustomerInvoices service in  FinancialRestController ******");
		if(Util.isNotEmptyObject(employeeFinancialTransactionRequest.getSessionKey()) && Util.isNotEmptyObject(employeeFinancialTransactionRequest.getRequestUrl())
			&& Util.isNotEmptyObject(employeeFinancialTransactionRequest.getActionUrl())) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,employeeFinancialTransactionRequest.getDeviceToken());
			logger.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				employeeFinancialTransactionRequest.setBookingFormId(customerSession.getFlatBookingId());
				return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getCustomerInvoices.getUrl(), employeeFinancialTransactionRequest, Result.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value = "/generateConsolidatedReceipt.spring", method = RequestMethod.POST, produces = "application/json",consumes="application/json")
	public Result generateConsolidatedReceipt(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		logger.info("******* The control inside of the getCustomerInvoices service in  FinancialRestController ******");
		if(Util.isNotEmptyObject(employeeFinancialRequest.getSessionKey())) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateSession(sessionKey,employeeFinancialRequest.getDeviceToken());
			logger.info("**** The session validation info is ******" + isSessionValid);
			if (isSessionValid) {
				CustomerSession customerSession = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
				employeeFinancialRequest.setBookingFormId(customerSession.getFlatBookingId());
				int portNumber = request.getLocalPort();
				employeeFinancialRequest.setPortNumber((long)portNumber);
				return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GENERATE_CONSOLIDATED_RECEIPT.getUrl(), employeeFinancialRequest, Result.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
}
