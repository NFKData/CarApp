package com.backend.boundary;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.backend.control.BrandService;
import com.backend.entity.Brand;
import com.backend.exception.BrandNotFoundException;
import com.backend.exception.InvalidEntityException;

@Path("/brands")
public class BrandResourceImpl extends BrandResource {

	@EJB(name = "brandService")
	private BrandService brandService;

	@Override
	public Response getAll() {
		return Response.status(Status.OK).entity(new GenericEntity<List<Brand>>(brandService.getAllBrands()) {
		}).build();
	}

	@Override
	public Response getOne(String id) throws BrandNotFoundException {
		return Response.status(Status.OK).entity(brandService.getBrand(id)).build();
	}

	@Override
	public Response create(Brand brand) throws InvalidEntityException {
		return Response.status(Status.CREATED).entity(brandService.createBrand((Brand) brand)).build();
	}

	@Override
	public Response update(String id, Brand entity) throws InvalidEntityException, BrandNotFoundException {
		Brand brand = (Brand) entity;
		brand.setId(id);
		return Response.status(Status.OK).entity(brandService.updateBrand(brand)).build();
	}

	@Override
	public Response delete(String id) throws BrandNotFoundException {
		brandService.deleteBrand(id);
		return Response.status(Status.NO_CONTENT).build();
	}
}