package com.backend.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.backend.entity.Brand;
import com.backend.entity.Car;
import com.backend.exception.BrandNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.helper.ValidationHelper;
import com.backend.interceptor.LogInterceptor;

@Stateless
@Interceptors(LogInterceptor.class)
@RequestScoped
public class BrandServiceImpl implements BrandService {

	@Inject
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Brand> getAllBrands() {
		return (List<Brand>) em.createNamedQuery("BrandService.findAllBrands").getResultList();
	}

	@Override
	public Brand getBrand(Integer id) throws BrandNotFoundException {
		Brand brand = em.find(Brand.class, id);
		if(brand == null) {
			throw new BrandNotFoundException(id);
		}
		return brand;
	}

	@Override
	public Brand createBrand(Brand brand) throws InvalidEntityException {
		ValidationHelper.validateBrand(brand);
		em.persist(brand);
		return brand;
	}

	@Override
	public Brand updateBrand(Brand brand) throws InvalidEntityException, BrandNotFoundException {
		ValidationHelper.validateBrand(brand);
		Brand auxBrand = getBrand(brand.getId());
		auxBrand.setName(brand.getName());
		return em.merge(auxBrand);
	}

	@Override
	public void deleteBrand(Integer id) throws BrandNotFoundException {
		em.remove(getBrand(id));
	}
	
	@Override
	public List<Car> getBrandCars(Integer id) {
		Session session = em.getEntityManagerFactory().unwrap(SessionFactory.class).getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Brand brand = em.find(Brand.class, id);
		List<Car> cars = brand.getCars();
		cars.size();
		transaction.commit();
		return cars;
	}

}
