package com.backend.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.interceptor.Interceptors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.core.GenericEntity;

import com.backend.entity.Car;
import com.backend.entity.dto.ValidationErrorDto;
import com.backend.exception.InvalidEntityException;
import com.backend.interceptor.LogInterceptor;

@Interceptors(LogInterceptor.class)
public class ValidationHelper<T> {
	
	private static Map<Class<?>, ValidationHelper<?>> helpers = new HashMap<>();

	/**
	 * Method that delegates a car validation on a general validation method
	 * @param car {@link Car} to be validated
	 * @throws InvalidEntityException When there's any validation error
	 */
	@SuppressWarnings("unchecked")
	public static void validateCar(Car car) throws InvalidEntityException{
		ValidationHelper<Car> helper = (ValidationHelper<Car>) helpers.get(Car.class);
		if(helper == null) {
			helper = new ValidationHelper<Car>();
			helpers.put(Car.class, helper);
		}
		helper.validate(car);
	}
	
	/**
	 * Validate and create errors if any
	 * @param object Entity to be validated
	 * @throws InvalidEntityException When there's any validation error
	 */
	public void validate(T object) throws InvalidEntityException {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(object);
		if (!violations.isEmpty()) {
			List<ValidationErrorDto> validationErrors = new ArrayList<>();
			int errorNumbers = 0;
			Iterator<ConstraintViolation<T>> it = violations.iterator();
			while (it.hasNext()) {
				validationErrors.add(new ValidationErrorDto(++errorNumbers, it.next().getMessage()));
			}
			throw new InvalidEntityException(validationErrors, new GenericEntity<List<ValidationErrorDto>>(validationErrors) {});
		}
	}
	
}
