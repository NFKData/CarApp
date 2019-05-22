package backend.resource;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import backend.entity.Car;
import backend.exception.CarNotFoundException;
import backend.service.CarService;

@Path("cars")
public class CarResource {

	@EJB(name = "carService")
	private CarService carService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCars() {
		return Response.status(Status.OK)
				.entity(new GenericEntity<List<Car>>(carService.getAllCars()) {})
				.build();
	}

	@GET
	@Path("/{carId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCar(@PathParam(value = "carId") String id) {
		return Response.status(Status.OK)
				.entity(carService.getCar(id))
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCar(Car car) {
		return Response.status(Status.CREATED)
				.entity(carService.createCar(car))
				.build();
	}

	@PUT
	@Path("/{carId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCar(@PathParam(value = "carId") String id, Car car) {
		car.setId(id);
		try {
			return Response.status(Status.OK)
					.entity(carService.updateCar(car))
					.build();
		} catch (CarNotFoundException e) {
			return Response.status(Status.NO_CONTENT)
					.build();
		}
	}
	
	@DELETE
	@Path("/{carId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCar(@PathParam(value = "carId") String id) {
		try {
			return Response.status(Status.OK)
						.entity(carService.deleteCar(id))
						.build();
		} catch (CarNotFoundException e) {
			return Response.status(Status.NO_CONTENT)
					.build();
		}
	}
}
