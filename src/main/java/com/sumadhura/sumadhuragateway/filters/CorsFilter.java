package com.sumadhura.sumadhuragateway.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class CorsFilter
 */
public class CorsFilter implements Filter {

	private final static Logger logger = Logger.getLogger(CorsFilter.class);

	/**
	 * Default constructor.
	 */
	public CorsFilter() {
		logger.info("*** The control inside  Default constructor of CorsFilter ***");
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		logger.info("*** The control inside destroy method of CorsFilter ***");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) req;
		logger.info("CORSFilter HTTP Request: " + httpServletRequest.getMethod());
		HttpServletResponse response = (HttpServletResponse) resp;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "false");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, Content-Type, X-Requested-With, accept, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
		response.setHeader("Access-Control-Expose-Headers",
				"Origin, Access-Control-Request-Method, Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
		chain.doFilter(req, resp);
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		logger.info("*** The control is inside the init method in CorsFilter ***");
	}

}
