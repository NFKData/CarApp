package com.backend.boundary;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import com.backend.control.fallback.ResourceFallbackHandler;
import com.backend.entity.Car;
import com.fasterxml.jackson.core.JsonProcessingException;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class CarTopicResource {

	@POST
	@CircuitBreaker(failOn = { NamingException.class,
			JMSException.class }, delay = 200, failureRatio = 0.5, requestVolumeThreshold = 4, successThreshold = 10)
	@Timeout(1000)
	@Fallback(ResourceFallbackHandler.class)
	public abstract Response create(Car car) throws NamingException, JMSException, JsonProcessingException;
	
}
