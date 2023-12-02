package com.sumadhura.sumadhuragateway.employeeservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.employeeservice.dto.TicketReport;
import com.sumadhura.sumadhuragateway.employeeservice.dto.TicketReportingResponceWrapper;
import com.sumadhura.sumadhuragateway.employeeservice.enums.Module;
import com.sumadhura.sumadhuragateway.employeeservice.enums.SubModule;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;
/**
 * TicketReportingRestController class provides reporting services 
 * @author Malladi Venkatesh
 * @since 2021-01-06
 * @time 02:30 pm
 *
 */
@RestController
@RequestMapping("/employeeservice/ticketreport")
public class TicketReportingRestController extends SessionValidate {
	
	private static final Logger LOGGER = Logger.getLogger(TicketReportingRestController.class);
	
	@RequestMapping(value="/getProjectWiseTicketCount.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getProjectWiseTicketCount(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getProjectWiseTicketCount in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_PROJECT_WISE_TICKET_COUNT.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getProjectWiseRaisedTicketTypeCount.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getProjectWiseRaisedTicketTypeCount(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getProjectWiseRaisedTicketTypeCount in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_PROJECT_WISE_RAISED_TICKET_TYPE_COUNT.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	

	@RequestMapping(value="/getProjectWiseClosedTicketCount.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getProjectWiseClosedTicketCount(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getAllTicketTypesProjectWiseTicketCount in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			
			if ("crmFinance".equalsIgnoreCase(ticketReport.getRequestUrl())) {
				  ticketReport.setDepartmentId(995l);
			}else if ("crmTech".equalsIgnoreCase(ticketReport.getRequestUrl())) {
				  ticketReport.setDepartmentId(994l);
			} else 
			{
				  ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())? Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())? employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId(): 0l: 0l);
			}
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			List<Long> siteIds = null;
			 
			if(Util.isEmptyObject(ticketReport.getSiteIds())) {
				siteIds = Util.getUserAccessSiteList(employeeSession.getLoginResponse().getDepartments(), Module.REPORT.getName(), SubModule.Ticket_Avg_Close_Time_Report.getName());//SubModule.MODIFY_SUSPENSE_ENTRY.getName()
				ticketReport.setSiteIds(siteIds);
			} 

			
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_PROJECT_WISE_CLOSED_TICKETS_COUNT.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}

	@RequestMapping(value="/getAllMainTicketTypesProjectWiseTicketCount.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getAllMainTicketTypesProjectWiseTicketCount(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getAllTicketTypesProjectWiseTicketCount in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_ALL_MAINTICKET_TYPES_PROJECT_WISE_TICKET_COUNT.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getAllTicketTypesProjectWiseTicketCount.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getAllTicketTypesProjectWiseTicketCount(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getAllTicketTypesProjectWiseTicketCount in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_ALL_TICKET_TYPES_PROJECT_WISE_TICKET_COUNT.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getEmployeeProjectwiseTicketCount.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getEmployeeProjectwiseTicketCount(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getEmployeeProjectwiseTicketCount in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_EMPLOYEE_PROJECT_WISE_TICKET_COUNT.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getEscalationTicketsDetailsCount.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getEscalationTicketsDetailsCount(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getEscalationTicketsDetailsCount in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_ESCALATION_TICKETS_DETAILS.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getSiteTickettypeLevelWiseEscalationDetails.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getSiteTickettypeLevelWiseEscalationDetails(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getSiteTickettypeLevelWiseEscalationDetails in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_SITE_TICKET_TYPE_LEVEL_WISE_ESCALATION_DETAILS.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getEmployeeWiseTicketAverageClosingTime.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getEmployeeWiseTicketAverageClosingTime(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getEmployeeWiseTicketAverageClosingTime in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_EMPLOYEE_WISE_TICKET_AVERAGE_CLOSING_TIME.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getEmployeeWiseTicketAverageClosingAndReplyTimeDtls.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getEmployeeWiseTicketAverageClosingAndReplyTimeDtls(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getEmployeeWiseTicketAverageClosingAndReplyTimeDtls in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_EMPLOYEE_WISE_TICKET_AVERAGE_CLOSING_TIME_DETAILS.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getUnRespondingTickets.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getUnRespondingTickets(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getUnRespondingTickets in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_UNRESPONDING_TICKETS.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getTicketingDashBoardDetails.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getTicketingDashBoardDetails(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getTicketingDashBoardDetails in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_TICKETING_DASHBORARD_DETAILS.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/getAllFeedbackWiseTickets.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getAllFeedbackWiseTickets(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getAllFeedbackWiseTickets in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_ALL_FEEDBACK_WISE_TICKETS.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	
	@RequestMapping(value="/getTicketOwners.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getTicketOwners(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getTicketOwners in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			sessionValidate(ticketReport.getSessionKey());
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_TICKET_OWNERS.getUrl(), ticketReport, Result.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	
	@RequestMapping(value="/getSpecificTicketReport.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getSpecificTicketReport(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getSpecificTicketReport in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_SPECIFIC_TICKET_REPORT.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	
	@RequestMapping(value="/getTicketDetailsReport.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getTicketDetailsReport(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getTicketDetailsReport in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_TICKET_DETAILS_REPORT.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	
	
	@RequestMapping(value="/getEscalationLevelDetails.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getEscalationLevelDetails(@RequestBody TicketReport ticketReport) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside of the getEscalationLevelDetails in TicketReportingRestController ***");
		if(Util.isNotEmptyObject(ticketReport.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(ticketReport.getSessionKey());
			//mprRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			ticketReport.setDepartmentId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())?
			    employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId():0l:0l);
			ticketReport.setRoleId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())?
				Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())?
				employeeSession.getLoginResponse().getDepartments().get(0).getRoleId():0l:0l);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_ESCALATION_LEVEL_DETAILS.getUrl(), ticketReport, TicketReportingResponceWrapper.class);
		}else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+ticketReport);
			throw new InSufficeientInputException(errors);
		}
	}
	

}
