package backend.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import backend.entity.CarEntity;
import backend.exception.CarFoundException;
import backend.exception.CarNotFoundException;
import backend.service.CarService;

@Stateless
public class CarServiceImpl implements CarService {

	@PersistenceContext(unitName="carEntityPU")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CarEntity> getAllCars() {
		return (List<CarEntity>) em.createNamedQuery("CarService.findAllCars").getResultList();
	}

	@Override
	public CarEntity getCar(String id) {
		return em.find(CarEntity.class, id);
	}

	@Override
	public CarEntity createCar(CarEntity car) throws CarFoundException {
		try {
			em.persist(car);
			return car;
		} catch (EntityExistsException ex) {
			throw new CarFoundException(car);
		}
	}

	@Override
	public CarEntity updateCar(CarEntity car) throws CarNotFoundException {
		CarEntity auxCar = em.find(CarEntity.class, car.getId());
		if(auxCar == null) {
			throw new CarNotFoundException(car.getId());
		}
		return em.merge(car);
	}

	@Override
	public boolean deleteCar(String id) throws CarNotFoundException {
		CarEntity car = em.find(CarEntity.class, id);
		if(car == null) {
			throw new CarNotFoundException(id);
		}
		em.remove(car);
		return true;
	}

}
