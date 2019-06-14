package com.backend.exception.handler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class UnexpectedExceptionHandler implements ExceptionMapper<Exception> {

	private static Logger LOG = LoggerFactory.getLogger("com.backend");
	
	@Override
	public Response toResponse(Exception exception) {
		LOG.error("Unexpected exception occurred", exception);
		StringBuilder builder = new StringBuilder("Internal Server Error. Please contact the administrator with the following information: ");
		builder.append(" Timestamp: ");
		builder.append(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
		builder.append(" Error: ");
		builder.append(exception.getMessage());
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(builder.toString()).build();
	}

}
