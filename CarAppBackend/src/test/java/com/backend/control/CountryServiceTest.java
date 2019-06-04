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

import com.backend.entity.Country;
import com.backend.exception.CountryNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.helper.ValidationHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidationHelper.class)
public class CountryServiceTest {

	private static final Integer DEFAULT_ID = 123;
	@Mock
	private EntityManager em;
	@Mock
	private Query query;
	@InjectMocks
	private CountryServiceImpl countryService;

	@Before
	public void setUp() {
		initMocks(this);
	}

	@Test
	public void whenGettingAllCountries_shouldReturnListOfCountries() {
		List<Country> countrys = new ArrayList<>();
		when(em.createNamedQuery("CountryService.findAllCountries")).thenReturn(query);
		when(query.getResultList()).thenReturn(countrys);
		assertEquals(countrys, countryService.getAllCountries());
		verify(em).createNamedQuery("CountryService.findAllCountries");
		verify(query).getResultList();
	}

	@Test
	public void whenGettingACountry_shouldReturnCountryEntity() throws CountryNotFoundException {
		Country country = new Country();
		country.setId(DEFAULT_ID);
		when(em.find(Country.class, DEFAULT_ID)).thenReturn(country);
		assertEquals(country, countryService.getCountry(DEFAULT_ID));
		verify(em).find(Country.class, DEFAULT_ID);
	}

	@Test(expected = CountryNotFoundException.class)
	public void whenGettingANonExistentCountry_shouldThrowCountryNotFoundException() throws CountryNotFoundException {
		when(em.find(Country.class, DEFAULT_ID)).thenReturn(null);
		countryService.getCountry(DEFAULT_ID);
		verify(em).find(Country.class, DEFAULT_ID);
	}

	@Test
	public void whenCreatingACountry_shouldReturnCountryEntity() throws InvalidEntityException {
		Country country = new Country();
		country.setId(DEFAULT_ID);
		PowerMockito.mockStatic(ValidationHelper.class);
		doNothing().when(em).persist(country);
		PowerMockito.doNothing().when(ValidationHelper.class);
		assertEquals(country, countryService.createCountry(country));
		verify(em).persist(country);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}

	@Test(expected = InvalidEntityException.class)
	public void whenCreatingAnInvalidCountry_shouldThrowInvalidEntityException() throws Exception {
		Country country = new Country();
		PowerMockito.mockStatic(ValidationHelper.class);
		doNothing().when(em).persist(country);
		PowerMockito.doThrow(new InvalidEntityException(new ArrayList<>())).when(ValidationHelper.class, "validateCountry",
				country);
		countryService.createCountry(country);
		verify(em).persist(country);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}

	@Test
	public void whenUpdatingACountry_shouldReturnCountryEntity() throws CountryNotFoundException, InvalidEntityException {
		Country country = new Country();
		country.setId(DEFAULT_ID);
		PowerMockito.mockStatic(ValidationHelper.class);
		when(em.find(Country.class, DEFAULT_ID)).thenReturn(country);
		when(em.merge(country)).thenReturn(country);
		PowerMockito.doNothing().when(ValidationHelper.class);
		assertEquals(country, countryService.updateCountry(country));
		verify(em).merge(country);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}

	@Test(expected = CountryNotFoundException.class)
	public void whenUpdatingANonExistentCountry_shouldThrowCountryNotFoundException()
			throws CountryNotFoundException, InvalidEntityException {
		PowerMockito.mockStatic(ValidationHelper.class);
		when(em.find(Country.class, DEFAULT_ID)).thenReturn(null);
		countryService.updateCountry(new Country());
		verify(em.find(Country.class, DEFAULT_ID));
	}

	@Test(expected = InvalidEntityException.class)
	public void whenUpdatingAnInvalidCountry_shouldThrownInvalidEntityException() throws Exception {
		Country country = new Country();
		PowerMockito.mockStatic(ValidationHelper.class);
		PowerMockito.doThrow(new InvalidEntityException(new ArrayList<>())).when(ValidationHelper.class, "validateCountry",
				country);
		countryService.updateCountry(country);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}

	@Test
	public void whenDeletingACountry_shouldntThrowAnything() throws CountryNotFoundException {
		Country country = new Country();
		when(em.find(Country.class, DEFAULT_ID)).thenReturn(country);
		doNothing().when(em).remove(country);
		countryService.deleteCountry(DEFAULT_ID);
		verify(em).remove(country);
	}
	
	@Test(expected = CountryNotFoundException.class)
	public void whenDeletingANonExistentCountry_shouldThrowCountryNotFoundException() throws CountryNotFoundException {
		Country country = new Country();
		country.setId(DEFAULT_ID);
		when(em.find(Country.class, DEFAULT_ID)).thenReturn(null);
		doNothing().when(em).remove(country);
		countryService.deleteCountry(DEFAULT_ID);
		verify(em).find(Country.class, DEFAULT_ID);
		verify(em).remove(country);
	}

}
