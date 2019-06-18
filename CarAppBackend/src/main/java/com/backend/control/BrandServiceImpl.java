package com.backend.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.backend.entity.Brand;
import com.backend.entity.Car;
import com.backend.exception.BrandNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.helper.ValidationHelper;
import com.backend.interceptor.LogInterceptor;

@Stateless
@Interceptors(LogInterceptor.class)
public class BrandServiceImpl implements BrandService {

	@PersistenceContext(name = "carPU")
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
		Brand brand = em.find(Brand.class, id);
		List<Car> cars = brand.getCars();
		cars.size();
		session.close();
		return cars;
	}

}
