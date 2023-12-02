package com.sumadhura.sumadhuragateway.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.sumadhura.sumadhuragateway.employeeservice.dto.Department;
import com.sumadhura.sumadhuragateway.employeeservice.dto.LoginModule;
import com.sumadhura.sumadhuragateway.employeeservice.dto.LoginSubModule;
import com.sumadhura.sumadhuragateway.employeeservice.dto.Site;

/**
 * IsEmptyUtil class provides utility specific functionality.
 * 
 * @author Venkat_Koniki
 * @since 26.04.2019
 * @time 05:50PM
 */
public class Util {
	
	private final static Logger LOGGER = Logger.getLogger(Util.class);
	

	private static final String isProjectNotification="Project Notifications";
	
	public static boolean isEmptyObject(Object object) {
		
		//LOGGER.info("**** The control is inside the isEmptyObject in IsEmptyUtil*****");

		if (object == null) {
			return true;
		} else if (object != null && object instanceof String
				&& StringUtils.isEmpty(object)) {
			return true;
		} else if (object != null && object instanceof Long
				&& ((Long) object) == 0) {
			return true;
		} else if (object != null && object instanceof Integer
				&& ((Integer) object) == 0) {
			return true;
		} else if (object != null && object instanceof Byte
				&& ((Byte) object) == 0) {
			return true;
		} else if (object != null && object instanceof Collection<?>
				&& ((Collection<?>) object).isEmpty()) {
			return true;
		}else if (object != null && object instanceof Map<?,?>
		&& ((Map<?,?>) object).isEmpty()) {
	       return true;
        } 
		else {
			return false;
		}
	}

	public static boolean isNotEmptyObject(Object object) {
		return !(isEmptyObject(object));
	}
	
	public static List<Long> getUserAccessSiteList(List<Department> departments,String isSendNotification){
		List<Long> siteList=new ArrayList<>();
		
		
		for (Department department : departments) {
			List<LoginModule> loginModule = department.getLoginModule();
			for (LoginModule loginModule2 : loginModule) {
				String moduleName = loginModule2.getModuleName();
				if(moduleName.equalsIgnoreCase(isProjectNotification)){
					List<LoginSubModule> loginSubModules = loginModule2.getLoginSubModules();
					for (LoginSubModule loginSubModule : loginSubModules) {
						
						if(loginSubModule.getSubModuleName().equalsIgnoreCase(isSendNotification)){
							List<Site> sites = loginSubModule.getSites();
							for (Site site : sites) {
								siteList.add(site.getSiteId());
							}
							
						}
					}
				}
			}
		}
		//req.setIds(siteList);
		return siteList;
	}
	
	public static List<Long> getUserAccessSiteList(List<Department> departments,String type,String subModule){
		List<Long> siteList=new ArrayList<>();
		
		
		for (Department department : departments) {
			List<LoginModule> loginModule = department.getLoginModule();
			for (LoginModule loginModule2 : loginModule) {
				String moduleName = loginModule2.getModuleName();
				if(moduleName.equalsIgnoreCase(type)){
					List<LoginSubModule> loginSubModules = loginModule2.getLoginSubModules();
					for (LoginSubModule loginSubModule : loginSubModules) {
						
						if(loginSubModule.getSubModuleName().equalsIgnoreCase(subModule)){
							List<Site> sites = loginSubModule.getSites();
							for (Site site : sites) {
								siteList.add(site.getSiteId());
							}	
						}
					}
				}
			}
		}
		//req.setIds(siteList);
		return siteList;
	}
	public static String getDate(String pattern,Date date) {
		 LOGGER.info("**** The control is inside the getDate in Util*****");
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		 String result = simpleDateFormat.format(new Date());
		 LOGGER.info("**** The output result is *****"+result);
		 return result;
	}
	
	public static String fill (String word,int size) {
		if(Util.isNotEmptyObject(word)) {
			StringBuilder sb = null ;
			if(word.length()<size) {
				 sb = new StringBuilder(word);
				for(;sb.length()<size;) {
					sb.append("v");
				}
			}
			return sb.toString();
		}else {
			char[] arr = new char[size];
			 Arrays.fill(arr, 'v');
			 return new String(arr);
		}
	}
	
	public static Timestamp getTimeInTimestamp(String format, String time) throws ParseException {
		LOGGER.info("**** The control is inside the getTimeInTimestamp in Util*****");
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return new Timestamp(sdf.parse(time).getTime());
	}
	

	  public static void main(String ... args) {
	
	  
	  String s = "null";
	  System.out.println(Util.fill(s,6));
	  
	  }
	 
	
	
}
