package backend.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import backend.entity.Car;
import backend.entity.dto.ValidationErrorDto;
import backend.exception.ValidationNotSucceededException;

public class ValidationHelper<T> {

	public static void validateCar(Car car) throws ValidationNotSucceededException{
		new ValidationHelper<Car>().validate(car);
	}
	
	private void validate(T object) throws ValidationNotSucceededException{
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(object);
		if (!violations.isEmpty()) {
			List<ValidationErrorDto> validationErrors = new ArrayList<>();
			int errorNumbers = 0;
			Iterator<ConstraintViolation<T>> it = violations.iterator();
			while (it.hasNext()) {
				validationErrors.add(new ValidationErrorDto(++errorNumbers, it.next().getMessage()));
			}
			throw new ValidationNotSucceededException(validationErrors);
		}
	}
	
}
