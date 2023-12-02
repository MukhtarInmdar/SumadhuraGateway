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

import com.sumadhura.sumadhuragateway.dto.BlockRequest;
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
@RequestMapping(value = "/employeeservice/block",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
public class BlockRestController extends SessionValidate {

	private final Logger LOGGER = Logger.getLogger(BlockRestController.class);



	@RequestMapping(value="/blocks.spring",method=RequestMethod.POST)
	public Result getBlockNames(@RequestBody  DropDownRequest siteList) throws InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.debug("******* The control inside of the getBlocksNames  in  BlockRestController ********");
		String cacheKey = siteList.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		//List<Long> siteIds = siteList.getIds();
		LOGGER.debug("*** The EmployeeSession object ***"+employee);
		/* Taking input parameters from the session  */
		siteList.setLoginUserId(employee.getLoginResponse().getEmployee().getEmployeeId());
		LOGGER.debug("*** The employeeTicketRequest obj ***"+siteList);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getBlocksNamesBySite.getUrl(), siteList, Result.class);

		return result;
	}
	
	@RequestMapping(value="/getBlocks.spring",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result getBlocks(@RequestBody  BlockRequest blockRequest) throws InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getBlocks in BlockRestController ***");
		if(Util.isNotEmptyObject(blockRequest.getSessionKey()) && Util.isNotEmptyObject(blockRequest.getSiteId())) {
			sessionValidate(blockRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_BLOCK_DETAILS.getUrl(),blockRequest,Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given ");
			throw new InSufficeientInputException(errors);
		}
	}

}