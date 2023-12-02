package com.sumadhura.sumadhuragateway.customerservice.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.Employee;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.util.HttpStatus;
import com.sumadhura.sumadhuragateway.util.SessionHandler;

/**
 * SessionRestController class provides Registration specific services.
 * 
 * @author Venkat_Koniki
 * @since 26.03.2019
 * @time 04.50 PM
 */

@RestController
public class SessionRestController {
	
	private final static Logger logger = Logger.getLogger(SessionRestController.class);	
	
	@Autowired
	@Qualifier("CustomerSessionHandlerImpl")
	private SessionHandler customerSessionHandlerImpl;
	
	@Autowired
	@Qualifier("EmployeeSessionHandlerImpl")
	private SessionHandler employeeSessionHandlerImpl;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET,produces="application/json")
	public Employee home(Locale locale, Model model) {
		
		logger.info("Welcome home! The client locale is {}."+locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		Employee employee = new Employee();
		employee.setId(1l);
		employee.setName("venkat");
		employee.setSex("male");
		return employee;
	}
	
	@RequestMapping(value = "/invalidateSession", method = RequestMethod.POST,produces="application/json",consumes="application/json")
	public Result setInvalidate(Result result) {
		logger.info("**** The control is inside the setInvalidate Session in SessionrestController ****");
		
	   final String employeeHashKey = "employee";
	   final String customerHashKey = "customer";
		if(result.getStatus().equalsIgnoreCase(customerHashKey)) {
			customerSessionHandlerImpl.deleteSession(result.getSessionKey());
			result.setResponseCode(HttpStatus.success.getResponceCode());
			result.setStatus(HttpStatus.success.getStatus());
		}else if(result.getStatus().equalsIgnoreCase(employeeHashKey)) {
			employeeSessionHandlerImpl.deleteSession(result.getSessionKey());
			result.setResponseCode(HttpStatus.success.getResponceCode());
			result.setStatus(HttpStatus.success.getStatus());
		}
		return result;
	}

}
