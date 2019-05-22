package backend.resource.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import backend.entity.Car;
import backend.exception.CarNotFoundException;
import backend.resource.CarResource;
import backend.service.CarService;

@Path("cars")
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
	public Response create(Car car) {
		return Response.status(Status.CREATED).entity(carService.createCar((Car)car)).build();
	}

	@Override
	public Response update(String id, Car entity) {
		Car car = (Car) entity;		
		car.setId(id);
		try {
			return Response.status(Status.OK).entity(carService.updateCar(car)).build();
		} catch (CarNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Override
	public Response delete(String id) {
		try {
			carService.deleteCar(id);
			return Response.status(Status.NO_CONTENT).build();
		} catch (CarNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
