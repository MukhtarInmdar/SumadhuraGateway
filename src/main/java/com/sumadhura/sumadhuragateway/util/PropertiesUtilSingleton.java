package com.sumadhura.sumadhuragateway.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;



/**
 * PropertiesUtilSingleton class provides singleton properties object.
 *  
 * @author Venkat_Koniki
 * @since 05.07.2019
 * @time 11:33AM
 */
public final class PropertiesUtilSingleton {
	
	private final static Logger LOGGER = Logger.getLogger(PropertiesUtilSingleton.class);
	private static Properties viewObj;
	
	
	private PropertiesUtilSingleton() {
		super();
	}
	/**
	 * Returns the Properties object associated with the current Java application.
	 * 
	 * @return  the <code>Properties</code> object associated with the current
	 *          Java application.
	 * @throws IOException 
	 */
	public final static Properties getView() throws IOException{
		LOGGER.info("*** The control is inside the getView in PropertiesUtilSingleton ***");
		if(viewObj == null) {
			viewObj = new Properties();
			viewObj.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("views.properties"));
		}
		LOGGER.debug("**** The view Properties object is ****"+viewObj);
        return viewObj;
    }
}
