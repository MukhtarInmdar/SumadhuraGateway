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
import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.dto.Site;
import com.sumadhura.sumadhuragateway.dto.SiteWrapper;
import com.sumadhura.sumadhuragateway.employeeservice.dto.Department;
import com.sumadhura.sumadhuragateway.employeeservice.dto.DropDownRequest;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * 
 * @author rayudu
 *
 */

@RestController
@RequestMapping(value = "/employeeservice/site",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
public class SiteRestController extends SessionValidate{
	private final Logger LOGGER = Logger.getLogger(SiteRestController.class);

	//private static final String isProjectNotification="Project Notificatiions";
	private static final String isSendNotification="Send Notification";
	
	
	@RequestMapping(value="/site.spring",method=RequestMethod.POST)
	public Result getSiteList(@RequestBody DropDownRequest req ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		LOGGER.debug("******* The control inside of the getSiteList  in  SiteRestController ********");
		EmployeeSession sessionValidate = sessionValidate(req.getSessionKey());
		List<Long> userAccessSiteList = null;
		List<Department> departments = sessionValidate.getLoginResponse().getDepartments();
		if(Util.isNotEmptyObject(req.getSiteIds())) {
			userAccessSiteList = req.getSiteIds();
		}else {
		userAccessSiteList = Util.getUserAccessSiteList(departments,isSendNotification);
		if(userAccessSiteList==null || userAccessSiteList.isEmpty()){
			return null;
		}
		}
		req.setIds(userAccessSiteList);
	
		Result sendJSONGetRequest = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getSite.getUrl(),req.getIds(), Result.class);
		return sendJSONGetRequest;
	}
	
	@RequestMapping(value="/allSite.spring",method=RequestMethod.POST)
	public Result getAllSiteList(@RequestBody DropDownRequest req ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		LOGGER.debug("******* The control inside of the getSiteList  in  SiteRestController ********");
		EmployeeSession sessionValidate = sessionValidate(req.getSessionKey());
		
		@SuppressWarnings("unused")
		List<Department> departments = sessionValidate.getLoginResponse().getDepartments();
		
		Result sendJSONGetRequest = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getALLSite.getUrl(),null, Result.class);
		return sendJSONGetRequest;
	}
	
	
	@RequestMapping(value="/getSiteMenu.spring",method=RequestMethod.POST)
	public Result getSiteLists(@RequestBody  Customer req) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		LOGGER.debug("******* The control inside of the getSiteList  in  SiteRestController ********");
		String cacheKey = req.getSessionKey();
		@SuppressWarnings("unused")
		CustomerSession employee = customerSessionValidate(cacheKey,req.getDeviceId());

	
		SiteWrapper sendJSONGetRequest = (SiteWrapper) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_SITE_LIST.getUrl(),new Site(), SiteWrapper.class);
		return sendJSONGetRequest;
	}
	
	@RequestMapping(value="/allSites.spring",method=RequestMethod.POST)
	public Result getAllSitesList(@RequestBody DropDownRequest req ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		LOGGER.debug("******* The control inside of the getSiteList  in  SiteRestController ********");
		sessionValidate(req.getSessionKey());
	
		Result sendJSONGetRequest = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getALLSites.getUrl(),null, Result.class);
		return sendJSONGetRequest;
	}
	@RequestMapping(value="/getStateWiseSites.spring",method=RequestMethod.POST)
	public Result getStateWiseSitesList(@RequestBody DropDownRequest req ) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		LOGGER.debug("******* The control inside of the getSiteList  in  SiteRestController ********");
		sessionValidate(req.getSessionKey());
		List<Long> userAccessSiteList = new ArrayList<Long>();
		if(Util.isNotEmptyObject(req.getIds())) {
			
			userAccessSiteList = req.getIds();
			}
		req.setIds(userAccessSiteList);
		Result sendJSONGetRequest = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getStateWiseSites.getUrl(),req.getIds(), Result.class);
		return sendJSONGetRequest;
	}
	
	
}
