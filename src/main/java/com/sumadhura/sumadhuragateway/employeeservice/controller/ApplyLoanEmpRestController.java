package com.sumadhura.sumadhuragateway.employeeservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.LoanRequest;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.Department;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.employeeservice.enums.Module;
import com.sumadhura.sumadhuragateway.employeeservice.enums.SubModule;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * AppointmentBookingRestController is responsible for providing customer applied bank loan detaills
 * @author Aniket Chavan
 * @since 20-07-2022
 * @time 5:50
 *
 */
@RestController
@RequestMapping("/employeeservice/loan")
public class ApplyLoanEmpRestController extends SessionValidate {
	
	private final static Logger log = Logger.getLogger(ApplyLoanEmpRestController.class);
	
	@RequestMapping(value="/viewLoanAppliedLeadDetailsList.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getLoanAppliedLeadDetailsList(@RequestBody LoanRequest loanRequest) throws InSufficeientInputException, SessionTimeoutException {
		log.info("*** The control is inside of the getLoanAppliedLeadDetails in ApplyLoanEmpRestController ***");
		if(Util.isNotEmptyObject(loanRequest.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(loanRequest.getSessionKey());
			
			loanRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId();
			List<Department> departments = employeeSession.getLoginResponse().getDepartments();
			if(Util.isEmptyObject(loanRequest.getSiteIds())) {
				/* Employee Accessed Site List */
				List<Long> siteIds = new ArrayList<Long>();
				siteIds = Util.getUserAccessSiteList(departments, Module.APPLY_LOAN.getName(), SubModule.VIEW_LEAD_DETAILS.getName());
				/* Employee Accessed Sites */
				loanRequest.setSiteIds(siteIds);
			}
			if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
				if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
					loanRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
					loanRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				} else {
					loanRequest.setDepartmentId(0l);
					loanRequest.setRoleId(0l);
				}
			}
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getLoanAppliedLeadDetailsList.getUrl(), loanRequest, Result.class);
		} else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+loanRequest);
			throw new InSufficeientInputException(errors);
		}
	}

	@RequestMapping(value="/loadLoanAppliedLeadDetailsById.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result loadLoanAppliedLeadDetails(@RequestBody LoanRequest loanRequest) throws InSufficeientInputException, SessionTimeoutException {
		log.info("*** The control is inside of the loadLoanAppliedLeadDetailsById in ApplyLoanEmpRestController ***");
		if(Util.isNotEmptyObject(loanRequest.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(loanRequest.getSessionKey());
//			loanRequest.setRequestUrl("Employee");
			loanRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.loadLoanAppliedLeadDetailsById.getUrl(), loanRequest, Result.class);
		} else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+loanRequest);
			throw new InSufficeientInputException(errors);
		}
	}

	@RequestMapping(value="/updateBankerLeadSeenStatus.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result updateLeadDetailsSeenStatus(@RequestBody LoanRequest loanRequest) throws InSufficeientInputException, SessionTimeoutException {
		log.info("*** The control is inside of the updateBankerLeadSeenStatus in ApplyLoanEmpRestController ***");
		if(Util.isNotEmptyObject(loanRequest.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(loanRequest.getSessionKey());
			//loanRequest.setRequestUrl("Employee");
			loanRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())){
				if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId()) && Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId())){
					loanRequest.setDepartmentId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
					loanRequest.setRoleId(employeeSession.getLoginResponse().getDepartments().get(0).getRoleId());
				} else {
					loanRequest.setDepartmentId(0l);
					loanRequest.setRoleId(0l);
				}
			}
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.updateBankerLeadSeenStatus.getUrl(), loanRequest, Result.class);
		} else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+loanRequest);
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value="/updateApplyLoanLeadDetails.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result updateApplyLoanLeadDetailsById(@RequestBody LoanRequest loanRequest) throws InSufficeientInputException, SessionTimeoutException {
		log.info("*** The control is inside of the updateApplyLoanLeadDetails in ApplyLoanEmpRestController ***");
		if(Util.isNotEmptyObject(loanRequest.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(loanRequest.getSessionKey());
			//loanRequest.setRequestUrl("Employee");
			loanRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.updateApplyLoanLeadDetails.getUrl(), loanRequest, Result.class);
		} else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+loanRequest);
			throw new InSufficeientInputException(errors);
		}
	}

	@RequestMapping(value="/getBankList.spring",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getBankList(@RequestBody LoanRequest loanRequest) throws InSufficeientInputException, SessionTimeoutException {
		log.info("*** The control is inside of the getBankList in ApplyLoanEmpRestController ***");
		if(Util.isNotEmptyObject(loanRequest.getSessionKey())) {
			EmployeeSession employeeSession = sessionValidate(loanRequest.getSessionKey());
			//loanRequest.setRequestUrl("Employee");
			loanRequest.setEmployeeId(Util.isNotEmptyObject(employeeSession.getLoginResponse().getEmpId())?employeeSession.getLoginResponse().getEmpId():0l);
			return (Result)RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_BANKER_LIST.getUrl(), loanRequest, Result.class);
		} else {
			List<String> errors = new ArrayList<>();
			errors.add("The insufficeint input is given "+loanRequest);
			throw new InSufficeientInputException(errors);
		}
	}

}
