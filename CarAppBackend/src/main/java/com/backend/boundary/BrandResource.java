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

import com.backend.entity.Brand;
import com.backend.entity.dto.BrandDto;
import com.backend.entity.dto.CarDto;
import com.backend.entity.dto.ValidationErrorDto;
import com.backend.exception.BrandNotFoundException;
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
public abstract class BrandResource {

	/**
	 * Retrieve every {@link Brand} in the system
	 * @return Response with list of brands
	 * @throws InstantiationException If any exception happens with Reflection API
	 * @throws IllegalAccessException If any exception happens with Reflection API
	 * @throws IllegalArgumentException If any exception happens with Reflection API
	 * @throws InvocationTargetException If any exception happens with Reflection API
	 * @throws NoSuchMethodException If any exception happens with Reflection API
	 * @throws SecurityException If any exception happens with Reflection API
	 */
	@Operation(summary = "Get all brands", tags = {
			"brands" }, description = "Retrieve every brand on the system", responses = {
					@ApiResponse(description = "List of brands", responseCode = "200", content = @Content(schema = @Schema(implementation = BrandDto.class, type = "array"))) })
	@GET
	public abstract Response getAll() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Retrieve the brand with the specified ID
	 * @param id ID of the brand which will be looked for
	 * @return Response with found {@link Brand} 
	 * @throws BrandNotFoundException If there's no brand in the system with the specified ID
	 * @throws InstantiationException If any exception happens with Reflection API
	 * @throws IllegalAccessException If any exception happens with Reflection API
	 * @throws IllegalArgumentException If any exception happens with Reflection API
	 * @throws InvocationTargetException If any exception happens with Reflection API
	 * @throws NoSuchMethodException If any exception happens with Reflection API
	 * @throws SecurityException If any exception happens with Reflection API
	 */
	@Operation(summary = "Get brand by ID", tags = {
			"brands" }, description = "Retrieve the brand with the specified ID", responses = {
					@ApiResponse(description = "Brand", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BrandDto.class)))),
					@ApiResponse(description = "Brand not found", responseCode = "404") })
	@GET
	@Path("/{id}")
	@Hidden
	public abstract Response getOne(
			@Parameter(description = "ID of the brand to retrieve", schema = @Schema(type = "integer", format = "id", description = "param ID of the brand to retrieve"), required = true) @PathParam(value = "id") Integer id) throws BrandNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Insert a new brand in the system
	 * @param brand Brand to be inserted
	 * @return Response with created {@link Brand} entity 
	 * @throws InvalidEntityException If validation of the entity failed
	 * @throws InstantiationException If any exception happens with Reflection API
	 * @throws IllegalAccessException If any exception happens with Reflection API
	 * @throws IllegalArgumentException If any exception happens with Reflection API
	 * @throws InvocationTargetException If any exception happens with Reflection API
	 * @throws NoSuchMethodException If any exception happens with Reflection API
	 * @throws SecurityException If any exception happens with Reflection API
	 */
	@Operation(summary = "Create a new brand", tags = {
			"brands" }, description = "Create a new brand with specified data", responses = {
					@ApiResponse(description = "Brand", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BrandDto.class))),
					@ApiResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ValidationErrorDto.class)))) })
	@POST
	public abstract Response create(
			@Parameter(description = "Brand to create", schema = @Schema(implementation = BrandDto.class), required = true) Brand brand)
			throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Update the brand with the specified ID
	 * @param id ID of the brand to be updated
	 * @param brand Entity containing new brand data
	 * @return Response with the new {@link Brand} entity
	 * @throws InvalidEntityException If validation of the entity failed
	 * @throws BrandNotFoundException If there's no brand in the system with the specified ID
	 * @throws InstantiationException If any exception happens with Reflection API
	 * @throws IllegalAccessException If any exception happens with Reflection API
	 * @throws IllegalArgumentException If any exception happens with Reflection API
	 * @throws InvocationTargetException If any exception happens with Reflection API
	 * @throws NoSuchMethodException If any exception happens with Reflection API
	 * @throws SecurityException If any exception happens with Reflection API
	 */
	@Operation(summary = "Update brand by ID", tags = {
			"brands" }, description = "Update the brand with the specified ID and new data", responses = {
					@ApiResponse(description = "Brand", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BrandDto.class))),
					@ApiResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ValidationErrorDto.class)))),
					@ApiResponse(description = "Brand not found", responseCode = "404") })
	@PUT
	@Path("/{id}")
	@Hidden
	public abstract Response update(
			@Parameter(description = "ID of the brand to update", schema = @Schema(type = "integer", format = "integer", description = "param ID of the brand to update"), required = true) @PathParam(value = "id") Integer id,
			@Parameter(description = "New data for the brand", schema = @Schema(implementation = BrandDto.class), required = true) Brand brand)
			throws InvalidEntityException, BrandNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * Deletes a brand from the system
	 * @param id ID of the brand to be deleted
	 * @return Response without entities
	 * @throws BrandNotFoundException If there's no brand in the system with the specified ID
	 */
	@Operation(summary = "Delete brand by ID", tags = {
			"brands" }, description = "Delete the brand with the specified ID", responses = {
					@ApiResponse(description = "Brand deleted", responseCode = "204"),
					@ApiResponse(description = "Brand not found", responseCode = "404") })
	@DELETE
	@Path("/{id}")
	@Hidden
	public abstract Response delete(
			@Parameter(description = "ID of the brand to update", schema = @Schema(type = "integer", format = "integer", description = "param ID of the brand to update"), required = true) @PathParam(value = "id") Integer id) throws BrandNotFoundException;
			
	/**
	 * Retrieve list of cars ({@link Car}) with the specified {@link Brand}
	 * @param id ID of the brand from which to get cars
	 * @return List of cars with the specified brand
	 * @throws BrandNotFoundException If there's no brand in the system with the specified ID
	 * @throws InstantiationException If any exception happens with Reflection API
	 * @throws IllegalAccessException If any exception happens with Reflection API
	 * @throws IllegalArgumentException If any exception happens with Reflection API
	 * @throws InvocationTargetException If any exception happens with Reflection API
	 * @throws NoSuchMethodException If any exception happens with Reflection API
	 * @throws SecurityException If any exception happens with Reflection API
	 */
	@Operation(summary = "Get brand's cars by brand ID", tags = {
	"brands" }, description = "Retrieve brand's cars with the specified brand ID", responses = {
			@ApiResponse(description = "Cars", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CarDto.class)))),
			@ApiResponse(description = "Brand not found", responseCode = "404") })
	@GET
	@Path("/{id}/cars")
	@Hidden
	public abstract Response getCars(
			@Parameter(description = "ID of the brand from which to get cars", schema = @Schema(type = "integer", format = "integer", description = "ID of the brand from which to get cars"), required = true) @PathParam(value = "id") Integer id)
			throws BrandNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
	
}
