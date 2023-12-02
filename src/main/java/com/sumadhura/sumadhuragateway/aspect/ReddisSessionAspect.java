/**
 * 
 */
package com.sumadhura.sumadhuragateway.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionTimeout;


/**
 * Aspect declaration
 * 
 * @author Venkat_Koniki
 * @since 22.03.2019
 * @time 05:19PM
 */

//@Aspect
//@Component
public class ReddisSessionAspect {

	private final static Logger logger = Logger.getLogger(ReddisSessionAspect.class);
	
	//@Autowired
	//@Qualifier("CustomerSessionHandlerImpl")
	private SessionHandler customerSessionHandlerImpl;
	
	//@Autowired
	//@Qualifier("EmployeeSessionHandlerImpl")
	//private SessionHandler employeeSessionHandlerImpl;
	

	//@Before("execution(* com.mkyong.customer.bo.CustomerBo.addCustomer(..)) && args(yourString,..)")
	//@Before("execution(* com.howtodoinjava.app.service.impl.EmployeeManagerImpl.*(..))")
	//@Before("execution(* com.sumadhura.sumadhuragateway.util.SessionHandler.getSession(..)) || execution(* com.sumadhura.sumadhuragateway.util.SessionHandler.isSessionValid(..)) ")
	
	//@After("execution(* com.sumadhura.sumadhuragateway.util.SessionHandler.createSession(..))")
	public  void  afterCreateSession(JoinPoint joinPoint) {
		logger.info("*** The Control is inside the afterCreateSession in ReddisSessionAspect ****");
		logger.info("**** The  afterCreateSession method is executed for the method *****"+ joinPoint.getSignature().getName());
		Object[] arguments = joinPoint.getArgs();
		for( Object obj :arguments)
		logger.info("**** The arguments passes for this method is "+joinPoint.getSignature().getName()+" ********"+obj);
		setMaxInactiveInterval(arguments[0].toString(),SessionTimeout.MEDIUM);
	}
	//@Before("execution(* com.sumadhura.sumadhuragateway.util.SessionHandler.getSession(..))")
	public  void  beforeGetSession(JoinPoint joinPoint) {
		logger.info("*** The Control is inside the beforeGetSession in ReddisSessionAspect ****");
		logger.info("**** The  beforeGetSession method is executed for the method *****"+ joinPoint.getSignature().getName());
		Object[] arguments = joinPoint.getArgs();
		for( Object obj :arguments)
		logger.info("**** The arguments passes for this method is "+joinPoint.getSignature().getName()+" ********"+obj);
		setMaxInactiveInterval(arguments[0].toString(),SessionTimeout.MEDIUM);
	}
	//@After("execution(* com.sumadhura.sumadhuragateway.util.SessionHandler.createOtpSession(..))")
	public  void  afterCreateOtpSession(JoinPoint joinPoint) {
		logger.info("*** The Control is inside the afterCreateSession in ReddisSessionAspect ****");
		logger.info("**** The  afterCreateSession method is executed for the method *****"+ joinPoint.getSignature().getName());
		Object[] arguments = joinPoint.getArgs();
		for( Object obj :arguments)
		logger.info("**** The arguments passes for this method is "+joinPoint.getSignature().getName()+" ********"+obj);
		setMaxInactiveInterval(arguments[0].toString(),SessionTimeout.HIGH);
	}
	public void setMaxInactiveInterval(String sessionKey,SessionTimeout timeout) {
		logger.info("*** The Control is inside the setMaxInactiveInterval in ReddisSessionAspect ****");
		customerSessionHandlerImpl.expire(sessionKey,timeout.expiryTime);
		logger.info("*** The Control is at end of the setMaxInactiveInterval in ReddisSessionAspect ****");
	}

}






