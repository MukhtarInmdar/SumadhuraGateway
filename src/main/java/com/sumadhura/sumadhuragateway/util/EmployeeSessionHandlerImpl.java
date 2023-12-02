package com.sumadhura.sumadhuragateway.util;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * EmployeeSessionHandlerImpl class is for the implementation of SessionHandler
 * Interface, for simplified implementation of SessionHandler to
 * createEmployeeSession & updateEmployeeSession & getEmployeeSession &
 * deleteEmployeeSession & findAllEmployeeSession & expireEmployeeSession.
 * 
 * @author Venkat_Koniki
 * @since 18.03.2019
 * @time 04:19PM
 */
 @Component("EmployeeSessionHandlerImpl")
public class EmployeeSessionHandlerImpl implements SessionHandler {

	private final static Logger logger = Logger.getLogger(EmployeeSessionHandlerImpl.class);

	@Autowired(required = true)
	@Qualifier("employeeRedisTemplate")
	private RedisTemplate<String, Object> employeeRedisTemplate;

	private String HashKey = new String("employee");

	@Override
	public void createSession(String sessionKey, Object entity) {
		logger.info("**** The control is inside the createSession method in EmployeeSessionHandlerImpl *****");
		this.employeeRedisTemplate.opsForHash().put(sessionKey,HashKey,entity);
		setMaxInactiveInterval(sessionKey,SessionTimeout.HIGH);
	}

	@Override
	public boolean isSessionValid(String sessionKey) {
		logger.info("**** The control is inside the isSessionValid method in EmployeeSessionHandlerImpl *****");
		return this.employeeRedisTemplate.opsForHash().hasKey(sessionKey, HashKey);
	}

	@Override
	public Object getSession(String sessionKey) {
		logger.info("**** The control is inside the getSession method in EmployeeSessionHandlerImpl *****");
		setMaxInactiveInterval(sessionKey,SessionTimeout.HIGH);
		return this.employeeRedisTemplate.opsForHash().get(sessionKey, HashKey);
	}

	@Override
	public void deleteSession(String sessionKey) {
		logger.info("**** The control is inside the deleteSession method in EmployeeSessionHandlerImpl *****");
		this.employeeRedisTemplate.opsForHash().delete(sessionKey, HashKey);
	}

	private void deleteMultipleSession(String sessionKey,Object[] HashKeys) {
		logger.info("**** The control is inside the deleteMultipleSession method in CustomerSessionHandlerImpl *****");
		this.employeeRedisTemplate.opsForHash().delete(sessionKey, HashKeys);
	}

	@Override
	public void flushAll() {
		logger.info("**** The control is inside the flushAll method in EmployeeSessionHandlerImpl *****");
		this.employeeRedisTemplate.getConnectionFactory().getConnection().flushAll();
	}

	@Override
	public void expire(String sessionKey,long timeout) {
		logger.info("**** The control is inside  expire method in EmployeeSessionHandlerImpl *****");
		employeeRedisTemplate.expire(sessionKey,timeout, TimeUnit.MILLISECONDS);
	}

	@Override
	public Long size(String sessionKey) {
		logger.info("**** The control is inside the size method in EmployeeSessionHandlerImpl *****");
		return this.employeeRedisTemplate.opsForHash().size(sessionKey);
	}

	@Override
	public Map<Object, Object> findAll(String sessionKey) {
		logger.info("**** The control is inside the findAll method in EmployeeSessionHandlerImpl *****");
		return this.employeeRedisTemplate.opsForHash().entries(sessionKey);
	}

	@Override
	public void flushClient(String sessionKey) {
		logger.info("**** The control is inside the flushClient method in EmployeeSessionHandlerImpl *****");
		Map<Object, Object> sessionMap = findAll(sessionKey);
		Set<Object> haskKeySet = sessionMap.keySet();
		deleteMultipleSession(sessionKey,haskKeySet.toArray());
	}
	
	@Override
	public Long getExpire(String sessionKey) {
		logger.info("**** The control is inside the flushClient method in EmployeeSessionHandlerImpl *****");
		return employeeRedisTemplate.getExpire(sessionKey,TimeUnit.MILLISECONDS);
	}
	
	@Override
	public void createOtpSession(String key, Object entity) {
		logger.info("**** The control is inside the flushClient method in EmployeeSessionHandlerImpl *****");
		this.employeeRedisTemplate.opsForHash().put(key,HashKey,entity);
		setMaxInactiveInterval(key,SessionTimeout.HIGH);
	}
	
	@Override
	public Object getOtpSession(String sessionKey) {
		logger.info("**** The control is inside the getSession method in EmployeeSessionHandlerImpl *****");
		setMaxInactiveInterval(sessionKey,SessionTimeout.HIGH);
		return this.employeeRedisTemplate.opsForHash().get(sessionKey, HashKey);
	}
	
	private void setMaxInactiveInterval(String sessionKey,SessionTimeout timeout) {
		logger.info("*** The Control is inside the setMaxInactiveInterval in EmployeeSessionHandlerImpl ****");
	     expire(sessionKey,timeout.expiryTime);
		logger.info("*** The Control is at end of the setMaxInactiveInterval in EmployeeSessionHandlerImpl ****");
	}
	
}
