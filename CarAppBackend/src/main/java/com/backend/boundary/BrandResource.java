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

import com.backend.entity.Brand;
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

	@Operation(summary = "Get all brands", tags = {
			"brands" }, description = "Retrieve every brand on the system", responses = {
					@ApiResponse(description = "List of brands", responseCode = "200", content = @Content(schema = @Schema(implementation = Brand.class, type = "array"))) })
	@GET
	public abstract Response getAll();

	@Operation(summary = "Get brand by ID", tags = {
			"brands" }, description = "Retrieve the brand with the specified ID", responses = {
					@ApiResponse(description = "Brand", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Brand.class)))),
					@ApiResponse(description = "Brand not found", responseCode = "404") })
	@GET
	@Path("/{id}")
	@Hidden
	public abstract Response getOne(
			@Parameter(description = "ID of the brand to retrieve", schema = @Schema(type = "string", format = "id", description = "param ID of the brand to retrieve"), required = true) @PathParam(value = "id") String id) throws BrandNotFoundException;

	@Operation(summary = "Create a new brand", tags = {
			"brands" }, description = "Create a new brand with specified data", responses = {
					@ApiResponse(description = "Brand", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Brand.class))),
					@ApiResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ValidationErrorDto.class)))) })
	@POST
	public abstract Response create(
			@Parameter(description = "Brand to create", schema = @Schema(implementation = Brand.class), required = true) Brand brand)
			throws InvalidEntityException;

	@Operation(summary = "Update brand by ID", tags = {
			"brands" }, description = "Update the brand with the specified ID and new data", responses = {
					@ApiResponse(description = "Brand", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Brand.class))),
					@ApiResponse(description = "Validation errors", responseCode = "400", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ValidationErrorDto.class)))),
					@ApiResponse(description = "Brand not found", responseCode = "404") })
	@PUT
	@Path("/{id}")
	@Hidden
	public abstract Response update(
			@Parameter(description = "ID of the brand to update", schema = @Schema(type = "string", format = "uuid", description = "param ID of the brand to update"), required = true) @PathParam(value = "id") String id,
			@Parameter(description = "New data for the brand", schema = @Schema(implementation = Brand.class), required = true) Brand brand)
			throws InvalidEntityException, BrandNotFoundException;

	@Operation(summary = "Delete brand by ID", tags = {
			"brands" }, description = "Delete the brand with the specified ID", responses = {
					@ApiResponse(description = "Brand deleted", responseCode = "204"),
					@ApiResponse(description = "Brand not found", responseCode = "404") })
	@DELETE
	@Path("/{id}")
	@Hidden
	public abstract Response delete(
			@Parameter(description = "ID of the brand to update", schema = @Schema(type = "string", format = "uuid", description = "param ID of the brand to update"), required = true) @PathParam(value = "id") String id) throws BrandNotFoundException;
			

}
