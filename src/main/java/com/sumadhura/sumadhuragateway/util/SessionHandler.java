/**
 * 
 */
package com.sumadhura.sumadhuragateway.util;

import java.util.Map;

/**
 * CustomerSessionHandlerImpl class is for the implementation of SessionHandler
 * Interface, for simplified implementation of SessionHandler to createSession &
 * isSessionValid & getSession & deleteSession & findAll & flushAll & flushClient & size & expire.
 * 
 * @author Venkat_Koniki
 * @since 18.03.2019
 * @time 04:19PM
 */

public interface SessionHandler {

	public void createSession(String key, Object entity);
	
	public Object getSession(String sessionKey);
	
	public void deleteSession(String sessionKey);

	public boolean isSessionValid(String sessionKey);

	public void expire(String sessionKey,long timeout);
	
	public Long getExpire(String sessionKey);
	
	public Long size(String sessionKey);
	
	public void flushAll();
	
	public Map<Object, Object> findAll(String sessionKey);
	
	public void flushClient(String sessionKey);
	
	public void createOtpSession(String key, Object entity);
	
	public Object getOtpSession(String sessionKey);
	
}
