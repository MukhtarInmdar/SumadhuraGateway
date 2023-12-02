package com.sumadhura.sumadhuragateway.employeeservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.CarParkingAllotmentRequest;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * CarParkingAllotmentRestController is responsible for providing customer car parking allotment related services
 * @author Malladi Venkatesh
 * @since 2021-04-14
 * @time 16:35
 *
 */

@RestController
@RequestMapping("/employeeservice/carParkingAllotment")
public class CarParkingAllotmentRestController extends SessionValidate {
	
	private final static Logger LOGGER = Logger.getLogger(CarParkingAllotmentRestController.class);
	
	@RequestMapping(value="/getCarParkingBasementDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCarParkingBasementDetails(@RequestBody CarParkingAllotmentRequest carParkingAllotmentRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCarParkingBasementDetails in CarParkingAllotmentRestController ***");
		if(Util.isNotEmptyObject(carParkingAllotmentRequest.getSessionKey()) && Util.isNotEmptyObject(carParkingAllotmentRequest.getRequestUrl())
			&& (Util.isNotEmptyObject(carParkingAllotmentRequest.getSiteId()) || Util.isNotEmptyObject(carParkingAllotmentRequest.getBasementId()))) {
			sessionValidate(carParkingAllotmentRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CAR_PARKING_BASEMENT_DETAILS.getUrl(), carParkingAllotmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+carParkingAllotmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/saveCarParkingAllotmentDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result saveCarParkingAllotmentDetails(@RequestBody CarParkingAllotmentRequest carParkingAllotmentRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the saveCarParkingAllotmentDetails in CarParkingAllotmentRestController ***");
		if(Util.isNotEmptyObject(carParkingAllotmentRequest.getSessionKey()) && Util.isNotEmptyObject(carParkingAllotmentRequest.getFlatBookId())
			&& (Util.isNotEmptyObject(carParkingAllotmentRequest.getCarParkingAllotmentBasementInfoList()))) {
			EmployeeSession employeeSession = sessionValidate(carParkingAllotmentRequest.getSessionKey());
			carParkingAllotmentRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SAVE_CAR_PARKING_ALLOTMENT_DETAILS.getUrl(), carParkingAllotmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+carParkingAllotmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getAllCarParkingAllotmentDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getAllCarParkingAllotmentDetails(@RequestBody CarParkingAllotmentRequest carParkingAllotmentRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getAllCarParkingAllotmentDetails in CarParkingAllotmentRestController ***");
		if(Util.isNotEmptyObject(carParkingAllotmentRequest.getSessionKey()) && Util.isNotEmptyObject(carParkingAllotmentRequest.getSiteId()) 
			&& Util.isNotEmptyObject(carParkingAllotmentRequest.getSlotStatusName())) {
			EmployeeSession employeeSession = sessionValidate(carParkingAllotmentRequest.getSessionKey());
			carParkingAllotmentRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_ALL_CAR_PARKING_ALLOTMENT_DETAILS.getUrl(), carParkingAllotmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+carParkingAllotmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/updateCarParkingAllotmentStatus.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result updateCarParkingAllotmentStatus(@RequestBody CarParkingAllotmentRequest carParkingAllotmentRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the updateCarParkingAllotmentStatus in CarParkingAllotmentRestController ***");
		if(Util.isNotEmptyObject(carParkingAllotmentRequest.getSessionKey()) && Util.isNotEmptyObject(carParkingAllotmentRequest.getSlotStatusName()) 
			&& (Util.isNotEmptyObject(carParkingAllotmentRequest.getSlotId()) || Util.isNotEmptyObject(carParkingAllotmentRequest.getAllotmentId()))) {
			EmployeeSession employeeSession = sessionValidate(carParkingAllotmentRequest.getSessionKey());
			carParkingAllotmentRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.UPDATE_CAR_PARKING_ALLOTMENT_STATUS.getUrl(), carParkingAllotmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+carParkingAllotmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/approveOrRejectAllotment.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result approveOrRejectAllotment(@RequestBody CarParkingAllotmentRequest carParkingAllotmentRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the approveOrRejectAllotment in CarParkingAllotmentRestController ***");
		if(Util.isNotEmptyObject(carParkingAllotmentRequest.getSessionKey()) && Util.isNotEmptyObject(carParkingAllotmentRequest.getSlotStatusName())
			&& Util.isNotEmptyObject(carParkingAllotmentRequest.getAllotmentId()) && Util.isNotEmptyObject(carParkingAllotmentRequest.getSlotId())
			&& Util.isNotEmptyObject(carParkingAllotmentRequest.getFlatBookId())) {
			EmployeeSession employeeSession = sessionValidate(carParkingAllotmentRequest.getSessionKey());
			carParkingAllotmentRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.APPROVE_OR_REJECT_CAR_PARKING_ALLOTMENT.getUrl(), carParkingAllotmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+carParkingAllotmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/allotmentLetterPdfPreview.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result allotmentLetterPdfPreview(@RequestBody CarParkingAllotmentRequest carParkingAllotmentRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the allotmentLetterPdfPreview in CarParkingAllotmentRestController ***");
		if(Util.isNotEmptyObject(carParkingAllotmentRequest.getSessionKey()) && Util.isNotEmptyObject(carParkingAllotmentRequest.getSlotId())
			&& Util.isNotEmptyObject(carParkingAllotmentRequest.getFlatBookId())) {
			EmployeeSession employeeSession = sessionValidate(carParkingAllotmentRequest.getSessionKey());
			carParkingAllotmentRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.CAR_PARKING_ALLOTMENT_LETTER_PDF_PREVIEW.getUrl(), carParkingAllotmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+carParkingAllotmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
}
