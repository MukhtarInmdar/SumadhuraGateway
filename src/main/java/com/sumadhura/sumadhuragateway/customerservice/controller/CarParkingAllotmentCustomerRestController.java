package com.sumadhura.sumadhuragateway.customerservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.CarParkingAllotmentRequest;
import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * CarParkingAllotmentCustomerRestController is responsible for providing customer car parking allotment related services
 * @author Malladi Venkatesh
 * @since 2021-04-14
 * @time 16:35
 *
 */

@RestController
@RequestMapping("/customerservice/carParkingAllotment")
public class CarParkingAllotmentCustomerRestController extends SessionValidate{
	
	private final static Logger LOGGER = Logger.getLogger(CarParkingAllotmentCustomerRestController.class);
	
	@RequestMapping(value="/getCustomerCarParkingAllotmentDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCustomerCarParkingAllotmentDetails(@RequestBody CarParkingAllotmentRequest carParkingAllotmentRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("*** The control is inside of the getCustomerCarParkingAllotmentDetails in CarParkingAllotmentCustomerRestController ***"+carParkingAllotmentRequest);
		if(Util.isNotEmptyObject(carParkingAllotmentRequest.getSessionKey()) && Util.isNotEmptyObject(carParkingAllotmentRequest.getDeviceToken())) {
			CustomerSession customerSession = customerSessionValidate(carParkingAllotmentRequest.getSessionKey(), carParkingAllotmentRequest.getDeviceToken());
			Long flatBookId = Util.isNotEmptyObject(customerSession.getFlatBookingId())?customerSession.getFlatBookingId():0l;
			carParkingAllotmentRequest.setFlatBookId(flatBookId);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_CAR_PARKING_ALLOTMENT_DETAILS.getUrl(), carParkingAllotmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given."+carParkingAllotmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
}
