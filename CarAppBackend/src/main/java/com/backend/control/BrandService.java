package com.backend.control;

import java.util.List;

import com.backend.entity.Brand;
import com.backend.exception.BrandNotFoundException;
import com.backend.exception.InvalidEntityException;

public interface BrandService {

	List<Brand> getAllBrands();

	Brand getBrand(Integer id) throws BrandNotFoundException;

	Brand createBrand(Brand brand) throws InvalidEntityException;

	Brand updateBrand(Brand brand) throws InvalidEntityException, BrandNotFoundException;

	void deleteBrand(Integer id) throws BrandNotFoundException;

}
