package com.backend.control;

import java.util.List;

import com.backend.entity.Car;
import com.backend.entity.Country;
import com.backend.exception.CountryNotFoundException;
import com.backend.exception.InvalidEntityException;

public interface CountryService {

	/**
	 * Obtain every {@link Country} in the system
	 * @return List of countries
	 */
	public List<Country> getAllCountries();

	/**
	 * Obtain a country based on the received id
	 * @param id ID to look for on the system
	 * @return The requested {@link Country}, if found
	 * @throws CountryNotFoundException If there's no country in the system with the specified ID
	 */
	public Country getCountry(Integer id) throws CountryNotFoundException;

	/**
	 * Insert a new country in the system
	 * @param country The country to be inserted
	 * @return The {@link Country} if succeeded
	 * @throws InvalidEntityException If the data are not valid
	 */
	public Country createCountry(Country country) throws InvalidEntityException;

	/**
	 * Update a country based on the received one
	 * @param country The country to be updated with new data
	 * @return The updated {@link Country}
	 * @throws CountryNotFoundException if there's no country with the ID of the received one
	 * @throws InvalidEntityException If the data are not valid
	 */
	public Country updateCountry(Country country) throws InvalidEntityException, CountryNotFoundException;
	
	/**
	 * Delete a {@link Country} based on the received id
	 * @param id ID of the country to be deleted
	 * @throws CountryNotFoundException If there's no country with the received ID
	 */
	public void deleteCountry(Integer id) throws CountryNotFoundException;
	
	/**
	 * Retrieve the list of cars of the specified {@link Country}
	 * @param id ID of the country
	 * @return List of the found cars
	 * @throws CountryNotFoundException If there's no country with the received ID
	 */
	public List<Car> getCountryCars(Integer id) throws CountryNotFoundException;

}
