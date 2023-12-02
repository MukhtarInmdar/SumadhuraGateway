package com.sumadhura.sumadhuragateway.customerservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.AppointmentRequest;
import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * AppointmentBookingCustomerRestController is responsible for providing Appointment Booking related services
 * @author Malladi Venkatesh
 * @since 2021-04-04
 * @time 16:35
 *
 */
@RestController
@RequestMapping("/customerservice/appointmentBooking")
public class AppointmentBookingCustomerRestController extends SessionValidate {
	
	private final static Logger LOGGER = Logger.getLogger(AppointmentBookingCustomerRestController.class);
	
	@RequestMapping(value="/getAppointmentTypeDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getAppointmentTypeDetails(@RequestBody AppointmentRequest appointmentRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("*** The control is inside of the getAppointmentTypeDetails in AppointmentBookingCustomerRestController ***"+appointmentRequest);
		if(Util.isNotEmptyObject(appointmentRequest.getSessionKey()) && Util.isNotEmptyObject(appointmentRequest.getDeviceToken())) {
			customerSessionValidate(appointmentRequest.getSessionKey(), appointmentRequest.getDeviceToken());
			appointmentRequest.setRequestUrl("Customer");
			return (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_APPOINTMENT_TYPE_DETAILS.getUrl(), appointmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given."+appointmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getAppointmentTimeSlotDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getAppointmentTimeSlotDetails(@RequestBody AppointmentRequest appointmentRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("*** The control is inside of the getAppointmentTimeSlotDetails in AppointmentBookingCustomerRestController ***"+appointmentRequest);
		if(Util.isNotEmptyObject(appointmentRequest.getSessionKey()) && Util.isNotEmptyObject(appointmentRequest.getDeviceToken()) 
			&& Util.isNotEmptyObject(appointmentRequest.getApptmtDate()) && Util.isNotEmptyObject(appointmentRequest.getApptmtSubTypeId())) {
			CustomerSession customerSession = customerSessionValidate(appointmentRequest.getSessionKey(), appointmentRequest.getDeviceToken());
			Long customerId = Util.isNotEmptyObject(customerSession.getCustomerId())?customerSession.getCustomerId():0l;
			Long flatBookingId = Util.isNotEmptyObject(customerSession.getFlatBookingId())?customerSession.getFlatBookingId():0l;
			appointmentRequest.setCustId(customerId);
			appointmentRequest.setFlatBookingId(flatBookingId);
			return (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_APPOINTMENT_TIME_SLOT_DETAILS.getUrl(), appointmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given."+appointmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/saveAppointmentTimeSlotDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result saveAppointmentTimeSlotDetails(@RequestBody AppointmentRequest appointmentRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("*** The control is inside of the saveAppointmentTimeSlotDetails in AppointmentBookingCustomerRestController ***"+appointmentRequest);
		if(Util.isNotEmptyObject(appointmentRequest.getSessionKey()) && Util.isNotEmptyObject(appointmentRequest.getDeviceToken()) 
			&& Util.isNotEmptyObject(appointmentRequest.getApptmtSlotTimesId()) && Util.isNotEmptyObject(appointmentRequest.getApptmtSubTypeId())) {
			CustomerSession customerSession = customerSessionValidate(appointmentRequest.getSessionKey(), appointmentRequest.getDeviceToken());
			Long customerId = Util.isNotEmptyObject(customerSession.getCustomerId())?customerSession.getCustomerId():0l;
			Long flatBookingId = Util.isNotEmptyObject(customerSession.getFlatBookingId())?customerSession.getFlatBookingId():0l;
			appointmentRequest.setCustId(customerId);
			appointmentRequest.setFlatBookingId(flatBookingId);
			return (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.SAVE_APPOINTMENT_TIME_SLOT_DETAILS.getUrl(), appointmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given."+appointmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	
	@RequestMapping(value="/getAppointmentBookingDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getAppointmentBookingDetails(@RequestBody AppointmentRequest appointmentRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("*** The control is inside of the getAppointmentBookingDetails in AppointmentBookingCustomerRestController ***"+appointmentRequest);
		if(Util.isNotEmptyObject(appointmentRequest.getSessionKey()) && Util.isNotEmptyObject(appointmentRequest.getDeviceToken())) {
			CustomerSession customerSession = customerSessionValidate(appointmentRequest.getSessionKey(), appointmentRequest.getDeviceToken());
			Long customerId = Util.isNotEmptyObject(customerSession.getCustomerId())?customerSession.getCustomerId():0l;
			Long flatBookingId = Util.isNotEmptyObject(customerSession.getFlatBookingId())?customerSession.getFlatBookingId():0l;
			appointmentRequest.setRequestUrl("Customer");
			appointmentRequest.setCustId(customerId);
			appointmentRequest.setFlatBookingId(flatBookingId);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_APPOINTMENT_BOOKING_DETAILS.getUrl(), appointmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given."+appointmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getAppointmentSlotDatesDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getAppointmentSlotDatesDetails(@RequestBody AppointmentRequest appointmentRequest) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("*** The control is inside of the getAppointmentSlotDatesDetails in AppointmentBookingCustomerRestController ***"+appointmentRequest);
		if(Util.isNotEmptyObject(appointmentRequest.getSessionKey()) && Util.isNotEmptyObject(appointmentRequest.getApptmtSubTypeId())) {
			CustomerSession customerSession = customerSessionValidate(appointmentRequest.getSessionKey(), appointmentRequest.getDeviceToken());
			Long customerId = Util.isNotEmptyObject(customerSession.getCustomerId())?customerSession.getCustomerId():0l;
			Long flatBookingId = Util.isNotEmptyObject(customerSession.getFlatBookingId())?customerSession.getFlatBookingId():0l;
			appointmentRequest.setCustId(customerId);
			appointmentRequest.setFlatBookingId(flatBookingId);
			return (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_APPOINTMENT_SLOT_DATES_DETAILS.getUrl(), appointmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given."+appointmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
}
