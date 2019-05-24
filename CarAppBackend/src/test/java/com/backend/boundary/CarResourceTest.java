package com.backend.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.backend.control.CarService;
import com.backend.entity.Car;
import com.backend.exception.CarNotFoundException;
import com.backend.exception.InvalidEntityException;

class CarResourceTest {

	private static final String DEFAULT_ID = "123";
	@Mock
	private CarService carService;
	@InjectMocks
	private CarResourceImpl carResource;

	@BeforeEach
	void setUp() {
		initMocks(this);
	}

	@Test
	void whenGettingAllCars_shouldReturnOk() {
		List<Car> carsExpected = new ArrayList<Car>();
		carsExpected.add(new Car());
		carsExpected.get(0).setId(DEFAULT_ID);
		when(carService.getAllCars()).thenReturn(carsExpected);
		Response response = carResource.getAll();
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(carsExpected, response.getEntity());
		verify(carService).getAllCars();
	}

	@Test
	void whenGettingOneCar_shouldReturnOk() throws CarNotFoundException {
		Car carExpected = new Car();
		carExpected.setId(DEFAULT_ID);
		when(carService.getCar(carExpected.getId())).thenReturn(carExpected);
		Response response = carResource.getOne(carExpected.getId());
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(carExpected, response.getEntity());
		verify(carService).getCar(carExpected.getId());
	}
	
	@Test
	void whenGettingAnNonexistentCar_shouldThrowCarNotFoundException() throws CarNotFoundException {
		when(carService.getCar(DEFAULT_ID)).thenThrow(new CarNotFoundException(DEFAULT_ID));
		assertThrows(CarNotFoundException.class, () -> {
			carResource.getOne(DEFAULT_ID);
		});
		verify(carService).getCar(DEFAULT_ID);
	}

	@Test
	void whenCreatingACar_shouldReturnCreated() throws InvalidEntityException {
		Car toCreate = new Car();
		toCreate.setBrand("BMW");
		toCreate.setCountry("Spain");
		toCreate.setRegistration(LocalDateTime.now());
		toCreate.prePersit();
		when(carService.createCar(toCreate)).thenReturn(toCreate);
		Response response = carResource.create(toCreate);
		assertEquals(Status.CREATED, Status.fromStatusCode(response.getStatus()));
		assertEquals(toCreate, response.getEntity());
		verify(carService).createCar(toCreate);
	}
	
	@Test
	void whenCreatingAnInvalidCar_shouldThrowInvalidEntityException() throws InvalidEntityException {
		Car car = new Car();
		when(carService.createCar(car)).thenThrow(new InvalidEntityException(null));
		assertThrows(InvalidEntityException.class, () -> {			
			carResource.create(car);
		});
		verify(carService).createCar(car);
	}

	@Test
	void whenUpdatingACar_shouldReturnOk() throws CarNotFoundException, InvalidEntityException {
		Car car = new Car();
		car.setId(DEFAULT_ID);
		when(carService.updateCar(car)).thenReturn(car);
		Response response = carResource.update(car.getId(), car);
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(car, response.getEntity());
		verify(carService).updateCar(car);
	}
	
	@Test
	void whenUpdatingAnNonexistentCar_shouldThrowCarNotFoundException() throws CarNotFoundException, InvalidEntityException {
		Car car = new Car();
		car.setId(DEFAULT_ID);
		when(carService.updateCar(car)).thenThrow(new CarNotFoundException(DEFAULT_ID));
		assertThrows(CarNotFoundException.class, () -> {
			carResource.update(car.getId(), car);
		});
		verify(carService).updateCar(car);
	}
	
	@Test
	void whenUpdatingAnInvalidCar_shouldThrowInvalidEntityException() throws InvalidEntityException, CarNotFoundException {
		Car car = new Car();
		car.setId(DEFAULT_ID);
		when(carService.updateCar(car)).thenThrow(new InvalidEntityException(null));
		assertThrows(InvalidEntityException.class, () -> {			
			carResource.update(DEFAULT_ID, car);
		});
		verify(carService).updateCar(car);
	}

	@Test
	void whenDeletingACar_shouldReturnNoContent() throws CarNotFoundException {
		doNothing().when(carService).deleteCar(DEFAULT_ID);
		Response response = carResource.delete(DEFAULT_ID);
		assertEquals(Status.NO_CONTENT, Status.fromStatusCode(response.getStatus()));
		assertNull(response.getEntity());
		verify(carService).deleteCar(DEFAULT_ID);
	}
	
	@Test
	void whenDeletingAnNonexistentCar_shouldThrowCarNotFoundException() throws CarNotFoundException, InvalidEntityException {
		doThrow(new CarNotFoundException(DEFAULT_ID)).when(carService).deleteCar(DEFAULT_ID);
		assertThrows(CarNotFoundException.class, () -> {
			carResource.delete(DEFAULT_ID);
		});
		verify(carService).deleteCar(DEFAULT_ID);
	}

}
