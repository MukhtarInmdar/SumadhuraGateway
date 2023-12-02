package com.sumadhura.sumadhuragateway.employeeservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.Customer;
import com.sumadhura.sumadhuragateway.dto.ReferFriendWrapper;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.Department;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.employeeservice.dto.MenuModuleStatusRequest;
import com.sumadhura.sumadhuragateway.employeeservice.dto.ReferredFrndComments;
import com.sumadhura.sumadhuragateway.employeeservice.dto.SearchReferrerCustomer;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

@RestController
@RequestMapping(value = "/employeeservice/references")
public class ReferFriendEmpController  extends SessionValidate {

	
	private final static Logger LOGGER = Logger.getLogger(ReferFriendEmpController.class);

	private final static String IS_REFERENCE="References";
	private final static String SUB_REFERENCE="View Reference Details";
	
	@RequestMapping(value="/referedCustomers.spring",method=RequestMethod.POST)
	public Result getReferedCustomers(@RequestBody  SearchReferrerCustomer searchReq) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the getReferedCustomers  in  ReferFriendEmpController ********");
		String cacheKey = searchReq.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		
		//set default values for pagination if values are null
		if (Util.isEmptyObject(searchReq.getPageNo()) && Util.isEmptyObject(searchReq.getPageSize())) {
			searchReq.setPageNo(1);
			searchReq.setPageSize(10000);
		}
		searchReq.setEmpId(employee.getLoginResponse().getEmpId());
		
		List<Department> departments = employee.getLoginResponse().getDepartments();
		List<Long> siteIds = Util.getUserAccessSiteList(departments, IS_REFERENCE,SUB_REFERENCE);
		Result result=new Result();
		if (siteIds == null || siteIds.isEmpty()) {	
			//result.setResponseCode(HttpStatus.failure.getResponceCode());
			//result.setDescription("No Access");
			//return result;
			siteIds.add(0l);
		}
		searchReq.setSiteIds(siteIds);
		result =(Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SEARCH_REFER_FRND.getUrl(), searchReq, Result.class);
		return result;
	}
	
	@RequestMapping(value="/saveReferredFrndComments.spring",method=RequestMethod.POST)
	public Result saveReferredFrndComments(@RequestBody  ReferredFrndComments saveComment) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the saveReferredFrndComments  in  ReferFriendEmpController ********");
		String cacheKey = saveComment.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		Result result =(Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.UPDATE_REFERRER_FRND_COMMENTS.getUrl(), saveComment, Result.class);
		return result;
	}
	
	@RequestMapping(value="/searchCustomer.spring",method=RequestMethod.POST)
	public Result searchCustomer(@RequestBody  SearchReferrerCustomer saveComment) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the saveReferredFrndComments  in  ReferFriendEmpController ********");
		String cacheKey = saveComment.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		Result result =(Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SEARCH_CUSTOMER.getUrl(), saveComment, Result.class);
		return result;
	}
	
	@RequestMapping(value="/referralStatusList.spring", method=RequestMethod.POST)
	public Result getAllReferralStatus(@RequestBody MenuModuleStatusRequest menuModuleStatusRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the saveReferredFrndComments  in  ReferFriendEmpController ********" +menuModuleStatusRequest );
		String sessionKey = menuModuleStatusRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
		if(Util.isNotEmptyObject(menuModuleStatusRequest) && Util.isNotEmptyObject(menuModuleStatusRequest.getModuleId())
				&& Util.isNotEmptyObject(menuModuleStatusRequest.getSubModuleId())) {
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.REFERRAL_STATUS_LIST.getUrl(), menuModuleStatusRequest, Result.class);
		}else {
			 throw new InSufficeientInputException();
		}
		
	}
	
	@RequestMapping("/getReferFriendList.spring")
    public Result getReferFriendList(@RequestBody Customer customer) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("******* The control inside of the getReferFriendList  in  ReferFriendEmpController ********" +customer );
		if(Util.isNotEmptyObject(customer.getSessionKey()) && Util.isNotEmptyObject(customer.getCustomerId())) {
	        EmployeeSession employeeSession = sessionValidate(customer.getSessionKey());
			LOGGER.debug("*** The EmployeeSession object ***"+employeeSession);
	        ReferFriendWrapper referFriendWrapper = (ReferFriendWrapper)RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_REFER_FRIEND_LIST.getUrl(), customer, ReferFriendWrapper.class);
	        return referFriendWrapper;
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customer);
			throw new InSufficeientInputException(errors);
		}
    }
	
}
