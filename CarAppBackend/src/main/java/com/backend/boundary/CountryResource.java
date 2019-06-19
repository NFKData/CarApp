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

import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.backend.control.fallback.ResourceFallbackHandler;
import com.backend.entity.Car;
import com.backend.entity.Country;
import com.backend.entity.dto.ErrorDto;
import com.backend.exception.CountryNotFoundException;
import com.backend.exception.InvalidEntityException;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class CountryResource {

	/**
	 * Retrieve every {@link Country} in the system
	 * 
	 * @return Response with list of countries
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
	@Operation(summary = "Get all countries", description = "Retrieve every country on the system")
	@APIResponse(description = "List of countries", responseCode = "200", content = @Content(schema = @Schema(implementation = Country.class, type = SchemaType.ARRAY)))
	@Tag(name = "countries")
	@GET
	@Timeout(5000)
	@Retry(maxRetries = 5, retryOn = IllegalStateException.class, maxDuration = 2000)
	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
	@Fallback(ResourceFallbackHandler.class)
	@Bulkhead(10)
	public abstract Response getAll() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Retrieve the country with the specified ID
	 * 
	 * @param id ID of the country which will be looked for
	 * @return Response with found {@link Country}
	 * @throws CountryNotFoundException  If there's no country in the system with
	 *                                   the specified ID
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
	@Operation(summary = "Get country by ID", description = "Retrieve the country with the specified ID", hidden = true)
	@APIResponses({
			@APIResponse(description = "Country", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Country.class, type = SchemaType.OBJECT))),
			@APIResponse(description = "Country not found", responseCode = "404") })
	@Tag(name = "countries")
	@GET
	@Path("/{id}")
	@Timeout(5000)
	@Retry(maxRetries = 5, retryOn = IllegalStateException.class, maxDuration = 2000)
	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
	@Fallback(ResourceFallbackHandler.class)
	@Bulkhead(10)
	public abstract Response getOne(
			@Parameter(description = "ID of the country to retrieve", schema = @Schema(type = SchemaType.INTEGER, format = "id", description = "param ID of the country to retrieve"), required = true) @PathParam(value = "id") Integer id)
			throws CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Insert a new country in the system
	 * 
	 * @param country Country to be inserted
	 * @return Response with created {@link Country} entity
	 * @throws InvalidEntityException    If validation of the entity failed
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
	@Operation(summary = "Create a new country", description = "Create a new country with specified data")
	@APIResponses({
			@APIResponse(description = "Country", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Country.class, type = SchemaType.OBJECT))),
			@APIResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class, type = SchemaType.OBJECT))) })
	@Tag(name = "countries")
	@POST
	@Timeout(5000)
	@Retry(maxRetries = 5, retryOn = IllegalStateException.class, maxDuration = 2000)
	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
	@Fallback(ResourceFallbackHandler.class)
	@Bulkhead(10)
	public abstract Response create(
			@Parameter(description = "Country to create", schema = @Schema(implementation = Country.class, type = SchemaType.OBJECT), required = true) Country country)
			throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Update the country with the specified ID
	 * 
	 * @param id    ID of the country to be updated
	 * @param brand Entity containing new country data
	 * @return Response with the new {@link Country} entity
	 * @throws InvalidEntityException    If validation of the entity failed
	 * @throws CountryNotFoundException  If there's no country in the system with
	 *                                   the specified ID
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
	@Operation(summary = "Update country by ID", description = "Update the country with the specified ID and new data", hidden = true)
	@APIResponses({
			@APIResponse(description = "Country", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Country.class, type = SchemaType.OBJECT))),
			@APIResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class, type = SchemaType.OBJECT))),
			@APIResponse(description = "Country not found", responseCode = "404") })
	@Tag(name = "countries")
	@PUT
	@Path("/{id}")
	@Timeout(5000)
	@Retry(maxRetries = 5, retryOn = IllegalStateException.class, maxDuration = 2000)
	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
	@Fallback(ResourceFallbackHandler.class)
	@Bulkhead(10)
	public abstract Response update(
			@Parameter(description = "ID of the country to update", schema = @Schema(type = SchemaType.INTEGER, format = "id", description = "param ID of the country to update"), required = true) @PathParam(value = "id") Integer id,
			@Parameter(description = "New data for the country", schema = @Schema(implementation = Country.class, type = SchemaType.OBJECT), required = true) Country country)
			throws InvalidEntityException, CountryNotFoundException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Deletes a country from the system
	 * 
	 * @param id ID of the country to be deleted
	 * @return Response without entities
	 * @throws CountryNotFoundException If there's no country in the system with the
	 *                                  specified ID
	 */
	@Operation(summary = "Delete country by ID", description = "Delete the country with the specified ID", hidden = true)
	@APIResponses({ @APIResponse(description = "Country deleted", responseCode = "204"),
			@APIResponse(description = "Country not found", responseCode = "404") })
	@Tag(name = "countries")
	@DELETE
	@Path("/{id}")
	@Timeout(5000)
	@Retry(maxRetries = 5, retryOn = IllegalStateException.class, maxDuration = 2000)
	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
	@Fallback(ResourceFallbackHandler.class)
	@Bulkhead(10)
	public abstract Response delete(
			@Parameter(description = "ID of the country to update", schema = @Schema(type = SchemaType.INTEGER, format = "id", description = "param ID of the country to update"), required = true) @PathParam(value = "id") Integer id)
			throws CountryNotFoundException;

	/**
	 * Retrieve list of cars ({@link Car}) with the specified {@link Country}
	 * 
	 * @param id ID of the country from which to get cars
	 * @return List of cars with the specified country
	 * @throws CountryNotFoundException  If there's no country in the system with
	 *                                   the specified ID
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
	@Operation(summary = "Get country's cars by brand ID", description = "Retrieve country's cars with the specified country ID", hidden = true)
	@APIResponses({
			@APIResponse(description = "Cars", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Car.class, type = SchemaType.ARRAY))),
			@APIResponse(description = "Country not found", responseCode = "404") })
	@Tag(name = "countries")
	@GET
	@Path("/{id}/cars")
	@Timeout(5000)
	@Retry(maxRetries = 5, retryOn = IllegalStateException.class, maxDuration = 2000)
	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
	@Fallback(ResourceFallbackHandler.class)
	@Bulkhead(10)
	public abstract Response getCars(
			@Parameter(description = "ID of the country from which to get cars", schema = @Schema(type = SchemaType.INTEGER, format = "id", description = "ID of the country from which to get cars"), required = true) @PathParam(value = "id") Integer id)
			throws CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;
}
