/**
 *  This controller is responsible to authenticate the customer.
 */
package com.sumadhura.sumadhuragateway.customerservice.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sumadhura.sumadhuragateway.dto.Block;
import com.sumadhura.sumadhuragateway.dto.ChangePasswordReq;
import com.sumadhura.sumadhuragateway.dto.CustomerPropertyDetails;
import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.CustomerTicketRequest;
import com.sumadhura.sumadhuragateway.dto.Device;
import com.sumadhura.sumadhuragateway.dto.Flat;
import com.sumadhura.sumadhuragateway.dto.Floor;
import com.sumadhura.sumadhuragateway.dto.Login;
import com.sumadhura.sumadhuragateway.dto.OTP;
import com.sumadhura.sumadhuragateway.dto.Registration;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.dto.Site;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.HttpStatus;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * LoginController class provides login specific services.
 * 
 * @author Venkat_Koniki
 * @since 25.03.2019
 * @time 12:12PM
 */

@RestController("CustomerLoginController")
@RequestMapping(value = "/customerservice/login")
public class LoginRestController extends SessionValidate {

	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("CustomerSessionHandlerImpl")
	private SessionHandler customerSessionHandlerImpl;

	private final static Logger LOGGER = Logger.getLogger(LoginRestController.class);

	@RequestMapping(value = "/authenticate.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result authenticate(@RequestBody Login login) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			JsonParseException, JsonMappingException, IOException {

		LOGGER.info("******* The control inside of the authenticate service in  LoginController controller ******"
				+ login.getDeviceId());

		Login loginObj = (Login) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.aunticate.getUrl(), login,
				Login.class);
		CustomerSession customer = new CustomerSession();

		LOGGER.debug("**** The Response login object is ****"+loginObj);
		
		if (loginObj.getResponseCode().equals(HttpStatus.authenticationError.getResponceCode())
				|| loginObj.getResponseCode().equals(HttpStatus.insufficientInput.getResponceCode())
				|| loginObj.getResponseCode().equals(HttpStatus.exceptionRaisedInFlow.getResponceCode())
				|| loginObj.getResponseCode().equals(HttpStatus.customerHaveNoFlat.getResponceCode())) {
			return loginObj;
		} else {
			/* if customer is logging second time into the app. */
			if (login.getAppRegId() != null && login.getFlatId() != null) {
				
				CustomerTicketRequest  customerTicketRequest = new CustomerTicketRequest();
				customerTicketRequest.setFlatNo(login.getFlatId()!=null?login.getFlatId():0l);
				CustomerPropertyDetails propertyDetails = (CustomerPropertyDetails) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.customerSpecificPropertyInfo.getUrl(), customerTicketRequest,
						CustomerPropertyDetails.class);
				LOGGER.info("**** The CustomerPropertyDetails Object is ****"+propertyDetails);
				String customerEmail = propertyDetails.getCustomerEmail();
				String customerName = propertyDetails.getCustomerName();
				customer.setName(customerName);
				customer.setEmail(customerEmail);
				
				Flat flat = new Flat();
				Device device = new Device();
				Floor floor = new Floor();
				Block block = new Block();
				Site site = new Site();
				flat.setFlatNo(loginObj.getFlats().get(0).getFlatNo());

				customer.setAppRegId(login.getAppRegId());
				customer.setCustomerId(login.getCustId());
				customer.setUsername(login.getUsername());
				customer.setPassword(login.getPassword());
				//customer.setEmail(login.);
				device.setUuid(login.getUuid());
				device.setDeviceToken(login.getDeviceId());
				device.setSerialNo(login.getSerialNo());

				customer.setDevice(device);
				flat.setFlatId(login.getFlatId());
				site.setId(propertyDetails.getSiteId());
				block.setSite(site);
				floor.setBlock(block);
				flat.setFloor(floor);
				customer.setFlat(flat);
				customer.setFlatBookingId(propertyDetails.getFlatBookingId());

				// creating sessionkey.
				loginObj.setSessionKey(createSession(customer));

				/*loginObj.setResponseCode(HttpStatus.success.getResponceCode());
				loginObj.setStatus(HttpStatus.success.getStatus());*/

				/*
				 * logger.info("**** The session validation info is ******"+sessionUtils.validateSession(sessionUtils.decryptSessionKey(login.getSessionKey())));
				 * logger.info("********* session object **********"+customerSessionHandlerImpl.getSession(sessionUtils.decryptSessionKey(login.getSessionKey())));
				 * customerSessionHandlerImpl.deleteSession(sessionUtils.decryptSessionKey(login.getSessionKey()));
				 * logger.info("********* session object **********"+customerSessionHandlerImpl. getSession(sessionUtils.decryptSessionKey(login.getSessionKey())));
				 */
			} else {
				/* if customer contains only one flat. */
				if (loginObj.getFlatIds().size() == 1) {
					CustomerTicketRequest  customerTicketRequest = new CustomerTicketRequest();
					customerTicketRequest.setFlatNo(loginObj.getFlatIds().get(0)!=null?loginObj.getFlatIds().get(0):0l);
					CustomerPropertyDetails propertyDetails = (CustomerPropertyDetails) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.customerSpecificPropertyInfo.getUrl(), customerTicketRequest,
							CustomerPropertyDetails.class);
					
					String customerEmail = propertyDetails.getCustomerEmail();
					String customerName = propertyDetails.getCustomerName();
					customer.setName(customerName);
					customer.setEmail(customerEmail);	
					LOGGER.info("**** The CustomerPropertyDetails Object is ****"+propertyDetails);
					//CustomerSession customer = new CustomerSession();
					Flat flat = new Flat();
					Floor floor = new Floor();
					Block block = new Block();
					Site site = new Site();
					Device device = new Device();

					customer.setAppRegId(loginObj.getAppRegId());
					customer.setCustomerId(loginObj.getCustId());
					customer.setUsername(login.getUsername());
					customer.setPassword(login.getPassword());

					device.setUuid(login.getUuid());
					device.setDeviceToken(login.getDeviceId());
					device.setSerialNo(login.getSerialNo());

					customer.setDevice(device);
					flat.setFlatId(loginObj.getFlatIds().get(0));
					flat.setFlatNo(loginObj.getFlats().get(0).getFlatNo());
					site.setId(propertyDetails.getSiteId());
					block.setSite(site);
					floor.setBlock(block);
					flat.setFloor(floor);
					customer.setFlat(flat);
					customer.setFlatBookingId(propertyDetails.getFlatBookingId());
					LOGGER.debug("*** The Session object is ****"+customer);

					// creating sessionkey.
					loginObj.setSessionKey(createSession(customer));

					/*loginObj.setResponseCode(HttpStatus.success.getResponceCode());
					loginObj.setStatus(HttpStatus.success.getStatus());*/

				}
			}
			return loginObj;
		}

	}

	@RequestMapping(value = "/flatSpecificSession.spring", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Result FlatSpecificSession(@RequestBody Login login) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InSufficeientInputException {
		
		if(Util.isNotEmptyObject(login.getDeviceId()) && Util.isNotEmptyObject(login.getUsername()) && Util.isNotEmptyObject(login.getPassword()) 
			&& Util.isNotEmptyObject(login.getSerialNo()) && Util.isNotEmptyObject(login.getFlatId()) && Util.isNotEmptyObject(login.getCustId()) 
			&& Util.isNotEmptyObject(login.getUuid()) && Util.isNotEmptyObject(login.getAppRegId())){
			LOGGER.info("**** The control is inside the FlatSpecificSession method in LoginController ******"
					+ login.getDeviceId());
	     
			CustomerSession customer = new CustomerSession();
			Flat flat = new Flat();
			Floor floor = new Floor();
			Block block = new Block();
			Site site = new Site();
			Device device = new Device();
			
			CustomerTicketRequest  customerTicketRequest = new CustomerTicketRequest();
			customerTicketRequest.setFlatNo(login.getFlatId()!=null?login.getFlatId():0l);
			CustomerPropertyDetails propertyDetails = (CustomerPropertyDetails) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.customerSpecificPropertyInfo.getUrl(), customerTicketRequest,
					CustomerPropertyDetails.class);
			
			customer.setAppRegId(login.getAppRegId());
			customer.setCustomerId(login.getCustId());
			customer.setUsername(login.getUsername());
			customer.setPassword(login.getPassword());

			device.setUuid(login.getUuid());
			device.setDeviceToken(login.getDeviceId());
			device.setSerialNo(login.getSerialNo());

			customer.setDevice(device);
			flat.setFlatId(login.getFlatId());
			site.setId(propertyDetails.getSiteId());
			block.setSite(site);
			floor.setBlock(block);
			flat.setFloor(floor);
			customer.setFlat(flat);
			customer.setFlatBookingId(propertyDetails.getFlatBookingId());
			// creating sessionkey.
			login.setSessionKey(createSession(customer));

			login.setResponseCode(HttpStatus.success.getResponceCode());
			login.setStatus(HttpStatus.success.getStatus());
		}
		else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		return login;
	}
	private String createSession(CustomerSession customerSession) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		LOGGER.info("**** The control is inside the create session method in LoginController ******"
				+ customerSession.getDevice().getDeviceToken());
		String sessionKey = sessionUtils.generateSessionKey(customerSession, sessionUtils.generateModeOf());
		customerSessionHandlerImpl.createSession(sessionKey, customerSession);
		return sessionUtils.encryptSessionKey(sessionKey);
	}

	@RequestMapping("/changeMpin.spring")
	public Result changeMpin(@RequestBody ChangePasswordReq changeMpinReq) throws SessionTimeoutException, InSufficeientInputException {
		String cacheKey = changeMpinReq.getSessionKey();
		CustomerSession customerSession = customerSessionValidate(cacheKey,changeMpinReq.getDeviceId());
		LOGGER.debug("*** The EmployeeSession object ***"+customerSession);
		changeMpinReq.setCustId(customerSession.getCustomerId());
		Result result = (Result) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.CHANGE_M_PIN.getUrl(), changeMpinReq, Result.class);
		return result;
	}
	
	@RequestMapping("/forgotMpin.spring")
	public Result forgotMpin(@RequestBody Registration changeMpinReq) throws SessionTimeoutException, InSufficeientInputException {
		//String cacheKey = changeMpinReq.getSessionKey();
		//CustomerSession customerSession = customerSessionValidate(cacheKey,changeMpinReq.getDeviceId());
		//LOGGER.debug("*** The EmployeeSession object ***"+customerSession);
		//changeMpinReq.setCustId(customerSession.getCustomerId());
		Registration result = (Registration) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.FORGOT_M_PIN.getUrl(), changeMpinReq, Registration.class);
		if (result.getResponseCode().equals(HttpStatus.otpSendSuccessfully.getResponceCode())) {
			LOGGER.info("**** create session with device Id and put otp there *****");
			result.setDeviceToken(changeMpinReq.getDeviceId());
			result.setSessionKey(result.getDeviceToken());
			OTP otp = new OTP();
			otp.setOtp(result.getOtp());
			otp.setActive(Boolean.TRUE);
			otp.setCreatedDate(new Date());
			customerSessionHandlerImpl.createOtpSession(result.getDeviceToken(), otp);
			return result;
		}
		return result;
	}
	
	@RequestMapping(value="/logout.spring", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result logOUt(@RequestBody Login login) throws SessionTimeoutException, InSufficeientInputException{
		LOGGER.info("******* The control inside of the logOut service in  LoginController controller ******"+login);
		String cacheKey = login.getSessionKey();
		customerSessionValidate(cacheKey,login.getDeviceId());
		try {
			cacheKey = sessionUtils.decryptSessionKey(cacheKey);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given Or Invalid Key");
			throw new InSufficeientInputException(errors);
		}
		customerSessionHandlerImpl.deleteSession(cacheKey);
		Result result = new Result();
		result.setResponseCode(HttpStatus.success.getResponceCode());
		result.setStatus(HttpStatus.success.getStatus());
		result.setDescription("Successfully Logout");
		return result;
		
	}
	
	@RequestMapping(value="/getIosCustomerRegistrationStatus.spring", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getIosCustomerRegistrationStatus(@RequestBody Login login) {
		LOGGER.info("*** The control is inside of the getIosCustomerRegistrationStatus service in  LoginController controller ***"+login);
		/* if UUID is empty then return the response as Registered NonCustomer */
		if(Util.isEmptyObject(login) || Util.isEmptyObject(login.getUuid())) {
			Result result = new Login();
			result.setResponseCode(HttpStatus.RegisteredNonCustomer.getResponceCode());
			result.setDescription(HttpStatus.RegisteredNonCustomer.getStatus());
			return result;
		}
		return  (Login) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_IOS_CUSTOMERS_REGISTRATION_STATUS.getUrl(), login, Login.class);

	}
	
	@RequestMapping(value="/getAppMenuDetails.spring", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getAppMenuDetails(@RequestBody Login login) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("*** The control is inside of the getAppMenuDetails in LoginRestController ***"+login);
		if(Util.isNotEmptyObject(login.getSessionKey()) && Util.isNotEmptyObject(login.getFlatId())) {
			String cacheKey = login.getSessionKey();
			customerSessionValidate(cacheKey,login.getDeviceId());
		}
		return (Result)RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_APP_MEN_DETAILS.getUrl(), login, Result.class);
	}
	
	@RequestMapping(value="/getAppMenuList.spring", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getAppMenuListDetails(@RequestBody Login login) throws SessionTimeoutException, InSufficeientInputException {
		LOGGER.info("*** The control is inside of the getAppMenuDetails in LoginRestController ***"+login);
		if(Util.isNotEmptyObject(login.getSessionKey()) && Util.isNotEmptyObject(login.getFlatId())) {
			String cacheKey = login.getSessionKey();
			customerSessionValidate(cacheKey,login.getDeviceId());
		}
		return (Result)RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_APP_MEN_LIST_DETAILS.getUrl(), login, Result.class);
	}
	
	@RequestMapping(value="/getautoupdateversion.spring",  method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Result getupdateversion(@RequestBody Login login) throws InSufficeientInputException {
		LOGGER.info("*** The control is inside of the getAppMenuDetails in LoginRestController ***"+login);
		Result result = new Login();
		final StringBuffer xmlData = new StringBuffer();
		String bufferLocationdata="";
		int version= (int) 5.3;
		String name="Sumadhura";
		xmlData.append("<xml>");
		xmlData.append("<update>");
		xmlData.append("<version>" +version + "</version>");
		xmlData.append("<name>" + name + "</name>");
		xmlData.append("<url>" + "sumadhur.com" + "</url>");
		xmlData.append("</update>");
		xmlData.append("</xml>");
		bufferLocationdata=xmlData.toString();
		LOGGER.info("xml data"+bufferLocationdata);
		result.setDescription(bufferLocationdata);
		return result;
	}
	
	

}