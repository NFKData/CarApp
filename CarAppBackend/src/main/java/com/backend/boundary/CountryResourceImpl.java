package com.backend.boundary;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.backend.control.CountryService;
import com.backend.entity.Country;
import com.backend.entity.dto.CarDto;
import com.backend.entity.dto.CountryDto;
import com.backend.entity.dto.helper.DtoHelper;
import com.backend.exception.CountryNotFoundException;
import com.backend.exception.InvalidEntityException;

@Path("/countries")
public class CountryResourceImpl extends CountryResource {

	@EJB(name = "countryService")
	private CountryService countryService;

	@Override
	public Response getAll() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return Response.status(Status.OK).entity(new GenericEntity<List<CountryDto>>(DtoHelper.entityListToDtoList(countryService.getAllCountries(), CountryDto.class)) {
		}).build();
	}

	@Override
	public Response getOne(Integer id) throws CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return Response.status(Status.OK).entity(DtoHelper.entityToDto(countryService.getCountry(id), CountryDto.class)).build();
	}

	@Override
	public Response create(Country country) throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return Response.status(Status.CREATED).entity(DtoHelper.entityToDto(countryService.createCountry(country), CountryDto.class)).build();
	}

	@Override
	public Response update(Integer id, Country entity) throws InvalidEntityException, CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Country country = (Country) entity;
		country.setId(id);
		return Response.status(Status.OK).entity(DtoHelper.entityToDto(countryService.updateCountry(country), CountryDto.class)).build();
	}

	@Override
	public Response delete(Integer id) throws CountryNotFoundException {
		countryService.deleteCountry(id);
		return Response.status(Status.NO_CONTENT).build();
	}

	@Override
	public Response getCars(Integer id) throws CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return Response.status(Status.OK).entity(new GenericEntity<List<CarDto>>(DtoHelper.entityListToDtoList(countryService.getCountry(id).getCars(), CarDto.class)) {}).build();
	}
}