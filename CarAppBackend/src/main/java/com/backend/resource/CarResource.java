package com.backend.resource;

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

import com.backend.entity.Car;
import com.backend.exception.CarNotFoundException;
import com.backend.exception.InvalidEntityException;

/**
 * Resource of the API.
 * Exceptions are mapped by {@link CarApiBaseExceptionHandler}
 * @author gmiralle
 *
 */
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
	 * May return the following HTTP Codes:
	 * <ul>
	 * 	<li>200 - OK</li>
	 * 	<li>204 - No content - On no cars found</li>
	 * </ul>
	 * @param id UUID of the car to look for
	 * @return Response with the found {@link Car}
	 * @throws CarNotFoundException If there's no car in the system with the specified ID
	 */
	@GET
	@Path("/{id}")
	public abstract Response getOne(@PathParam(value = "id")String id) throws CarNotFoundException;
	
	/**
	 * Insert a new car in the system
	 * May return the following HTTP Codes:
	 * <ul>
	 * 	<li>201 - Created</li>
	 * 	<li>404 - Not found - On validation errors</li>
	 * </ul>
	 * @param car The car to be created
	 * @return Response with the new {@link Car} in the body
	 * @throws InvalidEntityException If validation of the entity failed
	 */
	@POST
	public abstract Response create(Car car) throws InvalidEntityException;
	
	/**
	 * Update the car with the specified ID
	 * May return the following HTTP Codes:
	 * <ul>
	 * 	<li>200 - OK</li>
	 * 	<li>400 - Bad Request - On validation errors</li>
	 * 	<li>404 - Not Found - If there's no car with the specified ID</li>
	 * </ul>
	 * @param id UUID of the car which will be updated
	 * @param car The new car data
	 * @return Response with the updated {@link Car} in the body
	 * @throws InvalidEntityException If validation of the entity failed
	 * @throws CarNotFoundException If a car with the specified Id couldn't be found
	 * 
	 */
	@PUT
	@Path("/{id}")
	public abstract Response update(@PathParam(value = "id")String id, Car car) throws CarNotFoundException, InvalidEntityException;
	/**
	 * Delete the {@link Car} with the specified ID
	 * May return the following HTTP Codes:
	 * <ul>
	 * 	<li>204 - No Content - On Success</li>
	 * 	<li>404 - Not Found - If there's no car with the specified ID</li>
	 * </ul>
	 * @param id UUID of the car which will be deleted
	 * @return Response without body
	 * @throws CarNotFoundException If a car with the specified Id couldn't be found
	 */
	@DELETE
	@Path("/{id}")
	public abstract Response delete(@PathParam(value = "id") String id) throws CarNotFoundException;
	
}
