package com.backend.service;

import java.util.List;

import com.backend.entity.Car;
import com.backend.exception.CarNotFoundException;
import com.backend.exception.InvalidEntityException;

public interface CarService {

	/**
	 * Obtain every car in the system
	 * @return List of cars
	 * {@link Car}
	 */
	public List<Car> getAllCars();
	
	/**
	 * Obtain a car based on the received id
	 * @param id UUID to look for on the system
	 * @return The requested {@link Car}, if found
	 */
	public Car getCar(String id);
	
	/**
	 * Insert a new car in the system
	 * @param car The car to be inserted
	 * @return The {@link Car} if succeeded
	 * @throws InvalidEntityException If the data are not valid
	 * @throws CarFoundException if there's another car with the same ID
	 */
	public Car createCar(Car car) throws InvalidEntityException;
	
	/**
	 * Update a car based on the received one
	 * @param car The car to be updated with new data
	 * @return The updated {@link Car}
	 * @throws CarNotFoundException if there's no car with the ID of the received one
	 * @throws InvalidEntityException If the data are not valid
	 */
	public Car updateCar(Car car) throws CarNotFoundException, InvalidEntityException;
	
	/**
	 * Delete a {@link Car} based on the received id
	 * @param id UUID of the car to be deleted
	 * @throws CarNotFoundException if there's no car with the received ID
	 */
	public void deleteCar(String id) throws CarNotFoundException;
}
