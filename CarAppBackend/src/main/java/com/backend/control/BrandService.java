package com.backend.control;

import java.util.List;

import com.backend.entity.Brand;
import com.backend.exception.BrandNotFoundException;
import com.backend.exception.InvalidEntityException;

public interface BrandService {

	/**
	 * Obtain every {@link Brand} in the system
	 * @return List of brands
	 */
	public List<Brand> getAllBrands();

	/**
	 * Obtain a brand based on the received id
	 * @param id ID to look for on the system
	 * @return The requested {@link Brand}, if found
	 * @throws BrandNotFoundException If there's no brand in the system with the specified ID
	 */
	public Brand getBrand(Integer id) throws BrandNotFoundException;

	/**
	 * Insert a new brand in the system
	 * @param brand The brand to be inserted
	 * @return The {@link Brand} if succeeded
	 * @throws InvalidEntityException If the data are not valid
	 */
	public Brand createBrand(Brand brand) throws InvalidEntityException;

	/**
	 * Update a brand based on the received one
	 * @param brand The brand to be updated with new data
	 * @return The updated {@link Brand}
	 * @throws BrandNotFoundException if there's no brand with the ID of the received one
	 * @throws InvalidEntityException If the data are not valid
	 */
	public Brand updateBrand(Brand brand) throws InvalidEntityException, BrandNotFoundException;

	/**
	 * Delete a {@link Brand} based on the received id
	 * @param id ID of the brand to be deleted
	 * @throws BrandNotFoundException if there's no brand with the received ID
	 */
	public void deleteBrand(Integer id) throws BrandNotFoundException;

}
