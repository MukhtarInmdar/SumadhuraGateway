package com.sumadhura.sumadhuragateway.customerservice.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.Customer;
import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

@RestController
@RequestMapping(value="/customerservice/notification",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
public class NotificationController extends SessionValidate {
	private final Logger LOGGER = Logger.getLogger(NotificationController.class);

	@RequestMapping("/companysNotifys.spring")
	public Result getCompanyNotifications(@RequestBody  Customer req) throws SessionTimeoutException, InSufficeientInputException{
		String cacheKey = req.getSessionKey();
		CustomerSession employee = customerSessionValidate(cacheKey,req.getDeviceId());
		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.VIEW_COMPANYS_NOTIFY.getUrl(), req, Result.class);
		return result;
	}

	@RequestMapping("/sitesNotifys.spring")
	public Result getSitesNotifications(@RequestBody  Customer req) throws SessionTimeoutException, InSufficeientInputException{
		String cacheKey = req.getSessionKey();
		CustomerSession employee = customerSessionValidate(cacheKey,req.getDeviceId());
		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.VIEW_SITE_NOTIFYS.getUrl(), req, Result.class);
		return result;
	}
	
	@RequestMapping("/noncustomerNotifys.spring")
	public Result getNonCustomerNotifications(@RequestBody  Customer req) throws SessionTimeoutException, InSufficeientInputException{
		Result result = null;
		if(Util.isNotEmptyObject(req.getUuid()) && Util.isNotEmptyObject(req.getSerialNo()) && Util.isNotEmptyObject(req.getStateIds())) {
		result = (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.VIEW_NONCUSTOMER_NOTIFYS.getUrl(), req, Result.class);
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}
	
	@RequestMapping("/saveNotifyViewStatus.spring")
	public Result saveViewNotifyStatus(@RequestBody  Customer req) throws SessionTimeoutException, InSufficeientInputException{
		String cacheKey = req.getSessionKey();
		CustomerSession employee = customerSessionValidate(cacheKey, req.getDeviceId());
		req.setCustomerId(employee.getCustomerId());
		req.setAppRegId(employee.getAppRegId());
		req.setFlatId(employee.getFlat().getFlatId());
		//req.setE
		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.SAVE_CUST_VIEW_NOTIFY.getUrl(), req, Result.class);
		return result;
	}
	
	@RequestMapping("/saveNonCustomerNotifyViewStatus.spring")
	public Result saveNonCustomerViewNotifyStatus(@RequestBody  Customer req) throws SessionTimeoutException, InSufficeientInputException{
		Result result = null;
		if(Util.isNotEmptyObject(req.getNonCustomerNotificationSentDetailsId())) {
		 result = (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.SAVE_NON_CUST_VIEW_NOTIFY.getUrl(), req, Result.class);
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}
}
