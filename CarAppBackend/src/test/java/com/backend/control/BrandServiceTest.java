package com.backend.control;

import static org.junit.Assert.*;
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

import com.backend.entity.Brand;
import com.backend.exception.BrandNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.helper.ValidationHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidationHelper.class)
public class BrandServiceTest {

	private static final Integer DEFAULT_ID = 123;
	@Mock
	private EntityManager em;
	@Mock
	private Query query;
	@InjectMocks
	private BrandServiceImpl brandService;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void whenGettingAllBrands_shouldReturnListOfBrands() {
		List<Brand> brands = new ArrayList<>();
		when(em.createNamedQuery("BrandService.findAllBrands")).thenReturn(query);
		when(query.getResultList()).thenReturn(brands);
		assertEquals(brands, brandService.getAllBrands());
		verify(em).createNamedQuery("BrandService.findAllBrands");
		verify(query).getResultList();
	}

	@Test
	public void whenGettingABrand_shouldReturnBrandEntity() throws BrandNotFoundException {
		Brand brand = new Brand();
		brand.setId(DEFAULT_ID);
		when(em.find(Brand.class, DEFAULT_ID)).thenReturn(brand);
		assertEquals(brand, brandService.getBrand(DEFAULT_ID));
		verify(em).find(Brand.class, DEFAULT_ID);
	}

	@Test(expected = BrandNotFoundException.class)
	public void whenGettingANonExistentBrand_shouldThrowBrandNotFoundException() throws BrandNotFoundException {
		when(em.find(Brand.class, DEFAULT_ID)).thenReturn(null);
		brandService.getBrand(DEFAULT_ID);
		verify(em).find(Brand.class, DEFAULT_ID);
	}

	@Test
	public void whenCreatingABrand_shouldReturnBrandEntity() throws InvalidEntityException {
		Brand brand = new Brand();
		brand.setId(DEFAULT_ID);
		PowerMockito.mockStatic(ValidationHelper.class);
		doNothing().when(em).persist(brand);
		PowerMockito.doNothing().when(ValidationHelper.class);
		assertEquals(brand, brandService.createBrand(brand));
		verify(em).persist(brand);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}

	@Test(expected = InvalidEntityException.class)
	public void whenCreatingAnInvalidBrand_shouldThrowInvalidEntityException() throws Exception {
		Brand brand = new Brand();
		PowerMockito.mockStatic(ValidationHelper.class);
		doNothing().when(em).persist(brand);
		PowerMockito.doThrow(new InvalidEntityException(new ArrayList<>())).when(ValidationHelper.class, "validateBrand",
				brand);
		brandService.createBrand(brand);
		verify(em).persist(brand);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}

	@Test
	public void whenUpdatingABrand_shouldReturnBrandEntity() throws BrandNotFoundException, InvalidEntityException {
		Brand brand = new Brand();
		brand.setId(DEFAULT_ID);
		PowerMockito.mockStatic(ValidationHelper.class);
		when(em.find(Brand.class, DEFAULT_ID)).thenReturn(brand);
		when(em.merge(brand)).thenReturn(brand);
		assertEquals(brand, brandService.updateBrand(brand));
		verify(em).merge(brand);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}

	@Test(expected = BrandNotFoundException.class)
	public void whenUpdatingANonExistentBrand_shouldThrowBrandNotFoundException()
			throws BrandNotFoundException, InvalidEntityException {
		PowerMockito.mockStatic(ValidationHelper.class);
		when(em.find(Brand.class, DEFAULT_ID)).thenReturn(null);
		brandService.updateBrand(new Brand());
		verify(em.find(Brand.class, DEFAULT_ID));
	}

	@Test(expected = InvalidEntityException.class)
	public void whenUpdatingAnInvalidBrand_shouldThrowInvalidEntityException() throws Exception {
		Brand brand = new Brand();
		brand.setId(DEFAULT_ID);
		when(em.find(Brand.class, DEFAULT_ID)).thenReturn(brand);
		PowerMockito.mockStatic(ValidationHelper.class);
		PowerMockito.doThrow(new InvalidEntityException(new ArrayList<>())).when(ValidationHelper.class, "validateBrand",
				brand);
		brandService.updateBrand(brand);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}

	@Test
	public void whenDeletingABrand_shouldntThrowAnything() throws BrandNotFoundException {
		Brand brand = new Brand();
		when(em.find(Brand.class, DEFAULT_ID)).thenReturn(brand);
		doNothing().when(em).remove(brand);
		brandService.deleteBrand(DEFAULT_ID);
		verify(em).remove(brand);
	}
	
	@Test(expected = BrandNotFoundException.class)
	public void whenDeletingANonExistentBrand_shouldThrowBrandNotFoundException() throws BrandNotFoundException {
		Brand brand = new Brand();
		brand.setId(DEFAULT_ID);
		when(em.find(Brand.class, DEFAULT_ID)).thenReturn(null);
		doNothing().when(em).remove(brand);
		brandService.deleteBrand(DEFAULT_ID);
		verify(em).find(Brand.class, DEFAULT_ID);
		verify(em).remove(brand);
	}
}
