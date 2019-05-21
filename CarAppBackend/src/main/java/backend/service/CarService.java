package backend.service;

import java.util.List;

import backend.entity.CarEntity;

public interface CarService {

	/**
	 * Obtain every car in the system
	 * @return List of cars
	 */
	public List<CarEntity> getAllCars();
	
	/**
	 * Obtain a car based on the received id
	 * @param id UUID to look for on the system
	 * @return The requested car, if found
	 */
	public CarEntity getCar(String id);
	
	/**
	 * Insert a new car in the system
	 * @param car The car to be inserted
	 * @return The car if succeeded, null if car already exists
	 */
	public CarEntity createCar(CarEntity car);
	
	/**
	 * Update a car based on the received one
	 * @param car The car to be updated with new data
	 * @return The updated car
	 */
	public CarEntity updateCar(CarEntity car);
	
	/**
	 * Delete a car based on the received id
	 * @param id UUID of the car to be deleted
	 */
	public void deleteCar(String id);
}
