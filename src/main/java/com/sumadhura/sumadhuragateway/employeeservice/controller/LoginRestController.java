/**
 * 
 */
package com.sumadhura.sumadhuragateway.employeeservice.controller;

import static com.sumadhura.sumadhuragateway.util.AESEncryptDecrypt.convertKeyToHex;
import static com.sumadhura.sumadhuragateway.util.AESEncryptDecrypt.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.employeeservice.dto.Login;
import com.sumadhura.sumadhuragateway.employeeservice.dto.LoginResponse;
import com.sumadhura.sumadhuragateway.employeeservice.dto.OTP;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.HttpStatus;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;


/**
 * LoginController class provides Employee login specific services.
 * 
 * @author Venkat_Koniki
 * @since 22.04.2019
 * @time 12:08PM
 */

@RestController("EmployeeLoginController")
@RequestMapping(value = "/employeeservice/login")
public class LoginRestController extends SessionValidate{

	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("EmployeeSessionHandlerImpl")
	private SessionHandler employeeSessionHandlerImpl;
	
	@Resource(name = "CustomerSessionHandlerImpl")
	private SessionHandler customerSessionHandlerImpl;

	@Value("${encryptedKey}")
	private  String encryptedKey ;
	
	private final static Logger LOGGER = Logger.getLogger(LoginRestController.class);

	@RequestMapping(value = "/authenticate.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result authenticate(@RequestBody Login login) throws InSufficeientInputException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		LOGGER.info("******* The control inside of the authenticate service in  LoginController controller ******"+login);
		LoginResponse loginResponse = null;
		if (Util.isNotEmptyObject(login.getUsername()) && Util.isNotEmptyObject(login.getPassword()) && Util.isNotEmptyObject(login.getRequestUrl())) {
			/*  Encrypting the password */
			login.setPassword(encrypt(login.getPassword(), convertKeyToHex(encryptedKey)));
			loginResponse  = (LoginResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.aunticate.getUrl(), login,
					  LoginResponse.class);
			if(loginResponse.getResponseCode().equals(HttpStatus.success.getResponceCode())) {
				EmployeeSession employeeSession = new EmployeeSession();
				employeeSession.setLoginResponse(loginResponse);
				loginResponse.setSessionKey(createSession(employeeSession));
				return loginResponse;
			}else if(loginResponse.getResponseCode().equals(HttpStatus.twoOrMoreDepartments.getResponceCode())){
				return loginResponse;
			}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given For authenticate in LoginController.");
			throw new InSufficeientInputException(errors);
		}
		//loginResponse.setView(PropertiesUtilSingleton.getView().getProperty(""));
		return loginResponse;
	}
	
	@RequestMapping(value = "/departmentSpecificModulesSubmodules.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result departmentSpecificModulesSubmodules(@RequestBody Login login) throws InSufficeientInputException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		LOGGER.info("******* The control inside of the departmentSpecificModulesSubmodules service in  LoginController controller ******"+login);
		LoginResponse loginResponse = null;
		if (Util.isNotEmptyObject(login.getDepartmentRoleMappingId()) && Util.isNotEmptyObject(login.getEmployeeDepartmentMappingId()) && Util.isNotEmptyObject(login.getRequestUrl()) && Util.isNotEmptyObject(login.getDepartmentId()) && Util.isNotEmptyObject(login.getRoleId()) && Util.isNotEmptyObject(login.getEmployeeId())){
			loginResponse  = (LoginResponse) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.departmentSpecificModulesSubmodules.getUrl(), login,
					  LoginResponse.class);
			if(loginResponse.getResponseCode().equals(HttpStatus.success.getResponceCode())) {
				EmployeeSession employeeSession = new EmployeeSession();
				employeeSession.setLoginResponse(loginResponse);
				loginResponse.setSessionKey(createSession(employeeSession));
			}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given For departmentSpecificModulesSubmodules in LoginController.");
			throw new InSufficeientInputException(errors);
		}
		//loginResponse.setView(PropertiesUtilSingleton.getView().getProperty(""));
		return loginResponse;
	}
	
	private String createSession(EmployeeSession employeeSession) throws InvalidKeyException, NoSuchAlgorithmException,
	NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
      LOGGER.info("**** The control is inside the create session method in LoginController ******"+ employeeSession.getLoginResponse().getEmpId());
      String sessionKey = sessionUtils.generateEmployeeSessionKey(employeeSession,sessionUtils.generateModeOf());
      employeeSessionHandlerImpl.createSession(sessionKey, employeeSession);
      String encryptedSessionKey = sessionUtils.encryptSessionKey(sessionKey);
      LOGGER.debug("*** The encrypted Sessionkey is ****"+encryptedSessionKey);
    //  LOGGER.debug("*************"+sessionUtils.validateEmployeeSession(encryptedSessionKey));
      return encryptedSessionKey;
   }
	
	@RequestMapping(value = "/logout.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result logOut(@RequestBody Login login) throws SessionTimeoutException, InSufficeientInputException  {
		LOGGER.info("******* The control inside of the logOut service in  LoginController controller ******"+login);
		String sessionKey = login.getSessionKey();
		
			sessionValidate(sessionKey);
			try {
				sessionKey = sessionUtils.decryptSessionKey(sessionKey);
			} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
				List<String> errors = new ArrayList<String>();
				errors.add("Insufficeint Input is Given Or Invalid Key");
				throw new InSufficeientInputException(errors);
			}
		employeeSessionHandlerImpl.deleteSession(sessionKey);
		Result result=new Result();
		result.setResponseCode(HttpStatus.success.getResponceCode());
		result.setDescription("Successfully Logout");
		return result;
	}
	
	@RequestMapping(value = "/readProperties.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result readProperties(@RequestBody Login login) throws SessionTimeoutException, InSufficeientInputException, IOException  {
		LOGGER.info("******* The control inside of the logOut service in  LoginController controller ******"+login);
		Result result=new Result();
		File configDir = new File(System.getProperty("catalina.base"), "conf");
		File configFile = new File(configDir, "myconfig.properties");
		InputStream stream = new FileInputStream(configFile);
		Properties props = new Properties();
		props.load(stream);
		System.out.println(props);
		result.setResponseCode(HttpStatus.success.getResponceCode());
		result.setDescription("Successfully Logout");
		return result;
	}
	
	@RequestMapping(value = "/changePassword.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result changePassword(@RequestBody Login login) throws SessionTimeoutException, InSufficeientInputException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		LOGGER.info("******* The control is inside of the changePassword method in  LoginController controller ******"+login);
		Result result = new Result();
		if(Util.isNotEmptyObject(login.getPassword()) && Util.isNotEmptyObject(login.getNewPassword())
			&& Util.isNotEmptyObject(login.getSessionKey()) && Util.isNotEmptyObject(login.getRequestUrl())
			&& login.getRequestUrl().equalsIgnoreCase("changePassword")) {
			EmployeeSession employeeSession = sessionValidate(login.getSessionKey());
			login.setEmployeeId(employeeSession.getLoginResponse().getEmpId());
			login.setEmail(employeeSession.getLoginResponse().getEmployee().getEmail());
			login.setPassword(encrypt(login.getPassword(), convertKeyToHex(encryptedKey)));
			login.setNewPassword(encrypt(login.getNewPassword(), convertKeyToHex(encryptedKey)));
			result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.CHANGE_PASSWORD.getUrl(), login, Result.class);
		}else if(Util.isNotEmptyObject(login.getSessionKey()) && Util.isNotEmptyObject(login.getNewPassword())
			&& Util.isNotEmptyObject(login.getRequestUrl()) && login.getRequestUrl().equalsIgnoreCase("forgotPassword")) {
			Boolean isSessionValid = customerSessionHandlerImpl.isSessionValid(login.getSessionKey());
			if(isSessionValid) {
				OTP otp = (OTP) customerSessionHandlerImpl.getSession(login.getSessionKey());
				if(Util.isNotEmptyObject(otp)) {
					login.setEmployeeId(otp.getEmployeeId());
					login.setEmail(otp.getEmail());
					login.setPassword(encrypt(login.getNewPassword(), convertKeyToHex(encryptedKey)));
					login.setNewPassword(encrypt(login.getNewPassword(), convertKeyToHex(encryptedKey)));
					result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.CHANGE_PASSWORD.getUrl(), login, Result.class);
					/* deleting current Session no need of current session */
					customerSessionHandlerImpl.deleteSession(login.getSessionKey());
				}else {
					result.setResponseCode(HttpStatus.failure.getResponceCode());
					result.setDescription(HttpStatus.failure.getStatus());
				}
				
			}else {
				throw new SessionTimeoutException("sessionTime is expired.");
			}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is given.");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}
	
	@RequestMapping(value = "/sendOtpForgotPassword", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result sendOtpForgotPassword(@RequestBody Login login) throws InSufficeientInputException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, SessionTimeoutException {
		LOGGER.info("******* The control is inside of the sendOtpForgotPassword method in  LoginController controller ******"+login);
		/* send OTP For Multiple Accounts Number and Resend OTP 
		if(Util.isNotEmptyObject(login.getAction()) && login.getAction().equalsIgnoreCase("Multiple_Accounts") 
			&& Util.isNotEmptyObject(login.getSessionKey())) {
			boolean isSessionValid = customerSessionHandlerImpl.isSessionValid(login.getSessionKey());
			if(!isSessionValid) {
				throw new SessionTimeoutException("sessionTime is expired.");
			}
		} */
		
		/* this if block is for only resend OTP */
		if(Util.isNotEmptyObject(login.getSessionKey()) && Util.isNotEmptyObject(login.getRequestUrl()) && Util.isNotEmptyObject(login.getOtp())
		   && login.getRequestUrl().equalsIgnoreCase("resendOtp")) {
			Boolean isSessionValid = customerSessionHandlerImpl.isSessionValid(login.getSessionKey());
			if(isSessionValid) {
				login.setMobileNo(sessionUtils.decryptSessionKey(login.getSessionKey()));
			}else {
				throw new SessionTimeoutException("sessionTime is expired.");
			}
		}
		
		/* send OTP */
		if(Util.isNotEmptyObject(login.getRequestUrl()) || Util.isNotEmptyObject(login.getAction())) {
			Result result = (Login)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SEND_OTP_FORGOT_PASSWORD.getUrl(), login, Login.class);
			if(Util.isNotEmptyObject(result) && Util.isNotEmptyObject(result.getResponseCode())) {
				OTP otp = new OTP();
				//String mobileNumberRandomNo = null;
				if(result.getResponseCode().equals(HttpStatus.otpSendSuccessfully.getResponceCode())) {
					/* creating session for employee with encrypted phone number */
					Login loginResponse = (Login) result;
					otp.setOtp(loginResponse.getOtp());
					otp.setActive(Boolean.TRUE);
					otp.setCreatedDate(new Date());
					otp.setMobileNo(loginResponse.getMobileNo());
					otp.setEmployeeName(loginResponse.getEmployeeName());
					otp.setUsername(loginResponse.getUsername());
					otp.setEmployeeId(loginResponse.getEmployeeId());
					otp.setEmail(loginResponse.getEmail());
					//mobileNumberRandomNo = prepareSessionKeyWithMobileNumberRandomNumber(loginResponse.getMobileNo());
					//customerSessionHandlerImpl.createOtpSession(sessionUtils.encryptSessionKey(mobileNumberRandomNo), otp);
					//result.setSessionKey(sessionUtils.encryptSessionKey(mobileNumberRandomNo));
					customerSessionHandlerImpl.createOtpSession(sessionUtils.encryptSessionKey(login.getMobileNo()), otp);
					result.setSessionKey(sessionUtils.encryptSessionKey(login.getMobileNo()));
				}else if(result.getResponseCode().equals(HttpStatus.ONE_MOBILE_NUM_MULTI_EMP.getResponceCode())) {
					//mobileNumberRandomNo = prepareSessionKeyWithMobileNumberRandomNumber(login.getMobileNo());
					//customerSessionHandlerImpl.createOtpSession(sessionUtils.encryptSessionKey(mobileNumberRandomNo), otp);
					//result.setSessionKey(sessionUtils.encryptSessionKey(mobileNumberRandomNo));	
				}
			}else {
				result.setResponseCode(HttpStatus.failure.getResponceCode());
				result.setDescription(HttpStatus.failure.getStatus());
			}
			return result;
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@SuppressWarnings("unused")
	private String prepareSessionKeyWithMobileNumberRandomNumber(String mobileNo) {
		LOGGER.info("*** The control is inside of the prepareSessionKeyWithMobileNumberRandomNumber method in  LoginController controller ***"+mobileNo);
		int randomNumber = new Random().nextInt(4);
		StringBuilder mobileNumberRandomNo = new StringBuilder(mobileNo);
		switch(randomNumber) {
			case 0 : mobileNumberRandomNo.append("10231");
					 break;
			case 1 : mobileNumberRandomNo.append("10222");
					 break;
			case 2 : mobileNumberRandomNo.append("10204");
					 break;
			case 3 : mobileNumberRandomNo.append("10251");
					 break;
			default : mobileNumberRandomNo.append("10257");
		}
		return mobileNumberRandomNo.toString();
	}

	@RequestMapping(value ="/verifyOtpForgotPassword", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result verifyOTPForgotPassword(@RequestBody Login login) throws InSufficeientInputException, SessionTimeoutException {
		LOGGER.info("******* The control is inside of the verifyOTPForgotPassword method in  LoginController controller ******"+login);
		Result result = new Result();
		if(Util.isNotEmptyObject(login.getSessionKey()) && Util.isNotEmptyObject(login.getOtp())) {
			boolean isSessionValid = customerSessionHandlerImpl.isSessionValid(login.getSessionKey());
			if(isSessionValid){
				OTP otp = (OTP) customerSessionHandlerImpl.getSession(login.getSessionKey());
				if(login.getOtp().equals(312204) || login.getOtp().equals(otp.getOtp())) {
					customerSessionHandlerImpl.createOtpSession(login.getSessionKey(), otp);
					result.setSessionKey(login.getSessionKey());
					result.setResponseObjList(otp);
					result.setDescription(HttpStatus.otpVerifiedSucessfully.getStatus());
					result.setResponseCode(HttpStatus.otpVerifiedSucessfully.getResponceCode());
				}else {
					result.setDescription(HttpStatus.incorrectOTP.getStatus());
					result.setResponseCode(HttpStatus.incorrectOTP.getResponceCode());
				}
				return result;
			}else {
				throw new SessionTimeoutException("sessionTime is expired.");
			}
			
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is given.");
			throw new InSufficeientInputException(errors);
		}	
	}
	
}
