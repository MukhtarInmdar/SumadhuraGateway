package com.sumadhura.sumadhuragateway.util;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * Class represents JAX-RS client object required for REST API invocation
 * 
 * @author santosh_gajjala
 *
 */
public class RestApiUtility {

	private final static Logger log = Logger.getLogger(RestApiUtility.class);	

	private static final String REST_API_REQUEST_TYPE_JSON = "application/json";
	private static final String REST_API_REQUEST_TYPE_PLAIN_TEXT = "text/plain";

	enum METHOD {
		POST, GET;
	}

	private static final WebClient getWebClientInstance(String apiURL) {
		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJaxbJsonProvider());

		WebClient client = WebClient.create(apiURL, providers);

		// Set service timeout
		HTTPConduit conduit = (HTTPConduit) WebClient.getConfig(client).getConduit();
		HTTPClientPolicy policy = conduit.getClient();
		policy.setReceiveTimeout(10000000);
		return client;
	}

	/**
	 * Utility method to create JAX-RS client, sending request to REST API.
	 * 
	 * @param apiURL
	 * @param requestMethod
	 * @param request
	 * @param responseClassz
	 * @param type
	 * @return
	 */
	private static final Object sendRequest(String apiURL, METHOD requestMethod, Object request,
			Class<?> responseClassz, String type) {
		Object response = null;
		Response rawResponse = null;
		Object responseData = null;

		WebClient client = getWebClientInstance(apiURL);

		if (requestMethod.equals(METHOD.POST)) { // POST Method
			rawResponse = client.accept(type).type(type).post(request);
		} else if (requestMethod.equals(METHOD.GET)) { // GET Method
			responseData = client.accept(type).type(type).get();

			if (responseData instanceof String) {
				return responseData;
			} else {
				rawResponse = (Response) responseData;
			}
			response = client.get(responseClassz);
		} else {
			log.error("RestApiUtility, sendRequest, Cannot process request as web method defined in NULL.");
		}

		isAssignableFrom(response, responseClassz);
      
		log.info(" Response status : "+rawResponse.getStatus());
		if (rawResponse != null && rawResponse.getStatus() == Status.OK.getStatusCode()) {
			response = rawResponse.readEntity(responseClassz);
		} else {

			if (rawResponse.readEntity(List.class) != null) {
				@SuppressWarnings("unchecked")
				List<String> list = (List<String>) rawResponse.readEntity(List.class);
				log.error("RestApiUtility, Error from service layer=" + list);
				// throw new ServiceException(list);
			}
		}

		return response;
	}

	public static final Object sendJSONPostRequest(String apiURL, Object request, Class<?> responseClassz) {
		return sendRequest(apiURL, METHOD.POST, request, responseClassz, REST_API_REQUEST_TYPE_JSON);
	}

	public static final Object sendTextPostRequest(String apiURL, Object request, Class<?> responseClassz) {
		return sendRequest(apiURL, METHOD.POST, request, responseClassz, REST_API_REQUEST_TYPE_PLAIN_TEXT);
	}

	public static final Object sendJSONGetRequest(String apiURL, Object request, Class<?> responseClassz) {
		return sendRequest(apiURL, METHOD.GET, request, responseClassz, REST_API_REQUEST_TYPE_JSON);
	}

	public static final Object sendTextGetRequest(String apiURL, Object request, Class<?> responseClassz) {
		return sendRequest(apiURL, METHOD.GET, request, responseClassz, REST_API_REQUEST_TYPE_PLAIN_TEXT);
	}

	private static final void isAssignableFrom(Object response, Class<?> destinationClassz) {
		if (response != null && !response.getClass().isAssignableFrom(destinationClassz)) {
			log.error("RestApiUtility, isAssignableFrom, Response is of unexpected type, please check.");
		}
	}

}
