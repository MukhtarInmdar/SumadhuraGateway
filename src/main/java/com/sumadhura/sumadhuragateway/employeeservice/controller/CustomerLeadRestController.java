package com.sumadhura.sumadhuragateway.employeeservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.CustomerLeadRequest;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * CustomerLeadRestController is responsible for creating a lead of new customer
 * @author Inamdar
 * @since 18-01-2023
 * @time 16:35
 *
 */

@RestController
@RequestMapping("/employeeservice/customerLead")
public class CustomerLeadRestController extends SessionValidate {
	
	private final static Logger LOGGER = Logger.getLogger(CustomerLeadRestController.class);
	
	/*
	 * 
	 * GET_CUSTOMER_LEAD_VIEW(commonurl.getUrl()+"/customerLead/customerLeadView.spring"),
	 GET_CUSTOMER_SOURCE(commonurl.getUrl()+"/customerLead/customerSource.spring"),
	GET_CUSTOMER_Project_Preferred_Location(commonurl.getUrl()+"/customerLead/customerProjectPreferedLocation.spring"),
	GET_CUSTOMER_Time_Frame_To_Purchase(commonurl.getUrl()+"/customerLead/customerTimeFrameToPurchase.spring"),
	GET_CUSTOMER_Housing_Requirement(commonurl.getUrl()+"/customerLead/customerHousingRequirement.spring"),
	GET_CUSTOMER_Lead_Sub_Status(commonurl.getUrl()+"/customerLead/customerLeadSubStatus.spring"),
	GET_CUSTOMER_Marketing_Type(commonurl.getUrl()+"/customerLead/customerMarketingType.spring"),
	;
	*/
	
	@RequestMapping(value="/customerLeadMISCount.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerLeadMIS(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCustomerLeadMIS in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_LEAD_MIS_COUNT.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/customerLeadMIS.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerLeadGenerated(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCustomerLeadGenerated in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_LEAD_MIS.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	
	@RequestMapping(value="/customerLeadView.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerLead(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCustomerLead in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_LEAD_VIEW.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/customerLeadSiteVisitList.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerLeadSiteVisitList(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCustomerLeadNewSiteVisit in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_LEAD_SITE_VISIT_LIST.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/customerLeadSave.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result saveCustomerLead(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the saveCustomerLead in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())
				&&Util.isNotEmptyObject (customerLeadRequest.getCustomerName()) 
						&& Util.isNotEmptyObject (customerLeadRequest.getMobile())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SAVE_CUSTOMER_LEAD.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/customerLeadSiteVisitSave.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result saveCustomerLeadSiteVisit(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the saveCustomerLead in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())
				&&Util.isNotEmptyObject (customerLeadRequest.getCustomerName()) 
						&& Util.isNotEmptyObject (customerLeadRequest.getMobile())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SAVE_CUSTOMER_LEAD_SITE_VISIT.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/customerLeadDelete.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result deleteCustomerLead(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the saveCustomerLead in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())
				&&Util.isNotEmptyObject (customerLeadRequest.getLeadId()) 
						) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.DELETE_CUSTOMER_LEAD.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/customerSource.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerSource(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCustomerSource in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_SOURCE.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	
	@RequestMapping(value="/customerMarketingType.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerMarketingType(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCustomerMarketingType in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_MARKETING_TYPE.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/customerLeadSubStatus.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerLeadSubStatus(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCustomerLeadSubStatus in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_LEAD_SUB_STATUS.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/customerHousingRequirement.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerHousingRequirement(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCustomerHousingRequirement in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_HOUSING_REQUIREMENT.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	
	@RequestMapping(value="/customerTimeFrameToPurchase.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerTimeFrameToPurchase(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCustomerTimeFrameToPurchase in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_TIME_FRAME_TO_PURCHASE.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/customerProjectPreferedLocation.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerProjectPreferedLocation(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCustomerProjectPreferedLocation in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_PROJECT_PREFERRED_LOCATION.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/customerInactiveRemarks.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerInactiveRemarks(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCustomerInactiveRemarks in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_INACTIVE_REMARKS.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/customerLeadComments.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerLeadComments(@RequestBody CustomerLeadRequest customerLeadRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCustomerLeadComments in CustomerLeadRestController ***");
		if(Util.isNotEmptyObject(customerLeadRequest.getSessionKey()) && Util.isNotEmptyObject(customerLeadRequest.getRequestUrl())) {
			sessionValidate(customerLeadRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_LEAD_COMMENTS.getUrl(), customerLeadRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+customerLeadRequest);
			throw new InSufficeientInputException(errors);
		}
	}

	
	
}
