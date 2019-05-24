package com.backend.boundary;

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
import com.backend.entity.dto.ValidationErrorDto;
import com.backend.exception.CarNotFoundException;
import com.backend.exception.InvalidEntityException;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class CarResource {

	/**
	 * Obtain all cars in the system May return the following HTTP Codes:
	 * <ul>
	 * 	<li>200 - OK</li>
	 * </ul>
	 * 
	 * @return Response with every {@link Car} in the body
	 */
	@Operation(summary = "Get all cars", tags = {
			"cars" }, description = "Retrieve every car on the system", responses = {
					@ApiResponse(description = "List of cars", responseCode = "200", content = @Content(schema = @Schema(implementation = Car.class, type = "array"))) })
	@GET
	public abstract Response getAll();

	/**
	 * Obtain the car with the specified ID May return the following HTTP Codes:
	 * <ul>
	 * 	<li>200 - OK</li>
	 * 	<li>404 - Not found - If there's no car with the specified ID on the
	 * system</li>
	 * </ul>
	 * 
	 * @param id UUID of the car to look for
	 * @return Response with the found {@link Car}
	 * @throws CarNotFoundException If there's no car in the system with the
	 *                              specified ID
	 */
	@Operation(summary = "Get car by ID", tags = {
			"cars" }, description = "Retrieve the car with the specified ID", responses = {
					@ApiResponse(description = "Car", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Car.class)))),
					@ApiResponse(description = "Car not found", responseCode = "404") })
	@GET
	@Path("/{id}")
	@Hidden
	public abstract Response getOne(
			@Parameter(description = "UUID of the car to retrieve", schema = @Schema(type = "string", format = "uuid", description = "param UUID of the car to retrieve"), required = true) @PathParam(value = "id") String id)
			throws CarNotFoundException;

	/**
	 * Insert a new car in the system May return the following HTTP Codes:
	 * <ul>
	 *	<li>201 - Created</li>
	 * 	<li>400 - Bad request - On validation errors</li>
	 * </ul>
	 * 
	 * @param car The car to be created
	 * @return Response with the new {@link Car} in the body
	 * @throws InvalidEntityException If validation of the entity failed
	 */
	@Operation(summary = "Create a new car", tags = {
			"cars" }, description = "Create a new car with specified data", responses = {
					@ApiResponse(description = "Car", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
					@ApiResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ValidationErrorDto.class)))) })
	@POST
	public abstract Response create(
			@Parameter(description = "Car to create", schema = @Schema(implementation = Car.class), required = true) Car car)
			throws InvalidEntityException;

	/**
	 * Update the car with the specified ID May return the following HTTP Codes:
	 * <ul>
	 * 	<li>200 - OK</li>
	 * 	<li>400 - Bad Request - On validation errors</li>
	 * 	<li>404 - Not Found - If there's no car with the specified ID</li>
	 * </ul>
	 * 
	 * @param id  UUID of the car which will be updated
	 * @param car The new car data
	 * @return Response with the updated {@link Car} in the body
	 * @throws InvalidEntityException If validation of the entity failed
	 * @throws CarNotFoundException   If a car with the specified Id couldn't be
	 *                                found
	 */
	@Operation(summary = "Update car by ID", tags = {
			"cars" }, description = "Update the car with the specified ID and new data", responses = {
					@ApiResponse(description = "Car", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class))),
					@ApiResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ValidationErrorDto.class)))),
					@ApiResponse(description = "Car not found", responseCode = "404") })
	@PUT
	@Path("/{id}")
	@Hidden
	public abstract Response update(
			@Parameter(description = "UUID of the car to update", schema = @Schema(type = "string", format = "uuid", description = "param UUID of the car to update"), required = true) @PathParam(value = "id") String id,
			@Parameter(description = "New data for the car", schema = @Schema(implementation = Car.class), required = true) Car car)
			throws CarNotFoundException, InvalidEntityException;

	/**
	 * Delete the {@link Car} with the specified ID May return the following HTTP
	 * Codes:
	 * <ul>
	 * 	<li>204 - No Content - On Success</li>
	 * 	<li>404 - Not Found - If there's no car with the specified ID</li>
	 * </ul>
	 * 
	 * @param id UUID of the car which will be deleted
	 * @return Response without body
	 * @throws CarNotFoundException If a car with the specified Id couldn't be found
	 */
	@Operation(summary = "Delete car by ID", tags = {
			"cars" }, description = "Delete the car with the specified ID", responses = {
					@ApiResponse(description = "Car deleted", responseCode = "204"),
					@ApiResponse(description = "Car not found", responseCode = "404") })
	@DELETE
	@Path("/{id}")
	@Hidden
	public abstract Response delete(
			@Parameter(description = "UUID of the car to update", schema = @Schema(type = "string", format = "uuid", description = "param UUID of the car to update"), required = true) @PathParam(value = "id") String id)
			throws CarNotFoundException;

}
