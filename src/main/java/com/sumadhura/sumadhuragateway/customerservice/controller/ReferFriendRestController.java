package com.sumadhura.sumadhuragateway.customerservice.controller;



import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sumadhura.sumadhuragateway.dto.Customer;
import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.ReferFriendDto;
import com.sumadhura.sumadhuragateway.dto.ReferFriendWrapper;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.TicketReport;
import com.sumadhura.sumadhuragateway.exception.InSufficeientInputException;
import com.sumadhura.sumadhuragateway.exception.SessionTimeoutException;
import com.sumadhura.sumadhuragateway.util.CustomerServiceUrls;
import com.sumadhura.sumadhuragateway.util.EmployeeServiceUrls;
import com.sumadhura.sumadhuragateway.util.RestApiUtility;
import com.sumadhura.sumadhuragateway.util.SessionValidate;
import com.sumadhura.sumadhuragateway.util.Util;




@RestController
@RequestMapping(value = { "/customerservice/references" }, consumes = { "application/json" }, produces = { "application/json" })
public class ReferFriendRestController extends SessionValidate
{
    private Logger LOGGER = Logger.getLogger(ReferFriendRestController.class);
    
    /**
     * 
     * @param referFriendDto
     * @return referFriendWrapper
     * @throws SessionTimeoutException
     * @throws InSufficeientInputException
     */
    @RequestMapping("/saveReferFriend.spring")
    @JsonIgnoreProperties
    public Result saveReferFriend(@RequestBody ReferFriendDto referFriendDto) throws SessionTimeoutException, InSufficeientInputException {
        String cacheKey = referFriendDto.getSessionKey();
        CustomerSession customerSession = this.customerSessionValidate(cacheKey, referFriendDto.getDeviceId());
        referFriendDto.setCustomerId(customerSession.getCustomerId());
        referFriendDto.setFlatId(customerSession.getFlat().getFlatId());
        referFriendDto.setFlatBookingId(customerSession.getFlatBookingId());
        LOGGER.debug("*** The CustomerSession Object ***" + customerSession);
        Result result = (Result)RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.SAVE_REFER_FRIEND.getUrl(), referFriendDto, Result.class);
        return result;
    }
    
    /**
     * 
     * @param customer
     * @return referFriendWrapper
     * @throws SessionTimeoutException
     * @throws InSufficeientInputException
     */
    @RequestMapping("/getReferFriendList.spring")
    public Result getReferFriendList(@RequestBody Customer customer) throws SessionTimeoutException, InSufficeientInputException {
        String cacheKey = customer.getSessionKey();
        CustomerSession customerSession = this.customerSessionValidate(cacheKey, customer.getDeviceId());
        customer.setCustomerId(customerSession.getCustomerId());
        customer.setFlatBookingId(customerSession.getFlatBookingId());
        LOGGER.debug("*** The CustomerSession Object ***" + customerSession);
     
        ReferFriendWrapper referFriendWrapper = (ReferFriendWrapper)RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_REFER_FRIEND_LIST.getUrl(), customer, ReferFriendWrapper.class);
        return referFriendWrapper;
    }
    /**
     * 
     * @param customer
     * @return referFriendWrapper
     * @throws SessionTimeoutException
     * @throws InSufficeientInputException
     */
    @RequestMapping(value="/stateList.spring",method=RequestMethod.POST)
    public Result getStateList(@RequestBody Customer customer) throws SessionTimeoutException, InSufficeientInputException {
    	String cacheKey=customer.getSessionKey();
    	CustomerSession customerSession=this.customerSessionValidate(cacheKey, customer.getDeviceId());
    	LOGGER.debug("*** The CustomerSession Object ***" + customerSession);

    	 ReferFriendWrapper referFriendWrapper = (ReferFriendWrapper)RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_STATE_LIST.getUrl(), customer, ReferFriendWrapper.class);
         return referFriendWrapper;
    }
    /**
     * 
     * @param customer
     * @return referFriendWrapper
     * @throws SessionTimeoutException
     * @throws InSufficeientInputException
     */
    @RequestMapping(value="/getFlatBhkList.spring",method=RequestMethod.POST)
    public Result getFlatBhkList(@RequestBody Customer customer) throws SessionTimeoutException, InSufficeientInputException {
    	String cacheKey=customer.getSessionKey();
    	CustomerSession customerSession=this.customerSessionValidate(cacheKey, customer.getDeviceId());
    	LOGGER.debug("*** The CustomerSession Object ***" + customerSession);
    	
    	ReferFriendWrapper referFriendWrapper = (ReferFriendWrapper)RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_FLATBHK_LIST.getUrl(), customer, ReferFriendWrapper.class);
        return referFriendWrapper;
    }
    
    /**
     * 
     * @param customer
     * @return referFriendWrapper
     * @throws SessionTimeoutException
     * @throws InSufficeientInputException
     */
    @RequestMapping(value="/getReferencesSecurityPolicies.spring",method=RequestMethod.POST)
    public Result getReferencesSecurityPolicies(@RequestBody Customer customer) throws SessionTimeoutException, InSufficeientInputException {
    	String cacheKey=customer.getSessionKey();
    	CustomerSession customerSession=this.customerSessionValidate(cacheKey, customer.getDeviceId());
    	LOGGER.debug("*** The CustomerSession Object ***" + customerSession);
    	ReferFriendWrapper referFriendWrapper = (ReferFriendWrapper)RestApiUtility.sendJSONPostRequest(CustomerServiceUrls.GET_REFERENCES_SECURITY_POLCIES.getUrl(), customer, ReferFriendWrapper.class);
        return referFriendWrapper;
    }
    /*******  this method used getTicketOwners.spring******/
   	@RequestMapping(value="/getEmployees.spring", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
   	public Result getTicketOwners(@RequestBody Customer customer) throws InSufficeientInputException, SessionTimeoutException {
   		LOGGER.info("*** The control is inside of the getTicketOwners in TicketReportingRestController ***");
   		
   		if(Util.isNotEmptyObject(customer.getSessionKey())) {
   			TicketReport ticketReport = new TicketReport();
   			ticketReport.setDepartmentIds(customer.getDepartmentIds());
   			ticketReport.setSiteIds(customer.getSiteIds());
   			String cacheKey=customer.getSessionKey();
   	    	CustomerSession customerSession=this.customerSessionValidate(cacheKey, customer.getDeviceId());
   	    	LOGGER.debug("*** The CustomerSession Object ***" + customerSession);
   			return (Result) RestApiUtility.sendJSONPostRequest(EmployeeServiceUrls.GET_TICKET_OWNERS.getUrl(), ticketReport, Result.class);
   		}else {
   			List<String> errors = new ArrayList<>();
   			errors.add("The insufficeint input is given "+customer);
   			throw new InSufficeientInputException(errors);
   		}
   	}
}
