package com.backend.boundary;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.backend.control.CarService;
import com.backend.entity.Car;
import com.backend.entity.dto.BrandDto;
import com.backend.entity.dto.CarDto;
import com.backend.entity.dto.CountryDto;
import com.backend.entity.dto.helper.DtoHelper;
import com.backend.exception.CarNotFoundException;
import com.backend.exception.InvalidEntityException;

@Path("/cars")
public class CarResourceImpl extends CarResource {

	@EJB(name = "carService")
	private CarService carService;

	@Override
	public Response getAll() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return Response.status(Status.OK).entity(new GenericEntity<List<CarDto>>(DtoHelper.entityListToDtoList(carService.getAllCars(), CarDto.class)) {
		}).build();
	}

	@Override
	public Response getOne(String id) throws CarNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return Response.status(Status.OK).entity(DtoHelper.entityToDto(carService.getCar(id), CarDto.class)).build();
	}

	@Override
	public Response create(Car car) throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return Response.status(Status.CREATED).entity(DtoHelper.entityToDto(carService.createCar(car), CarDto.class)).build();
	}

	@Override
	public Response update(String id, Car entity) throws CarNotFoundException, InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Car car = (Car) entity;
		car.setId(id);
		return Response.status(Status.OK).entity(DtoHelper.entityToDto(carService.updateCar(car), CarDto.class)).build();
	}

	@Override
	public Response delete(String id) throws CarNotFoundException {
		carService.deleteCar(id);
		return Response.status(Status.NO_CONTENT).build();
	}

	@Override
	public Response getBrand(String id) throws CarNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return Response.status(Status.OK).entity(DtoHelper.entityToDto(carService.getCar(id).getBrand(), BrandDto.class)).build();
	}

	@Override
	public Response getCountry(String id) throws CarNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return Response.status(Status.OK).entity(DtoHelper.entityToDto(carService.getCar(id).getCountry(), CountryDto.class)).build();
	}
}