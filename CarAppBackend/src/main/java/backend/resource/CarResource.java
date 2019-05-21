package backend.resource;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import backend.entity.Car;
import backend.exception.CarFoundException;
import backend.exception.CarNotFoundException;
import backend.service.CarService;

@Path("cars")
public class CarResource {

	@EJB(name = "carService")
	private CarService carService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Car> getCars() {
		return carService.getAllCars();
	}

	@GET
	@Path("/{carId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car getCar(@PathParam(value = "carId") String id) {
		return carService.getCar(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Car createCar(Car car) throws CarFoundException {
		try {
			return carService.createCar(car);
		} catch (CarFoundException e) {
			return null;
		}
	}

	@PUT
	@Path("/{carId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Car updateCar(@PathParam(value = "carId") String id, Car car) {
		car.setId(id);
		try {
			return carService.updateCar(car);
		} catch (CarNotFoundException e) {
			return null;
		}
	}
}
