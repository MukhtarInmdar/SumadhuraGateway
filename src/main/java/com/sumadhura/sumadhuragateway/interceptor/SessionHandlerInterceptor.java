package com.sumadhura.sumadhuragateway.interceptor;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sumadhura.sumadhuragateway.util.SessionUtils;


public class SessionHandlerInterceptor extends HandlerInterceptorAdapter {

	private final static Logger logger = Logger.getLogger(SessionHandlerInterceptor.class);	
	
	@Autowired
	@Qualifier("SessionUtils")
	private SessionUtils sessionUtils;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		logger.info("*** The Control is inside the preHandle in SessionHandlerInterceptor ****");
		/*  String json = null; 
		  StringBuffer jsonData = new StringBuffer();
		  BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  try {
			while ((json = reader.readLine()) != null)
			jsonData.append(json);
			logger.info("*** The data supplied for the service ****"+jsonData);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Object mapper instance
		 */
		  
	   /* ObjectMapper mapper = new ObjectMapper(); // Convert JSON to POJO Customer
		customer = mapper.readValue(json, Customer.class);*/
		
		
		
		/*boolean status = false;
		try {
	       status = sessionUtils.authenticateSession(request,response);
		}catch(Exception ex) {
			logger.info("**** The Exception information *****"+ex);
		}
		return status;*/
		
		/*try {
		// if request is coming from customerservice app.
		if(domain.equalsIgnoreCase("/sumadhuragateway")) {
			// if sessionId is exists. then verify session.
			if(sessionKey !=null) {
				return 	customerSessionHandlerImpl.isSessionValid(sessionKey);
			// if sessionId is not exists. then create session.
			}else {
				Customer customer = new Customer();
				customer.setUsername("venkat");
				customer.setAge(21);
				customer.setSex("male");
				
			    Customer customer1 = new Customer();
				customer1.setUsername("venkat1");
				customer1.setAge(22);
				customer1.setSex("male");
				
				Customer customer2 = new Customer();
				customer2.setUsername("venkat2");
				customer2.setAge(23);
				customer2.setSex("male");
				
				//customerSessionHandlerImpl.expire();
				customerSessionHandlerImpl.createSession(customer.getAge().toString(),customer);
				// Thread.currentThread().sleep(500);
				// logger.info(customerSessionHandlerImpl.isSessionValid(customer.getUsername()));
				customerSessionHandlerImpl.createSession(customer1.getAge().toString(),customer1);
				// Thread.currentThread().sleep(500);
				// logger.info(customerSessionHandlerImpl.isSessionValid(customer.getUsername()));
				// logger.info(customerSessionHandlerImpl.isSessionValid(customer1.getUsername()));
				customerSessionHandlerImpl.createSession(customer2.getAge().toString(),customer2);
				//logger.info("**** size *****"+customerSessionHandlerImpl.size()); 
				//Thread.currentThread().sleep(500);
				// logger.info(customerSessionHandlerImpl.isSessionValid(customer.getUsername()));
				// logger.info(customerSessionHandlerImpl.isSessionValid(customer1.getUsername()));	
				// logger.info(customerSessionHandlerImpl.isSessionValid(customer2.getUsername()));	
			
				
				 
				logger.info(customerSessionHandlerImpl.isSessionValid(customer.getAge().toString()));
				logger.info(customerSessionHandlerImpl.isSessionValid(customer1.getAge().toString()));
				logger.info(customerSessionHandlerImpl.isSessionValid(customer2.getAge().toString()));
				logger.info(customerSessionHandlerImpl.getSession(customer.getAge().toString()));
				logger.info(customerSessionHandlerImpl.getSession(customer1.getAge().toString()));
				logger.info(customerSessionHandlerImpl.getSession(customer2.getAge().toString()));
				logger.info("**** size *****"+customerSessionHandlerImpl.size(customer.getAge().toString()));
				logger.info("**** size *****"+customerSessionHandlerImpl.size(customer1.getAge().toString()));
				logger.info("**** size *****"+customerSessionHandlerImpl.size(customer2.getAge().toString()));
				
				// customerSessionHandlerImpl.expire(customer.getAge().toString(),100);
				// customerSessionHandlerImpl.expire(customer.getAge().toString(),1000);
				// customerSessionHandlerImpl.expire(customer.getAge().toString(),2000);
				 
				// Thread.sleep(1000);
				 
				 logger.info("***** to get expire time ****** "+customerSessionHandlerImpl.getExpire(customer.getAge().toString()));
				
				// customerSessionHandlerImpl.expire(customer.getAge().toString(),1000);
				 
				 logger.info("***** to get expire time ****** "+customerSessionHandlerImpl.getExpire(customer.getAge().toString()));
				 
				 // customerSessionHandlerImpl.expire(customer1.getAge().toString(),1000);
				// customerSessionHandlerImpl.expire(customer2.getAge().toString(),2000);
				// Thread.currentThread().sleep(100);
				 
				 for(int i = 0;i<=10;i++) {
					logger.info(customerSessionHandlerImpl.isSessionValid(customer.getAge().toString()));
					logger.info(customerSessionHandlerImpl.isSessionValid(customer1.getAge().toString()));
					logger.info(customerSessionHandlerImpl.isSessionValid(customer2.getAge().toString()));
					logger.info(customerSessionHandlerImpl.getSession(customer.getAge().toString()));
					logger.info(customerSessionHandlerImpl.getSession(customer1.getAge().toString()));
					logger.info(customerSessionHandlerImpl.getSession(customer2.getAge().toString()));
					logger.info("**** size *****"+customerSessionHandlerImpl.size(customer.getAge().toString()));
					logger.info("**** size *****"+customerSessionHandlerImpl.size(customer1.getAge().toString()));
					logger.info("**** size *****"+customerSessionHandlerImpl.size(customer2.getAge().toString()));
				 }
					
				
				// logger.info("**** findAll *****"+customerSessionHandlerImpl.findAll());
		      //  Thread.currentThread().sleep(100);
			
//				logger.info(customerSessionHandlerImpl.isSessionValid(customer.getUsername()));
//				logger.info(customerSessionHandlerImpl.isSessionValid(customer1.getUsername()));
//				logger.info(customerSessionHandlerImpl.isSessionValid(customer2.getUsername()));
//				logger.info(customerSessionHandlerImpl.getSession(customer.getUsername()));
//				logger.info(customerSessionHandlerImpl.getSession(customer1.getUsername()));
//				logger.info(customerSessionHandlerImpl.getSession(customer2.getUsername()));
//				
				
			//	employeeSessionHandlerImpl.createSession(customer.getUsername(),customer);
			//	employeeSessionHandlerImpl.createSession(customer1.getUsername(),customer1);
			//	employeeSessionHandlerImpl.createSession(customer2.getUsername(),customer2);
				
				
			//	logger.info("**** size *****"+employeeSessionHandlerImpl.size());
			//	logger.info("**** size *****"+customerSessionHandlerImpl.size());
				
			//	logger.info(employeeSessionHandlerImpl.getSession(customer.getUsername()));
			//	logger.info(employeeSessionHandlerImpl.getSession(customer1.getUsername()));
			//	logger.info(employeeSessionHandlerImpl.getSession(customer2.getUsername()));
				
				 new Thread() {
					public void run() {
						for(int i = 0 ; i < 10 ; i++) {
							try {
								Thread.sleep(100);
								logger.info("**** size *****"+customerSessionHandlerImpl.size());
								logger.info(customerSessionHandlerImpl.getSession(customer1.getUsername()));
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							logger.info("**** size *****"+customerSessionHandlerImpl.size());
							
						}
					}
				}.start();
				
				// Thread.sleep(1000);
			//	logger.info("**** final size *****"+customerSessionHandlerImpl.size());
				
			//	customerSessionHandlerImpl.flushClient();
		
				
			//	logger.info("**** size *****"+employeeSessionHandlerImpl.size());
			//	logger.info("**** size *****"+customerSessionHandlerImpl.size());
				
			//	logger.info(employeeSessionHandlerImpl.getSession(customer.getUsername()));
			//	logger.info(employeeSessionHandlerImpl.getSession(customer1.getUsername()));
			//	logger.info(employeeSessionHandlerImpl.getSession(customer2.getUsername()));
				
			//	employeeSessionHandlerImpl.flushClient();
				
		
				
			//	logger.info("**** size *****"+employeeSessionHandlerImpl.size());
			//	logger.info("**** size *****"+customerSessionHandlerImpl.size());
				
				
			//	logger.info(employeeSessionHandlerImpl.getSession(customer.getUsername()));
			//	logger.info(employeeSessionHandlerImpl.getSession(customer1.getUsername()));
			//	logger.info(employeeSessionHandlerImpl.getSession(customer2.getUsername()));
				
			//	logger.info(customerSessionHandlerImpl.getSession(customer.getUsername()));
			//	logger.info(customerSessionHandlerImpl.getSession(customer1.getUsername()));
			//	logger.info(customerSessionHandlerImpl.getSession(customer2.getUsername()));
			}
		}
	}catch(Exception ex) {
		logger.info(ex.toString());
	} */
		return true;
    }

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("*** The Control is inside the postHandle in SessionHandlerInterceptor ****");
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("*** The Control is inside the afterCompletion in SessionHandlerInterceptor ****");
		super.afterCompletion(request, response, handler, ex);
	}

}
