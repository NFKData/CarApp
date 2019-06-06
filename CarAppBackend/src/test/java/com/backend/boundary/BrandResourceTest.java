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

import com.backend.control.BrandService;
import com.backend.entity.Brand;
import com.backend.entity.Car;
import com.backend.entity.dto.BrandDto;
import com.backend.entity.dto.CarDto;
import com.backend.exception.BrandNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.helper.DtoHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DtoHelper.class)
public class BrandResourceTest {

	private static final Integer DEFAULT_ID = 123;
	@Mock
	private BrandService brandService;
	@InjectMocks
	private BrandResourceImpl brandResource;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void whenGettingEveryBrand_shouldReturnOk() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<Brand> expectedBrands = new ArrayList<>();
		expectedBrands.add(new Brand());
		expectedBrands.get(0).setId(DEFAULT_ID);
		List<BrandDto> expectedDto = new ArrayList<>();
		expectedDto.add(new BrandDto(expectedBrands.get(0)));
		when(brandService.getAllBrands()).thenReturn(expectedBrands);
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityListToDtoList(expectedBrands, BrandDto.class)).thenReturn(expectedDto);
		Response response = brandResource.getAll();
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedDto, response.getEntity());
		verify(brandService).getAllBrands();
		PowerMockito.verifyStatic(DtoHelper.class);
	}

	@Test
	public void whenGettingOneBrand_shouldReturnOk() throws BrandNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Brand expectedBrand = new Brand();
		expectedBrand.setId(DEFAULT_ID);
		BrandDto expectedDto = new BrandDto(expectedBrand);
		when(brandService.getBrand(DEFAULT_ID)).thenReturn(expectedBrand);
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityToDto(expectedBrand, BrandDto.class)).thenReturn(expectedDto);
		Response response = brandResource.getOne(DEFAULT_ID);
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedDto, response.getEntity());
		verify(brandService).getBrand(DEFAULT_ID);
		PowerMockito.verifyStatic(DtoHelper.class);
	}
	
	@Test(expected = BrandNotFoundException.class)
	public void whenGettingANonExistentBrand_shouldThrowBrandNotFoundException() throws BrandNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		when(brandService.getBrand(DEFAULT_ID)).thenThrow(new BrandNotFoundException(DEFAULT_ID));
		brandResource.getOne(DEFAULT_ID);
		verify(brandService).getBrand(DEFAULT_ID);
	}

	@Test
	public void whenCreatingAValidCar_shouldReturnCreated() throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Brand expectedBrand = new Brand();
		expectedBrand.setId(DEFAULT_ID);
		BrandDto expectedDto = new BrandDto(expectedBrand);
		when(brandService.createBrand(expectedBrand)).thenReturn(expectedBrand);
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityToDto(expectedBrand, BrandDto.class)).thenReturn(expectedDto);
		Response response = brandResource.create(expectedBrand);
		assertEquals(Status.CREATED, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedDto, response.getEntity());
		verify(brandService).createBrand(expectedBrand);
		PowerMockito.verifyStatic(DtoHelper.class);
	}
	
	@Test(expected = InvalidEntityException.class)
	public void whenCreatingANonValidCar_shouldThrowInvalidEntityException() throws InvalidEntityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Brand expectedBrand = new Brand();
		expectedBrand.setId(DEFAULT_ID);
		when(brandService.createBrand(expectedBrand)).thenThrow(new InvalidEntityException(null));
		brandResource.create(expectedBrand);
		verify(brandService).createBrand(expectedBrand);
	}

	@Test
	public void whenUpdatingABrand_shouldReturnOk() throws InvalidEntityException, BrandNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Brand expectedBrand = new Brand();
		expectedBrand.setId(DEFAULT_ID);
		BrandDto expectedDto = new BrandDto(expectedBrand);
		when(brandService.updateBrand(expectedBrand)).thenReturn(expectedBrand);
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityToDto(expectedBrand, BrandDto.class)).thenReturn(expectedDto);
		Response response = brandResource.update(DEFAULT_ID, expectedBrand);
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedDto, response.getEntity());
		verify(brandService).updateBrand(expectedBrand);
		PowerMockito.verifyStatic(DtoHelper.class);
	}

	@Test(expected = BrandNotFoundException.class)
	public void whenUpdatingANonExistentBrand_shouldThrowBrandNotFoundException() throws InvalidEntityException, BrandNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Brand expectedBrand = new Brand();
		expectedBrand.setId(DEFAULT_ID);
		when(brandService.updateBrand(expectedBrand)).thenThrow(new BrandNotFoundException(DEFAULT_ID));
		brandResource.update(DEFAULT_ID, expectedBrand);
		verify(brandService).updateBrand(expectedBrand);
	}
	
	@Test(expected = InvalidEntityException.class)
	public void whenUpdatingANonValidCar_shouldThrowInvalidEntityException() throws InvalidEntityException, BrandNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Brand expectedBrand = new Brand();
		expectedBrand.setId(DEFAULT_ID);
		when(brandService.updateBrand(expectedBrand)).thenThrow(new InvalidEntityException(null));
		brandResource.update(DEFAULT_ID, expectedBrand);
		verify(brandService).updateBrand(expectedBrand);
	}
	
	@Test
	public void whenDeletingABrand_shouldReturnNoContent() throws BrandNotFoundException {
		doNothing().when(brandService).deleteBrand(DEFAULT_ID);
		Response response = brandResource.delete(DEFAULT_ID);
		assertEquals(Status.NO_CONTENT, Status.fromStatusCode(response.getStatus()));
		assertNull(response.getEntity());
		verify(brandService).deleteBrand(DEFAULT_ID);
	}

	@Test(expected = BrandNotFoundException.class)
	public void whenDeletingAnNonexistentBrand_shouldThrowBrandNotFoundException() throws BrandNotFoundException, InvalidEntityException {
		doThrow(new BrandNotFoundException(DEFAULT_ID)).when(brandService).deleteBrand(DEFAULT_ID);
		brandResource.delete(DEFAULT_ID);
		verify(brandService).deleteBrand(DEFAULT_ID);
	}
	
	@Test
	public void whenGettingCarsOfABrand_shouldReturnOk() throws BrandNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<Car> expectedCars = new ArrayList<>();
		expectedCars.add(new Car());
		expectedCars.get(0).setId(DEFAULT_ID.toString());
		when(brandService.getBrandCars(DEFAULT_ID)).thenReturn(expectedCars);
		List<CarDto> expectedDto = new ArrayList<>();
		PowerMockito.mockStatic(DtoHelper.class);
		PowerMockito.when(DtoHelper.entityListToDtoList(expectedCars, CarDto.class)).thenReturn(expectedDto);
		Response response = brandResource.getCars(DEFAULT_ID);
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedDto, response.getEntity());
		verify(brandService).getBrandCars(DEFAULT_ID);
		PowerMockito.verifyStatic(DtoHelper.class);
	}

	@Test(expected = BrandNotFoundException.class)
	public void whenGettingCarsOfANonExistentBrand_shouldThrowBrandNotFoundException() throws BrandNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		when(brandService.getBrandCars(DEFAULT_ID)).thenThrow(new BrandNotFoundException(DEFAULT_ID));
		brandResource.getCars(DEFAULT_ID);
		verify(brandService).getBrandCars(DEFAULT_ID);
	}
	
}
