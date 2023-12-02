package com.sumadhura.sumadhuragateway.customerservice.controller;



import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.Flat;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.dto.TicketFeedbackDTO;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;

@RestController
@RequestMapping(value="/customerservice/feedback",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
public class FeedBackCustController extends SessionValidate {
	private final Logger LOGGER = Logger.getLogger(FeedBackCustController.class);

	@RequestMapping("/saveTktFeedback.spring")
	public Result saveTicketFeedback(@RequestBody TicketFeedbackDTO tktFeedback) throws SessionTimeoutException, InSufficeientInputException{
		String cacheKey = tktFeedback.getSessionKey();
		CustomerSession employee = customerSessionValidate(cacheKey,tktFeedback.getDeviceId());
		tktFeedback.setCustomerName(employee.getName());
		tktFeedback.setCustomerId(employee.getCustomerId());
		tktFeedback.setEmailId(employee.getEmail());
		Flat flat = employee.getFlat();
		tktFeedback.setFlatNo(flat.getFlatNo());
	
		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.SAVE_TKT_FEED_BACK.getUrl(), tktFeedback, Result.class);
		return result;
	}

	@RequestMapping("/nonFeedbackTkt.spring")
	public Result getNonFeedbackTkt(@RequestBody TicketFeedbackDTO tktFeedback) throws SessionTimeoutException, InSufficeientInputException{
		
		String cacheKey = tktFeedback.getSessionKey();
		CustomerSession employee = customerSessionValidate(cacheKey,tktFeedback.getDeviceId());
		tktFeedback.setCustomerName(employee.getFirstName() +" "+employee.getLastName());
		tktFeedback.setCustomerId(employee.getCustomerId());
		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_NON_FEED_BACK_TKT.getUrl()+"?custId="+employee.getCustomerId(),tktFeedback, Result.class);
		return result;
	}	

}
