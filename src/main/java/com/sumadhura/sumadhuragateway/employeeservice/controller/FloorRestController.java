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

import com.sumadhura.sumadhuragateway.dto.FloorRequest;
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
@RequestMapping(value="/employeeservice/floor",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
public class FloorRestController extends SessionValidate{

private final Logger LOGGER = Logger.getLogger(FloorRestController.class);

	@RequestMapping(value="/floor.spring",method=RequestMethod.POST)
	public Result getFloorDataByBlock( @RequestBody DropDownRequest blockList) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the getFloorDataByBlock  in  FloorRestService ********");
		String cacheKey = blockList.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);

		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getFloorNamesByBlock.getUrl(), blockList, Result.class);

		return result;
	}
	
	@RequestMapping(value="/floorSite.spring",method=RequestMethod.POST)
	public Result getFloorsDataBySite(@RequestBody  DropDownRequest siteList) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the getFloorsDataBySite  in  FloorRestService ********");
		String cacheKey = siteList.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);

		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getFloorNamesBySite.getUrl(), siteList, Result.class);

		return result;
	}
	
	@RequestMapping(value="/getFloors.spring",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result getFloors(@RequestBody  FloorRequest floorRequest) throws InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getFloors in FloorRestController ***");
		if(Util.isNotEmptyObject(floorRequest.getSessionKey()) && (Util.isNotEmptyObject(floorRequest.getSiteId()) 
			|| Util.isNotEmptyObject(floorRequest.getBlockDetIds()))) {
			sessionValidate(floorRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_FLOOR_DETAILS.getUrl(),floorRequest,Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given ");
			throw new InSufficeientInputException(errors);
		}
	}
	
}
