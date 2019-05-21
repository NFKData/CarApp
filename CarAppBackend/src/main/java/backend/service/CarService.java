package backend.service;

import java.util.List;

import backend.entity.CarEntity;
import backend.exception.CarFoundException;
import backend.exception.CarNotFoundException;

public interface CarService {

	/**
	 * Obtain every car in the system
	 * @return List of cars
	 * {@link CarEntity}
	 */
	public List<CarEntity> getAllCars();
	
	/**
	 * Obtain a car based on the received id
	 * @param id UUID to look for on the system
	 * @return The requested car, if found
	 * {@link CarEntity}
	 */
	public CarEntity getCar(String id);
	
	/**
	 * Insert a new car in the system
	 * @param car The car to be inserted
	 * @return The car if succeeded
	 * @throws CarFoundException if there's another car with the same ID
	 * {@link CarEntity}
	 */
	public CarEntity createCar(CarEntity car) throws CarFoundException;
	
	/**
	 * Update a car based on the received one
	 * @param car The car to be updated with new data
	 * @return The updated car
	 * @throws CarNotFoundException if there's no car with the ID of the received one
	 * {@link CarEntity}
	 */
	public CarEntity updateCar(CarEntity car) throws CarNotFoundException;
	
	/**
	 * Delete a car based on the received id
	 * @param id UUID of the car to be deleted
	 * @throws CarNotFoundException if there's no car with the received ID
	 */
	public boolean deleteCar(String id) throws CarNotFoundException;
}
