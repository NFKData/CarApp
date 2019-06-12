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

import com.backend.entity.Car;
import com.backend.entity.Country;
import com.backend.entity.dto.ErrorDto;
import com.backend.exception.CountryNotFoundException;
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
public abstract class CountryResource {

	/**
	 * Retrieve every {@link Country} in the system
	 * @return Response with list of countries
	 * @throws InstantiationException If any exception happens with Reflection API
	 * @throws IllegalAccessException If any exception happens with Reflection API
	 * @throws IllegalArgumentException If any exception happens with Reflection API
	 * @throws InvocationTargetException If any exception happens with Reflection API
	 * @throws NoSuchMethodException If any exception happens with Reflection API
	 * @throws SecurityException If any exception happens with Reflection API
	 */
	@Operation(summary = "Get all countries", tags = {
			"countries" }, description = "Retrieve every country on the system", responses = {
					@ApiResponse(description = "List of countries", responseCode = "200", content = @Content(schema = @Schema(implementation = Country.class, type = "array"))) })
	@GET
	public abstract Response getAll() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Retrieve the country with the specified ID
	 * @param id ID of the country which will be looked for
	 * @return Response with found {@link Country} 
	 * @throws CountryNotFoundException If there's no country in the system with the specified ID
	 * @throws InstantiationException If any exception happens with Reflection API
	 * @throws IllegalAccessException If any exception happens with Reflection API
	 * @throws IllegalArgumentException If any exception happens with Reflection API
	 * @throws InvocationTargetException If any exception happens with Reflection API
	 * @throws NoSuchMethodException If any exception happens with Reflection API
	 * @throws SecurityException If any exception happens with Reflection API
	 */
	@Operation(summary = "Get country by ID", tags = {
			"countries" }, description = "Retrieve the country with the specified ID", responses = {
					@ApiResponse(description = "Country", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Country.class)))),
					@ApiResponse(description = "Country not found", responseCode = "404") })
	@GET
	@Path("/{id}")
	@Hidden
	public abstract Response getOne(
			@Parameter(description = "ID of the country to retrieve", schema = @Schema(type = "integer", format = "id", description = "param ID of the country to retrieve"), required = true) @PathParam(value = "id") Integer id) throws CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Insert a new country in the system
	 * @param country Country to be inserted
	 * @return Response with created {@link Country} entity 
	 * @throws InvalidEntityException If validation of the entity failed
	 * @throws InstantiationException If any exception happens with Reflection API
	 * @throws IllegalAccessException If any exception happens with Reflection API
	 * @throws IllegalArgumentException If any exception happens with Reflection API
	 * @throws InvocationTargetException If any exception happens with Reflection API
	 * @throws NoSuchMethodException If any exception happens with Reflection API
	 * @throws SecurityException If any exception happens with Reflection API
	 */
	@Operation(summary = "Create a new country", tags = {
			"countries" }, description = "Create a new country with specified data", responses = {
					@ApiResponse(description = "Country", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Country.class))),
					@ApiResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ErrorDto.class)))) })
	@POST
	public abstract Response create(
			@Parameter(description = "Country to create", schema = @Schema(implementation = Country.class), required = true) Country country)
			throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Update the country with the specified ID
	 * @param id ID of the country to be updated
	 * @param brand Entity containing new country data
	 * @return Response with the new {@link Country} entity
	 * @throws InvalidEntityException If validation of the entity failed
	 * @throws CountryNotFoundException If there's no country in the system with the specified ID
	 * @throws InstantiationException If any exception happens with Reflection API
	 * @throws IllegalAccessException If any exception happens with Reflection API
	 * @throws IllegalArgumentException If any exception happens with Reflection API
	 * @throws InvocationTargetException If any exception happens with Reflection API
	 * @throws NoSuchMethodException If any exception happens with Reflection API
	 * @throws SecurityException If any exception happens with Reflection API
	 */
	@Operation(summary = "Update country by ID", tags = {
			"countries" }, description = "Update the country with the specified ID and new data", responses = {
					@ApiResponse(description = "Country", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Country.class))),
					@ApiResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ErrorDto.class)))),
					@ApiResponse(description = "Country not found", responseCode = "404") })
	@PUT
	@Path("/{id}")
	@Hidden
	public abstract Response update(
			@Parameter(description = "ID of the country to update", schema = @Schema(type = "integer", format = "uuid", description = "param ID of the country to update"), required = true) @PathParam(value = "id") Integer id,
			@Parameter(description = "New data for the country", schema = @Schema(implementation = Country.class), required = true) Country country)
			throws InvalidEntityException, CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Deletes a country from the system
	 * @param id ID of the country to be deleted
	 * @return Response without entities
	 * @throws CountryNotFoundException If there's no country in the system with the specified ID
	 */
	@Operation(summary = "Delete country by ID", tags = {
			"countries" }, description = "Delete the country with the specified ID", responses = {
					@ApiResponse(description = "Country deleted", responseCode = "204"),
					@ApiResponse(description = "Country not found", responseCode = "404") })
	@DELETE
	@Path("/{id}")
	@Hidden
	public abstract Response delete(
			@Parameter(description = "ID of the country to update", schema = @Schema(type = "integer", format = "uuid", description = "param ID of the country to update"), required = true) @PathParam(value = "id") Integer id) throws CountryNotFoundException;
			
	/**
	 * Retrieve list of cars ({@link Car}) with the specified {@link Country}
	 * @param id ID of the country from which to get cars
	 * @return List of cars with the specified country
	 * @throws CountryNotFoundException If there's no country in the system with the specified ID
	 * @throws InstantiationException If any exception happens with Reflection API
	 * @throws IllegalAccessException If any exception happens with Reflection API
	 * @throws IllegalArgumentException If any exception happens with Reflection API
	 * @throws InvocationTargetException If any exception happens with Reflection API
	 * @throws NoSuchMethodException If any exception happens with Reflection API
	 * @throws SecurityException If any exception happens with Reflection API
	 */
	@Operation(summary = "Get country's cars by brand ID", tags = {
	"countries" }, description = "Retrieve country's cars with the specified country ID", responses = {
			@ApiResponse(description = "Cars", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Car.class)))),
			@ApiResponse(description = "Country not found", responseCode = "404") })
	@GET
	@Path("/{id}/cars")
	@Hidden
	public abstract Response getCars(
			@Parameter(description = "ID of the country from which to get cars", schema = @Schema(type = "integer", format = "integer", description = "ID of the country from which to get cars"), required = true) @PathParam(value = "id") Integer id)
			throws CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
