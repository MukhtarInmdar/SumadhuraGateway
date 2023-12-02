package com.sumadhura.sumadhuragateway.employeeservice.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.dto.ThirdPartyRequest;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * @author @NIKET CH@V@N
 * @description this class provides basic properties for controller third party module
 * @since 10-08-2021
 * @time 11:41 AM
 */
@RestController
@RequestMapping(value = "/employeeservice/thirdParty")
public class ThirdPartyRestController {
	/*$.getScript( "ajax/test.js", function( data, textStatus, jqxhr ) {
		  console.log( data ); // Data returned
		  console.log( textStatus ); // Success
		  console.log( jqxhr.status ); // 200
		  console.log( "Load was performed." );
		});*/
	
	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("EmployeeSessionHandlerImpl")
	private SessionHandler employeeSessionHandlerImpl;

	private final Logger log = Logger.getLogger(ThirdPartyRestController.class);

	
	/*
	 * http://localhost:8888/SumadhuraGateway/employeeservice/thirdParty/getEmployeeOfMultiplePortal.spring 
	  { "empId": "1000001", "siteId": "112",
	  "sessionKey": "Login", "requestUrl": "thirdPartyCall",
	  "thirdPartyportNumber":8009, "portalName":"Sumadhura" 
	  }
	 * 
	 */
	@RequestMapping(value = "/getEmployeeOfMultiplePortal.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getEmployeeDetails(@RequestBody ThirdPartyRequest thirdPartyRequest,
			HttpServletRequest request) throws  Exception {
		log.info("***** Control inside the ThirdPartyRestController.getEmployeeDetails() *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(thirdPartyRequest.getSessionKey()) && Util.isNotEmptyObject(thirdPartyRequest.getRequestUrl())
				 && Util.isNotEmptyObject(thirdPartyRequest.getSiteId()) && Util.isNotEmptyObject(thirdPartyRequest.getEmpId())
				) {
			
			int portNumber = request.getLocalPort();
			thirdPartyRequest.setPortNumber((long) portNumber);

			result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getEmployeeOfMultiplePortal.getUrl(), thirdPartyRequest, Result.class);
		} else {
			throw new InSufficeientInputException(Arrays.asList("Insufficeint Input is Given."));
		}
		
		return result;
	}
}
