package com.sumadhura.sumadhuragateway.customerservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sumadhura.sumadhuragateway.dto.Customer;
import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.MprRequest;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * MprCustomerRestController class provides customer specific mpr services
 * @author Malladi Venkatesh
 * @since 2020-11-21
 * @time 11:50
 */
@RestController
@RequestMapping(value="/customerservice/mpr")
public class MprCustomerRestController extends SessionValidate {
	
	private static final Logger LOGGER = Logger.getLogger(MprCustomerRestController.class);
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@RequestMapping(value="/getCustomerMPRDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerMPRDetails(@RequestBody MprRequest mprRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("*** The control is inside of the getCustomerMPRDetails in MprCustomerRestController ***");
		if(Util.isNotEmptyObject(mprRequest.getSessionKey()) && Util.isNotEmptyObject(mprRequest.getDeviceToken())) {
			CustomerSession customerSession = customerSessionValidate(mprRequest.getSessionKey(), mprRequest.getDeviceToken());
			Long customerId = Util.isNotEmptyObject(customerSession.getCustomerId())?customerSession.getCustomerId():0l;
			Long flatBookingId = Util.isNotEmptyObject(customerSession.getFlatBookingId())?customerSession.getFlatBookingId():0l;
			mprRequest.setCustomerId(customerId);
			mprRequest.setFlatBookingId(flatBookingId);
			mprRequest.setAppRegId(customerSession.getAppRegId());
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_MPR_DETAILS.getUrl(), mprRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given."+mprRequest);
			throw new InSufficeientInputException(errors);
		}
	}

	@RequestMapping("/saveMPRViewStatus.spring")
	public Result saveMPRViewStatus(@RequestBody  Customer req) throws SessionTimeoutException, InSufficeientInputException{
		String cacheKey = req.getSessionKey();
		CustomerSession employee = customerSessionValidate(cacheKey, req.getDeviceId());
		req.setCustomerId(employee.getCustomerId());
		req.setAppRegId(employee.getAppRegId());
		req.setFlatBookingId(employee.getFlatBookingId());
		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SAVE_CUST_VIEW_MPR.getUrl(), req, Result.class);
		return result;
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@RequestMapping(value="/getNonCustMPRDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getNonCustomerMPRList(@RequestBody MprRequest mprRequest) throws SessionTimeoutException, InSufficeientInputException{
		
		if(Util.isNotEmptyObject(mprRequest.getUuid())) { 
			
			LOGGER.debug("*** The noncustomerdevicetoken object ***"+mprRequest);
			 return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_NON_CUST_MPR_DETAILS.getUrl(), mprRequest, Result.class);
			
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given .");
			throw new InSufficeientInputException(errors);
		}
	
		
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@RequestMapping(value="/saveNonCustMPRViewStatus.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result saveNonCustMPRViewStatus(@RequestBody  MprRequest mprRequest) throws SessionTimeoutException, InSufficeientInputException{
		
		
		LOGGER.debug("*** The EmployeeSession object ***"+mprRequest);
		Result result=  (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SAVE_NON_CUST_VIEW_MPR.getUrl(), mprRequest, Result.class);
		return result;
		
		 
	}
	
	
	
}
