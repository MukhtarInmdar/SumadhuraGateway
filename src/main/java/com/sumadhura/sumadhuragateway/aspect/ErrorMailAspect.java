/**
 * 
 */
package com.sumadhura.sumadhuragateway.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.sumadhura.sumadhuragateway.util.MailService;

/**
 * This Annotation is responsible to Print Error Mail.
 * 
 * @author Venkat_Koniki
 * @since 08.04.2019
 * @time 07:06PM
 */

@Aspect
@Component
public class ErrorMailAspect {

	@Autowired
	@Lazy
	private MailService mailServiceImpl;

	private final static Logger logger = Logger.getLogger(ErrorMailAspect.class);

	 @After("@annotation(com.sumadhura.sumadhuragateway.annotations.ErrorMail)")
	//@After("execution(* com.sumadhura.sumadhuragateway.globalexceptionhandler.CustomGlobalExceptionHandler.*(..))")
	public void errorMail(JoinPoint joinPoint) {
		logger.info("*** The Control is inside the afterCreateSession in ReddisSessionAspect ****");
		logger.info("**** The  afterCreateSession method is executed for the method *****"+ joinPoint.getSignature().getName());
		Object[] arguments = joinPoint.getArgs();
		for (Object obj : arguments) {
			logger.info("**** The arguments passes for this method is " + joinPoint.getSignature().getName()+ " ********" + obj);
		    if(obj instanceof Throwable) {
		    	errorMail((Throwable)obj);
		    }
		
		}
	}
	private void errorMail(Throwable exception) {
		logger.info("*** The Control is inside the errorMail in CustomGlobalExceptionHandler ****");
		mailServiceImpl.sendErrorMail((Exception) exception);
	}
}


















