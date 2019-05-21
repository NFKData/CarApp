package backend.service;

import java.util.List;

import backend.entity.Car;
import backend.exception.CarNotFoundException;

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
	 * @return The requested car, if found
	 * {@link Car}
	 */
	public Car getCar(String id);
	
	/**
	 * Insert a new car in the system
	 * @param car The car to be inserted
	 * @return The car if succeeded
	 * @throws CarFoundException if there's another car with the same ID
	 * {@link Car}
	 */
	public Car createCar(Car car);
	
	/**
	 * Update a car based on the received one
	 * @param car The car to be updated with new data
	 * @return The updated car
	 * @throws CarNotFoundException if there's no car with the ID of the received one
	 * {@link Car}
	 */
	public Car updateCar(Car car) throws CarNotFoundException;
	
	/**
	 * Delete a car based on the received id
	 * @param id UUID of the car to be deleted
	 * @throws CarNotFoundException if there's no car with the received ID
	 */
	public boolean deleteCar(String id) throws CarNotFoundException;
}
