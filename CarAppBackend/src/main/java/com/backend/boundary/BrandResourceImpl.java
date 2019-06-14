package com.backend.boundary;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.backend.control.BrandService;
import com.backend.entity.Brand;
import com.backend.entity.dto.BrandDto;
import com.backend.entity.dto.CarDto;
import com.backend.exception.BrandNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.helper.DtoHelper;

@Path("/brands")
public class BrandResourceImpl extends BrandResource {

	@Inject
	private BrandService brandService;

	@Override
	public Response getAll() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		return Response.status(Status.OK).entity(new GenericEntity<List<BrandDto>>(
				DtoHelper.entityListToDtoList(brandService.getAllBrands(), BrandDto.class)) {
		}).build();
	}

	@Override
	public Response getOne(Integer id) throws BrandNotFoundException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return Response.status(Status.OK).entity(DtoHelper.entityToDto(brandService.getBrand(id), BrandDto.class))
				.build();
	}

	@Override
	public Response create(Brand brand) throws InvalidEntityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return Response.status(Status.CREATED)
				.entity(DtoHelper.entityToDto(brandService.createBrand(brand), BrandDto.class)).build();
	}

	@Override
	public Response update(Integer id, Brand brand)
			throws InvalidEntityException, BrandNotFoundException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		brand.setId(id);
		return Response.status(Status.OK).entity(DtoHelper.entityToDto(brandService.updateBrand(brand), BrandDto.class))
				.build();
	}

	@Override
	public Response delete(Integer id) throws BrandNotFoundException {
		brandService.deleteBrand(id);
		return Response.status(Status.NO_CONTENT).build();
	}

	@Override
	public Response getCars(Integer id) throws BrandNotFoundException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		return Response.status(Status.OK).entity(new GenericEntity<List<CarDto>>(
				DtoHelper.entityListToDtoList(brandService.getBrandCars(id), CarDto.class)) {
		}).build();
	}
}