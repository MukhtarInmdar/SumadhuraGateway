package com.sumadhura.sumadhuragateway.employeeservice.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.Customer;
import com.sumadhura.sumadhuragateway.dto.FlatRequest;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.DropDownRequest;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;


@RestController
@RequestMapping(value="/employeeservice/flat",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
public class FlatRestController extends SessionValidate{

private final Logger LOGGER = Logger.getLogger(FlatRestController.class);

	@RequestMapping(value="/flat.spring",method=RequestMethod.POST)
	public Result getFlatsNames(@RequestBody  DropDownRequest floorList) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the getFlatsNames  in  FlatRestController ********");
		String cacheKey = floorList.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);

		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		//LOGGER.debug("*** The employeeTicketRequest obj ***"+siteList);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getFlatsNames.getUrl(), floorList, Result.class);

		return result;
	}
	
	@RequestMapping(value="/flatSite.spring",method=RequestMethod.POST)
	public Result getFlatsNamesBySite(@RequestBody  DropDownRequest dropDownRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the getFlatsNamesBySite  in  FlatRestController ********");
		String cacheKey = dropDownRequest.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);

		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getFlatsNamesBySite.getUrl(), dropDownRequest, Result.class);

		return result;
	}
	
	@RequestMapping(value="/flatBlock.spring",method=RequestMethod.POST)
	public Result getFlatsNamesByBlock(@RequestBody  DropDownRequest blockList) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the getFlatsNamesBySite  in  FlatRestController ********");
		String cacheKey = blockList.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);

		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getFlatsNamesByBlock.getUrl(), blockList, Result.class);
		return result;
	}

	@RequestMapping(value="/flatsbua.spring",method=RequestMethod.POST)
	public Result getFlatsNamesBySbuaSeries(@RequestBody  DropDownRequest dropDownRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the getFlatsNamesBySbuaSeriesFacingBhkType  in  FlatRestController ********");
		String cacheKey = dropDownRequest.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getFlatsNamesBySbuaSeries.getUrl(), dropDownRequest, Result.class);
		return result;
	}
	
	@RequestMapping(value="/getCustomerDetailsByFlatId.spring",method=RequestMethod.POST)
	public Result getCustomerDetailsByFlatId(@RequestBody  Customer customer) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the getCustomerDetailsByFlatId  in  FlatRestController ********");
		if(Util.isNotEmptyObject(customer.getSessionKey()) && Util.isNotEmptyObject(customer.getFlatId())){
			EmployeeSession employee = sessionValidate(customer.getSessionKey());
			LOGGER.debug("*** The EmployeeSession object ***"+employee);
			Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_DETAILS_BY_FLAT_ID.getUrl(), customer, Result.class);
			return result;
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customer);
			throw new InSufficeientInputException(errors);
		}
		
	}
	
	@RequestMapping(value="/getFlats.spring",method=RequestMethod.POST)
	public Result getFlats(@RequestBody  FlatRequest flatRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("*** The control inside of the getFlats in FlatRestController ***");
		if(Util.isNotEmptyObject(flatRequest.getSessionKey()) && (Util.isNotEmptyObject(flatRequest.getSiteId())
			|| Util.isNotEmptyObject(flatRequest.getBlockDetIds()) || Util.isNotEmptyObject(flatRequest.getFloorDetIds()))) {
			sessionValidate(flatRequest.getSessionKey());
			Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_FLAT_DETAILS.getUrl(), flatRequest, Result.class);
			return result;
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given ");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getBookingFlats.spring",method=RequestMethod.POST)
	public Result getBookingFlats(@RequestBody  FlatRequest flatRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("*** The control inside of the getBookingFlats in FlatRestController ***");
		if(Util.isNotEmptyObject(flatRequest.getSessionKey()) && Util.isNotEmptyObject(flatRequest.getSiteId())) {
			sessionValidate(flatRequest.getSessionKey());
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_BOOKING_FLATS.getUrl(), flatRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given ");
			throw new InSufficeientInputException(errors);
		}
	}

	
}
