/**
 * 
 */
package com.sumadhura.sumadhuragateway.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
  

/**
 * ManualReddisClearUtil class is for the clear session in reddis manually.
 *
 * @author Venkat_Koniki
 * @since 19.03.2019
 * @time 05:10PM
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class ManualReddisClearUtil {

	private final static Logger logger = Logger.getLogger(ManualReddisClearUtil.class);
	@SuppressWarnings("unused")
	private static RedisTemplate customerRedisTemplate ;
	@SuppressWarnings("unused")
	private static RedisTemplate employeeRedisTemplate ;

	public static void main(String... strings) {

		//logger.info("****** The control is inside the ManualReddisClearUtil class ********"+"F3A36C3E091966BDF18B43F248389866".length());

/*		         String string = "SUAMSCUSTOMERAPP";
				 char[] ch = string.toCharArray();
			      StringBuilder builder = new StringBuilder();
			      for (char c : ch) {
			         // Step-2 Use %H to format character to Hex
			         String hexCode=String.format("%H", c);
			         builder.append(hexCode);
			      }
			      System.out.println("Hex value is " + builder); */
		
		//  String string = "F89FBEDEE8E4C7815EE5CA39385F2928EF304039D56461F45512198EE9EF698565C95525C15F17C36CC0FD063190C731";
		//  System.out.println("last character: " +string.length()); 
		
				 
	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
	    Date date = new Date();  
	    System.out.println(formatter.format(date));  
		
		/*String input = "http://localhost:8080/sumadhuragateway/noncustomer/site"; 
		//boolean isFound = input.indexOf("noncustomer") !=-1? true: false; //true
		boolean isFound = input.contains("noncustomer")? true: false ;
		System.out.println(isFound);*/
		
		
		/*FileSystemXmlApplicationContext contextobj = new FileSystemXmlApplicationContext(
				"D:\\VENKAT\\REDDIS PROJECT\\src\\main\\webapp\\WEB-INF\\spring\\root-context.xml");
		customerRedisTemplate = contextobj.getBean("customerRedisTemplate", RedisTemplate.class);
		employeeRedisTemplate = contextobj.getBean("employeeRedisTemplate", RedisTemplate.class);
*/
	}

	@SuppressWarnings("unused")
	private static void flushCustomerRedisTemplate(RedisTemplate customerRedisTemplate) {

		logger.info("****** The control is inside the flushCustomerRedisTemplate method ********");
		
	//	this.customerRedisTemplate.opsForHash().entries(key);
		
		
	}

	
	@SuppressWarnings("unused")
	private static void flushEmployeeRedisTemplate(RedisTemplate employeeRedisTemplate) {

		logger.info("****** The control is inside the flushEmployeeRedisTemplate method ********");
		
	}
	

}

/*
 * AnnotationConfigApplicationContext context = new
 * AnnotationConfigApplicationContext();
 * context.register(CustomerSessionHandlerImpl.class,EmployeeSessionHandlerImpl.
 * class); context.refresh();
 * System.out.println(context.getBean("CustomerSessionHandlerImpl"));
 * System.out.println(context.getBean("EmployeeSessionHandlerImpl"));
 */