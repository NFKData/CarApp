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
import com.backend.entity.dto.ValidationErrorDto;
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

	@Operation(summary = "Get all countries", tags = {
			"countries" }, description = "Retrieve every country on the system", responses = {
					@ApiResponse(description = "List of countries", responseCode = "200", content = @Content(schema = @Schema(implementation = Country.class, type = "array"))) })
	@GET
	public abstract Response getAll() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	@Operation(summary = "Get country by ID", tags = {
			"countries" }, description = "Retrieve the country with the specified ID", responses = {
					@ApiResponse(description = "Country", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Country.class)))),
					@ApiResponse(description = "Country not found", responseCode = "404") })
	@GET
	@Path("/{id}")
	@Hidden
	public abstract Response getOne(
			@Parameter(description = "ID of the country to retrieve", schema = @Schema(type = "integer", format = "id", description = "param ID of the country to retrieve"), required = true) @PathParam(value = "id") Integer id) throws CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	@Operation(summary = "Create a new country", tags = {
			"countries" }, description = "Create a new country with specified data", responses = {
					@ApiResponse(description = "Country", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Country.class))),
					@ApiResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ValidationErrorDto.class)))) })
	@POST
	public abstract Response create(
			@Parameter(description = "Country to create", schema = @Schema(implementation = Country.class), required = true) Country country)
			throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	@Operation(summary = "Update country by ID", tags = {
			"countries" }, description = "Update the country with the specified ID and new data", responses = {
					@ApiResponse(description = "Country", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Country.class))),
					@ApiResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ValidationErrorDto.class)))),
					@ApiResponse(description = "Country not found", responseCode = "404") })
	@PUT
	@Path("/{id}")
	@Hidden
	public abstract Response update(
			@Parameter(description = "ID of the country to update", schema = @Schema(type = "integer", format = "uuid", description = "param ID of the country to update"), required = true) @PathParam(value = "id") Integer id,
			@Parameter(description = "New data for the country", schema = @Schema(implementation = Country.class), required = true) Country country)
			throws InvalidEntityException, CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	@Operation(summary = "Delete country by ID", tags = {
			"countries" }, description = "Delete the country with the specified ID", responses = {
					@ApiResponse(description = "Country deleted", responseCode = "204"),
					@ApiResponse(description = "Country not found", responseCode = "404") })
	@DELETE
	@Path("/{id}")
	@Hidden
	public abstract Response delete(
			@Parameter(description = "ID of the country to update", schema = @Schema(type = "integer", format = "uuid", description = "param ID of the country to update"), required = true) @PathParam(value = "id") Integer id) throws CountryNotFoundException;
			

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
