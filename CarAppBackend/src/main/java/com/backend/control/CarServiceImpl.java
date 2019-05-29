package com.backend.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.backend.entity.Car;
import com.backend.exception.CarNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.helper.ValidationHelper;
import com.backend.interceptor.LogInterceptor;

@Stateless
@Interceptors(LogInterceptor.class)
public class CarServiceImpl implements CarService {

	@PersistenceContext(unitName = "carPU")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Car> getAllCars() {
		return (List<Car>) em.createNamedQuery("CarService.findAllCars").getResultList();
	}

	@Override
	public Car getCar(String id) throws CarNotFoundException {
		Car car = em.find(Car.class, id);
		if(car == null) 
			throw new CarNotFoundException(id);
		return car;
	}

	@Override
	public Car createCar(Car car) throws InvalidEntityException {
		ValidationHelper.validateCar(car);
		em.persist(car);
		return car;

	}

	@Override
	public Car updateCar(Car car) throws CarNotFoundException, InvalidEntityException {
		ValidationHelper.validateCar(car);
		Car auxCar = getCar(car.getId());
		auxCar.setBrand(car.getBrand());
		auxCar.setCountry(car.getCountry());
		auxCar.setRegistration(car.getRegistration());
		return em.merge(auxCar);
	}

	@Override
	public void deleteCar(String id) throws CarNotFoundException {
		em.remove(getCar(id));
	}

}
