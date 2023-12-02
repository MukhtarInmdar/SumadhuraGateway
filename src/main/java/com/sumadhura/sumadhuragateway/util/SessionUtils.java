package com.sumadhura.sumadhuragateway.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sumadhura.sumadhuragateway.dto.CustomerSession;
import com.sumadhura.sumadhuragateway.dto.Device;
import com.sumadhura.sumadhuragateway.dto.Result;
import com.sumadhura.sumadhuragateway.employeeservice.dto.Employee;
import com.sumadhura.sumadhuragateway.employeeservice.dto.EmployeeSession;


/**
 * SessionUtils class provides some utility methods for session.
 *  
 * @author Venkat_Koniki
 * @since 18.03.2019
 * @time 04:19PM
 */

@Component("SessionUtils")
public class SessionUtils {
	
private final static Logger LOGGER = Logger.getLogger(SessionUtils.class);	
	
	@Autowired
	@Qualifier("CustomerSessionHandlerImpl")
	private SessionHandler customerSessionHandlerImpl;
	
	@Autowired
	@Qualifier("EmployeeSessionHandlerImpl")
	private SessionHandler employeeSessionHandlerImpl;
	
	@Value("${encryptedKey}")
	private  String encryptedKey ;
	
	public String generateSessionKey(Result result,int modeOf){
		LOGGER.info("**** The control is inside the generateSessionKey method in SessionUtils ****");
		CustomerSession customer = (CustomerSession) result ;
	    Long custId = customer.getCustomerId();
		Device device =  customer.getDevice();
		String uuid = device.getUuid();
		String deviceToken = device.getDeviceToken();
		String serialNo = device.getSerialNo();
		Long flatId = customer.getFlat().getFlatId();
		
		String sessionKey = createSessionKey(uuid,deviceToken,serialNo,flatId,custId,modeOf);
		return sessionKey;
	}
	public String generateEmployeeSessionKey(EmployeeSession employeeSession,int modeOf) {
		LOGGER.info("**** The control is inside the generateEmployeeSessionKey method in SessionUtils ****");
		Employee employee = employeeSession.getLoginResponse().getEmployee();
		Long empId = employee.getEmployeeId();
		String empName = employee.getEmployeeName();
		String email = employee.getEmail();
		com.sumadhura.sumadhuragateway.employeeservice.dto.Department department = employeeSession.getLoginResponse().getDepartments().get(0);
		Long deptId = department.getDepartmentId();
		String deptName = department.getDepartmentName();
		String roleName = employeeSession.getLoginResponse().getDepartments().get(0).getName();
		Long roleId = employeeSession.getLoginResponse().getDepartments().get(0).getRoleId();
		String sessionKey = createEmployeeSessionKey(empId,empName,email,deptId,deptName,roleId,roleName,modeOf);
		LOGGER.debug("**** The Sessionkey object is ****"+sessionKey);
		return sessionKey;
	}
	private String createEmployeeSessionKey(Long empId,String empName,String email,Long deptId,String deptName,Long roleId,String roleName,int modeOf) {
		LOGGER.info("**** The control is inside the createEmployeeSessionKey method in SessionUtils ****");
		StringBuilder sessionKey = null;
		
		if (empName.length() < 5 && email.length() < 5 && deptName.length() < 5 && roleName.length() < 5) {
			empName  = Util.fill(empName,5);
			email    = Util.fill(email, 5);
			deptName = Util.fill(deptName, 5);
			roleName = Util.fill(roleName, 5);
		}
		
		
		switch(modeOf){
		case 1 :
		sessionKey = new StringBuilder()
				  .append(empId)
				  .append("-")
				  .append(empName.substring(0,4))
				  .append("-")
				  .append(email.substring(0,4))
				  .append("-")
		          .append(deptId)
		          .append("-")
		          .append(deptName.substring(0,4))
		          .append("-")
		          .append(roleId)
		          .append("-")
		          .append(roleName.substring(0,4))
		          .append("-")
		          .append("1");
		           break;
		case 2 :
			sessionKey = new StringBuilder()
		      .append(deptId)
			  .append("-")
			  .append(deptName.substring(0,4))
			  .append("-")
			  .append(empId)
			  .append("-")
			  .append(empName.substring(0,4))
			  .append("-")
			  .append(email.substring(0,4))
			  .append("-")
			  .append(roleId)
	          .append("-")
	          .append(roleName.substring(0,4))
	          .append("-")
	          .append("2");
	           break;
		case 3 :
			sessionKey = new StringBuilder()
			  .append(roleId)
			  .append("-")
			  .append(roleName.substring(0,4))  
			  .append("-")
			  .append(empId)
			  .append("-")
			  .append(empName.substring(0,4))
			  .append("-")
			  .append(email.substring(0,4))
			  .append("-")
	          .append(deptId)
	          .append("-")
	          .append(deptName.substring(0,4))
	          .append("-")
	          .append("3");
	           break;
		default:
			sessionKey = new StringBuilder()
			  .append(deptId)
			  .append("-")
			  .append(empName.substring(0,4))
			  .append("-")
			  .append(email.substring(0,4))
			  .append("-")
			  .append(empId)
	          .append("-")
	          .append(deptName.substring(0,4))
	          .append("-")
	          .append(roleName.substring(0,4))
	          .append("-")
	          .append(roleId)
			  .append("-")
	          .append("4");
	           break;
		}
		
        LOGGER.info("**** The random sessionkey generated for the user is *****"+sessionKey);
		return sessionKey.toString();
	}
	private String createSessionKey(String uuid,String deviceToken,String serialNo,Long flatId,Long custId,int modeOf) {
		LOGGER.info("**** The control is inside the createSessionKey method in SessionUtils ****");
		StringBuilder sessionKey = null;
		
		if(serialNo.length()<6 && deviceToken.length()<6 && uuid.length()<6 ) {
			serialNo = Util.fill(serialNo,10);
			deviceToken = Util.fill(deviceToken,10);
			uuid = Util.fill(uuid,10);
		}
		
		switch(modeOf){
		case 1 :
		sessionKey = new StringBuilder()
				  .append(uuid.substring(0,6))
				  .append("-")
				  .append(deviceToken.substring(0,6))
				  .append("-")
		          .append(serialNo.substring(0,6))
		          .append("-")
		          .append(flatId)
		          .append("-")
		          .append(custId)
		          .append("-")
		          .append("udsfc")
		          .append("-")
		          .append("1");
		           break;
		case 2 :
		sessionKey = new StringBuilder()
				 .append(custId)
				 .append("-")
				 .append(flatId)
				 .append("-")
				 .append(serialNo.substring(0,6))
				 .append("-")
				 .append(deviceToken.substring(0,6))
				 .append("-")
				 .append(uuid.substring(0,6))
		         .append("-")
		         .append("cfsdu")
		         .append("-")
		         .append("2");
		         break;
		case 3 :
		sessionKey = new StringBuilder()
				 .append(serialNo.substring(0,6))
				 .append("-")
				 .append(deviceToken.substring(0,6))
				 .append("-")
				 .append(uuid.substring(0,6))
		         .append("-")
		         .append(custId)
				 .append("-")
				 .append(flatId)
				 .append("-")
		         .append("sducf")
		         .append("-")
		         .append("3");
		          break;
		default:
		sessionKey = new StringBuilder()
			   .append(custId)
			   .append("-")
			   .append(serialNo.substring(0,6))
			   .append("-")
			   .append(deviceToken.substring(0,6))
			   .append("-")
			   .append(uuid.substring(0,6))
	           .append("-")
			   .append(flatId)
			   .append("-")
	           .append("csduf")
	           .append("-")
		       .append("4");
		}
		
        LOGGER.info("**** The random sessionkey generated for the user is *****"+sessionKey);
		return sessionKey.toString();
	}
	public String encryptSessionKey(String sessionKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		 LOGGER.info("**** The control is inside the encryptSessionKey in SessionUtils *****"+encryptedKey);
		 String Key = AESEncryptDecrypt.convertKeyToHex(encryptedKey);
		 String encryptedSessionKey =  AESEncryptDecrypt.encrypt(sessionKey,Key);
		 LOGGER.info("*** The encrypted sessionkey ****"+encryptedSessionKey);
		 return encryptedSessionKey;
	}
	
	public boolean validateSession(String sessionKey,String deviceId) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		 LOGGER.info("**** The control is inside the validate session in SessionUtils *****");
		  //  String decryptedSessionKey = decryptSessionKey(sessionKey);
			boolean sessionStatus = customerSessionHandlerImpl.isSessionValid(sessionKey);
			LOGGER.info("**** The session status for  customerservice is ****"+sessionStatus);
		if (sessionStatus) {
			CustomerSession customer = (CustomerSession) customerSessionHandlerImpl.getSession(sessionKey);
			if (customer.getDevice().getDeviceToken().equals(deviceId)) {
				int modeOf = Integer.valueOf(sessionKey.substring(sessionKey.length() - 1));
				LOGGER.info("**** The session validation is completed succesfully ****");
				return sessionKey.equals(generateSessionKey(customer, modeOf));
			}else {
				return false;
			}
		}
		 LOGGER.info("**** The session is not valid (some thing like request forgery attack) ****");
		return false;
	}
	public boolean validateEmployeeSession(String decryptedSessionKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		 LOGGER.info("**** The control is inside the validateEmployeeSession in SessionUtils *****");
		 // String decryptedSessionKey = decryptSessionKey(sessionKey);
		   EmployeeSession employeeSession = (EmployeeSession) employeeSessionHandlerImpl.getSession(decryptedSessionKey);
			if (Util.isNotEmptyObject(employeeSession)) {
				int modeOf = Integer.valueOf(decryptedSessionKey.substring(decryptedSessionKey.length() - 1));
				LOGGER.info("**** The session validation is completed succesfully ****");
				return decryptedSessionKey.equals(generateEmployeeSessionKey(employeeSession, modeOf)); 
			}else {
				return false;
			}
	}
	public String decryptSessionKey(String sessionKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		 LOGGER.info("**** The control is inside the decryptSessionKey in SessionUtils *****");
		 String decryptedSessionKey = AESEncryptDecrypt.decrypt(sessionKey,AESEncryptDecrypt.convertKeyToHex(encryptedKey));
		 LOGGER.info("**** The decryptSessionKey in SessionUtils *****"+decryptedSessionKey);
	     return decryptedSessionKey;
	}
	
	public int generateModeOf() {
		 LOGGER.info("**** The control is inside the generateModeOf in SessionUtils *****");
		 return new Random().nextInt(4);
	}
	
}
