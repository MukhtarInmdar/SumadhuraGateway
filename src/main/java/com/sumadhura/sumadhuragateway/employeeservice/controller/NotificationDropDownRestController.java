package com.sumadhura.sumadhuragateway.employeeservice.controller;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.DropDownRequest;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;

@RestController
@RequestMapping(value = "/employeeservice/notificationdropdown", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
public class NotificationDropDownRestController extends SessionValidate{
	
	private static final Logger LOGGER = Logger.getRootLogger();
	
	@RequestMapping(value ="/seriesbysiteblockfloor.spring", method = RequestMethod.POST)
	public Result getSeriesBySiteBlockFloor(@RequestBody DropDownRequest dropDownRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("**** The control is inside the getSeriesBySiteBlockFloor in NotificationDropDownRestController ****"+dropDownRequest);
		String sessionKey = dropDownRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		LOGGER.debug("**** The EmployeeSession Object ****"+employeeSession);
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_SERIES_BY_SITE_BLOCK_FLOOR.getUrl(), dropDownRequest, Result.class);
	}
	
	@RequestMapping(value ="/sbuabysiteblockfloor.spring", method = RequestMethod.POST)
	public Result getSbuaBySiteBlockFloor(@RequestBody DropDownRequest dropDownRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("**** The control is inside the getSbuaBySite in NotificationDropDownRestController ****"+dropDownRequest);
		String sessionKey = dropDownRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		LOGGER.debug("**** The EmployeeSession Object ****"+employeeSession);
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_SBUA_BY_SITE_BLOCK_FLOOR.getUrl(), dropDownRequest, Result.class);
	}
	
	@RequestMapping(value ="/facingbysiteblockfloor.spring", method = RequestMethod.POST)
	public Result getFacingBySiteBlockFloor(@RequestBody DropDownRequest dropDownRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("**** The control is inside the getFacingBySiteBlockFloor in NotificationDropDownRestController ****"+dropDownRequest);
		String sessionKey = dropDownRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		LOGGER.debug("**** The EmployeeSession Object ****"+employeeSession);
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_FACING_BY_SITE_BLOCK_FLOOR.getUrl(), dropDownRequest, Result.class);
	}
	
	@RequestMapping(value ="/bhktypebysiteblockfloor.spring", method = RequestMethod.POST)
	public Result getBhkTypeBySiteBlockFloor(@RequestBody DropDownRequest dropDownRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("**** The control is inside the getFacingBySiteBlockFloor in NotificationDropDownRestController ****"+dropDownRequest);
		String sessionKey = dropDownRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		LOGGER.debug("**** The EmployeeSession Object ****"+employeeSession);
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_BHK_TYPE_BY_SITE_BLOCK_FLOOR.getUrl(), dropDownRequest, Result.class);
	}

}
