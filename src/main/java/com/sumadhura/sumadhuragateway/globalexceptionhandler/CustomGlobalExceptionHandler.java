package com.sumadhura.sumadhuragateway.globalexceptionhandler;


import java.net.ConnectException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.catalina.connector.ClientAbortException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.IncorrectOtpException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.exception.UnauthorizedException;
import com.sumadhura.sumadhuragateway.util.HttpStatus;
import com.sumadhura.sumadhuragateway.util.MailService;

@ControllerAdvice
//@ErrorMail
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired(required = true)
	private MailService mailServiceImpl;

	private final static Logger logger = Logger.getLogger(CustomGlobalExceptionHandler.class);

	@ExceptionHandler(SessionTimeoutException.class)
	public ResponseEntity<Result> handleSessionTimeoutException(Exception ex, WebRequest request) {

		logger.error("***** The Exception information is ******"+ex);
		// sending error mail to developer.
	   	 errorMail(ex);
		Result error = new Result();
		error.setResponseCode(HttpStatus.sessiontimeout.getResponceCode());
		error.setStatus(HttpStatus.sessiontimeout.getStatus());
		error.setErrors(HttpStatus.sessiontimeout.getErrors());
		return new ResponseEntity<Result>(error, org.springframework.http.HttpStatus.OK);
	}
	@ExceptionHandler(IncorrectOtpException.class)
	public ResponseEntity<Result> handleIncorrectOtpException(Exception ex, WebRequest request) {
		logger.error("***** The Exception information is ******"+ex);
		// sending error mail to developer.
		  errorMail(ex);
		Result error = new Result();
		error.setResponseCode(HttpStatus.incorrectOTP.getResponceCode());
		error.setStatus(HttpStatus.incorrectOTP.getStatus());
		error.setErrors(HttpStatus.incorrectOTP.getErrors());
		return new ResponseEntity<Result>(error, org.springframework.http.HttpStatus.OK);
	}
	
	@ExceptionHandler(InSufficeientInputException.class)
	public ResponseEntity<Result> handleInSufficeientInputException(InSufficeientInputException ex, WebRequest request) {
		logger.error("***** The Exception information is ******"+ex);
		// sending error mail to developer.
		errorMail(ex);
		Result error = new Result();
		error.setResponseCode(HttpStatus.insufficientInput.getResponceCode());
		error.setStatus(HttpStatus.insufficientInput.getStatus());
		error.setErrors(ex.getMessages());
		return new ResponseEntity<Result>(error, org.springframework.http.HttpStatus.OK);
	}

	@ExceptionHandler(value = { InvalidKeyException.class, NoSuchAlgorithmException.class, NoSuchPaddingException.class,
			InvalidAlgorithmParameterException.class, IllegalBlockSizeException.class, BadPaddingException.class })
	public ResponseEntity<Result> handleEncryptSessionKeyException(Exception ex, WebRequest request) {
		logger.error("***** The Exception information is ******"+ex);
		// sending error mail to developer.
		errorMail(ex);
		Result error = new Result();
		error.setResponseCode(HttpStatus.encryptSessionKey.getResponceCode());
		error.setStatus(HttpStatus.encryptSessionKey.getStatus());
		error.setErrors(HttpStatus.encryptSessionKey.getErrors());
		return new ResponseEntity<Result>(error, org.springframework.http.HttpStatus.OK);

	}
	
	@ExceptionHandler(ConnectException.class)
	public ResponseEntity<Result> handleConnectionException(Exception ex, WebRequest request) {
		// sending error mail to developer.
		errorMail(ex);
		logger.error("**** The Exception information is *****"+ex);
		List<String> errors = new ArrayList<String>();
		errors.add(ex.toString());
		Result error = new Result();
		error.setResponseCode(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE.value());
		error.setStatus(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
		error.setErrors(errors);
		return new ResponseEntity<Result>(error, org.springframework.http.HttpStatus.OK);

	}
	
	
	@ExceptionHandler(ClientAbortException.class)
	 public ResponseEntity<Result> handleClientAbortException(Exception ex, WebRequest request) {
	 logger.error("*** The Exception information is ****"+ex);
	 errorMail(ex);
	 List<String> errors = new ArrayList<String>();
	 errors.add(ex.toString());
	 Result error = new Result();
	 error.setResponseCode(org.springframework.http.HttpStatus.REQUEST_TIMEOUT.value());
	 error.setStatus(org.springframework.http.HttpStatus.REQUEST_TIMEOUT.getReasonPhrase());
	 error.setErrors(errors);
	 return new ResponseEntity<Result>(error, org.springframework.http.HttpStatus.OK);
	 }
		
		@ExceptionHandler(UnauthorizedException.class)
		public ResponseEntity<Result> handleUnauthorizedException(Exception ex, WebRequest request) {
			logger.error("*** The Exception information is ****"+ex);
			errorMail(ex);
			List<String> errors = new ArrayList<String>();
			errors.add(ex.toString());
			Result error = new Result();
			error.setResponseCode(org.springframework.http.HttpStatus.UNAUTHORIZED.value());
			error.setStatus(org.springframework.http.HttpStatus.UNAUTHORIZED.getReasonPhrase());
			error.setErrors(errors);
			return new ResponseEntity<Result>(error, org.springframework.http.HttpStatus.OK);
	    }

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, org.springframework.http.HttpStatus status, WebRequest request) {
		errorMail(ex);
		logger.error("***** The Exception information is ******"+ex);
		List<String> errors = new ArrayList<String>();
		for(FieldError error : ex.getBindingResult().getFieldErrors()) {
        	errors.add(error.getDefaultMessage());
        }
		Result error = new Result();
		error.setResponseCode(HttpStatus.insufficientInput.getResponceCode());
		error.setStatus(ex.getMessage());
		error.setErrors(errors);
		return new ResponseEntity<Object>(error, org.springframework.http.HttpStatus.OK);
	}
		
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Result> handleGenericException(Exception ex, WebRequest request) {
		// sending error mail to developer.
		errorMail(ex);
		logger.error("***** The Exception information is ******"+ex);
		List<String> errors = new ArrayList<String>();
		errors.add(ex.toString());
		Result error = new Result();
		error.setResponseCode(HttpStatus.exceptionRaisedInFlow.getResponceCode());
		error.setStatus(HttpStatus.exceptionRaisedInFlow.getStatus());
		error.setErrors(errors);
		return new ResponseEntity<Result>(error, org.springframework.http.HttpStatus.OK);

	}
	@SuppressWarnings("unused")
	private void errorMail(final Throwable exception) {
		logger.info("*** The Control is inside the errorMail in CustomGlobalExceptionHandler ****");
		Thread thread = new Thread() {
			public void run() {
				mailServiceImpl.sendErrorMail((Exception) exception);
			}
		};
		thread.start();
	}

}
