package com.backend.boundary;

import java.lang.reflect.InvocationTargetException;

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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.backend.entity.Brand;
import com.backend.entity.Car;
import com.backend.entity.Country;
import com.backend.entity.dto.ErrorDto;
import com.backend.exception.CarNotFoundException;
import com.backend.exception.InvalidEntityException;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class CarResource {

	/**
	 * Obtain all cars in the system May return the following HTTP Codes:
	 * <ul>
	 * <li>200 - OK</li>
	 * </ul>
	 * 
	 * @return Response with every {@link Car} in the body
	 * @throws SecurityException         If any exception happens with Reflection
	 *                                   API
	 * @throws NoSuchMethodException     If any exception happens with Reflection
	 *                                   API
	 * @throws InvocationTargetException If any exception happens with Reflection
	 *                                   API
	 * @throws IllegalArgumentException  If any exception happens with Reflection
	 *                                   API
	 * @throws IllegalAccessException    If any exception happens with Reflection
	 *                                   API
	 * @throws InstantiationException    If any exception happens with Reflection
	 *                                   API
	 */
	@Operation(summary = "Get all cars", description = "Retrieve every car on the system")
	@APIResponse(description = "List of cars", responseCode = "200", content = @Content(schema = @Schema(implementation = Car.class, type = SchemaType.ARRAY)))
	@Tag(name = "cars")
	@GET
	public abstract Response getAll() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Obtain the car with the specified ID May return the following HTTP Codes:
	 * <ul>
	 * <li>200 - OK</li>
	 * <li>404 - Not found - If there's no car with the specified ID on the
	 * system</li>
	 * </ul>
	 * 
	 * @param id UUID of the car to look for
	 * @return Response with the found {@link Car}
	 * @throws CarNotFoundException      If there's no car in the system with the
	 *                                   specified ID
	 * @throws SecurityException         If any exception happens with Reflection
	 *                                   API
	 * @throws NoSuchMethodException     If any exception happens with Reflection
	 *                                   API
	 * @throws InvocationTargetException If any exception happens with Reflection
	 *                                   API
	 * @throws IllegalArgumentException  If any exception happens with Reflection
	 *                                   API
	 * @throws IllegalAccessException    If any exception happens with Reflection
	 *                                   API
	 * @throws InstantiationException    If any exception happens with Reflection
	 *                                   API
	 */
	@Operation(summary = "Get car by ID", description = "Retrieve the car with the specified ID", hidden = true)
	@APIResponses({
			@APIResponse(description = "Car", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class, type = SchemaType.ARRAY))),
			@APIResponse(description = "Car not found", responseCode = "404") })
	@Tag(name = "cars")
	@GET
	@Path("/{id}")
	public abstract Response getOne(
			@Parameter(description = "UUID of the car to retrieve", schema = @Schema(type = SchemaType.STRING, format = "uuid", description = "param UUID of the car to retrieve"), required = true) @PathParam(value = "id") String id)
			throws CarNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Insert a new car in the system May return the following HTTP Codes:
	 * <ul>
	 * <li>201 - Created</li>
	 * <li>400 - Bad request - On validation errors</li>
	 * </ul>
	 * 
	 * @param car The car to be created
	 * @return Response with the new {@link Car} in the body
	 * @throws InvalidEntityException    If validation of the entity failed
	 * @throws SecurityException         If any exception happens with Reflection
	 *                                   API
	 * @throws NoSuchMethodException     If any exception happens with Reflection
	 *                                   API
	 * @throws InvocationTargetException If any exception happens with Reflection
	 *                                   API
	 * @throws IllegalArgumentException  If any exception happens with Reflection
	 *                                   API
	 * @throws IllegalAccessException    If any exception happens with Reflection
	 *                                   API
	 * @throws InstantiationException    If any exception happens with Reflection
	 *                                   API
	 */
	@Operation(summary = "Create a new car", description = "Create a new car with specified data")
	@APIResponses({
			@APIResponse(description = "Car", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class, type = SchemaType.OBJECT))),
			@APIResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class, type = SchemaType.ARRAY))) })
	@Tag(name = "cars")
	@POST
	public abstract Response create(
			@Parameter(description = "Car to create", schema = @Schema(implementation = Car.class, type = SchemaType.OBJECT), required = true) Car car)
			throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Update the car with the specified ID May return the following HTTP Codes:
	 * <ul>
	 * <li>200 - OK</li>
	 * <li>400 - Bad Request - On validation errors</li>
	 * <li>404 - Not Found - If there's no car with the specified ID</li>
	 * </ul>
	 * 
	 * @param id  UUID of the car which will be updated
	 * @param car The new car data
	 * @return Response with the updated {@link Car} in the body
	 * @throws InvalidEntityException    If validation of the entity failed
	 * @throws CarNotFoundException      If a car with the specified Id couldn't be
	 *                                   found
	 * @throws SecurityException         If any exception happens with Reflection
	 *                                   API
	 * @throws NoSuchMethodException     If any exception happens with Reflection
	 *                                   API
	 * @throws InvocationTargetException If any exception happens with Reflection
	 *                                   API
	 * @throws IllegalArgumentException  If any exception happens with Reflection
	 *                                   API
	 * @throws IllegalAccessException    If any exception happens with Reflection
	 *                                   API
	 * @throws InstantiationException    If any exception happens with Reflection
	 *                                   API
	 */
	@Operation(summary = "Update car by ID", description = "Update the car with the specified ID and new data", hidden = true)
	@APIResponses({
			@APIResponse(description = "Car", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class, type = SchemaType.OBJECT))),
			@APIResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class, type = SchemaType.ARRAY))),
			@APIResponse(description = "Car not found", responseCode = "404") })
	@Tag(name = "cars")
	@PUT
	@Path("/{id}")
	public abstract Response update(
			@Parameter(description = "UUID of the car to update", schema = @Schema(type = SchemaType.STRING, format = "uuid", description = "param UUID of the car to update"), required = true) @PathParam(value = "id") String id,
			@Parameter(description = "New data for the car", schema = @Schema(implementation = Car.class, type = SchemaType.OBJECT), required = true) Car car)
			throws CarNotFoundException, InvalidEntityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Delete the {@link Car} with the specified ID May return the following HTTP
	 * Codes:
	 * <ul>
	 * <li>204 - No Content - On Success</li>
	 * <li>404 - Not Found - If there's no car with the specified ID</li>
	 * </ul>
	 * 
	 * @param id UUID of the car which will be deleted
	 * @return Response without body
	 * @throws CarNotFoundException If a car with the specified Id couldn't be found
	 */
	@Operation(summary = "Delete car by ID", description = "Delete the car with the specified ID", hidden = true)
	@APIResponses({ @APIResponse(description = "Car deleted", responseCode = "204"),
			@APIResponse(description = "Car not found", responseCode = "404") })
	@Tag(name = "cars")
	@DELETE
	@Path("/{id}")
	public abstract Response delete(
			@Parameter(description = "UUID of the car to update", schema = @Schema(type = SchemaType.STRING, format = "uuid", description = "param UUID of the car to update"), required = true) @PathParam(value = "id") String id)
			throws CarNotFoundException;

	/**
	 * Get the brand of the car with the specified ID
	 * 
	 * @param id UUID of the car which to be looked for
	 * @return {@link Brand} object of the car
	 * @throws CarNotFoundException      If there's no car in the system with the
	 *                                   specified ID
	 * @throws InstantiationException    If any exception happens with Reflection
	 *                                   API
	 * @throws IllegalAccessException    If any exception happens with Reflection
	 *                                   API
	 * @throws IllegalArgumentException  If any exception happens with Reflection
	 *                                   API
	 * @throws InvocationTargetException If any exception happens with Reflection
	 *                                   API
	 * @throws NoSuchMethodException     If any exception happens with Reflection
	 *                                   API
	 * @throws SecurityException         If any exception happens with Reflection
	 *                                   API
	 */
	@Operation(summary = "Get a car's brand by car ID", description = "Retrieve the car's brand with the specified car ID", hidden = true)
	@APIResponses({
			@APIResponse(description = "Brand", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Brand.class, type = SchemaType.ARRAY))),
			@APIResponse(description = "Car not found", responseCode = "404") })
	@Tag(name = "cars")
	@GET
	@Path("/{id}/brand")
	public abstract Response getBrand(
			@Parameter(description = "UUID of the car from which to get brand", schema = @Schema(type = SchemaType.STRING, format = "uuid", description = "param UUID of the car to update"), required = true) @PathParam(value = "id") String id)
			throws CarNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Get the country of the car with the specified ID
	 * 
	 * @param id UUID of the car which to be looked for
	 * @return {@link Country} object of the car
	 * @throws CarNotFoundException      If there's no car in the system with the
	 *                                   specified ID
	 * @throws InstantiationException    If any exception happens with Reflection
	 *                                   API
	 * @throws IllegalAccessException    If any exception happens with Reflection
	 *                                   API
	 * @throws IllegalArgumentException  If any exception happens with Reflection
	 *                                   API
	 * @throws InvocationTargetException If any exception happens with Reflection
	 *                                   API
	 * @throws NoSuchMethodException     If any exception happens with Reflection
	 *                                   API
	 * @throws SecurityException         If any exception happens with Reflection
	 *                                   API
	 */
	@Operation(summary = "Get a car's country by car ID", description = "Retrieve the car's country with the specified car ID", hidden = true)
	@APIResponses({
			@APIResponse(description = "Country", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Country.class, type = SchemaType.ARRAY))),
			@APIResponse(description = "Car not found", responseCode = "404") })
	@Tag(name = "cars")
	@GET
	@Path("/{id}/country")
	public abstract Response getCountry(
			@Parameter(description = "UUID of the car from which to get country", schema = @Schema(type = SchemaType.STRING, format = "uuid", description = "param UUID of the car to update"), required = true) @PathParam(value = "id") String id)
			throws CarNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;

}
