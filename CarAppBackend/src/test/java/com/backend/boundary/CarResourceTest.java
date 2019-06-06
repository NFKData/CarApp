package com.backend.boundary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.backend.control.CarService;
import com.backend.entity.Brand;
import com.backend.entity.Car;
import com.backend.entity.Country;
import com.backend.entity.dto.BrandDto;
import com.backend.entity.dto.CarDto;
import com.backend.entity.dto.CountryDto;
import com.backend.exception.CarNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.helper.DtoHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DtoHelper.class)
public class CarResourceTest {

	private static final String DEFAULT_ID = "123";
	private static final Integer DEFAULT_ID_INT = 123;
	@Mock
	private CarService carService;
	@InjectMocks
	private CarResourceImpl carResource;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void whenGettingAllCars_shouldReturnOk() throws Exception {
		List<Car> carsExpected = new ArrayList<>();
		carsExpected.add(new Car());
		carsExpected.get(0).setId(DEFAULT_ID);
		when(carService.getAllCars()).thenReturn(carsExpected);
		List<CarDto> entitiesExpected = new ArrayList<>();
		entitiesExpected.add(new CarDto(carsExpected.get(0)));
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityListToDtoList(carsExpected, CarDto.class)).thenReturn(entitiesExpected);
		Response response = carResource.getAll();
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(entitiesExpected, response.getEntity());
		verify(carService).getAllCars();
		PowerMockito.verifyStatic(DtoHelper.class);
	}

	@Test
	public void whenGettingOneCar_shouldReturnOk()
			throws CarNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Car carExpected = new Car();
		carExpected.setId(DEFAULT_ID);
		when(carService.getCar(carExpected.getId())).thenReturn(carExpected);
		CarDto dtoExpected = new CarDto(carExpected);
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityToDto(carExpected, CarDto.class)).thenReturn(dtoExpected);
		Response response = carResource.getOne(carExpected.getId());
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(dtoExpected, response.getEntity());
		verify(carService).getCar(carExpected.getId());
		PowerMockito.verifyStatic(DtoHelper.class);
	}

	@Test(expected = CarNotFoundException.class)
	public void whenGettingAnNonexistentCar_shouldThrowCarNotFoundException()
			throws CarNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		when(carService.getCar(DEFAULT_ID)).thenThrow(new CarNotFoundException(DEFAULT_ID));
		carResource.getOne(DEFAULT_ID);
		verify(carService).getCar(DEFAULT_ID);
	}

	@Test
	public void whenCreatingACar_shouldReturnCreated() throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Car toCreate = new Car();
		Brand brand = new Brand();
		brand.setId(DEFAULT_ID_INT);
		brand.setName("BMW");
		Country country = new Country();
		country.setId(DEFAULT_ID_INT);
		country.setName("Spain");
		toCreate.setBrand(brand);
		toCreate.setCountry(country);
		toCreate.setRegistration(LocalDateTime.now());
		when(carService.createCar(toCreate)).thenReturn(toCreate);
		CarDto expectedDto = new CarDto(toCreate);
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityToDto(toCreate, CarDto.class)).thenReturn(expectedDto);
		Response response = carResource.create(toCreate);
		assertEquals(Status.CREATED, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedDto, response.getEntity());
		verify(carService).createCar(toCreate);
		PowerMockito.verifyStatic(DtoHelper.class);
	}

	@Test(expected = InvalidEntityException.class)
	public void whenCreatingAnInvalidCar_shouldThrowInvalidEntityException()
			throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Car car = new Car();
		when(carService.createCar(car)).thenThrow(new InvalidEntityException(null));
		carResource.create(car);
		verify(carService).createCar(car);
	}

	@Test
	public void whenUpdatingACar_shouldReturnOk()
			throws CarNotFoundException, InvalidEntityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Car car = new Car();
		car.setId(DEFAULT_ID);
		when(carService.updateCar(car)).thenReturn(car);
		CarDto dtoExpected = new CarDto(car);
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityToDto(car, CarDto.class)).thenReturn(dtoExpected);
		Response response = carResource.update(car.getId(), car);
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(dtoExpected, response.getEntity());
		verify(carService).updateCar(car);
		PowerMockito.verifyStatic(DtoHelper.class);
	}

	@Test(expected = CarNotFoundException.class)
	public void whenUpdatingAnNonexistentCar_shouldThrowCarNotFoundException()
			throws CarNotFoundException, InvalidEntityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Car car = new Car();
		car.setId(DEFAULT_ID);
		when(carService.updateCar(car)).thenThrow(new CarNotFoundException(DEFAULT_ID));
		carResource.update(car.getId(), car);
		verify(carService).updateCar(car);
	}

	@Test(expected = InvalidEntityException.class)
	public void whenUpdatingAnInvalidCar_shouldThrowInvalidEntityException()
			throws InvalidEntityException, CarNotFoundException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Car car = new Car();
		car.setId(DEFAULT_ID);
		when(carService.updateCar(car)).thenThrow(new InvalidEntityException(null));
		carResource.update(DEFAULT_ID, car);
		verify(carService).updateCar(car);
	}

	@Test
	public void whenDeletingACar_shouldReturnNoContent() throws CarNotFoundException {
		doNothing().when(carService).deleteCar(DEFAULT_ID);
		Response response = carResource.delete(DEFAULT_ID);
		assertEquals(Status.NO_CONTENT, Status.fromStatusCode(response.getStatus()));
		assertNull(response.getEntity());
		verify(carService).deleteCar(DEFAULT_ID);
	}

	@Test(expected = CarNotFoundException.class)
	public void whenDeletingAnNonexistentCar_shouldThrowCarNotFoundException()
			throws CarNotFoundException, InvalidEntityException {
		doThrow(new CarNotFoundException(DEFAULT_ID)).when(carService).deleteCar(DEFAULT_ID);
		carResource.delete(DEFAULT_ID);
		verify(carService).deleteCar(DEFAULT_ID);
	}

	@Test
	public void whenGettingACountryOfACar_shouldReturnOk()
			throws CarNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Car expectedCar = new Car();
		expectedCar.setId(DEFAULT_ID);
		Country expectedCountry = new Country();
		expectedCountry.setId(DEFAULT_ID_INT);
		expectedCar.setCountry(expectedCountry);
		when(carService.getCar(DEFAULT_ID)).thenReturn(expectedCar);
		CountryDto expectedDto = new CountryDto(expectedCountry);
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityToDto(expectedCountry, CountryDto.class)).thenReturn(expectedDto);
		Response response = carResource.getCountry(DEFAULT_ID);
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedDto, response.getEntity());
		verify(carService).getCar(DEFAULT_ID);
		PowerMockito.verifyStatic(DtoHelper.class);
	}

	@Test(expected = CarNotFoundException.class)
	public void whenGettingACountryOfANonExistentCar_shouldThrowCarNotFoundException()
			throws CarNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		when(carService.getCar(DEFAULT_ID)).thenThrow(new CarNotFoundException(DEFAULT_ID));
		carResource.getCountry(DEFAULT_ID);
		verify(carService).getCar(DEFAULT_ID);
	}

	@Test
	public void whenGettingABrandOfACar_shouldReturnOk()
			throws CarNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Car expectedCar = new Car();
		expectedCar.setId(DEFAULT_ID);
		Brand expectedBrand = new Brand();
		expectedBrand.setId(DEFAULT_ID_INT);
		expectedCar.setBrand(expectedBrand);
		when(carService.getCar(DEFAULT_ID)).thenReturn(expectedCar);
		BrandDto expectedDto = new BrandDto(expectedBrand);
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityToDto(expectedBrand, BrandDto.class)).thenReturn(expectedDto);
		Response response = carResource.getBrand(DEFAULT_ID);
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedDto, response.getEntity());
		verify(carService).getCar(DEFAULT_ID);
		PowerMockito.verifyStatic(DtoHelper.class);
	}

	@Test(expected = CarNotFoundException.class)
	public void whenGettingABrandOfANonExistentCar_shouldThrowCarNotFoundException()
			throws CarNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		when(carService.getCar(DEFAULT_ID)).thenThrow(new CarNotFoundException(DEFAULT_ID));
		carResource.getBrand(DEFAULT_ID);
		verify(carService).getCar(DEFAULT_ID);
	}

}
