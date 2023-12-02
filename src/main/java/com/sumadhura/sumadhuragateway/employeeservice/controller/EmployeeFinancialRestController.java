package com.sumadhura.sumadhuragateway.employeeservice.controller;

import static com.sumadhura.sumadhuragateway.util.Util.isNotEmptyObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sumadhura.sumadhuragateway.dto.Customer;
import com.sumadhura.sumadhuragateway.dto.EmployeeFinancialMultipleTRNRequest;
import com.sumadhura.sumadhuragateway.dto.EmployeeFinancialRequest;
import com.sumadhura.sumadhuragateway.dto.EmployeeFinancialResponse;
import com.sumadhura.sumadhuragateway.dto.EmployeeFinancialTransactionRequest;
import com.sumadhura.sumadhuragateway.dto.Financial;
import com.sumadhura.sumadhuragateway.dto.FinancialProjectMileStoneRequest;
import com.sumadhura.sumadhuragateway.dto.FinancialProjectMileStoneResponse;
import com.sumadhura.sumadhuragateway.dto.MileStone;
import com.sumadhura.sumadhuragateway.dto.PaymentDetailsWrapper;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.Department;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;
import com.sumadhura.sumadhuragateway.employeeservice.enums.Module;
import com.sumadhura.sumadhuragateway.employeeservice.enums.SubModule;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.HttpStatus;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionHandler;
import com.sumadhura.sumadhuragateway.util.SessionUtils;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;

/**
 * @author @NIKET CH@V@N
 * @description this class for financial operation , for demand note to client for flat payment 
 * @since 11-01-2020
 * @time 10:40 AM
 */

@RestController
@RequestMapping(value = "/employeeservice/financial")
public class EmployeeFinancialRestController extends SessionValidate {

	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;

	@Autowired
	@Qualifier("EmployeeSessionHandlerImpl")
	private SessionHandler employeeSessionHandlerImpl;
	
	private final  Logger log = Logger.getLogger(EmployeeFinancialRestController.class);
	
	private RoundingMode roundingMode = RoundingMode.HALF_UP;
	private int roundingModeSize = 2;

	@Autowired(required=true)
	private static Map<Long,Object> holdApprovingTransactions = new HashMap<>();

	/*public String convertUstoInFormat(String currency) {
		log.info("***** Control inside the CurrencyUtil.convertUstoInFormat() *****");
		Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
		return format.format(new BigDecimal(currency)).substring(1);
	}

	public String getTheAmountWithCommas(Double amountToConvertInWords, int roundingModeSize, RoundingMode roundingMode) {
		return convertUstoInFormat(BigDecimal.valueOf(amountToConvertInWords).setScale(roundingModeSize, roundingMode).toString());
	}*/

	
	/**
	 * @description this controller will return all the related sets if site for demand notes
	 * @param siteList
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InSufficeientInputException
	 * @throws SessionTimeoutException
	 */
	@RequestMapping(value = "/getMileStoneSetsDtls.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getMileStoneSetsDtls(@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {	
		//log.info("***** Control inside FinancialRestController.getMileStoneSetsDtls()  *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(employeeFinancialRequest.getSessionKey())) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				//employeeFinancialRequest.setCreatedBy(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				//log.info(" ***** EmployeeFinancialRestController.getMileStoneSetsDtls() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getMileStoneSetsDtls.getUrl(), employeeFinancialRequest, Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}

	@RequestMapping(value = "/loadDemandNoteFormats.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result loadDemandNoteFormats(@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {	
		//log.info("***** Control inside FinancialRestController.getMileStoneSetsDtls()  *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(employeeFinancialRequest.getSessionKey())) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession)employeeSessionHandlerImpl.getSession(sessionKey);
				employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.loadDemandNoteFormats.getUrl(), employeeFinancialRequest, Result.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}

	
	/**
	 * @description this controller for getting demand notes milestone details like milestone name, date of milestone
	 * @param employeeFinancialRequest
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InSufficeientInputException
	 * @throws SessionTimeoutException
	 */
	@RequestMapping(value = "/getMileStoneDemandNoteDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getMileStoneDetails(@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {	
		//log.info("***** Control inside FinancialRestController.getMileStoneDetails() *****");
		Result result = new Result();
		String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialRequest.getSessionKey());
		boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
		if (isSessionValid) {
			EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
			employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
			//log.info(" ***** EmployeeFinancialRestController.getMileStoneDetails() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
			result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getMileStoneDemandNoteDetails.getUrl(), employeeFinancialRequest, Result.class);
		}else {
			throw new SessionTimeoutException("Your Session has been Timed Out.");
		}	
		return result;
	}
	
	@RequestMapping(value = "/getMileStoneDetailsForTDS.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	 public Result getMileStoneDetailsForTDS(@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, SessionTimeoutException, InvalidAlgorithmParameterException {
		//log.info(" ***** EmployeeFinancialRestService.getMileStoneDetailsForTDS() ***** ");
		Result result = new Result();
		if(Util.isNotEmptyObject(employeeFinancialRequest.getSiteId()) && Util.isNotEmptyObject(employeeFinancialRequest.getBlockIds()) && Util.isNotEmptyObject(employeeFinancialRequest.getFlatIds())){
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				//log.info(" ***** EmployeeFinancialRestController.getMileStoneDetailsForTDS() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getMileStoneDetailsForTDS.getUrl(), employeeFinancialRequest, Result.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}	
		}else {
			List<String> errorMsgs = new ArrayList<String>();
			errorMsgs.add("The Insufficient Input is given for requested service.");
			throw new InSufficeientInputException(errorMsgs);
		}
		return result;
	}
	
	@RequestMapping(value = "/updateMileStoneTDSDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	 public Result updateMileStoneTDSDetails(@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, SessionTimeoutException, InvalidAlgorithmParameterException {
		//log.info(" ***** EmployeeFinancialRestService.updateMileStoneTDSDetails() ***** ");
		Result result = new Result();
		if(Util.isNotEmptyObject(employeeFinancialRequest.getSiteId()) && Util.isNotEmptyObject(employeeFinancialRequest.getFinancialProjectMileStoneRequests())){
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				//log.info(" ***** EmployeeFinancialRestController.updateMileStoneTDSDetails() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.updateMileStoneTDSDetails.getUrl(), employeeFinancialRequest, Result.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}	
		}else {
			List<String> errorMsgs = new ArrayList<String>();
			errorMsgs.add("The Insufficient Input is given for requested service.");
			throw new InSufficeientInputException(errorMsgs);
		}
		return result;
	}

	@RequestMapping(value = "/getDemandNoteBlockSelectionDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getMileStoneWiseBlockDetails(@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {	
		//log.info("***** Control inside FinancialRestController.getMileStoneDetails() *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(employeeFinancialRequest.getSessionKey())&&Util.isNotEmptyObject(employeeFinancialRequest.getSiteId())
				&&Util.isNotEmptyObject(employeeFinancialRequest.getFinMilestoneClassifidesId())) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				//log.info(" ***** EmployeeFinancialRestController.getMileStoneWiseBlockDetails() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getDemandNoteBlockSelectionDetails.getUrl(), employeeFinancialRequest, Result.class);
				//RestTemplate restTemplate=new RestTemplate();
				//Result result1 = (Result) restTemplate.postForObject(EmployeeServiceUrls.getMileStoneSetsDtls.getUrl(),employeeFinancialRequest, Result.class);
				//System.out.println(result1);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}
	
	@RequestMapping(value = "/generateDemandNote.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result generateDemandNote(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
	//log.info("EmployeeFinancialRestController.generateDemandNote()");
		Result result = new Result();
		if (Util.isNotEmptyObject(employeeFinancialRequest.getSessionKey())&&Util.isNotEmptyObject(employeeFinancialRequest.getFinMilestoneClassifidesId())) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				int portNumber = request.getLocalPort();
				employeeFinancialRequest.setPortNumber((long)portNumber);
				employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				//log.info(" ***** EmployeeFinancialRestController.generateDemandNote() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.generateDemandNote.getUrl(), employeeFinancialRequest, Result.class);
				//RestTemplate restTemplate=new RestTemplate();
				//Result result1 = (Result) restTemplate.postForObject(EmployeeServiceUrls.getMileStoneSetsDtls.getUrl(),employeeFinancialRequest, Result.class);
				//System.out.println(result1);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}

	/**
	 * Edit Milestone does't send the nofification to the customer 
	 */
	@RequestMapping(value = "/editDemandNoteDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result updateDemandNoteDetails(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
	//log.info("EmployeeFinancialRestController.generateDemandNote()");
		Result result = new Result();
		if (Util.isNotEmptyObject(employeeFinancialRequest.getSessionKey()) && Util.isNotEmptyObject(employeeFinancialRequest.getFinMilestoneClassifidesId())) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				int portNumber = request.getLocalPort();
				employeeFinancialRequest.setPortNumber((long)portNumber);
				employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				//log.info(" ***** EmployeeFinancialRestController.generateDemandNote() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
				 result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.editDemandNoteDetails.getUrl(), employeeFinancialRequest, Result.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}

	
	@RequestMapping(value = "/uploadDemandNoteMilestones.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result uploadMilestone(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		log.info("***** Control inside the EmployeeFinancialRestController.uploadMilestone() *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(employeeFinancialRequest.getSessionKey())) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				employeeFinancialRequest.setPortNumber((long) request.getLocalPort());
				result = (Result) RestApiUtility.sendJSONPostRequest(
						EmployeeServiceUrls.uploadDemandNoteMilestones.getUrl(), employeeFinancialRequest,
						Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			InSufficeientInputException();
		}
		return result;
	}

	/**
	 * Uploading the data and generating the receipt and giving generated files in ZIP File
	 * @param employeeFinancialRequest
	 * @param request
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InSufficeientInputException
	 * @throws SessionTimeoutException
	 */
	@RequestMapping(value = "/uploadFinancialTransaction.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result uploadFinancialTransaction(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		log.info("***** Control inside the EmployeeFinancialRestController.uploadMilestone() *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(employeeFinancialRequest.getSessionKey())) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				employeeFinancialRequest.setPortNumber((long) request.getLocalPort());
				result = (Result) RestApiUtility.sendJSONPostRequest(
						EmployeeServiceUrls.uploadFinancialTransaction.getUrl(), employeeFinancialRequest,
						Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			InSufficeientInputException();
		}
		return result;
	}

	@RequestMapping(value = "/saveFinancialTransactionReceiptRequest.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result saveFinancialTransactionRequest(@RequestBody EmployeeFinancialTransactionRequest  financialTransactionRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
	//log.info(" ***** EmployeeFinancialRestController.saveFinancialTransactionRequest() *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(financialTransactionRequest.getSessionKey())) {
			
			String sessionKey = sessionUtils.decryptSessionKey(financialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				financialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				financialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				financialTransactionRequest.setPortNumber((long)request.getLocalPort());
				
				if(holdApprovingTransactions.containsKey(financialTransactionRequest.getBookingFormId())) {
					//throw new InSufficeientInputException(Arrays.asList("Found duplicate request for transaction, failed to process transaction please try again..."));
					throw new InSufficeientInputException(Arrays.asList("Oops !!! There was a improper request found. Please check transaction in transaction status."));
				}
				holdApprovingTransactions.put(financialTransactionRequest.getBookingFormId(), financialTransactionRequest.getBookingFormId());

				try {
					//log.info(" ***** EmployeeFinancialRestController.saveFinancialTransactionRequest() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
					result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.saveFinancialTransactionReceiptRequest.getUrl(), financialTransactionRequest, Result.class);
					holdApprovingTransactions.remove(financialTransactionRequest.getBookingFormId());
				} catch (Exception ex) {
					try {
						holdApprovingTransactions.remove(financialTransactionRequest.getBookingFormId());
					}catch(Exception ex1) {
						log.info(ex1.getMessage(), ex1);	
					}
					ex.printStackTrace();
					log.info(ex.getMessage(), ex);
				}		
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			InSufficeientInputException();
		}
		return result;
	}

	@RequestMapping(value = "/saveInterestWaiver.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result saveInterestWaiver(@RequestBody  EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
	//log.info(" ***** EmployeeFinancialRestController.saveFinancialTransactionRequest() *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(employeeFinancialRequest.getSessionKey())) {
			
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				employeeFinancialRequest.setPortNumber((long)request.getLocalPort());
				
				if(holdApprovingTransactions.containsKey(employeeFinancialRequest.getBookingFormId())) {
					//throw new InSufficeientInputException(Arrays.asList("Found duplicate request for transaction, failed to process transaction please try again..."));
					throw new InSufficeientInputException(Arrays.asList("Oops !!! There was a improper request found. Please check transaction in transaction status."));
				}
				holdApprovingTransactions.put(employeeFinancialRequest.getBookingFormId(), employeeFinancialRequest.getBookingFormId());

				try {
					//log.info(" ***** EmployeeFinancialRestController.saveFinancialTransactionRequest() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
					result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.saveInterestWaiver.getUrl(), employeeFinancialRequest, Result.class);
					holdApprovingTransactions.remove(employeeFinancialRequest.getBookingFormId());
				} catch (Exception ex) {
					try {
						holdApprovingTransactions.remove(employeeFinancialRequest.getBookingFormId());
					}catch(Exception ex1) {
						log.info(ex1.getMessage(), ex1);	
					}
					ex.printStackTrace();
					log.info(ex.getMessage(), ex);
				}		
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			InSufficeientInputException();
		}
		return result;
	}

	
	@RequestMapping(value = "/editFinancialTransaction.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result editFinancialTransaction(@RequestBody EmployeeFinancialTransactionRequest  financialTransactionRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
	//log.info("***** Control inside the EmployeeFinancialRestController.deleteFinancialTransaction() *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(financialTransactionRequest.getSessionKey()) 
				&& Util.isNotEmptyObject(financialTransactionRequest.getSiteId())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionTypeId())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionModeId())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionTypeName())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionModeName())
				)  {
			String sessionKey = sessionUtils.decryptSessionKey(financialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				financialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				financialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				financialTransactionRequest.setPortNumber((long)request.getLocalPort());
				
				if(holdApprovingTransactions.containsKey(financialTransactionRequest.getBookingFormId())) {
					//throw new InSufficeientInputException(Arrays.asList("Found duplicate request for transaction, failed to process transaction please try again..."));
					throw new InSufficeientInputException(Arrays.asList("Oops !!! There was a improper request found, Another transaction processing for current flat, Please retry."));
				}
				holdApprovingTransactions.put(financialTransactionRequest.getBookingFormId(), financialTransactionRequest.getBookingFormId());
	
				try {	
					result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.editFinancialTransaction.getUrl(), financialTransactionRequest, Result.class);
					holdApprovingTransactions.remove(financialTransactionRequest.getBookingFormId());
				} catch (Exception ex) {
					try {
						holdApprovingTransactions.remove(financialTransactionRequest.getBookingFormId());
					}catch(Exception ex1) {
						log.info(ex1.getMessage(), ex1);	
					}
					ex.printStackTrace();
					log.info(ex.getMessage(), ex);
				}			
			
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			InSufficeientInputException();
		}
		return result;
	}

	
	@RequestMapping(value = "/deleteFinancialTransaction.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result deleteFinancialTransaction(@RequestBody EmployeeFinancialTransactionRequest  financialTransactionRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
	//log.info("***** Control inside the EmployeeFinancialRestController.deleteFinancialTransaction() *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(financialTransactionRequest.getSessionKey()) 
				&& Util.isNotEmptyObject(financialTransactionRequest.getSiteId())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionEntryId())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionSetOffEntryId())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionTypeId())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionModeId())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionTypeName())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionModeName())
				)  {
			String sessionKey = sessionUtils.decryptSessionKey(financialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				financialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				financialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				financialTransactionRequest.setPortNumber((long)request.getLocalPort());
				
				if(holdApprovingTransactions.containsKey(financialTransactionRequest.getBookingFormId())) {
					//throw new InSufficeientInputException(Arrays.asList("Found duplicate request for transaction, failed to process transaction please try again..."));
					throw new InSufficeientInputException(Arrays.asList("Oops !!! There was a improper request found, Another transaction processing for current flat, Please retry."));
				}
				holdApprovingTransactions.put(financialTransactionRequest.getBookingFormId(), financialTransactionRequest.getBookingFormId());
	
				try {
					result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.deleteFinancialTransaction.getUrl(), financialTransactionRequest, Result.class);
					holdApprovingTransactions.remove(financialTransactionRequest.getBookingFormId());
				} catch (Exception ex) {
					try {
						holdApprovingTransactions.remove(financialTransactionRequest.getBookingFormId());
					} catch(Exception ex1) {
						log.info(ex1.getMessage(), ex1);	
					}
					ex.printStackTrace();
					log.info(ex.getMessage(), ex);
				}			
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			InSufficeientInputException();
		}
		return result;
	}
	
	@RequestMapping(value = "/getCustomerLedger.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public @JsonInclude(Include.NON_NULL) Result getUnclearedChequeDetails(@RequestBody EmployeeFinancialRequest  financialTransactionRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
	//log.info(" ***** EmployeeFinancialRestController.getUnclearedChequeDetails() *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(financialTransactionRequest.getSessionKey()) && Util.isNotEmptyObject(financialTransactionRequest.getSiteIds())) {
			String sessionKey = sessionUtils.decryptSessionKey(financialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				financialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				financialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				financialTransactionRequest.setPortNumber((long)request.getLocalPort());
				//log.info(" ***** EmployeeFinancialRestController.saveFinancialTransactionRequest() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getCustomerLedger.getUrl(), financialTransactionRequest, Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			InSufficeientInputException();
		}
		return result;
	}
	
	@RequestMapping(value = "/loadRequestedData.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result loadRequestedData(@RequestBody EmployeeFinancialRequest  financialTransactionRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		//log.info("***** Control inside the EmployeeFinancialRestController.loadRequestedData() *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(financialTransactionRequest.getSessionKey())
				&& Util.isNotEmptyObject(financialTransactionRequest.getActionUrl())) {
			if(financialTransactionRequest.getActionUrl().equals("Load Interest Data")) {
				if (Util.isEmptyObject(financialTransactionRequest.getSiteIds())) {
					InSufficeientInputException();
				}
			}
			String sessionKey = sessionUtils.decryptSessionKey(financialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				financialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				financialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				financialTransactionRequest.setPortNumber((long)request.getLocalPort());
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.loadRequestedData.getUrl(), financialTransactionRequest, Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			InSufficeientInputException();
		}
		return result;
	}

	private void InSufficeientInputException() throws InSufficeientInputException {
		throw new InSufficeientInputException(Arrays.asList("Insufficeint Input is Given."));
	}
	
	@RequestMapping(value = "/updateInterestRates.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result updateGST(@RequestBody EmployeeFinancialRequest  financialTransactionRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		//log.info("***** Control inside the EmployeeFinancialRestController.updateGST() *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(financialTransactionRequest.getSessionKey())
				&& Util.isNotEmptyObject(financialTransactionRequest.getSiteIds())
				&& Util.isNotEmptyObject(financialTransactionRequest.getFinancialGstDetailsRequests())) {
			String sessionKey = sessionUtils.decryptSessionKey(financialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				financialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				financialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				financialTransactionRequest.setPortNumber((long)request.getLocalPort());
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.updateInterestRates.getUrl(), financialTransactionRequest, Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			InSufficeientInputException();
		}
		return result;
	}

	@RequestMapping(value = "/getCustomerLedgerDetails.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getCustomerLedgerDetails(@RequestBody EmployeeFinancialRequest  financialTransactionRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
		//log.info("***** Control inside the EmployeeFinancialRestController.getCustomerLedgerDetails() *****");
		Result result = new Result();
		if (Util.isNotEmptyObject(financialTransactionRequest.getSessionKey())
				&& Util.isNotEmptyObject(financialTransactionRequest.getSiteIds())) {
			String sessionKey = sessionUtils.decryptSessionKey(financialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				financialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				financialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				financialTransactionRequest.setPortNumber((long)request.getLocalPort());
				//log.info(" ***** EmployeeFinancialRestController.getCustomerLedgerDetails() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getCustomerLedgerDetails.getUrl(), financialTransactionRequest, Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			InSufficeientInputException();
		}
		return result;
	}
	
	@RequestMapping(value = "/doOnlinePaymentAnonymousEntry.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result doOnlinePaymentAnonymousEntry(@RequestBody EmployeeFinancialTransactionRequest  financialTransactionRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
	//log.info(" ***** EmployeeFinancialRestController.doOnlinePaymentAnonymousEntry() ***** ");
		Result result = new Result();
		if (Util.isNotEmptyObject(financialTransactionRequest.getSessionKey())) {
			String sessionKey = sessionUtils.decryptSessionKey(financialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				financialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				financialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				int portNumber = request.getLocalPort();
				financialTransactionRequest.setPortNumber((long)portNumber);
				//log.info(" ***** EmployeeFinancialRestController.doOnlinePaymentAnonymousEntry() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.doOnlinePaymentAnonymousEntry.getUrl(), financialTransactionRequest, Result.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}
	
	@RequestMapping(value = "/updateOnlinePaymentAnonymousEntry.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result updateOnlinePaymentAnonymousEntry(@RequestBody EmployeeFinancialTransactionRequest  financialTransactionRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
	//log.info(" ***** EmployeeFinancialRestController.doOnlinePaymentAnonymousEntry() ***** ");
		Result result = new Result();
		if (Util.isNotEmptyObject(financialTransactionRequest.getSessionKey())) {
			String sessionKey = sessionUtils.decryptSessionKey(financialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				financialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				financialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				int portNumber = request.getLocalPort();
				financialTransactionRequest.setPortNumber((long)portNumber);
				//log.info(" ***** EmployeeFinancialRestController.doOnlinePaymentAnonymousEntry() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.updateOnlinePaymentAnonymousEntry.getUrl(), financialTransactionRequest, Result.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}
	
	@RequestMapping(value = "/doModificationChargesEntry.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result doModificationChargesEntry(@RequestBody EmployeeFinancialTransactionRequest  financialTransactionRequest,HttpServletRequest request) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InSufficeientInputException, SessionTimeoutException {
	//log.info(" ***** EmployeeFinancialRestController.doModificationChargesEntry() ***** ");
		Result result = new Result();
		if (Util.isNotEmptyObject(financialTransactionRequest.getSessionKey())) {
			String sessionKey = sessionUtils.decryptSessionKey(financialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				financialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				financialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				//log.debug("financialTransactionRequest"+financialTransactionRequest);
				int portNumber = request.getLocalPort();
				financialTransactionRequest.setPortNumber((long)portNumber);
				//log.info(" ***** EmployeeFinancialRestController.doModificationChargesEntry() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.doModificationChargesEntry.getUrl(), financialTransactionRequest, Result.class);
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}

	private static Map<String,Object> holdApprovingInvoices = new HashMap<>();	
	@RequestMapping(value = "/approveModificationChargesEntry.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result approveModificationChargesEntry(@RequestBody EmployeeFinancialTransactionRequest  financialTransactionRequest,HttpServletRequest request) throws  Exception {
		log.info(" ***** EmployeeFinancialRestController.approveModificationChargesEntry() ***** ");
		Result result = new Result();
		if (Util.isNotEmptyObject(financialTransactionRequest.getSessionKey())) {
			String sessionKey = sessionUtils.decryptSessionKey(financialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				financialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
				financialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
				//log.debug("financialTransactionRequest"+financialTransactionRequest);
				int portNumber = request.getLocalPort();
				financialTransactionRequest.setPortNumber((long)portNumber);
				//log.info(" ***** EmployeeFinancialRestController.doModificationChargesEntry() session is valid ***** "+employeeSession.getLoginResponse().getEmployee());
				String inv = financialTransactionRequest.getFinBookingFormModiCostId().toString()+financialTransactionRequest.getFinsetOffAppLevelId().toString();
				if(holdApprovingInvoices.containsKey(inv)) {
					//throw new InSufficeientInputException(Arrays.asList("Found duplicate request for transaction, failed to process transaction please try again..."));
					throw new InSufficeientInputException(Arrays.asList("Oops !!! There was a improper request found. Please check transaction in transaction status."));
				}
				holdApprovingInvoices.put(inv, financialTransactionRequest.getBookingFormId());
				try {
					result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.approveModificationChargesEntry.getUrl(), financialTransactionRequest, Result.class);
					holdApprovingInvoices.remove(inv);	
				}catch(Exception ex) {
					ex.printStackTrace();
					holdApprovingInvoices.remove(inv);
				}
			}else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
		return result;
	}

	
	@RequestMapping(value ="/incompletedEmpSitesList.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getAllIncompletedEmpSitesList (@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getAllIncompletedEmpSitesList in EmployeeFinancialRestController ***");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		Result result = new Result();
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		List<Department> departments = employeeSession.getLoginResponse().getDepartments();
		/* Employee Accessed Site List */
		List<Long> siteIds = new ArrayList<Long>();
		siteIds = Util.getUserAccessSiteList(departments, Module.MILESTONES.getName(), SubModule.CREATE_MILESTONES.getName());
		/* No Access when Employee have no accessed sites */
		if(Util.isEmptyObject(siteIds)) {
			result.setResponseCode(HttpStatus.failure.getResponceCode());
			result.setDescription("No Access to this Module and Sub_Module");
		}
		/* Employee Accessed Sites */
		employeeFinancialRequest.setSiteIds(siteIds);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getAllIncompletedEmpSitesListForMileStone.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/incompleteBlocksListForMileStone.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getAllActiveIncompleteBlocksListForMileStone (@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getAllActiveIncompleteBlocksListForMileStone in EmployeeFinancialRestController ***");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		@SuppressWarnings("unused")
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getAllIncompletBlocksListForMileStone.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/aliasNamesForMileStone.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getAllAliasNamesForMileStone (@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getAllAliasNamesForMileStone in EmployeeFinancialRestController ***");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		@SuppressWarnings("unused")
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getAllAliasNamesForMileStone.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/mileStonePercentages.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getMileStonePercentages (@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getMileStonePercentages in EmployeeFinancialRestController ***");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		@SuppressWarnings("unused")
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getMileStonePercentages.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/createMileStoneDataForDemandNote.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result createMileStoneDataForDemandNote(@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the createMileStoneDataForDemandNote in EmployeeFinancialRestController ***");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.createMileStoneForDemandNote.getUrl(), employeeFinancialRequest, Result.class);
	}
	/**
	 */
	@RequestMapping(value ="/updateMileStoneDataForDemandNote.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result updateMileStoneDataForDemandNote(@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the createMileStoneDataForDemandNote in EmployeeFinancialRestController ***");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		if (Util.isNotEmptyObject(employeeFinancialRequest.getSessionKey()) 
				&& Util.isNotEmptyObject(employeeFinancialRequest.getFinMilestoneClassifidesId()) 
				&& Util.isNotEmptyObject(employeeFinancialRequest.getSiteId())
				//&& Util.isNotEmptyObject(employeeFinancialRequest.getBlockIds())
				&& Util.isNotEmptyObject(employeeFinancialRequest.getFinancialProjectMileStoneRequests())
				&& Util.isNotEmptyObject(employeeFinancialRequest.getRequestUrl())
				) {
			EmployeeSession employeeSession = sessionValidate(sessionKey);
			//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
			employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
			employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.updateMileStoneDataForDemandNote.getUrl(), employeeFinancialRequest, Result.class);
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			// errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);

		}
	}
	
	
	@RequestMapping(value ="/generatedDemandNotePreview.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result generatedDemandNotePreview(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the generatedDemandNotePreview in EmployeeFinancialRestController ***");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GENERATED_DEMAND_NOTE_PREVIEW.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/deleteDemandNoteZipFile.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result deleteDemandNoteZipFile(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the generatedDemandNotePreview in EmployeeFinancialRestController ***");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.DELETE_DEMAND_NOTE_ZIP_FILE.getUrl(), employeeFinancialRequest, Result.class);
	}

	@RequestMapping(value ="/loadDemandNotePDFFile.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result loadDemandNotePDFFile(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the generatedDemandNotePreview in EmployeeFinancialRestController ***");
		if ( isNotEmptyObject(employeeFinancialRequest.getSiteId())&& isNotEmptyObject(employeeFinancialRequest.getFinBookingFormDemandNoteId())) {
			String sessionKey = employeeFinancialRequest.getSessionKey();
			EmployeeSession employeeSession = sessionValidate(sessionKey);
			//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
			employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
			employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			int portNumber = request.getLocalPort();
			employeeFinancialRequest.setPortNumber((long)portNumber);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.LOAD_DEMAND_NOTE_PDF_FILE.getUrl(), employeeFinancialRequest, Result.class);
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			// errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value = "/raisedMilestoneSites.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getRaisedMilestoneSites(@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getRaisedMilestoneSites in EmployeeFinancialRestController ***");
		if(Util.isNotEmptyObject(employeeFinancialRequest) && Util.isNotEmptyObject(employeeFinancialRequest.getSessionKey()) && Util.isNotEmptyObject(employeeFinancialRequest.getActionUrl())) {
			String sessionKey = employeeFinancialRequest.getSessionKey();
			EmployeeSession employeeSession = sessionValidate(sessionKey);
			Result result = new Result();
			//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
			employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
			List<Department> departments = employeeSession.getLoginResponse().getDepartments();
			/* Employee Accessed Site List */
			List<Long> siteIds = new ArrayList<Long>();
			if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("View Demand Notes")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.DEMAND_NOTES.getName(), SubModule.VIEW_DEMAND_NOTES.getName());
			} else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("View Interest Letter")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.DEMAND_NOTES.getName(), SubModule.VIEW_INTEREST_LETTER.getName());
			} else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("Generate Demand Note")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.DEMAND_NOTES.getName(), SubModule.GENERATE_DEMAND_NOTE.getName());
			}else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("Regenerate Demand Note")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.DEMAND_NOTES.getName(), SubModule.REGENERATE_DEMAND_NOTE.getName());
			}else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("Suspense Entry")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.SUSPENSE_ENTRY.getName());
			}else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("View Suspense Entries")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.VIEW_SUSPENSE_ENTRIES.getName());
			}else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("Receipt Cheque")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.RECEIPT_CHEQUE.getName());
			}else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("Payment Cheque")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), "Initiate Payment");//SubModule.INITIATE_PAYMENT.getName()
			}else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("Legal Invoice")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.LEGAL_INVOICE.getName());
			}else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("Modification Invoice")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.MODIFICATION_INVOICE.getName());
			}else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("View Milestones")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.MILESTONES.getName(), SubModule.VIEW_MILESTONES.getName());
			}else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("Update TDS")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.UPDATE_TDS.getName());
			}else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("Customer_ledger")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.REPORT.getName(), SubModule.CUSTOMER_LEDGER.getName());
			}else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("Update Interest Rates")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.RATE_OF_INTEREST.getName());
			}else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("EditTransaction")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.VIEW_COMPLETED_TRANSACTIONS.getName());
			}else if(employeeFinancialRequest.getActionUrl().equalsIgnoreCase("ViewCompletedTransaction")) {
				siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.VIEW_COMPLETED_TRANSACTIONS.getName());
			}else {
				siteIds = null;
			}
			/* No Access when Employee have no accessed sites */
			if(Util.isEmptyObject(siteIds)) {
				result.setResponseCode(HttpStatus.failure.getResponceCode());
				result.setDescription("No Site Access to this Module and Sub_Module");
				log.info("No Site Access to this Module and Sub_Module");
				//throw new InSufficeientInputException(Arrays.asList("No Site Access to this Module and Sub_Module"));
				return result;
			}
			/* Employee Accessed Sites */
			employeeFinancialRequest.setSiteIds(siteIds);
			return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.RAISED_MILESTONE_SITES.getUrl(), employeeFinancialRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value = "/activeBlocksFlats.spring", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
	public Result getActiveBlocksFlats(@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getActiveBlocksFlats in EmployeeFinancialRestController ***");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_ACTIVE_BLOCKS_FLATS.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/viewDemandNotes.spring", method=RequestMethod.POST,  produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getDemandNotes(@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getDemandNotes in EmployeeFinancialRestController ***");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.VIEW_DEMAND_NOTES.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/viewFinTransactionTypeModeData.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getFinTransactionTypeModeData(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getFinTransactionTypeModeData in EmployeeFinancialRestController ***");	
		String sessionKey = employeeFinancialTransactionRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_TRANSACTION_TYPE_MODE_DATA.getUrl(), employeeFinancialTransactionRequest, Result.class);
	}
	
	@RequestMapping(value ="/viewFinProjectAccountData.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getFinProjectAccountData(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getFinProjectAccountData in EmployeeFinancialRestController ***");	
		String sessionKey = employeeFinancialTransactionRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_FIN_PROJECT_ACCOUNT_DATA.getUrl(), employeeFinancialTransactionRequest, Result.class);
	}
	
	@RequestMapping(value ="/viewFinProjectAccountDataForInvoices.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result viewFinProjectAccountDataForInvoices(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getFinProjectAccountData in EmployeeFinancialRestController ***");	
		String sessionKey = employeeFinancialTransactionRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.viewFinProjectAccountDataForInvoices.getUrl(), employeeFinancialTransactionRequest, Result.class);
	}

	
	@RequestMapping(value ="/getCustomerInvoices.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getInvoices(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getInvoices in EmployeeFinancialRestController ***");	
		String sessionKey = employeeFinancialTransactionRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("** The EmployeeSession Object is ***"+employeeSession);
		if (Util.isNotEmptyObject(employeeFinancialTransactionRequest.getSiteIds())
				//&& Util.isNotEmptyObject(employeeFinancialTransactionRequest.getBlockIds())
				&& Util.isNotEmptyObject(employeeFinancialTransactionRequest.getFlatIds())
				&& Util.isNotEmptyObject(employeeFinancialTransactionRequest.getBookingFormIds())) {

			employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
			employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			int portNumber = request.getLocalPort();
			employeeFinancialTransactionRequest.setPortNumber((long) portNumber);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getCustomerInvoices.getUrl(),
					employeeFinancialTransactionRequest, Result.class);
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value ="/viewPendingAmount.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getPendingAmountData(@RequestBody EmployeeFinancialTransactionRequest financialTransactionRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getPendingAmountData in EmployeeFinancialRestController ***");
		if (Util.isNotEmptyObject(financialTransactionRequest.getSessionKey()) 
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionTypeId())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionModeId())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionTypeName())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionModeName())
	
			)  {
			String sessionKey = financialTransactionRequest.getSessionKey();
			EmployeeSession employeeSession = sessionValidate(sessionKey);
			//log.debug("** The EmployeeSession Object is ***"+employeeSession);
			financialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
			financialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_PENDING_AMOUNT_DATA.getUrl(), financialTransactionRequest, Result.class);
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value ="/viewMisPendingTransactions.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getMisPendingTransactions(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest) throws SessionTimeoutException, InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		//log.info("*** The Control is inside the getMisPendingTransactions in EmployeeFinancialRestController ***");	
		Result result = new Result();
		List<Long> siteIds = new ArrayList<Long>();
		if (Util.isNotEmptyObject(employeeFinancialTransactionRequest.getSessionKey())) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				//log.debug("** The EmployeeSession Object is ***"+employeeSession);
				employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
				employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			
				List<Department> departments = employeeSession.getLoginResponse().getDepartments();
				if (Util.isNotEmptyObject(employeeFinancialTransactionRequest.getCondition())) {
					if (employeeFinancialTransactionRequest.getCondition().equalsIgnoreCase("transactionStatus")) {
						if(Util.isNotEmptyObject(employeeFinancialTransactionRequest.getSiteIds())) {
							siteIds = employeeFinancialTransactionRequest.getSiteIds();
							employeeFinancialTransactionRequest.setSiteId(null);
						} else {
							siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.VIEW_TEMPORARY_TRANSACTIONS_STATUS.getName());
						}
					} else if (employeeFinancialTransactionRequest.getCondition().equalsIgnoreCase("modifyTransaction")) {
						siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.MODIFY_TRANSACTION.getName());//
					} else if (employeeFinancialTransactionRequest.getCondition().equalsIgnoreCase("approveTransaction")) {
						if(Util.isEmptyObject(employeeFinancialTransactionRequest.getSiteIds())) {	
						  siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), "Pending Transactions For Approval");// SubModule.PENDING_TRANSACTIONS_FOR_APPROVAL.getName()
						}else
						{
							siteIds=employeeFinancialTransactionRequest.getSiteIds();
						}
						if(employeeFinancialTransactionRequest.getActionUrl()!=null && employeeFinancialTransactionRequest.getActionUrl().equalsIgnoreCase("getUnclearedChequeDetails")) {
							siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.UNCLEARED_CHEQUE.getName());	
						}
					} else if (employeeFinancialTransactionRequest.getCondition().equalsIgnoreCase("loadCompletedTransaction") || employeeFinancialTransactionRequest.getCondition().equalsIgnoreCase("clearedCompletedTransaction")) {
						if(Util.isNotEmptyObject(employeeFinancialTransactionRequest.getSiteIds())) {
							siteIds = employeeFinancialTransactionRequest.getSiteIds();
							employeeFinancialTransactionRequest.setSiteId(null);
						} else {
							siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.VIEW_COMPLETED_TRANSACTIONS.getName());
						}
					}else if(employeeFinancialTransactionRequest.getCondition().equalsIgnoreCase("ViewCustomerData")){
						siteIds = employeeFinancialTransactionRequest.getSiteIds();
					}
				}
				
				/* No Access when Employee have no accessed sites */
				if(Util.isEmptyObject(siteIds)) {
					result.setResponseCode(HttpStatus.failure.getResponceCode());
					result.setDescription("No Site Access to this Module and Sub_Module");
					return result;
				}
				/* Employee Accessed Sites */
				employeeFinancialTransactionRequest.setSiteIds(siteIds);
				return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_MIS_PENDING_TRANSACTIONS.getUrl(), employeeFinancialTransactionRequest, Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
	}

	@RequestMapping(value ="/viewInterestWaiverPendingTransactions.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result viewInterestWaiverPendingTransactions(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest) throws SessionTimeoutException, InSufficeientInputException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		log.info("***** Control inside the EmployeeFinancialRestController.viewInterestWaiverPendingTransactions() *****");
		Result result = new Result();
		List<Long> siteIds = new ArrayList<Long>();
		if (Util.isNotEmptyObject(employeeFinancialTransactionRequest.getSessionKey())) {
			String sessionKey = sessionUtils.decryptSessionKey(employeeFinancialTransactionRequest.getSessionKey());
			boolean isSessionValid = sessionUtils.validateEmployeeSession(sessionKey);
			if (isSessionValid) {
				EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(sessionKey);
				//log.debug("** The EmployeeSession Object is ***"+employeeSession);
				employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
				employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			
				List<Department> departments = employeeSession.getLoginResponse().getDepartments();
				if (Util.isNotEmptyObject(employeeFinancialTransactionRequest.getCondition())) {
					if (employeeFinancialTransactionRequest.getCondition().equalsIgnoreCase("transactionStatus")) {
						siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.VIEW_INTEREST_WAIVER_REQUEST_STATUS.getName());
					}/* else if (employeeFinancialTransactionRequest.getCondition().equalsIgnoreCase("modifyTransaction")) {
						siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), "Modify Transaction");//SubModule.MODIFY_TRANSACTION.getName()
					}*/ else if (employeeFinancialTransactionRequest.getCondition().equalsIgnoreCase("approveTransaction")) {
							if(Util.isNotEmptyObject(employeeFinancialTransactionRequest.getSiteIds())) {
								siteIds = employeeFinancialTransactionRequest.getSiteIds();
							} else {
								siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.VIEW_INTEREST_WAIVER_REQUEST_FOR_APPROVAL.getName());
							}
						/*if(employeeFinancialTransactionRequest.getActionUrl()!=null && employeeFinancialTransactionRequest.getActionUrl().equalsIgnoreCase("getUnclearedChequeDetails")) {
							siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.UNCLEARED_CHEQUE.getName());	
						}*/
					} else if (employeeFinancialTransactionRequest.getCondition().equalsIgnoreCase("loadCompletedTransaction")) {
						if(Util.isNotEmptyObject(employeeFinancialTransactionRequest.getSiteIds())) {
							siteIds = employeeFinancialTransactionRequest.getSiteIds();
						} else {
							siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.VIEW_COMPLETED_TRANSACTIONS.getName());
						}
					}else if(employeeFinancialTransactionRequest.getCondition().equalsIgnoreCase("ViewCustomerData")){
						siteIds = employeeFinancialTransactionRequest.getSiteIds();
					}
				}
				
				/* No Access when Employee have no accessed sites */
				if(Util.isEmptyObject(siteIds)) {
					result.setResponseCode(HttpStatus.failure.getResponceCode());
					result.setDescription("No Site Access to this Module and Sub_Module");
					return result;
				}
				/* Employee Accessed Sites */
				employeeFinancialTransactionRequest.setSiteIds(siteIds);
				return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_MIS_PENDING_TRANSACTIONS.getUrl(), employeeFinancialTransactionRequest, Result.class);
			} else {
				throw new SessionTimeoutException("Your Session has been Timed Out.");
			}
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
	}

	
	@RequestMapping(value ="/viewMisReceiptChequeOnlineData.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public  Result getMisReceiptChequeOnlineData(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getMisReceiptChequeOnlineData in EmployeeFinancialRestController ***");	
		if(Util.isNotEmptyObject(employeeFinancialTransactionRequest) && (Util.isNotEmptyObject(employeeFinancialTransactionRequest.getBookingFormId()))
				&& Util.isNotEmptyObject(employeeFinancialTransactionRequest.getTransactionEntryId()) && Util.isNotEmptyObject(employeeFinancialTransactionRequest.getTransactionModeName())){
			String sessionKey = employeeFinancialTransactionRequest.getSessionKey();
			EmployeeSession employeeSession = sessionValidate(sessionKey);
			//log.debug("** The EmployeeSession Object is ***"+employeeSession);
			employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
			employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			int portNumber = request.getLocalPort();
			employeeFinancialTransactionRequest.setPortNumber((long)portNumber);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_MIS_RECEIPT_CHEQUE_ONLINE_DATA.getUrl(), employeeFinancialTransactionRequest, Result.class);
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
	}

	@RequestMapping(value ="/viewInterestWaiverData.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public  Result viewInterestWaiverData(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getMisReceiptChequeOnlineData in EmployeeFinancialRestController ***");	
		if(Util.isNotEmptyObject(employeeFinancialTransactionRequest) && (Util.isNotEmptyObject(employeeFinancialTransactionRequest.getBookingFormId()))
				&& Util.isNotEmptyObject(employeeFinancialTransactionRequest.getTransactionEntryId()) && Util.isNotEmptyObject(employeeFinancialTransactionRequest.getTransactionModeName())){
			String sessionKey = employeeFinancialTransactionRequest.getSessionKey();
			EmployeeSession employeeSession = sessionValidate(sessionKey);
			//log.debug("** The EmployeeSession Object is ***"+employeeSession);
			employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
			employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			int portNumber = request.getLocalPort();
			employeeFinancialTransactionRequest.setPortNumber((long)portNumber);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.viewInterestWaiverData.getUrl(), employeeFinancialTransactionRequest, Result.class);
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
	}

	
	@RequestMapping(value ="/mailService.spring", method =  { RequestMethod.GET, RequestMethod.POST }, consumes=MediaType.APPLICATION_OCTET_STREAM, produces=MediaType.APPLICATION_JSON)
	public Result mailService(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		System.out.println("***** Control inside the EmployeeFinancialRestController.mailService() *****");
		
		return null;
	}

	@RequestMapping(value ="/clearStaticObjects.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result clearStaticObjects(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest) throws SessionTimeoutException, InSufficeientInputException {
		System.out.println("EmployeeFinancialRestController.clearStaticObjects()");
		Result result = new Result();
		//reset class level static object
		holdApprovingTransactions = new HashMap<>();
		return result;
	}
	
	@RequestMapping(value ="/approveOrRejectMisReceiptOrPayment.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result setApproveOrRejectMisReceiptOrPayment(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the setApproveOrRejectMisReceiptOrPayment in EmployeeFinancialRestController ***");	
		if (Util.isNotEmptyObject(employeeFinancialTransactionRequest.getSiteId()) && Util.isNotEmptyObject(employeeFinancialTransactionRequest.getTransactionEntryId())) {
			Result result = new Result();
			String sessionKey = employeeFinancialTransactionRequest.getSessionKey();
			EmployeeSession employeeSession = sessionValidate(sessionKey);
			//log.debug("** The EmployeeSession Object is ***" + employeeSession);
			employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
			employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			int portNumber = request.getLocalPort();
			employeeFinancialTransactionRequest.setPortNumber((long) portNumber);
			if(holdApprovingTransactions.containsKey(employeeFinancialTransactionRequest.getBookingFormId())) {
				//throw new InSufficeientInputException(Arrays.asList("Found duplicate request for transaction, failed to process transaction please try again..."));
				throw new InSufficeientInputException(Arrays.asList("Oops !!! There was a improper request found. Please check transaction in transaction status."));
			}
			holdApprovingTransactions.put(employeeFinancialTransactionRequest.getBookingFormId(), employeeFinancialTransactionRequest.getBookingFormId());
			try {
				log.info("*** The Control is inside the setApproveOrRejectMisReceiptOrPayment in EmployeeFinancialRestController *** Before Approval"+holdApprovingTransactions);
				result = (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SET_APPROVE_REJECT_MIS_RECEIPT_PAYMENT.getUrl(), employeeFinancialTransactionRequest, Result.class);	
				holdApprovingTransactions.remove(employeeFinancialTransactionRequest.getBookingFormId());
			} catch (Exception ex) {
				try {
					holdApprovingTransactions.remove(employeeFinancialTransactionRequest.getBookingFormId());
				}catch(Exception ex1) {
					log.info(ex1.getMessage(), ex1);	
				}
				ex.printStackTrace();
				log.info(ex.getMessage(), ex);
			}
			log.info("*** The Control is inside the setApproveOrRejectMisReceiptOrPayment in EmployeeFinancialRestController *** After Approval"+holdApprovingTransactions);
			return result;
		} else {
			holdApprovingTransactions.remove(employeeFinancialTransactionRequest.getBookingFormId());
			holdApprovingTransactions.remove(employeeFinancialTransactionRequest.getTransactionEntryId());
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			// errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value ="/approveFinancialMultipleTransaction.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result approveMultipleTransaction(@RequestBody EmployeeFinancialMultipleTRNRequest employeeFinancialTransactionRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("***** Control inside the EmployeeFinancialRestController.approveMultipleTransaction() *****");	
		if (Util.isNotEmptyObject(employeeFinancialTransactionRequest) && Util.isNotEmptyObject(employeeFinancialTransactionRequest.getFinancialTRNRequests()) ) {
			String sessionKey = employeeFinancialTransactionRequest.getSessionKey();
			EmployeeSession employeeSession = sessionValidate(sessionKey);
			//log.debug("** The EmployeeSession Object is ***" + employeeSession);
			employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
			employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			int portNumber = request.getLocalPort();
			employeeFinancialTransactionRequest.setPortNumber((long) portNumber);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.approveFinancialMultipleTransaction.getUrl(), employeeFinancialTransactionRequest, Result.class);
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			// errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value ="/viewAnonymousEntriesData.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getAnonymousEntriesData(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getAnonymousEntriesData in EmployeeFinancialRestController ***");	
		String sessionKey = employeeFinancialTransactionRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		List<Long> siteIds = null;
		List<Department> departments = employeeSession.getLoginResponse().getDepartments();
		if(employeeFinancialTransactionRequest.getRequestUrl()!=null && employeeFinancialTransactionRequest.getRequestUrl().equals("LoadModifySuspenceEntry")) {
			siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.MODIFY_SUSPENSE_ENTRY.getName());//SubModule.MODIFY_SUSPENSE_ENTRY.getName()
		} else {
			/*if("getSuspnesEntryTransactions".equals(employeeFinancialTransactionRequest.getRequestUrl()))
			{}else {
			siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.VIEW_SUSPENSE_ENTRIES.getName());	
			}*/
			
			if("getSuspnesEntryTransactions".equals(employeeFinancialTransactionRequest.getRequestUrl())) {
				//siteIds = employeeFinancialTransactionRequest.getSiteIds();
			} else if("getSuspnesEntryTransactionReport".equals(employeeFinancialTransactionRequest.getRequestUrl())) {
				siteIds = employeeFinancialTransactionRequest.getSiteIds();
			} else {
				siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.VIEW_SUSPENSE_ENTRIES.getName());	
			}
		}
		
		employeeFinancialTransactionRequest.setSiteIds(siteIds);
		int portNumber = request.getLocalPort();
		employeeFinancialTransactionRequest.setPortNumber((long)portNumber);
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_ANONYMOUS_ENTRIES_DATA.getUrl(), employeeFinancialTransactionRequest, Result.class);
	}

	@RequestMapping(value ="/viewAnonymousEntriesDataReport.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result viewAnonymousEntriesDataReport(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getAnonymousEntriesData in EmployeeFinancialRestController ***");	
		String sessionKey = employeeFinancialTransactionRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		
		int portNumber = request.getLocalPort();
		employeeFinancialTransactionRequest.setPortNumber((long)portNumber);
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.viewAnonymousEntriesDataReport.getUrl(), employeeFinancialTransactionRequest, Result.class);
	}

	
	@RequestMapping(value ="/getTaxPercentage.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getTaxPercentageData(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getGstPercentageData in EmployeeFinancialRestController ***");	
		String sessionKey = employeeFinancialTransactionRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_TAX_PERCENTAGE_DATA.getUrl(), employeeFinancialTransactionRequest, Result.class);
	}
	
	@RequestMapping(value ="/saveLegalCharges.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result saveLegalCharges(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialTransactionRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the saveLegalCharges in EmployeeFinancialRestController ***");	
		String sessionKey = employeeFinancialTransactionRequest.getSessionKey();
		
		if(Util.isNotEmptyObject(employeeFinancialTransactionRequest) && Util.isNotEmptyObject(employeeFinancialTransactionRequest.getBookingFormIds())
				&& Util.isNotEmptyObject(employeeFinancialTransactionRequest.getSiteIds()) && Util.isNotEmptyObject(employeeFinancialTransactionRequest.getFinBookingFormLegalCostList())
				&& Util.isNotEmptyObject(employeeFinancialTransactionRequest.getFinBookingFormLegalCostList().get(0).getFinBookingFormLglCostDtlsList())
				&& Util.isNotEmptyObject(employeeFinancialTransactionRequest.getFinTaxMappingId()) && Util.isNotEmptyObject(employeeFinancialTransactionRequest.getPercentageValue())){

			EmployeeSession employeeSession = sessionValidate(sessionKey);
			//log.debug("** The EmployeeSession Object is ***"+employeeSession);
			employeeFinancialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
			employeeFinancialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			int portNumber = request.getLocalPort();
			employeeFinancialTransactionRequest.setPortNumber((long)portNumber);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.SAVE_LEGAL_CHARGES.getUrl(), employeeFinancialTransactionRequest, Result.class);
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			//errors.add("Session key is missing");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value ="/getCustomerDetailsAndPendingAmounts.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getCustomerDetailsAndPendingAmounts(@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getCustomerDetailsAndPendingAmounts in EmployeeFinancialRestController ***");	
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_CUSTOMER_DETAILS_PENDING_AMOUNTS.getUrl(), employeeFinancialRequest, Result.class);	
	}
	/**
	 * loading invoice number for payment refund 
	 * @param employeeFinancialTransactionRequest
	 * @return
	 * @throws SessionTimeoutException
	 * @throws InSufficeientInputException
	 */
	@RequestMapping(value ="/getExcessAmountDetailsForRefund", method  = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getExcessAmountDetailsForRefund(@RequestBody EmployeeFinancialTransactionRequest financialTransactionRequest) throws SessionTimeoutException, InSufficeientInputException {
		log.info("***** Control inside the EmployeeFinancialRestController.getExcessAmountDetailsForRefund() *****");
		if (Util.isNotEmptyObject(financialTransactionRequest.getSessionKey()) 
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionTypeId())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionModeId())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionTypeName())
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionModeName())
	
				&& Util.isNotEmptyObject(financialTransactionRequest.getTransactionFor())
				
				)  {
			
			String sessionKey = financialTransactionRequest.getSessionKey();
			EmployeeSession employeeSession = sessionValidate(sessionKey);
			//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
			financialTransactionRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
			financialTransactionRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_EXCESS_AMOUNT_DETAILS_FOR_REFUND.getUrl(), financialTransactionRequest, Result.class);
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}		
	}
	
	
	@RequestMapping(value ="/financialDtls.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result financialDtls(@RequestBody Customer customer,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the getCustomerDetailsAndPendingAmounts in EmployeeFinancialRestController ***");
		EmployeeFinancialRequest employeeFinancialRequest = new EmployeeFinancialRequest();
		if (customer.getSessionKey() != null && customer.getCustomerId()!=null && customer.getFlatId()!=null && customer.getFlatBookingId()!=null && customer.getSiteId()!=null) {
			String sessionKey = customer.getSessionKey();
			EmployeeSession employeeSession = sessionValidate(sessionKey);
			employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmployee().getEmployeeId());
			int portNumber = request.getLocalPort();
			employeeFinancialRequest.setPortNumber((long)portNumber);
			customer.setPortNumber((long)portNumber);
			employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			//sessionValidate(sessionKey);
		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
		Financial financial = (Financial) RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.customerfinancialdetails.getUrl(), customer, Financial.class);
		if(Util.isNotEmptyObject(financial) && Util.isNotEmptyObject(financial.getMileStones())) {
			List<Map<String,String>> dynamicKeys = new ArrayList<>(); 
			Map<String,String> financialAmtDetails = null;
			 
			 if(Util.isNotEmptyObject(financial.getTotalMilestoneDueAmount()) ) {
				 financialAmtDetails = new HashMap<>();
				 financialAmtDetails.put("key","Total Due/Excess amount as per Milestone Completion");
				 financialAmtDetails.put("value",financial.getTotalMilestoneDueAmount());
				 dynamicKeys.add(financialAmtDetails);
			 }
			 
			 if(Util.isNotEmptyObject(financial.getTotalMilestonePaidAmount()) ) {
				 financialAmtDetails = new HashMap<>();
				 financialAmtDetails.put("key","Total Amount Paid");
				 financialAmtDetails.put("value",financial.getTotalMilestonePaidAmount());
				 dynamicKeys.add(financialAmtDetails);
			 }
			 //double sumOfInterestWaiverAdjAmount = 0.0;
			 //Double finalAmount = 0.0;
			 //Double totalPenaltyAmount = 0.0;
			 if(Util.isNotEmptyObject(financial.getSumOfTotalPendingPenaltyAmount())) {
				 financialAmtDetails = new HashMap<>();
				 /*String waiverAmt = financial.getSumOfInterestWaiverAdjAmount();
				 if(Util.isNotEmptyObject(waiverAmt)) {
					 waiverAmt = waiverAmt.replaceAll("[^0-9.]", "");
				 }
				 sumOfInterestWaiverAdjAmount = Double.valueOf(waiverAmt);
				 totalPenaltyAmount = Double.valueOf(financial.getTotalPenaltyAmount().replaceAll("[^0-9.]", ""));
				 finalAmount = totalPenaltyAmount - sumOfInterestWaiverAdjAmount;*/
				 
				 
				 financialAmtDetails.put("key","Total Pending Interest Amount");
				 financialAmtDetails.put("value",financial.getSumOfTotalPendingPenaltyAmount());
				 dynamicKeys.add(financialAmtDetails);
			 }
			 
			 financial.setFinancialAmtDetails(dynamicKeys);
		}
		
		if(Util.isNotEmptyObject(financial) && Util.isNotEmptyObject(financial.getMileStones()) && "".equals("das")) {
			try {
				constructObjectForInterestCalculation(customer,financial,employeeFinancialRequest);
			
				EmployeeFinancialResponse resultOfDemandNote = (EmployeeFinancialResponse)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GENERATED_DEMAND_NOTE_PREVIEW.getUrl(), employeeFinancialRequest, EmployeeFinancialResponse.class);
				EmployeeFinancialResponse list = resultOfDemandNote;
				
				List<FinancialProjectMileStoneResponse> listOfMilestoneData = (List<FinancialProjectMileStoneResponse>)list.getFinancialProjectMileStoneResponse();
				if(Util.isNotEmptyObject(list)) {
					
					for (MileStone customerMilestones : financial.getMileStones()) {
						for(FinancialProjectMileStoneResponse msResponse : listOfMilestoneData) {
							if(customerMilestones.getProjectMilestoneId()!=null && 
									msResponse.getProjectMilestoneId() !=null &&
									//customerMilestones.getPaymentScheduleId()!=null &&
									customerMilestones.getProjectMilestoneId().equals(msResponse.getProjectMilestoneId())
									
									) {
								System.out.println(msResponse.getTotalPenaltyAmount());
								System.out.println(msResponse.getTotalPendingPenaltyAmount()+"\t"+customerMilestones.getTotalPenalityAmount());
								if(Util.isNotEmptyObject(msResponse.getTotalPendingPenaltyAmount())) {
									//customerMilestones.setTotalPenalityAmount(getTheAmountWithCommas(msResponse.getTotalPendingPenaltyAmount(), roundingModeSize, roundingMode));
									customerMilestones.setTotalPenalityAmount(BigDecimal.valueOf(msResponse.getTotalPendingPenaltyAmount()).setScale(roundingModeSize, roundingMode).toString());
								}
								//customerMilestones.setTotalPenalityAmount(msResponse.getTotalPenaltyAmount());
								break;
							}
						}
					}
				}
			}catch(Exception e ) {
				e.printStackTrace();
			}
		}//financial
		
		return financial;
	}
	
	private void constructObjectForInterestCalculation(Customer customer, Financial financial,
			EmployeeFinancialRequest employeeFinancialRequest) {
		employeeFinancialRequest.setSiteId(customer.getSiteId());
		employeeFinancialRequest.setSiteIds(Arrays.asList(customer.getSiteId()));
		employeeFinancialRequest.setIsInterestOrWithOutInterest("With Interest");
		employeeFinancialRequest.setIsReGenerateDemandNote("true");
		employeeFinancialRequest.setIsShowGstInPDF("true");
		employeeFinancialRequest.setRequestUrl("ViewCustomerDataGetInterest");
		employeeFinancialRequest.setFlatIds(Arrays.asList(customer.getFlatId()));
		employeeFinancialRequest.setDemandNoteDate(new Timestamp(new Date().getTime()));
		List<FinancialProjectMileStoneRequest> financialProjectMileStoneRequests = new ArrayList<>();
		employeeFinancialRequest.setFinancialProjectMileStoneRequests(financialProjectMileStoneRequests);
		for (MileStone customerMilestones : financial.getMileStones()) {
			if(customerMilestones.getMileStoneStatus()!=null && customerMilestones.getMileStoneStatus().equals("Completed")
					) {
				FinancialProjectMileStoneRequest milestoneRequest = new FinancialProjectMileStoneRequest();
				milestoneRequest.setMilestoneDate(customerMilestones.getMileStoneDate());
				milestoneRequest.setFinMilestoneClassifidesId(customerMilestones.getFinMilestoneClassifidesId());
				milestoneRequest.setMilestoneName(customerMilestones.getMileStoneName());
				milestoneRequest.setMileStonePercentage(customerMilestones.getPaymentPercentageInMileStone());
				milestoneRequest.setMileStoneDueDate(new Timestamp(customerMilestones.getDueDate().getTime()));
				milestoneRequest.setProjectMilestoneId(customerMilestones.getProjectMilestoneId());
				milestoneRequest.setMileStoneNo(customerMilestones.getMilStoneNo());
				milestoneRequest.setDemandNoteDate(new Timestamp(new Date().getTime()));
				if (financialProjectMileStoneRequests.isEmpty()) {
					financialProjectMileStoneRequests.add(milestoneRequest);
				} else {
					financialProjectMileStoneRequests.set(0, milestoneRequest);
				}
			}
		}
		//dummy milestone for calculating the interest calculation upto the date
		FinancialProjectMileStoneRequest milestoneRequest = new FinancialProjectMileStoneRequest();
		milestoneRequest.setFinMilestoneClassifidesId(financialProjectMileStoneRequests.get(0).getFinMilestoneClassifidesId());
		milestoneRequest.setMilestoneDate(new Timestamp(new Date().getTime()));
		milestoneRequest.setMilestoneName("Dummy MileStone for Regenerate Demand Note");
		milestoneRequest.setMileStonePercentage(12d);
		milestoneRequest.setMileStoneDueDate(new Timestamp(new Date().getTime()));
		milestoneRequest.setProjectMilestoneId(0l);
		milestoneRequest.setMileStoneNo(0l);
		milestoneRequest.setDemandNoteDate(new Timestamp(new Date().getTime()));
		financialProjectMileStoneRequests.add(milestoneRequest);
		
	}

	@RequestMapping(value = "/paymentDtls.spring", method = RequestMethod.POST, produces = "application/json",consumes="application/json")
	public Result getPaymentDtls(@RequestBody Customer customer) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, SessionTimeoutException, InSufficeientInputException {
		log.info("******* The control inside of the getFinancialDtls service in  FinancialRestController ******"+ customer.getDeviceId());
		
		if (customer.getSessionKey() != null) {
			String sessionKey = customer.getSessionKey();
			sessionValidate(sessionKey);

			PaymentDetailsWrapper paymentDetailsWrapper = (PaymentDetailsWrapper) RestApiUtility.sendJSONPostRequest(
					CustomerServiceUrls.customerpaymentdetails.getUrl(), customer, PaymentDetailsWrapper.class);
			return paymentDetailsWrapper;

		} else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}

	@RequestMapping(value ="/getNonRefundFlats.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getNonRefundFlats(@RequestBody EmployeeFinancialRequest employeeFinancialRequest) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.getNonRefundFlats()");	
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		List<Long> siteIds = new ArrayList<Long>();
		List<Department> departments = employeeSession.getLoginResponse().getDepartments();
		siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.RECEIPT_CHEQUE.getName());
		employeeFinancialRequest.setSiteIds(siteIds);
		//log.debug("** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getNonRefundFlats.getUrl(), employeeFinancialRequest, Result.class);	
	}
	
	@RequestMapping(value ="/generateConsolidatedReceipt.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result generateConsolidatedReceipt(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.generateConsolidatedReceipt()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GENERATE_CONSOLIDATED_RECEIPT.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/generateClosingBalanceReport.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result generateClosingBalanceReport(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("*** The Control is inside the generateConsolidatedReceipt in EmployeeFinancialRestController ***");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.generateClosingBalanceReport.getUrl(), employeeFinancialRequest, Result.class);
	}

	@RequestMapping(value ="/getCustomerFinancialDetails.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getCustomerFinancialDetails(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.getCustomerFinancialDetails()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getCustomerFinancialDetails.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/deleteUploadedAttachments.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result deleteUploadedAttachments(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		//log.info("***** Control inside the EmployeeFinancialRestController.deleteUploadedAttachments() *****");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.deleteUploadedAttachments.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/getPendingModificationInvoices.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getPendingModificationInvoices(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.getPendingModificationInvoices()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("** The EmployeeSession Object is **"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_PENDING_MOD_INVOICES.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/getModificationInvoiceDetails.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getModificationInvoiceDetails(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("**** Control inside the EmployeeFinancialRestController.getModificationInvoiceDetails.spring ****");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("** The EmployeeSession Object is **"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_PENDING_MOD_INVOICE_DETAILS.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/getInterestWaiverReportDetails.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getInterestWaiverReportDetails(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.getInterestWaiverReportDetails()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_INTEREST_WAIVER_REPORT_DETAILS.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/getInterestWaivedAndPaidDetails.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getInterestWaivedAndPaidDetails(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("*** The Control is inside the getInterestWaivedAndPaidDetails in EmployeeFinancialRestController ***"+employeeFinancialRequest);
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_INTEREST_WAIVED_AND_PAID_DETAILS.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/getAllCustomersInvoices.spring", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	public Result getAllCustomersInvoices(@RequestBody EmployeeFinancialTransactionRequest empTransReq,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.getAllCustomersInvoices()");	
		if (Util.isNotEmptyObject(empTransReq.getSessionKey()) && (Util.isNotEmptyObject(empTransReq.getSiteIds()) || Util.isNotEmptyObject(empTransReq.getInvoiceType()) 
			|| Util.isNotEmptyObject(empTransReq.getFromDate()) || Util.isNotEmptyObject(empTransReq.getToDate()))) {
			String sessionKey = empTransReq.getSessionKey();
			EmployeeSession employeeSession = sessionValidate(sessionKey);
			empTransReq.setEmpId(employeeSession.getLoginResponse().getEmpId());
			empTransReq.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
			/* Taking department role mapping id from session */
		    if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments())) {
		      // to get dept Id
		      if(Util.isNotEmptyObject(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId())) {	
		    	  empTransReq.setDeptId(employeeSession.getLoginResponse().getDepartments().get(0).getDepartmentId());
		      }else {
		    	  empTransReq.setDeptId(0l);
		      }
		    }else {
		    	empTransReq.setDeptId(0l);
		    }
			int portNumber = request.getLocalPort();
			empTransReq.setPortNumber((long) portNumber);
			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_ALL_CUSTOMERS_INVOICES.getUrl(),empTransReq, Result.class);
		}else {
			List<String> errors = new ArrayList<String>();
			errors.add("Insufficeint Input is Given.");
			throw new InSufficeientInputException(errors);
		}
	}
	
	@RequestMapping(value ="/getSuspenseEntryReport.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getSuspenseEntryReport(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.getSuspenseEntryReport()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getSuspenseEntryReport.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/loadTransactionStatusData.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result loadPendingLevelEmployeeList(@RequestBody EmployeeFinancialRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.loadPendingLevelEmployeeList()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.loadTransactionStatusData.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/getClearedTransactionReport.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getClearedTransactionReport(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.getClearedTransactionReport()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		List<Long> siteIds = null;
		List<Department> departments = employeeSession.getLoginResponse().getDepartments();
		if(Util.isEmptyObject(employeeFinancialRequest.getSiteIds())) {
			siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.CLEARED_TRANSACTION_REPORT.getName());//SubModule.MODIFY_SUSPENSE_ENTRY.getName()
		} else {
			siteIds = employeeFinancialRequest.getSiteIds();
		}
		employeeFinancialRequest.setSiteIds(siteIds);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getClearedTransactionReport.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/getPendingTransactionReport.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getPendingTransactionReport(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.getPendingTransactionReport()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		List<Long> siteIds = null;
		List<Department> departments = employeeSession.getLoginResponse().getDepartments();
		if(Util.isEmptyObject(employeeFinancialRequest.getSiteIds())) {
			siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.CLEARED_TRANSACTION_REPORT.getName());//SubModule.MODIFY_SUSPENSE_ENTRY.getName()
			employeeFinancialRequest.setSiteIds(siteIds);
		} 

		int portNumber = request.getLocalPort();
		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getPendingTransactionReport.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	
	@RequestMapping(value ="/getSuspnesEntryTransactionReport.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getSuspnesEntryTransactionReport(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.getSuspnesEntryTransactionReport()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		int portNumber = request.getLocalPort();
		List<Long> siteIds = null;
		List<Department> departments = employeeSession.getLoginResponse().getDepartments();
		if(Util.isEmptyObject(employeeFinancialRequest.getSiteIds())) {
			siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.CLEARED_TRANSACTION_REPORT.getName());//SubModule.MODIFY_SUSPENSE_ENTRY.getName()
			employeeFinancialRequest.setSiteIds(siteIds);
		} 

		employeeFinancialRequest.setPortNumber((long)portNumber);
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getSuspnesEntryTransactionReport.getUrl(), employeeFinancialRequest, Result.class);
	}

	@RequestMapping(value ="/getCleredUnclearedTransactionReport.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getCleredUnclearedTransactionReport(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.getPendingTransactionReport()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		//log.debug("*** The EmployeeSession Object is ***"+employeeSession);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		List<Long> siteIds = null;
		List<Department> departments = employeeSession.getLoginResponse().getDepartments();
		if(Util.isEmptyObject(employeeFinancialRequest.getSiteIds())) {
			siteIds = Util.getUserAccessSiteList(departments, Module.TRANSACTIONS.getName(), SubModule.CLEARED_UNCLEARED_TRANSACTION_REPORT.getName());//SubModule.MODIFY_SUSPENSE_ENTRY.getName()
			employeeFinancialRequest.setSiteIds(siteIds);
		} 
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getCleredUnclearedTransactionReport.getUrl(), employeeFinancialRequest, Result.class);
	}
	@RequestMapping(value ="/getAccountNumbers.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getAccountNumbers(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.getAccountNumbers()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getAccountNumbers.getUrl(), employeeFinancialRequest, Result.class);
	}
	@RequestMapping(value ="/getBookingStatuses.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getBookingStatuses(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.getBookingStatuses()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getBookingStatuses.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	
	@RequestMapping(value ="/getStates.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result getStates(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.getStates()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.getStates.getUrl(), employeeFinancialRequest, Result.class);
	}
	
	@RequestMapping(value ="/checkDuplicateTransactionOrNot.spring", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON, consumes= MediaType.APPLICATION_JSON)
	public Result checkDuplicateTransactionOrNot(@RequestBody EmployeeFinancialTransactionRequest employeeFinancialRequest,HttpServletRequest request) throws SessionTimeoutException, InSufficeientInputException {
		log.info("EmployeeFinancialRestController.checkDuplicateTransactionOrNot()");
		String sessionKey = employeeFinancialRequest.getSessionKey();
		EmployeeSession employeeSession = sessionValidate(sessionKey);
		employeeFinancialRequest.setEmpId(employeeSession.getLoginResponse().getEmpId());
		employeeFinancialRequest.setEmployeeName(employeeSession.getLoginResponse().getEmpName());
		return (Result)RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.checkDuplicateTransactionOrNot.getUrl(), employeeFinancialRequest, Result.class);
	}
}
