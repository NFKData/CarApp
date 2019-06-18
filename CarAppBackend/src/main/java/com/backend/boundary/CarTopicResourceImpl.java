package com.backend.boundary;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.backend.control.CarTopicService;
import com.backend.entity.Car;
import com.fasterxml.jackson.core.JsonProcessingException;

@Path("/jms/car")
public class CarTopicResourceImpl extends CarTopicResource {

	@EJB(name = "carTopicService")
	private CarTopicService carTopicService;
	
	@Override
	public Response create(Car car) throws NamingException, JMSException, JsonProcessingException {
		if(car == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		carTopicService.create(car);
		return Response.status(Status.OK).build();
	}

	
	
}
