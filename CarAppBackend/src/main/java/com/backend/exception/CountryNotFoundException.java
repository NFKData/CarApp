package com.backend.exception;

import javax.interceptor.Interceptors;
import javax.ws.rs.core.Response.Status;

import com.backend.entity.Country;
import com.backend.interceptor.LogInterceptor;

/**
 * Exception thrown when a {@link Country} is looked for in the system
 * but it hasn't been found
 * @author gmiralle
 */
@Interceptors(LogInterceptor.class)
public class CountryNotFoundException extends CarApiBaseException {

	private static final long serialVersionUID = 3715877372548587658L;
	private static final String DEFAULT_MESSAGE = "The requested country couldn't be found. ";

	/**
	 * Create an exception with the a default message<br>
	 * <b>"The requested country couldn't be found. "</b>
	 */
	public CountryNotFoundException() {
		super(DEFAULT_MESSAGE, Status.NOT_FOUND);
	}
	
	/**
	 * Create an exception with the default message and the Country's ID
	 * that provoked it
	 * @param countryId Country's ID that provoked the exception
	 */
	public CountryNotFoundException(String countryId) {
		super(DEFAULT_MESSAGE + countryId, Status.NOT_FOUND);
	}
}
