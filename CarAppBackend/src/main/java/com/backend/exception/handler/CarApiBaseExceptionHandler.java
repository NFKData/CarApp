package com.backend.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.backend.boundary.CarResource;
import com.backend.exception.CarApiBaseException;

/**
 * Handler that build a response base on the exception thrown by resources:<br>
 * <ul>
 * 	<li>{@link CarResource}</li>
 * </ul>
 * @author gmiralle
 *
 */
@Provider
public class CarApiBaseExceptionHandler implements ExceptionMapper<CarApiBaseException> {

	private static Logger LOG = LoggerFactory.getLogger("com.backend");
	
	@Override
	public Response toResponse(CarApiBaseException exception) {
		LOG.error(exception.getMessage());
		ResponseBuilder builder = Response.status(exception.getStatusToResponse());
		if (exception.getEntity() != null) {
			builder.entity(exception.getEntity());
		}
		return builder.build();
	}

}
