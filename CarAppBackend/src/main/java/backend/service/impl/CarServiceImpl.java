package backend.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import backend.entity.CarEntity;
import backend.service.CarService;

@Stateless(name="carService")
public class CarServiceImpl implements CarService {

	private static final String findAllCarsQuery = "SELECT c FROM CarEntity c";
	
	@PersistenceContext(unitName="carEntityPU")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CarEntity> getAllCars() {
		return (List<CarEntity>) em.createQuery(findAllCarsQuery).getResultList();
	}

	@Override
	public CarEntity getCar(String id) {
		return em.find(CarEntity.class, id);
	}

	@Override
	public CarEntity createCar(CarEntity car) {
		try {
			em.persist(car);
			return car;
		} catch (EntityExistsException ex) {
			return null;
		}
	}

	@Override
	public CarEntity updateCar(CarEntity car) {
		return em.merge(car);
	}

	@Override
	public void deleteCar(String id) {
		CarEntity car = new CarEntity();
		car.setId(id);
		em.remove(car);
	}

}
