package com.backend.boundary;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.backend.control.CarService;
import com.backend.entity.Car;
import com.backend.exception.CarNotFoundException;
import com.backend.exception.InvalidEntityException;

@Path("/cars")
public class CarResourceImpl extends CarResource {

	@EJB(name = "carService")
	private CarService carService;

	@Override
	public Response getAll() {
		return Response.status(Status.OK).entity(new GenericEntity<List<Car>>(carService.getAllCars()) {
		}).build();
	}

	@Override
	public Response getOne(String id) throws CarNotFoundException {
		return Response.status(Status.OK).entity(carService.getCar(id)).build();
	}

	@Override
	public Response create(Car car) throws InvalidEntityException {
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