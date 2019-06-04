package com.backend.boundary;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.backend.control.CountryService;
import com.backend.entity.Country;
import com.backend.exception.CountryNotFoundException;
import com.backend.exception.InvalidEntityException;

@Path("/countries")
public class CountryResourceImpl extends CountryResource {

	@EJB(name = "countryService")
	private CountryService countryService;

	@Override
	public Response getAll() {
		return Response.status(Status.OK).entity(new GenericEntity<List<Country>>(countryService.getAllCountries()) {
		}).build();
	}

	@Override
	public Response getOne(String id) throws CountryNotFoundException {
		return Response.status(Status.OK).entity(countryService.getCountry(id)).build();
	}

	@Override
	public Response create(Country country) throws InvalidEntityException {
		return Response.status(Status.CREATED).entity(countryService.createCountry((Country) country)).build();
	}

	@Override
	public Response update(String id, Country entity) throws InvalidEntityException, CountryNotFoundException {
		Country country = (Country) entity;
		country.setId(id);
		return Response.status(Status.OK).entity(countryService.updateCountry(country)).build();
	}

	@Override
	public Response delete(String id) throws CountryNotFoundException {
		countryService.deleteCountry(id);
		return Response.status(Status.NO_CONTENT).build();
	}
}