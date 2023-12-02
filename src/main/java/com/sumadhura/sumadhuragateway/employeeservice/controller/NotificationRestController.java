/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.Department;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.employeeservice.dto.NotificationRequest;
import com.sumadhura.sumadhuragateway.employeeservice.dto.SiteLevelNotifyRequestDTO;
import com.sumadhura.sumadhuragateway.employeeservice.enums.MetadataId;
import com.sumadhura.sumadhuragateway.employeeservice.enums.Module;
import com.sumadhura.sumadhuragateway.employeeservice.enums.SubModule;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.HttpStatus;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * NotificationRestService class provides Employee Notification specific
 * services.
 * 
 * @author Venkat_Koniki
 * @since 03.05.2019
 * @time 04:52PM
 */

@RestController
@RequestMapping(value = "/employeeservice/notification", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationRestController extends SessionValidate {

	private static final String isSendNotification = "View Notifications";

	private final Logger LOGGER = Logger.getLogger(NotificationRestController.class);

	@RequestMapping("/sendNotification.spring")
	public Result sendNotification(@RequestBody NotificationRequest notificationRequest)
			throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the sendNotification  in  NotificationRestService ********");
		String cacheKey = notificationRequest.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);

		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		Result result = null;// (Result)
								// RestApiUtility.sendJSONPostRequest(NotificationServiceUrls.blocksNamesBySite.getUrl(),
								// notificationRequest, Result.class);
		return result;
	}

	@RequestMapping(value = "/sendCompanyNotification.spring", method = RequestMethod.POST)
	public Result sendCompanyNotification(@RequestBody NotificationRequest req, BindingResult error)
			throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the sendNotification  in  NotificationRestService ********");
		/*
		 * if(error.hasErrors()){ List<FieldError> fieldErrors = error.getFieldErrors();
		 * List<String> errors=new ArrayList<>(); for (FieldError fieldError :
		 * fieldErrors) { errors.add(fieldError.getField() +" "
		 * +fieldError.getDefaultMessage()); } throw new
		 * InSufficeientInputException(errors); }
		 */
		String cacheKey = req.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		long empId = employee.getLoginResponse().getEmpId();
		req.setEmployeeId(empId);
		
		/* managing the all states */
		if (Util.isNotEmptyObject(req.getStateIds())) {
			if (req.getStateIds().size() == 1) {
				if (req.getStateIds().get(0) == 0) {
					req.setStateIds(null);
				}
			}
		}
		/* managing the all ostype */
		if(Util.isNotEmptyObject(req.getOsType())) {
			if(req.getOsType().equalsIgnoreCase("all") || req.getOsType().equalsIgnoreCase("null") ){
				req.setOsType(null);
			}
		}
		LOGGER.debug("*** The EmployeeSession object ***" + empId);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SEND_COMPANY_NOTIFYS.getUrl(),
				req, Result.class);

		return result;
	}

	/**
	 * @author rayudu
	 * @param notificationRequest
	 * @return
	 * @throws InSufficeientInputException
	 * @throws SessionTimeoutException
	 */
	@RequestMapping("/siteLevelNotifys.spring")
	public Result sendSiteLevelNotifications(@RequestBody SiteLevelNotifyRequestDTO notificationRequest,
			BindingResult error) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.debug("******* The control inside of the sendSiteLevelNotifications  in  NotificationRestService ********");
		/*
		 * if(error.hasErrors()){ List<FieldError> fieldErrors = error.getFieldErrors();
		 * List<String> errors=new ArrayList<>(); for (FieldError fieldError :
		 * fieldErrors) { errors.add(fieldError.getField() +" "
		 * +fieldError.getDefaultMessage()); } throw new
		 * InSufficeientInputException(errors); }
		 */

		String cacheKey = notificationRequest.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		notificationRequest.setEmployeeId(employee.getLoginResponse().getEmpId());

		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SEND_SITE_NOTIFYS.getUrl(),
				notificationRequest, Result.class);
		return result;
	}

	/**
	 * 
	 * @param companyNotifyReq
	 * @return
	 * @throws InSufficeientInputException
	 * @throws SessionTimeoutException
	 */
	@Deprecated
	@RequestMapping("/viewSiteNotifys.spring")
	public Result viewNotification(@RequestBody NotificationRequest companyNotifyReq)
			throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the sendNotification  in  NotificationRestService ********");
		String cacheKey = companyNotifyReq.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);

		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.VIEW_SITE_NOTIFY.getUrl(),
				companyNotifyReq, Result.class);

		return result;
	}

	/**
	 * Deprecated
	 * 
	 * @param companyNotifyReq
	 * @return
	 * @throws SessionTimeoutException
	 * @throws InSufficeientInputException
	 */
	@Deprecated
	@RequestMapping("/getCustomersNotifys.spring")
	public Result getCustomerNotification(@RequestBody NotificationRequest companyNotifyReq)
			throws SessionTimeoutException, InSufficeientInputException {
		String cacheKey = companyNotifyReq.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);

		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.VIEW_CUST_NOTIFYS.getUrl(),
				companyNotifyReq, Result.class);

		return result;

	}

	@RequestMapping("/getStateList.spring")
	public Result getStateList(@RequestBody NotificationRequest companyNotifyReq)
			throws SessionTimeoutException, InSufficeientInputException {
		String cacheKey = companyNotifyReq.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);

		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_STATES.getUrl(),
				companyNotifyReq, Result.class);

		return result;

	}

	@RequestMapping("/getAllStateList.spring")
	public Result getAllStateList(@RequestBody NotificationRequest companyNotifyReq)
			throws SessionTimeoutException, InSufficeientInputException {
		String cacheKey = companyNotifyReq.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);

		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_ALL_STATES.getUrl(),
				companyNotifyReq, Result.class);

		return result;

	}
	@RequestMapping("/companysNotifys.spring")
	public Result getCompanyNotifications(@RequestBody NotificationRequest companyNotifyReq)
			throws SessionTimeoutException, InSufficeientInputException {
		String cacheKey = companyNotifyReq.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);	
		companyNotifyReq.setTypeOf(MetadataId.COMPANY_NOTIFICATIONS.getId());	
		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		if (companyNotifyReq.getPageNo() == 0 && companyNotifyReq.getPageSize() == 0) {
			companyNotifyReq.setPageNo(1);
			companyNotifyReq.setPageSize(200);
		}
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.VIEW_COMPANYS_NOTIFY.getUrl(),
				companyNotifyReq, Result.class);
		return result;
	}
	
	@RequestMapping("/nonCustomerNotifys.spring")
	public Result getNonCustomerNotifications(@RequestBody NotificationRequest companyNotifyReq)
			throws SessionTimeoutException, InSufficeientInputException {
		String cacheKey = companyNotifyReq.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		
		companyNotifyReq.setTypeOf(MetadataId.NONCOUSTOMER.getId());

		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		if (companyNotifyReq.getPageNo() == 0 && companyNotifyReq.getPageSize() == 0) {
			companyNotifyReq.setPageNo(1);
			companyNotifyReq.setPageSize(200);
		}
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.VIEW_NONCUSTOMER_NOTIFY.getUrl(),
				companyNotifyReq, Result.class);
		return result;
	}
	
	
	@RequestMapping("/nonCustomerAndCompanyNotifications.spring")
	public Result getNonCustomerAndCompanyNotifications(@RequestBody NotificationRequest companyNotifyReq)
			throws SessionTimeoutException, InSufficeientInputException {
		String cacheKey = companyNotifyReq.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		
		companyNotifyReq.setTypeOf(MetadataId.NONCUSTOMER_COMPANY_NOTIFICATIONS.getId());

		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		if (companyNotifyReq.getPageNo() == 0 && companyNotifyReq.getPageSize() == 0) {
			companyNotifyReq.setPageNo(1);
			companyNotifyReq.setPageSize(200);
		}
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.NONCUSTOMER_COMPANY_NOTIFICATIONS.getUrl(),
				companyNotifyReq, Result.class);
		return result;
	}
	
	
	
	
	@RequestMapping("/sitesNotifys.spring")
	public Result getSitesNotifications(@RequestBody NotificationRequest companyNotifyReq)
			throws SessionTimeoutException, InSufficeientInputException {
		String cacheKey = companyNotifyReq.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);

		List<Department> departments = employee.getLoginResponse().getDepartments();
		List<Long> userAccessSiteList = Util.getUserAccessSiteList(departments, isSendNotification);
		if (userAccessSiteList == null || userAccessSiteList.isEmpty()) {
			//return new Result();
			userAccessSiteList.add(0l);
		}

		if (companyNotifyReq.getPageNo() == 0 && companyNotifyReq.getPageSize() == 0) {
			companyNotifyReq.setPageNo(1);
			companyNotifyReq.setPageSize(2000000);
		}

		LOGGER.debug("*** The EmployeeSession object ***" + employee);

		// To pass the userAccessSiteList to NotificationRestService
		companyNotifyReq.setEmpSiteList(userAccessSiteList);

		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.VIEW_SITE_NOTIFYS.getUrl(),
				companyNotifyReq, Result.class);

		return result;
	}
	
	@RequestMapping(value = "/sendProjectNotificationsForApprovals.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result sendProjectNotificationsForApprovals(@RequestBody SiteLevelNotifyRequestDTO notificationRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside the sendProjectNotificationsForApprovals in NotificationRestController ***");
		if(Util.isNotEmptyObject(notificationRequest) && Util.isNotEmptyObject(notificationRequest.getSessionKey())){
			Result result = new Result();
			EmployeeSession employeeSession = sessionValidate(notificationRequest.getSessionKey());
			notificationRequest.setEmployeeId(employeeSession.getLoginResponse().getEmpId());
			List<Department> departments = employeeSession.getLoginResponse().getDepartments();
			/* Employee Accessed Site List */
			List<Long> siteIds = new ArrayList<Long>();
			/* need to change this submodule after completion  */
			siteIds = Util.getUserAccessSiteList(departments, Module.PROJECT_NOTIFICATIONS.getName(), SubModule.SEND_NOTIFICATIONS.getName());
			/* No Access when Employee have no accessed sites */
			if(Util.isEmptyObject(siteIds)) {
				result.setResponseCode(HttpStatus.failure.getResponceCode());
				result.setDescription("No Access to this Module and Sub_Module");
				return result;
			}
			/* Employee Accessed Sites */
			notificationRequest.setEmpSiteList(siteIds);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SEND_PROJECT_NOTIF_FOR_APPROVALS.getUrl(), notificationRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value = "/viewModifiedProjectNotificationsForApprovals.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result viewModifiedProjectNotificationsForApprovals(@RequestBody SiteLevelNotifyRequestDTO notificationRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside the viewModifiedProjectNotificationsForApprovals in NotificationRestController ***");
		if(Util.isNotEmptyObject(notificationRequest) && Util.isNotEmptyObject(notificationRequest.getSessionKey())
			&& Util.isNotEmptyObject(notificationRequest.getNotificationType())){
			Result result = new Result();
			EmployeeSession employeeSession = sessionValidate(notificationRequest.getSessionKey());
			notificationRequest.setEmployeeId(employeeSession.getLoginResponse().getEmpId());
			List<Department> departments = employeeSession.getLoginResponse().getDepartments();
			/* Employee Accessed Site List */
			List<Long> siteIds = new ArrayList<>();
			/* need to change this submodule after completion  */
			if((notificationRequest.getNotificationType().equalsIgnoreCase("Project"))){
				if(Util.isNotEmptyObject(notificationRequest.getAction()) && notificationRequest.getAction().equalsIgnoreCase("Approval Level")) {
					siteIds = Util.getUserAccessSiteList(departments, Module.PROJECT_NOTIFICATIONS.getName(), SubModule.VIEW_NOTIFICATIONS_FOR_APPROVALS.getName());	
				}else if(Util.isNotEmptyObject(notificationRequest.getAction()) && notificationRequest.getAction().equalsIgnoreCase("Executive Level")) {
					siteIds = Util.getUserAccessSiteList(departments, Module.PROJECT_NOTIFICATIONS.getName(), SubModule.VIEW_NOTIFICATIONS_FOR_MODIFICATIONS.getName());	
				}
			}else if((notificationRequest.getNotificationType().equalsIgnoreCase("Company"))){
				if(Util.isNotEmptyObject(notificationRequest.getAction()) && notificationRequest.getAction().equalsIgnoreCase("Approval Level")) {
					siteIds = Util.getUserAccessSiteList(departments, Module.COMPANY_NOTIFICATIONS.getName(), SubModule.COMP_VIEW_NOTIFICATIONS_FOR_APPROVALS.getName());	
				}else if(Util.isNotEmptyObject(notificationRequest.getAction()) && notificationRequest.getAction().equalsIgnoreCase("Executive Level")) {
					siteIds = Util.getUserAccessSiteList(departments, Module.COMPANY_NOTIFICATIONS.getName(), SubModule.COMP_VIEW_NOTIFICATIONS_FOR_MODIFICATIONS.getName());		
				}
			}
			
			/* No Access when Employee has no accessed sites */
			if(Util.isEmptyObject(siteIds)) {
				result.setResponseCode(HttpStatus.failure.getResponceCode());
				result.setDescription("No Site Access to this Module and Sub_Module");
				return result;
			}
			/* Employee Accessed Sites */
			notificationRequest.setEmpSiteList(siteIds);
			/* setting defalut Page No and Page Size */
			notificationRequest.setPageNo(Util.isNotEmptyObject(notificationRequest.getPageNo())?notificationRequest.getPageNo():1);
			notificationRequest.setPageSize(Util.isNotEmptyObject(notificationRequest.getPageSize())?notificationRequest.getPageSize():200);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.VIEW_MODF_PROJECT_NOTIF_FOR_APPROVALS.getUrl(), notificationRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value = "/viewProjectNotificationDetailChanges.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result getProjectNotificationDetailChanges(@RequestBody SiteLevelNotifyRequestDTO notificationRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside the viewModifiedProjectNotificationsForApprovals in NotificationRestController ***");
		if(Util.isNotEmptyObject(notificationRequest) && Util.isNotEmptyObject(notificationRequest.getSessionKey())){
			EmployeeSession employeeSession = sessionValidate(notificationRequest.getSessionKey());
			notificationRequest.setEmployeeId(employeeSession.getLoginResponse().getEmpId());
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.VIEW_PROJECT_NOTIF_DET_CHANGES.getUrl(), notificationRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value = "/approveOrRejectProjectNotifications.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result approveOrRejectProjectNotifications(@RequestBody SiteLevelNotifyRequestDTO notificationRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside the approveOrRejectProjectNotifications in NotificationRestController ***");
		if(Util.isNotEmptyObject(notificationRequest) && Util.isNotEmptyObject(notificationRequest.getSessionKey())){
			EmployeeSession employeeSession = sessionValidate(notificationRequest.getSessionKey());
			notificationRequest.setEmployeeId(employeeSession.getLoginResponse().getEmpId());
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.APPROVE_OR_REJECT_PROJECT_NOTIFY.getUrl(), notificationRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value = "/sendCompanyNotificationsForApprovals.spring", method = RequestMethod.POST)
	public Result sendCompanyNotificationsForApprovals(@RequestBody SiteLevelNotifyRequestDTO notificationRequest, BindingResult error)throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("******* The control inside of the sendCompanyNotificationsForApprovals  in  NotificationRestService ********");
		if(Util.isNotEmptyObject(notificationRequest) && Util.isNotEmptyObject(notificationRequest.getSessionKey())){
			Result result = new Result();
			String cacheKey = notificationRequest.getSessionKey();
			EmployeeSession employeeSession = sessionValidate(cacheKey);
			Long empId = employeeSession.getLoginResponse().getEmpId();
			notificationRequest.setEmployeeId(empId);
			List<Department> departments = employeeSession.getLoginResponse().getDepartments();
			/* Employee Accessed Site List */
			List<Long> siteIds = new ArrayList<Long>();
			/* need to change this submodule after completion  */
			siteIds = Util.getUserAccessSiteList(departments, Module.COMPANY_NOTIFICATIONS.getName(), SubModule.COMP_SEND_NOTIFICATIONS.getName());
			/* No Access when Employee have no accessed sites */
			if(Util.isEmptyObject(siteIds)) {
				result.setResponseCode(HttpStatus.failure.getResponceCode());
				result.setDescription("No Access to this Module and Sub_Module");
				return result;
			}
			/* Employee Accessed Sites */
			notificationRequest.setEmpSiteList(siteIds);
			
			/* managing the all states */
			if(Util.isNotEmptyObject(notificationRequest.getStateIds()) && notificationRequest.getStateIds().get(0)!=null 
				&& notificationRequest.getStateIds().get(0) == 0) {
				notificationRequest.setStateIds(null);
			}
			
			/* managing the all ostype */
			if(Util.isNotEmptyObject(notificationRequest.getOsType()) && (notificationRequest.getOsType().equalsIgnoreCase("all") 
				|| notificationRequest.getOsType().equalsIgnoreCase("null") )) {
				notificationRequest.setOsType(null);
			}
			
			LOGGER.debug("*** The EmployeeSession object ***" + empId);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SEND_COMPANY_NOTIFYS_FOR_APPROVALS.getUrl(), notificationRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value = "/approveOrRejectCompanyNotifications.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result approveOrRejectCompanyNotifications(@RequestBody SiteLevelNotifyRequestDTO notificationRequest) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("*** The control is inside the approveOrRejectProjectNotifications in NotificationRestController ***");
		if(Util.isNotEmptyObject(notificationRequest) && Util.isNotEmptyObject(notificationRequest.getSessionKey())){
			EmployeeSession employeeSession = sessionValidate(notificationRequest.getSessionKey());
			notificationRequest.setEmployeeId(employeeSession.getLoginResponse().getEmpId());
			
			/* managing the all states */
			if(Util.isNotEmptyObject(notificationRequest.getStateIds()) && notificationRequest.getStateIds().get(0)!=null 
				&& notificationRequest.getStateIds().get(0) == 0) {
				notificationRequest.setStateIds(null);
			}
			
			/* managing the all ostype */
			if(Util.isNotEmptyObject(notificationRequest.getOsType()) && (notificationRequest.getOsType().equalsIgnoreCase("all") 
				|| notificationRequest.getOsType().equalsIgnoreCase("null") )) {
				notificationRequest.setOsType(null);
			}
			
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.APPROVE_OR_REJECT_COMPANY_NOTIFY.getUrl(), notificationRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	
	@RequestMapping("/getCompanyNotificationViewReport.spring")
	public Result getCompanyNotificationViewReport(@RequestBody NotificationRequest companyNotifyReq) throws SessionTimeoutException, InSufficeientInputException {
		String cacheKey = companyNotifyReq.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_COMPANY_NOTIFICATION_VIEW_REPORT.getUrl(),companyNotifyReq, Result.class);
		return result;
	}
	
	@RequestMapping("/getNotificationViewReport.spring")
	public Result getNotificationViewReport(@RequestBody NotificationRequest companyNotifyReq) throws SessionTimeoutException, InSufficeientInputException {
		String cacheKey = companyNotifyReq.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_NOTIFICATION_VIEW_REPORT.getUrl(),companyNotifyReq, Result.class);
		return result;
	}
	@RequestMapping("/getFlatsByDates.spring")
	public Result getFlatDetailsBydates(@RequestBody NotificationRequest companyNotifyReq) throws SessionTimeoutException, InSufficeientInputException {
		String cacheKey = companyNotifyReq.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_FLATS_BY_DATES.getUrl(),companyNotifyReq, Result.class);
		return result;
	}
	@RequestMapping("/getFlatDetailsCountBydates.spring")
	public Result getFlatDetailsCountBydates(@RequestBody NotificationRequest companyNotifyReq) throws SessionTimeoutException, InSufficeientInputException {
		String cacheKey = companyNotifyReq.getSessionKey();
		EmployeeSession employee = sessionValidate(cacheKey);
		LOGGER.debug("*** The EmployeeSession object ***" + employee);
		Result result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_FLATS_COUNT_BY_DATES.getUrl(),companyNotifyReq, Result.class);
		return result;
	}

}