package com.sumadhura.sumadhuragateway.employeeservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.AppointmentDetailsRequest;
import com.sumadhura.sumadhuragateway.dto.AppointmentRequest;
import com.sumadhura.sumadhuragateway.dto.ApptmtSlotTimeRequest;
import com.sumadhura.sumadhuragateway.dto.ApptmtSlotTimesInfo;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * AppointmentBookingRestController is responsible for providing customer raised Appointment Booking related services
 * @author Malladi Venkatesh
 * @since 2021-04-04
 * @time 16:35
 *
 */
@RestController
@RequestMapping("/employeeservice/appointmentBooking")
public class AppointmentBookingRestController extends SessionValidate {
	
	private final static Logger LOGGER = Logger.getLogger(AppointmentBookingRestController.class);
	
	@RequestMapping(value="/getAppointmentBookingDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getAppointmentBookingDetails(@RequestBody AppointmentRequest appointmentRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getAppointmentBookingDetails in AppointmentBookingRestController ***");
		if(Util.isNotEmptyObject(appointmentRequest.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(appointmentRequest.getSessionKey());
			appointmentRequest.setRequestUrl("Employee");
			appointmentRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_APPOINTMENT_BOOKING_DETAILS.getUrl(), appointmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+appointmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/saveAppointmentTimeslots.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result saveAppointmentTimeslots(@RequestBody AppointmentRequest appointmentRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the saveAppointmentTimeslots in AppointmentBookingRestController ***");
		if(Util.isNotEmptyObject(appointmentRequest.getSessionKey()) && Util.isNotEmptyObject(appointmentRequest.getApptmtDtlsReqstList())
			&& Util.isNotEmptyObject(appointmentRequest.getApptmtDtlsReqstList().get(0))
			&& Util.isNotEmptyObject(appointmentRequest.getApptmtDtlsReqstList().get(0).getApptmtSlotTimeReqList()) 
			&& Util.isNotEmptyObject(appointmentRequest.getApptmtDtlsReqstList().get(0).getApptmtSlotTimeReqList().get(0)) 
			&& Util.isNotEmptyObject(appointmentRequest.getApptmtDtlsReqstList().get(0).getSlotDatesList())
			&& Util.isNotEmptyObject(appointmentRequest.getApptmtDtlsReqstList().get(0).getSlotDatesList().get(0))
			&& Util.isNotEmptyObject(appointmentRequest.getApptmtDtlsReqstList().get(0).getType()) 
			&& Util.isNotEmptyObject(appointmentRequest.getApptmtDtlsReqstList().get(0).getTypeIds())
			&& Util.isNotEmptyObject(appointmentRequest.getApptmtDtlsReqstList().get(0).getSiteId())) {
			EmployeeSession employeeSession = sessionValidate(appointmentRequest.getSessionKey());
			appointmentRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			getApptmtSlotTimessInTimestampFormat(appointmentRequest);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SAVE_APPOINTMENT_TIME_SLOTS.getUrl(), appointmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+appointmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	private void getApptmtSlotTimessInTimestampFormat(AppointmentRequest appointmentRequest) throws InSufficeientInputException {
		LOGGER.info("*** The control is inside of the getApptmtSlotTimessInTimestampFormat in AppointmentBookingRestController ***");
		try {
			for(AppointmentDetailsRequest apptmtDetReq : appointmentRequest.getApptmtDtlsReqstList()) {
				List<ApptmtSlotTimesInfo> apptmtSlotTimesInfoList = new ArrayList<>();
				for(ApptmtSlotTimeRequest apptmtSlotTimeReq : apptmtDetReq.getApptmtSlotTimeReqList()) {
					ApptmtSlotTimesInfo apptmtSlotTimesInfo = new ApptmtSlotTimesInfo();
					apptmtSlotTimesInfo.setStartTime(Util.getTimeInTimestamp("hh:mm a", apptmtSlotTimeReq.getStartTime()));
					apptmtSlotTimesInfo.setEndTime(Util.getTimeInTimestamp("hh:mm a", apptmtSlotTimeReq.getEndTime()));
					apptmtSlotTimesInfoList.add(apptmtSlotTimesInfo);
				}
				apptmtDetReq.setApptmtSlotTimesInfoList(apptmtSlotTimesInfoList);
			}
		}catch(Exception ex) {
			LOGGER.error("*** The exception is occurred inside of the getApptmtSlotTimessInTimestampFormat in AppointmentBookingRestController ***"+ex.getMessage());
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+appointmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}

	@RequestMapping(value="/getAppointmentTimeslots.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getAppointmentTimeslots(@RequestBody AppointmentRequest appointmentRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getAppointmentTimeslots in AppointmentBookingRestController ***");
		if(Util.isNotEmptyObject(appointmentRequest.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(appointmentRequest.getSessionKey());
			appointmentRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_APPOINTMENT_TIME_SLOTS.getUrl(), appointmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+appointmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getAllAppointmentBookingDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getAllAppointmentBookingDetails(@RequestBody AppointmentRequest appointmentRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getAllAppointmentBookingDetails in AppointmentBookingRestController ***");
		if(Util.isNotEmptyObject(appointmentRequest.getSessionKey()) && (Util.isNotEmptyObject(appointmentRequest.getSiteId()) || Util.isNotEmptyObject(appointmentRequest.getEmployeeIds())
			|| Util.isNotEmptyObject(appointmentRequest.getStartDate()) || Util.isNotEmptyObject(appointmentRequest.getEndDate()) || Util.isNotEmptyObject(appointmentRequest.getBlockId())
			|| Util.isNotEmptyObject(appointmentRequest.getApptmtStatusName()))) {
			sessionValidate(appointmentRequest.getSessionKey());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_ALL_APPOINTMENT_BOOKING_DETAILS.getUrl(), appointmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+appointmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/updateAppointmentBookingStatus.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result updateAppointmentBookingStatus(@RequestBody AppointmentRequest appointmentRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the updateAppointmentBookingStatus in AppointmentBookingRestController ***");
		if(Util.isNotEmptyObject(appointmentRequest.getSessionKey()) && Util.isNotEmptyObject(appointmentRequest.getApptmtStatusName())
			&& (Util.isNotEmptyObject(appointmentRequest.getApptmtSlotTimesId()) || (Util.isNotEmptyObject(appointmentRequest.getApptmtBookingsId()) && Util.isNotEmptyObject(appointmentRequest.getFlatBookingId())
			&& Util.isNotEmptyObject(appointmentRequest.getApptmtSlotTimesId())))) {
			EmployeeSession employeeSession = sessionValidate(appointmentRequest.getSessionKey());
			appointmentRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.UPDATE_APPOINTMENT_BOOKING_STATUS.getUrl(), appointmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+appointmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/updateAppointmentSummary.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result updateAppointmentSummary(@RequestBody AppointmentRequest appointmentRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the updateAppointmentSummary in AppointmentBookingRestController ***");
		if(Util.isNotEmptyObject(appointmentRequest.getSessionKey()) && Util.isNotEmptyObject(appointmentRequest.getApptmtBookingsId())
			&& Util.isNotEmptyObject(appointmentRequest.getApptmtStatusName()) && Util.isNotEmptyObject(appointmentRequest.getApptmtSummary())) {
			EmployeeSession employeeSession = sessionValidate(appointmentRequest.getSessionKey());
			appointmentRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.UPDATE_APPOINTMENT_SUMMARY.getUrl(), appointmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+appointmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getCompletedAppointmentBookingDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getCompletedAppointmentBookingDetails(@RequestBody AppointmentRequest appointmentRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getCompletedAppointmentBookingDetails in AppointmentBookingRestController ***");
		if(Util.isNotEmptyObject(appointmentRequest.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(appointmentRequest.getSessionKey());
			appointmentRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_COMPLETED_APPOINTMENT_BOOKING_DETAILS.getUrl(), appointmentRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+appointmentRequest);
			throw new InSufficeientInputException(errors);
		}
	}
}
