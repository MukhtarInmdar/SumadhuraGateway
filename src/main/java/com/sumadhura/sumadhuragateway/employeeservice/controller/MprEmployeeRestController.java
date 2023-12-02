package com.sumadhura.sumadhuragateway.employeeservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sumadhura.sumadhuragateway.dto.MprRequest;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.Department;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.employeeservice.enums.Module;
import com.sumadhura.sumadhuragateway.employeeservice.enums.SubModule;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.HttpStatus;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * MprEmployeeRestController class provides MPR Employee Specific Services
 * @author Malldi Venkatesh
 * @since 2020-Nov-18
 * @time 11.00
 *
 */
@RestController
@RequestMapping("/employeeservice/mpr")
public class MprEmployeeRestController extends SessionValidate{
	
	private static final Logger LOGGER = Logger.getLogger(MprEmployeeRestController.class);
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@RequestMapping(value="/saveMPRDetails.spring", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result saveMPRDetails(@RequestBody MprRequest mprRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the saveMPRDetails in MprEmployeeRestController ***");
		if(Util.isNotEmptyObject(mprRequest.getSessionKey()) && Util.isNotEmptyObject(mprRequest.getMprName()) && Util.isNotEmptyObject(mprRequest.getSiteId())
			&& (Util.isNotEmptyObject(mprRequest.getFileInfos()) || Util.isNotEmptyObject(mprRequest.getExternalDriveLocation()))) {
			EmployeeSession employeeSession = sessionValidate(mprRequest.getSessionKey());
			mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SAVE_MPR_DETAILS.getUrl(), mprRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+mprRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@RequestMapping(value="/getMPRDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getMPRDetails(@RequestBody MprRequest mprRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getMPRDetails in MprEmployeeRestController ***");
		if(Util.isNotEmptyObject(mprRequest.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(mprRequest.getSessionKey());
			List<Department> deparmentList = employeeSession.getLoginResponse().getDepartments();
			List<Long> siteIds = Util.getUserAccessSiteList(deparmentList, Module.MPR.getName(), SubModule.VIEW_MPR.getName());
			Result result = new Result();
			/* No Access when Employee has no accessed sites */
			if(Util.isEmptyObject(siteIds)) {
				result.setResponseCode(HttpStatus.failure.getResponceCode());
				result.setDescription("No Site Access to this Module and Sub_Module");
				return result;
			}else {
				/* setting accessed sites list */
				mprRequest.setSiteIds(siteIds);
			}
			/* setting default page no */
			if(Util.isEmptyObject(mprRequest.getPageNo())) {
				mprRequest.setPageNo(1);
			}
			/* setting default page size */
			if(Util.isEmptyObject(mprRequest.getPageSize())) {
				mprRequest.setPageSize(10000);
			}
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_MPR_DETAILS.getUrl(), mprRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+mprRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@RequestMapping(value="/checkMprNameExistence.spring", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result checkMprNameExistence(@RequestBody MprRequest mprRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the checkMprNameExistence in MprEmployeeRestController ***");
		if(Util.isNotEmptyObject(mprRequest.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(mprRequest.getSessionKey());
			mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.CHECK_MPR_NAME_EXISTENCE.getUrl(), mprRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+mprRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@RequestMapping(value="/inActiveMPRDetails.spring", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result inActiveMPRDetails(@RequestBody MprRequest mprRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the inActiveMPRDetails in MprEmployeeRestController ***");
		if(Util.isNotEmptyObject(mprRequest.getSessionKey()) && Util.isNotEmptyObject(mprRequest.getMprIds()) && Util.isNotEmptyObject(mprRequest.getRequestUrl())) {
			EmployeeSession employeeSession = sessionValidate(mprRequest.getSessionKey());
			mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.INACTIVE_MPR_DETAILS.getUrl(), mprRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+mprRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	@RequestMapping("/getMPRViewReport.spring")
	public Result getMPRViewReport(@RequestBody MprRequest mprRequest) throws SessionTimeoutException, InSufficeientInputException {
		String cacheKey = mprRequest.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_MPR_VIEW_REPORT.getUrl(),mprRequest, Result.class);
		return result;
	}
	
	@RequestMapping("/sendMPRpush.spring")
	public Result sendMPRpush(@RequestBody MprRequest mprRequest) throws SessionTimeoutException, InSufficeientInputException {
		
		//LOGGER.debug("*** The EmployeeSession object ***" + employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SEND_MPR_NOTIFY.getUrl(),mprRequest, Result.class);
		return result;
	}
	
}
