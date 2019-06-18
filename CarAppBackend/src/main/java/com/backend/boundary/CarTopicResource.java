package com.backend.boundary;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.backend.entity.Car;
import com.fasterxml.jackson.core.JsonProcessingException;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class CarTopicResource {

	@POST
	public abstract Response create(Car car) throws NamingException, JMSException, JsonProcessingException;
	
}
