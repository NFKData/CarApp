package com.backend.resource.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.backend.entity.Car;
import com.backend.exception.CarNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.interceptor.LogInterceptor;
import com.backend.resource.CarResource;
import com.backend.service.CarService;

@Path("cars")
@Interceptors(LogInterceptor.class)
public class CarResourceImpl extends CarResource {

	@EJB(name = "carService")
	private CarService carService;

	@Override
	public Response getAll() {
		return Response.status(Status.OK).entity(new GenericEntity<List<Car>>(carService.getAllCars()) {
		}).build();
	}

	@Override
	public Response getOne(String id) {
		return Response.status(Status.OK).entity(carService.getCar(id)).build();
	}

	@Override
	public Response create(Car car) throws InvalidEntityException {
		Logger logger = LogManager.getLogger(getClass());
		logger.info("Method Called: create");
		logger.info("Parameters: " + car.toString());
		logger.debug("Method Called: create");        
		logger.debug("Parameters: " + car.toString());
		logger.error("Method Called: create");        
		logger.error("Parameters: " + car.toString());
		return Response.status(Status.CREATED).entity(carService.createCar((Car) car)).build();
	}

	@Override
	public Response update(String id, Car entity) throws CarNotFoundException, InvalidEntityException {
		Car car = (Car) entity;
		car.setId(id);
		return Response.status(Status.OK).entity(carService.updateCar(car)).build();
	}

	@Override
	public Response delete(String id) throws CarNotFoundException {
		carService.deleteCar(id);
		return Response.status(Status.NO_CONTENT).build();
	}
}
