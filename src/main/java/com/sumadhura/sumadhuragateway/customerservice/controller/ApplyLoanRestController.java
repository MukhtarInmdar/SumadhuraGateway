package com.sumadhura.sumadhuragateway.customerservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.Flat;
import com.sumadhura.sumadhuragateway.dto.LoanRequest;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * MprEmployeeRestController class provides MPR Employee Specific Services
 * @author bvr
 * @since 24-03-2022
 * @time 5:00pm
 *
 */
@RestController
@RequestMapping("/customerservice/loan")
public class ApplyLoanRestController  extends SessionValidate {
	
	private static final Logger LOGGER = Logger.getLogger(ApplyLoanRestController.class);
	
	@RequestMapping("/getDocumetsNamesToDisplay.spring")
	public Result getDocumetsNamesToDisplay(@RequestBody  LoanRequest req) throws SessionTimeoutException, InSufficeientInputException{
		LOGGER.info("*** The control is inside of the getDocumetsNamesToDisplay in ApplyLoanRestController ***");
		if(Util.isNotEmptyObject(req.getSessionKey()) && Util.isNotEmptyObject(req.getDeviceToken())) {
			CustomerSession customerSession = customerSessionValidate(req.getSessionKey(), req.getDeviceToken());
			Long customerId = Util.isNotEmptyObject(customerSession.getCustomerId())?customerSession.getCustomerId():0l;
			Long flatBookingId = Util.isNotEmptyObject(customerSession.getFlatBookingId())?customerSession.getFlatBookingId():0l;
			req.setCustomerId(customerId);
			req.setFlatBookingId(flatBookingId);
			req.setAppRegId(customerSession.getAppRegId());
			Flat flat = customerSession.getFlat();
			req.setFlatNo(flat.getFlatNo());
			req.setFlatId(flat.getFlatId());
			req.setEmailId(customerSession.getEmail());
			Long siteId = customerSession.getFlat().getFloor().getBlock().getSite().getId(); 
			req.setSiteId(siteId);
			return (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_DOCUMENTS_NAMES_TO_DISPLAY.getUrl(), req, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given."+req);
			throw new InSufficeientInputException(errors);
		}
	}
	
	
	@RequestMapping("/getBankList.spring")
	public Result getBankList(@RequestBody  LoanRequest req) throws SessionTimeoutException, InSufficeientInputException{
		LOGGER.info("*** The control is inside of the GET_BANKER_LIST in ApplyLoanRestController ***");
		if(Util.isNotEmptyObject(req.getSessionKey()) && Util.isNotEmptyObject(req.getDeviceToken())) {
			CustomerSession customerSession = customerSessionValidate(req.getSessionKey(), req.getDeviceToken());
			Long customerId = Util.isNotEmptyObject(customerSession.getCustomerId())?customerSession.getCustomerId():0l;
			Long flatBookingId = Util.isNotEmptyObject(customerSession.getFlatBookingId())?customerSession.getFlatBookingId():0l;
			req.setCustomerId(customerId);
			req.setFlatBookingId(flatBookingId);
			req.setAppRegId(customerSession.getAppRegId());
			Flat flat = customerSession.getFlat();
			req.setFlatNo(flat.getFlatNo());
			req.setFlatId(flat.getFlatId());
			req.setEmailId(customerSession.getEmail());
			Long siteId = customerSession.getFlat().getFloor().getBlock().getSite().getId(); 
			req.setSiteId(siteId);
			return (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_BANKER_LIST.getUrl(), req, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given."+req);
			throw new InSufficeientInputException(errors);
		}
	}
	
	
	@RequestMapping("/saveApplyLoanDocuments.spring")
	public Result saveApplyLoanDocuments(@RequestBody  LoanRequest req,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException{
		LOGGER.info("*** The control is inside of the saveApplyLoanDocuments in ApplyLoanRestController ***");
		if(Util.isNotEmptyObject(req.getSessionKey()) && Util.isNotEmptyObject(req.getDeviceToken())) {
			CustomerSession customerSession = customerSessionValidate(req.getSessionKey(), req.getDeviceToken());
			Long customerId = Util.isNotEmptyObject(customerSession.getCustomerId())?customerSession.getCustomerId():0l;
			Long flatBookingId = Util.isNotEmptyObject(customerSession.getFlatBookingId())?customerSession.getFlatBookingId():0l;
			req.setCustomerId(customerId);
			req.setFlatBookingId(flatBookingId);
			req.setAppRegId(customerSession.getAppRegId());
			Flat flat = customerSession.getFlat();
			req.setFlatNo(flat.getFlatNo());
			req.setFlatId(flat.getFlatId());
			req.setEmailId(customerSession.getEmail());
			Long siteId = customerSession.getFlat().getFloor().getBlock().getSite().getId(); 
			req.setSiteId(siteId);
			int portNumber = request.getLocalPort();
			req.setPortNumber((long)portNumber);
			return (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.SAVE_APPLY_LOAN_DOCUMENTS.getUrl(), req, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given."+req);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value = "/loadBankInterestLoanDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result loadBankInterestLoanDetails(@RequestBody  LoanRequest req,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException{
		LOGGER.info("*** The control is inside of the loadBankInterestLoanDetails in ApplyLoanRestController ***");
		if(Util.isNotEmptyObject(req.getSessionKey()) && Util.isNotEmptyObject(req.getDeviceToken())) {
			CustomerSession customerSession = customerSessionValidate(req.getSessionKey(), req.getDeviceToken());
			Long customerId = Util.isNotEmptyObject(customerSession.getCustomerId())?customerSession.getCustomerId():0l;
			Long flatBookingId = Util.isNotEmptyObject(customerSession.getFlatBookingId())?customerSession.getFlatBookingId():0l;
			req.setCustomerId(customerId);
			req.setFlatBookingId(flatBookingId);
			req.setAppRegId(customerSession.getAppRegId());
			Flat flat = customerSession.getFlat();
			req.setFlatNo(flat.getFlatNo());
			req.setFlatId(flat.getFlatId());
			req.setEmailId(customerSession.getEmail());
			Long siteId = customerSession.getFlat().getFloor().getBlock().getSite().getId(); 
			req.setSiteId(siteId);
			int portNumber = request.getLocalPort();
			req.setPortNumber((long)portNumber);
			return (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.loadBankInterestLoanDetails.getUrl(), req, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given."+req);
			throw new InSufficeientInputException(errors);
		}
	}

	
	
}