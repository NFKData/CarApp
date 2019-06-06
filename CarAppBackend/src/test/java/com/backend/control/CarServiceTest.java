package com.backend.control;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.backend.entity.Car;
import com.backend.exception.CarNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.helper.ValidationHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidationHelper.class)
public class CarServiceTest {

	private static final String DEFAULT_ID = "123";
	@Mock
	private EntityManager em;
	@Mock
	private Query query;
	@InjectMocks
	private CarServiceImpl carService;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void whenGettingAllCars_shouldReturnListOfCars() {
		List<Car> cars = new ArrayList<>();
		when(em.createNamedQuery("CarService.findAllCars")).thenReturn(query);
		when(query.getResultList()).thenReturn(cars);
		assertEquals(cars, carService.getAllCars());
		verify(em).createNamedQuery("CarService.findAllCars");
		verify(query).getResultList();
	}

	@Test
	public void whenGettingACar_shouldReturnCarEntity() throws CarNotFoundException {
		Car car = new Car();
		car.setId(DEFAULT_ID);
		when(em.find(Car.class, DEFAULT_ID)).thenReturn(car);
		assertEquals(car, carService.getCar(DEFAULT_ID));
		verify(em).find(Car.class, DEFAULT_ID);
	}

	@Test(expected = CarNotFoundException.class)
	public void whenGettingANonExistentCar_shouldThrowCarNotFoundException() throws CarNotFoundException {
		when(em.find(Car.class, DEFAULT_ID)).thenReturn(null);
		carService.getCar(DEFAULT_ID);
		verify(em).find(Car.class, DEFAULT_ID);
	}

	@Test
	public void whenCreatingACar_shouldReturnCarEntity() throws InvalidEntityException {
		Car car = new Car();
		car.setId(DEFAULT_ID);
		PowerMockito.mockStatic(ValidationHelper.class);
		doNothing().when(em).persist(car);
		PowerMockito.doNothing().when(ValidationHelper.class);
		assertEquals(car, carService.createCar(car));
		verify(em).persist(car);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}

	@Test(expected = InvalidEntityException.class)
	public void whenCreatingAnInvalidCar_shouldThrowInvalidEntityException() throws Exception {
		Car car = new Car();
		PowerMockito.mockStatic(ValidationHelper.class);
		doNothing().when(em).persist(car);
		PowerMockito.doThrow(new InvalidEntityException(new ArrayList<>())).when(ValidationHelper.class, "validateCar",
				car);
		carService.createCar(car);
		verify(em).persist(car);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}

	@Test
	public void whenUpdatingACar_shouldReturnCarEntity() throws CarNotFoundException, InvalidEntityException {
		Car car = new Car();
		car.setId(DEFAULT_ID);
		PowerMockito.mockStatic(ValidationHelper.class);
		when(em.find(Car.class, DEFAULT_ID)).thenReturn(car);
		when(em.merge(car)).thenReturn(car);
		assertEquals(car, carService.updateCar(car));
		verify(em).merge(car);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}

	@Test(expected = CarNotFoundException.class)
	public void whenUpdatingANonExistentCar_shouldThrowCarNotFoundException()
			throws CarNotFoundException, InvalidEntityException {
		PowerMockito.mockStatic(ValidationHelper.class);
		when(em.find(Car.class, DEFAULT_ID)).thenReturn(null);
		carService.updateCar(new Car());
		verify(em.find(Car.class, DEFAULT_ID));
	}

	@Test(expected = InvalidEntityException.class)
	public void whenUpdatingAnInvalidCar_shouldThrowInvalidEntityException() throws Exception {
		Car car = new Car();
		car.setId(DEFAULT_ID);
		when(em.find(Car.class, DEFAULT_ID)).thenReturn(car);
		PowerMockito.mockStatic(ValidationHelper.class);
		PowerMockito.doThrow(new InvalidEntityException(new ArrayList<>())).when(ValidationHelper.class, "validateCar",
				car);
		carService.updateCar(car);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}

	@Test
	public void whenDeletingACar_shouldntThrowAnything() throws CarNotFoundException {
		Car car = new Car();
		when(em.find(Car.class, DEFAULT_ID)).thenReturn(car);
		doNothing().when(em).remove(car);
		carService.deleteCar(DEFAULT_ID);
		verify(em).remove(car);
	}
	
	@Test(expected = CarNotFoundException.class)
	public void whenDeletingANonExistentCar_shouldThrowCarNotFoundException() throws CarNotFoundException {
		Car car = new Car();
		car.setId(DEFAULT_ID);
		when(em.find(Car.class, DEFAULT_ID)).thenReturn(null);
		doNothing().when(em).remove(car);
		carService.deleteCar(DEFAULT_ID);
		verify(em).find(Car.class, DEFAULT_ID);
		verify(em).remove(car);
	}

}
