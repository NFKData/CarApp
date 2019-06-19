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
import com.backend.entity.Brand;
import com.backend.entity.Car;
import com.backend.entity.dto.BrandDto;
import com.backend.entity.dto.CarDto;
import com.backend.entity.dto.ErrorDto;
import com.backend.exception.BrandNotFoundException;
import com.backend.exception.InvalidEntityException;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class BrandResource {

	/**
	 * Retrieve every {@link Brand} in the system
	 * 
	 * @return Response with list of brands
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
	@Operation(summary = "Get all brands", description = "Retrieve every brand on the system")
	@Tag(name = "brands")
	@APIResponse(description = "List of brands", responseCode = "200", content = @Content(schema = @Schema(implementation = BrandDto.class, type = SchemaType.ARRAY)))
	@GET
	@Timeout(5000)
	@Retry(maxRetries = 5, retryOn = IllegalStateException.class, maxDuration = 2000)
	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
	@Fallback(ResourceFallbackHandler.class)
	@Bulkhead(10)
	public abstract Response getAll() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Retrieve the brand with the specified ID
	 * 
	 * @param id ID of the brand which will be looked for
	 * @return Response with found {@link Brand}
	 * @throws BrandNotFoundException    If there's no brand in the system with the
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
	@Operation(summary = "Get brand by ID", hidden = true, description = "Retrieve the brand with the specified ID")
	@APIResponses({
			@APIResponse(description = "Brand", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BrandDto.class, type = SchemaType.OBJECT))),
			@APIResponse(description = "Brand not found", responseCode = "404") })
	@Tag(name = "brands")
	@GET
	@Path("/{id}")
	@Timeout(5000)
	@Retry(maxRetries = 5, retryOn = IllegalStateException.class, maxDuration = 2000)
	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
	@Fallback(ResourceFallbackHandler.class)
	@Bulkhead(10)
	public abstract Response getOne(
			@Parameter(description = "ID of the brand to retrieve", schema = @Schema(type = SchemaType.INTEGER, format = "id", description = "param ID of the brand to retrieve"), required = true) @PathParam(value = "id") Integer id)
			throws BrandNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Insert a new brand in the system
	 * 
	 * @param brand Brand to be inserted
	 * @return Response with created {@link Brand} entity
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
	@Operation(summary = "Create a new brand", description = "Create a new brand with specified data")
	@Tag(name = "brands")
	@APIResponses({
			@APIResponse(description = "Brand", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BrandDto.class, type = SchemaType.OBJECT))),
			@APIResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class, type = SchemaType.ARRAY))) })
	@POST
	@Timeout(5000)
	@Retry(maxRetries = 5, retryOn = IllegalStateException.class, maxDuration = 2000)
	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
	@Fallback(ResourceFallbackHandler.class)
	@Bulkhead(10)
	public abstract Response create(
			@Parameter(description = "Brand to create", schema = @Schema(implementation = BrandDto.class, type = SchemaType.OBJECT), required = true) Brand brand)
			throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Update the brand with the specified ID
	 * 
	 * @param id    ID of the brand to be updated
	 * @param brand Entity containing new brand data
	 * @return Response with the new {@link Brand} entity
	 * @throws InvalidEntityException    If validation of the entity failed
	 * @throws BrandNotFoundException    If there's no brand in the system with the
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
	@Operation(summary = "Update brand by ID", hidden = true, description = "Update the brand with the specified ID and new data")
	@APIResponses({
			@APIResponse(description = "Brand", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BrandDto.class, type = SchemaType.OBJECT))),
			@APIResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class, type = SchemaType.OBJECT))),
			@APIResponse(description = "Brand not found", responseCode = "404") })
	@Tag(name = "brands")
	@PUT
	@Path("/{id}")
	@Timeout(5000)
	@Retry(maxRetries = 5, retryOn = IllegalStateException.class, maxDuration = 2000)
	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
	@Fallback(ResourceFallbackHandler.class)
	@Bulkhead(10)
	public abstract Response update(
			@Parameter(description = "ID of the brand to update", schema = @Schema(type = SchemaType.INTEGER, format = "integer", description = "param ID of the brand to update"), required = true) @PathParam(value = "id") Integer id,
			@Parameter(description = "New data for the brand", schema = @Schema(implementation = BrandDto.class), required = true) Brand brand)
			throws InvalidEntityException, BrandNotFoundException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Deletes a brand from the system
	 * 
	 * @param id ID of the brand to be deleted
	 * @return Response without entities
	 * @throws BrandNotFoundException If there's no brand in the system with the
	 *                                specified ID
	 */
	@Operation(summary = "Delete brand by ID", hidden = true, description = "Delete the brand with the specified ID")
	@APIResponses({ @APIResponse(description = "Brand deleted", responseCode = "204"),
			@APIResponse(description = "Brand not found", responseCode = "404") })
	@Tag(name = "brands")
	@DELETE
	@Path("/{id}")
	@Timeout(5000)
	@Retry(maxRetries = 5, retryOn = IllegalStateException.class, maxDuration = 2000)
	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
	@Fallback(ResourceFallbackHandler.class)
	@Bulkhead(10)
	public abstract Response delete(
			@Parameter(description = "ID of the brand to update", schema = @Schema(type = SchemaType.INTEGER, format = "id", description = "param ID of the brand to update"), required = true) @PathParam(value = "id") Integer id)
			throws BrandNotFoundException;

	/**
	 * Retrieve list of cars ({@link Car}) with the specified {@link Brand}
	 * 
	 * @param id ID of the brand from which to get cars
	 * @return List of cars with the specified brand
	 * @throws BrandNotFoundException    If there's no brand in the system with the
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
	@Operation(summary = "Get brand's cars by brand ID", hidden = true, description = "Retrieve brand's cars with the specified brand ID")
	@APIResponses({
			@APIResponse(description = "Cars", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarDto.class, type = SchemaType.OBJECT))),
			@APIResponse(description = "Brand not found", responseCode = "404") })
	@Tag(name = "brands")
	@GET
	@Path("/{id}/cars")
	@Timeout(5000)
	@Retry(maxRetries = 5, retryOn = IllegalStateException.class, maxDuration = 2000)
	@CircuitBreaker(successThreshold = 10, requestVolumeThreshold = 4, failureRatio = 0.75, delay = 1000)
	@Fallback(ResourceFallbackHandler.class)
	@Bulkhead(10)
	public abstract Response getCars(
			@Parameter(description = "ID of the brand from which to get cars", schema = @Schema(type = SchemaType.INTEGER, format = "id", description = "ID of the brand from which to get cars"), required = true) @PathParam(value = "id") Integer id)
			throws BrandNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException;

}
