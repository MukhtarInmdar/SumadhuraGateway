package com.sumadhura.sumadhuragateway.util;

import java.util.ArrayList;
import java.util.List;

public enum HttpStatus  {

	failure(){
		@Override
		public Integer getResponceCode() {
			return 800;
		}
		@Override
		public String getStatus() {
			return "failure";
		}
		@Override
		public List<String> getErrors() {
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("*** failure ***");
			return errors;
		}
	},
 insufficientInput(){
    	/**
    	 * @return the responceCode
    	 */
    	public Integer getResponceCode() {
    		return 600;
    	}
    	/**
    	 * @return the description
    	 */
    	public String getStatus() {
    		return "Insufficient Input is given.";
    	}
    	/**
    	 * @return the errorMsgs
    	 */
    	public List<String> getErrors() {
    		List<String> errorMasgList = new ArrayList<String>();
    		errorMasgList.add("Insufficient Input is given.");
    		return errorMasgList;
    	}
    	
    },
 
 otpNotSend(){
    	/**
    	 * @return the responceCode
    	 */
    	public Integer getResponceCode() {
    		return 539;
    	}
    	/**
    	 * @return the description
    	 */
    	public String getStatus() {
    		return "OTP  Not sent.";
    	}
    	/**
    	 * @return the errorMsgs
    	 */
    	public List<String> getErrors() {
    		List<String> errorMasgList = new ArrayList<String>();
    		errorMasgList.add("OTP Not sent.");
    		return errorMasgList;
    	}	
    },
    IoError(){
    	/**
    	 * @return the responceCode
    	 */
    	public Integer getResponceCode() {
    		return 540;
    	}
    	/**
    	 * @return the description
    	 */
    	public String getStatus() {
    		return "Connection Exception";
    	}
    	/**
    	 * @return the errorMsgs
    	 */
    	public List<String> getErrors() {
    		List<String> errorMasgList = new ArrayList<String>();
    		errorMasgList.add("Connection Exception.");
    		return errorMasgList;
    	}	
    },
    customerHaveNoFlat(){
    	/**
    	 * @return the responceCode
    	 */
    	public Integer getResponceCode() {
    		return 537;
    	}
    	/**
    	 * @return the description
    	 */
    	public String getStatus() {
    		return "This account has been inactivated.";
    	}
    	/**
    	 * @return the errorMsgs
    	 */
    	public List<String> getErrors() {
    		List<String> errorMasgList = new ArrayList<String>();
    		errorMasgList.add("This account has been inactivated.");
    		return errorMasgList;
    	}
    },
	 authenticationError(){
    	/**
    	 * @return the responceCode
    	 */
    	public Integer getResponceCode() {
    		return 535;
    	}
    	/**
    	 * @return the description
    	 */
    	public String getStatus() {
    		return "Invalid Credentials,Please verify them and retry.";
    	}
    	/**
    	 * @return the errorMsgs
    	 */
    	public List<String> getErrors() {
    		List<String> errorMasgList = new ArrayList<String>();
    		errorMasgList.add("Invalid Credentials,Please verify them and retry.");
    		return errorMasgList;
    	}
    	
    },
	
	sessiontimeout(){
		@Override
		public Integer getResponceCode() {
			return 440;
		}
		@Override
		public String getStatus() {
			return "Your Session Timed Out.";
		}
		@Override
		public List<String> getErrors() {
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("Your Session Timed Out.");
			return errors;
		}		
	},   
	 exceptionRaisedInFlow(){
	    	/**
	    	 * @return the responceCode
	    	 */
	    	public Integer getResponceCode() {
	    		return 700;
	    	}
	    	/**
	    	 * @return the description
	    	 */
	    	public String getStatus() {
	    		return "Internal Application Error Please try again Later.";
	    	}
	    	/**
	    	 * @return the errorMsgs
	    	 */
	    	public List<String> getErrors() {
	    		List<String> errorMasgList = new ArrayList<String>();
	    		errorMasgList.add("Internal Application Error Please try again Later.");
	    		return errorMasgList;
	    	}
	    	
	    },
	 success(){
	    	/**
	    	 * @return the responceCode
	    	 */
	    	public Integer getResponceCode() {
	    		return 200;
	    	}
	    	/**
	    	 * @return the description
	    	 */
	    	public String getStatus() {
	    		return "sucess";
	    	}
	    	/**
	    	 * @return the errorMsgs
	    	 */
	    	public List<String> getErrors() {
	    		List<String> errorMasgList = new ArrayList<String>();
	    		errorMasgList.add("sucess");
	    		return errorMasgList;
	    	}
	    	
	    },
	 otpSendSuccessfully(){
	    	/**
	    	 * @return the responceCode
	    	 */
	    	public Integer getResponceCode() {
	    		return 534;
	    	}
	    	/**
	    	 * @return the description
	    	 */
	    	public String getStatus() {
	    		return "OTP sent successfully.";
	    	}
	    	/**
	    	 * @return the errorMsgs
	    	 */
	    	public List<String> getErrors() {
	    		List<String> errorMasgList = new ArrayList<String>();
	    		errorMasgList.add("OTP sent successfully.");
	    		return errorMasgList;
	    	}	
	    },
	   otpVerifiedSucessfully(){
	    	/**
	    	 * @return the responceCode
	    	 */
	    	public Integer getResponceCode() {
	    		return 545;
	    	}
	    	/**
	    	 * @return the description
	    	 */
	    	public String getStatus() {
	    		return "OTP verified successfully.";
	    	}
	    	/**
	    	 * @return the errorMsgs
	    	 */
	    	public List<String> getErrors() {
	    		List<String> errorMasgList = new ArrayList<String>();
	    		errorMasgList.add("OTP verified successfully.");
	    		return errorMasgList;
	    	}
	    },
	   incorrectOTP(){
	    	/**
	    	 * @return the responceCode
	    	 */
	    	public Integer getResponceCode() {
	    		return 546;
	    	}
	    	/**
	    	 * @return the description
	    	 */
	    	public String getStatus() {
	    		return "The OTP entered is Incorrect.";
	    	}
	    	/**
	    	 * @return the errorMsgs
	    	 */
	    	public List<String> getErrors() {
	    		List<String> errorMasgList = new ArrayList<String>();
	    		errorMasgList.add("The OTP entered is Incorrect or Invalid.");
	    		return errorMasgList;
	    	}
	    	
	    	
	    },
	   encryptSessionKey(){
	    	/**
	    	 * @return the responceCode
	    	 */
	    	public Integer getResponceCode() {
	    		return 547;
	    	}
	    	/**
	    	 * @return the description
	    	 */
	    	public String getStatus() {
	    		return "In correct Session Key Encryption.";
	    	}
	    	/**
	    	 * @return the errorMsgs
	    	 */
	    	public List<String> getErrors() {
	    		List<String> errorMasgList = new ArrayList<String>();
	    		errorMasgList.add("In correct Session Key Encryption.");
	    		return errorMasgList;
	    	}
	    },
	   twoOrMoreDepartments(){
	    	/**
	    	 * @return the responceCode
	    	 */
	    	public Integer getResponceCode() {
	    		return 1002;
	    	}
	    	/**
	    	 * @return the description
	    	 */
	    	public String getStatus() {
	    		return " Employee is registered with two or more departments ";
	    	}
	    	/**
	    	 * @return the errorMsgs
	    	 */
	    	public List<String> getErrors() {
	    		List<String> errorMasgList = new ArrayList<String>();
	    		errorMasgList.add("The Employee is registered with two or more departments.");
	    		return errorMasgList;
	    	}
	    },
	   ONE_MOBILE_NUM_MULTI_EMP(){
	    	/**
	    	 * @return the responceCode
	    	 */
	    	public Integer getResponceCode() {
	    		return 501;
	    	}
	    	/**
	    	 * @return the description
	    	 */
	    	public String getStatus() {
	    		return " On this Mobile Number multiple Employees are registered.";
	    	}
	    	/**
	    	 * @return the errorMsgs
	    	 */
	    	public List<String> getErrors() {
	    		List<String> errorMasgList = new ArrayList<String>();
	    		errorMasgList.add("On this Mobile Number multiple Employees are registered.");
	    		return errorMasgList;
	    	}
	    },
	   RegisteredNonCustomer(){
			/**
			 * @return the responce Code
			 */
	    	public Integer getResponceCode() {
	    		return 301;
	    	}
	    	/**
	    	 * @return the status
	    	 */
	    	public String getStatus() {
	    		return "Registered Active NonCustomer!";
	    	}
	    	/**
	    	 * @return the errorMsgs
	    	 */
	    	public List<String> getErrors() {
	    		List<String> errorMsgs = new ArrayList<String>();
	    		errorMsgs.add("Registered Active NonCustomer!");
	    		return errorMsgs;
	    	}
		},
	;
		
	private Integer responceCode;
	private String status;
	private List<String> errors;

	/**
	 * @return the responceCode
	 */
	public Integer getResponceCode() {
		return responceCode;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @return the errors
	 */
	public List<String> getErrors() {
		return errors;
	}
	
	
	
	
}
