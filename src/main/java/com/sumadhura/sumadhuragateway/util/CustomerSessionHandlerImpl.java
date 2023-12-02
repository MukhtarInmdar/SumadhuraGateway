/**
 * 
 */
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
 * CustomerSessionHandlerImpl class is for the implementation of SessionHandler
 * Interface, for simplified implementation of SessionHandler to
 * createCustomerSession & updateCustomerSession & getCustomerSession &
 * deleteCustomerSession & flushCustomerSession.& findAllCustomerSessions & ExpireCustomerSession.
 * & expireCustomerSessions.
 * 
 * @author Venkat_Koniki
 * @since 18.03.2019
 * @time 04:19PM
 */
@Component("CustomerSessionHandlerImpl")
public  class CustomerSessionHandlerImpl implements SessionHandler {

	private final static Logger logger = Logger.getLogger(CustomerSessionHandlerImpl.class);

	@Autowired(required = true)
	@Qualifier("customerRedisTemplate")
	private RedisTemplate<String, Object> customerRedisTemplate;
	private String HashKey = new String("customer");

	@Override
	public void createSession(String sessionKey, Object entity) {
		logger.info("**** The control is inside the createSession method in CustomerSessionHandlerImpl *****");
		this.customerRedisTemplate.opsForHash().put(sessionKey,HashKey,entity);
		setMaxInactiveInterval(sessionKey,SessionTimeout.HIGH);
	}

	@Override
	public boolean isSessionValid(String sessionKey) {
		logger.info("**** The control is inside the isSessionValid method in CustomerSessionHandlerImpl *****");
		return this.customerRedisTemplate.opsForHash().hasKey(sessionKey, HashKey);
	}

	@Override
	public Object getSession(String sessionKey) {
		logger.info("**** The control is inside the getSession method in CustomerSessionHandlerImpl *****");
		setMaxInactiveInterval(sessionKey,SessionTimeout.HIGH);
		return this.customerRedisTemplate.opsForHash().get(sessionKey, HashKey);
	}

	@Override
	public void deleteSession(String sessionKey) {
		logger.info("**** The control is inside the deleteSession method in CustomerSessionHandlerImpl *****");
		this.customerRedisTemplate.opsForHash().delete(sessionKey, HashKey);
	}

	private void deleteMultipleSession(String sessionKey,Object[] HashKeys) {
		logger.info("**** The control is inside the deleteMultipleSession method in CustomerSessionHandlerImpl *****");
		this.customerRedisTemplate.opsForHash().delete(sessionKey, HashKeys);
	}

	@Override
	public void flushAll() {
		logger.info("**** The control is inside the flushAll method in CustomerSessionHandlerImpl *****");
		this.customerRedisTemplate.getConnectionFactory().getConnection().flushAll();
	}

	@Override
	public void expire(String sessionKey,long timeout) {
		logger.info("**** The control is inside  expire method in CustomerSessionHandlerImpl *****");
		customerRedisTemplate.expire(sessionKey,timeout,TimeUnit.MILLISECONDS);
	}

	@Override
	public Long size(String sessionKey) {
		logger.info("**** The control is inside the size method in CustomerSessionHandlerImpl *****");
		return this.customerRedisTemplate.opsForHash().size(sessionKey);
	}

	@Override
	public Map<Object, Object> findAll(String sessionKey) {
		logger.info("**** The control is inside the findAll method in CustomerSessionHandlerImpl *****");
		return this.customerRedisTemplate.opsForHash().entries(sessionKey);
	}

	@Override
	public void flushClient(String sessionKey) {
		logger.info("**** The control is inside the flushClient method in CustomerSessionHandlerImpl *****");
		Map<Object, Object> sessionMap = findAll(sessionKey);
		Set<Object> haskKeySet = sessionMap.keySet();
		deleteMultipleSession(sessionKey,haskKeySet.toArray());
	}

	@Override
	public Long getExpire(String sessionKey) {
		logger.info("**** The control is inside the getExpire method in CustomerSessionHandlerImpl *****");
		return customerRedisTemplate.getExpire(sessionKey,TimeUnit.MILLISECONDS);
	}

	@Override
	public void createOtpSession(String key, Object entity) {
		logger.info("**** The control is inside the createOtpSession method in CustomerSessionHandlerImpl *****");
		this.customerRedisTemplate.opsForHash().put(key,HashKey,entity);
		setMaxInactiveInterval(key,SessionTimeout.HIGH);
	}
	
	@Override
	public Object getOtpSession(String sessionKey) {
		logger.info("**** The control is inside the getSession method in CustomerSessionHandlerImpl *****");
		return this.customerRedisTemplate.opsForHash().get(sessionKey, HashKey);
	}
	
	private void setMaxInactiveInterval(String sessionKey,SessionTimeout timeout) {
		logger.info("*** The Control is inside the setMaxInactiveInterval in CustomerSessionHandlerImpl ****");
	     expire(sessionKey,timeout.expiryTime);
		logger.info("*** The Control is at end of the setMaxInactiveInterval in CustomerSessionHandlerImpl ****");
	}
}
