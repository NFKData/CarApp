package com.backend.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.backend.entity.Country;
import com.backend.exception.CountryNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.helper.ValidationHelper;
import com.backend.interceptor.LogInterceptor;

@Stateless
@Interceptors(LogInterceptor.class)
public class CountryServiceImpl implements CountryService {

	@PersistenceContext(unitName="carPU")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Country> getAllCountries() {
		return (List<Country>) em.createNamedQuery("CountryService.findAllCountries").getResultList();
	}

	@Override
	public Country getCountry(String id) throws CountryNotFoundException {
		Country country = em.find(Country.class, id);
		if(country == null) {
			throw new CountryNotFoundException(id);
		}
		return country;
	}

	@Override
	public Country createCountry(Country country) throws InvalidEntityException {
		ValidationHelper.validateCountry(country);
		em.persist(country);
		return country;
	}

	@Override
	public Country updateCountry(Country country) throws InvalidEntityException, CountryNotFoundException {
		ValidationHelper.validateCountry(country);
		Country auxCountry = getCountry(country.getId());
		auxCountry.getName();
		return em.merge(country);
	}

	@Override
	public void deleteCountry(String id) throws CountryNotFoundException {
		em.remove(getCountry(id));
	}

}
