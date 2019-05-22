package backend.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import backend.entity.Car;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class CarResource {

	/**
	 * Obtain all cars in the system
	 * @return Response with every {@link Car} in the body
	 * 
	 */
	@GET
	public abstract Response getAll();
	
	/**
	 * Obtain the car with the specified ID
	 * @param id UUID of the car to look for
	 * @return Response with the found {@link Car}
	 */
	@GET
	@Path("/{id}")
	public abstract Response getOne(@PathParam(value = "id")String id);
	
	/**
	 * Insert a new car in the system
	 * @param car The car to be created
	 * @return Response with the new {@link Car} in the body
	 */
	@POST
	public abstract Response create(Car car);
	
	/**
	 * Update the car with the specified ID
	 * @param id UUID of the car which will be updated
	 * @param car The new car data
	 * @return Response with the updated {@link Car} in the body
	 * 
	 */
	@PUT
	@Path("/{id}")
	public abstract Response update(@PathParam(value = "id")String id, Car car);
	/**
	 * Delete the {@link Car} with the specified ID
	 * @param id UUID of the car which will be deleted
	 * @return Response without body
	 */
	@DELETE
	@Path("/{id}")
	public abstract Response delete(@PathParam(value = "id") String id);
	
}
