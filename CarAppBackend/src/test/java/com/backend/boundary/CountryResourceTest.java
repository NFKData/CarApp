package com.backend.boundary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.InvocationTargetException;
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

import com.backend.control.CountryService;
import com.backend.entity.Country;
import com.backend.entity.Car;
import com.backend.entity.dto.CountryDto;
import com.backend.entity.dto.CarDto;
import com.backend.exception.CountryNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.helper.DtoHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DtoHelper.class)
public class CountryResourceTest {

	private static final Integer DEFAULT_ID = 123;
	@Mock
	private CountryService countryService;
	@InjectMocks
	private CountryResourceImpl countryResource;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void whenGettingEveryCountry_shouldReturnOk() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<Country> expectedCountries = new ArrayList<>();
		expectedCountries.add(new Country());
		expectedCountries.get(0).setId(DEFAULT_ID);
		List<CountryDto> expectedDto = new ArrayList<>();
		expectedDto.add(new CountryDto(expectedCountries.get(0)));
		when(countryService.getAllCountries()).thenReturn(expectedCountries);
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityListToDtoList(expectedCountries, CountryDto.class)).thenReturn(expectedDto);
		Response response = countryResource.getAll();
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedDto, response.getEntity());
		verify(countryService).getAllCountries();
		PowerMockito.verifyStatic(DtoHelper.class);
	}

	@Test
	public void whenGettingOneCountry_shouldReturnOk() throws CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Country expectedCountry = new Country();
		expectedCountry.setId(DEFAULT_ID);
		CountryDto expectedDto = new CountryDto(expectedCountry);
		when(countryService.getCountry(DEFAULT_ID)).thenReturn(expectedCountry);
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityToDto(expectedCountry, CountryDto.class)).thenReturn(expectedDto);
		Response response = countryResource.getOne(DEFAULT_ID);
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedDto, response.getEntity());
		verify(countryService).getCountry(DEFAULT_ID);
		PowerMockito.verifyStatic(DtoHelper.class);
	}
	
	@Test(expected = CountryNotFoundException.class)
	public void whenGettingANonExistentCountry_shouldThrowCountryNotFoundException() throws CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		when(countryService.getCountry(DEFAULT_ID)).thenThrow(new CountryNotFoundException(DEFAULT_ID));
		countryResource.getOne(DEFAULT_ID);
		verify(countryService).getCountry(DEFAULT_ID);
	}

	@Test
	public void whenCreatingAValidCar_shouldReturnCreated() throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Country expectedCountry = new Country();
		expectedCountry.setId(DEFAULT_ID);
		CountryDto expectedDto = new CountryDto(expectedCountry);
		when(countryService.createCountry(expectedCountry)).thenReturn(expectedCountry);
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityToDto(expectedCountry, CountryDto.class)).thenReturn(expectedDto);
		Response response = countryResource.create(expectedCountry);
		assertEquals(Status.CREATED, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedDto, response.getEntity());
		verify(countryService).createCountry(expectedCountry);
		PowerMockito.verifyStatic(DtoHelper.class);
	}
	
	@Test(expected = InvalidEntityException.class)
	public void whenCreatingANonValidCar_shouldThrowInvalidEntityException() throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Country expectedCountry = new Country();
		expectedCountry.setId(DEFAULT_ID);
		when(countryService.createCountry(expectedCountry)).thenThrow(new InvalidEntityException(null));
		countryResource.create(expectedCountry);
		verify(countryService).createCountry(expectedCountry);
	}

	@Test
	public void whenUpdatingACountry_shouldReturnOk() throws InvalidEntityException, CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Country expectedCountry = new Country();
		expectedCountry.setId(DEFAULT_ID);
		CountryDto expectedDto = new CountryDto(expectedCountry);
		when(countryService.updateCountry(expectedCountry)).thenReturn(expectedCountry);
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityToDto(expectedCountry, CountryDto.class)).thenReturn(expectedDto);
		Response response = countryResource.update(DEFAULT_ID, expectedCountry);
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedDto, response.getEntity());
		verify(countryService).updateCountry(expectedCountry);
		PowerMockito.verifyStatic(DtoHelper.class);
	}

	@Test(expected = CountryNotFoundException.class)
	public void whenUpdatingANonExistentCountry_shouldThrowCountryNotFoundException() throws InvalidEntityException, CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Country expectedCountry = new Country();
		expectedCountry.setId(DEFAULT_ID);
		when(countryService.updateCountry(expectedCountry)).thenThrow(new CountryNotFoundException(DEFAULT_ID));
		countryResource.update(DEFAULT_ID, expectedCountry);
		verify(countryService).updateCountry(expectedCountry);
	}
	
	@Test(expected = InvalidEntityException.class)
	public void whenUpdatingANonValidCar_shouldThrowInvalidEntityException() throws InvalidEntityException, CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Country expectedCountry = new Country();
		expectedCountry.setId(DEFAULT_ID);
		when(countryService.updateCountry(expectedCountry)).thenThrow(new InvalidEntityException(null));
		countryResource.update(DEFAULT_ID, expectedCountry);
		verify(countryService).updateCountry(expectedCountry);
	}
	
	@Test
	public void whenDeletingACountry_shouldReturnNoContent() throws CountryNotFoundException {
		doNothing().when(countryService).deleteCountry(DEFAULT_ID);
		Response response = countryResource.delete(DEFAULT_ID);
		assertEquals(Status.NO_CONTENT, Status.fromStatusCode(response.getStatus()));
		assertNull(response.getEntity());
		verify(countryService).deleteCountry(DEFAULT_ID);
	}

	@Test(expected = CountryNotFoundException.class)
	public void whenDeletingAnNonexistentCountry_shouldThrowCountryNotFoundException() throws CountryNotFoundException, InvalidEntityException {
		doThrow(new CountryNotFoundException(DEFAULT_ID)).when(countryService).deleteCountry(DEFAULT_ID);
		countryResource.delete(DEFAULT_ID);
		verify(countryService).deleteCountry(DEFAULT_ID);
	}
	
	@Test
	public void whenGettingCarsOfACountry__shouldReturnOk() throws CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Country expectedCountry = new Country();
		expectedCountry.setId(DEFAULT_ID);
		List<Car> expectedCars = new ArrayList<>();
		expectedCars.add(new Car());
		expectedCars.get(0).setId(DEFAULT_ID.toString());
		expectedCountry.setCars(expectedCars);
		when(countryService.getCountry(DEFAULT_ID)).thenReturn(expectedCountry);
		List<CarDto> expectedDto = new ArrayList<>();
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityListToDtoList(expectedCars, CarDto.class)).thenReturn(expectedDto);
		Response response = countryResource.getCars(DEFAULT_ID);
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedDto, response.getEntity());
		verify(countryService).getCountry(DEFAULT_ID);
		PowerMockito.verifyStatic(DtoHelper.class);
	}

	@Test(expected = CountryNotFoundException.class)
	public void whenGettingCarsOfANonExistentCountry_shouldThrowCountryNotFoundException() throws CountryNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		when(countryService.getCountry(DEFAULT_ID)).thenThrow(new CountryNotFoundException(DEFAULT_ID));
		countryResource.getCars(DEFAULT_ID);
		verify(countryService).getCountry(DEFAULT_ID);
	}
	
}
