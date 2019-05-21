package backend.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import backend.entity.Car;
import backend.exception.CarFoundException;
import backend.exception.CarNotFoundException;
import backend.service.CarService;

@Stateless
public class CarServiceImpl implements CarService {

	@PersistenceContext(unitName="carPU")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Car> getAllCars() {
		return (List<Car>) em.createNamedQuery("CarService.findAllCars").getResultList();
	}

	@Override
	public Car getCar(String id) {
		return em.find(Car.class, id);
	}

	@Override
	public Car createCar(Car car) throws CarFoundException {
		try {
			em.persist(car);
			return car;
		} catch (EntityExistsException ex) {
			throw new CarFoundException(car);
		}
	}

	@Override
	public Car updateCar(Car car) throws CarNotFoundException {
		Car auxCar = em.find(Car.class, car.getId());
		if(auxCar == null) {
			throw new CarNotFoundException(car.getId());
		}
		car.setCreatedAt(auxCar.getCreatedAt());
		return em.merge(car);
	}

	@Override
	public boolean deleteCar(String id) throws CarNotFoundException {
		Car car = em.find(Car.class, id);
		if(car == null) {
			throw new CarNotFoundException(id);
		}
		em.remove(car);
		return true;
	}

}
