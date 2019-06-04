package com.backend.control;

import java.util.List;

import com.backend.entity.Country;
import com.backend.exception.CountryNotFoundException;
import com.backend.exception.InvalidEntityException;

public interface CountryService {

	List<Country> getAllCountries();

	Country getCountry(String id) throws CountryNotFoundException;

	Country createCountry(Country country) throws InvalidEntityException;

	Country updateCountry(Country country) throws InvalidEntityException, CountryNotFoundException;

	void deleteCountry(String id) throws CountryNotFoundException;

}
