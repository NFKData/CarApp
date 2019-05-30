package com.backend.helper;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.backend.entity.Car;
import com.backend.exception.InvalidEntityException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ValidationHelper.class, Validation.class})
public class ValidationHelperTest {

	@Mock
	ValidatorFactory factory;
	@Mock
	Validator validator;
	@Mock
	ConstraintViolation<Car> violation;
	@InjectMocks
	private ValidationHelper<Car> carValidator;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void whenCallingValidateCar_shouldInitANewValidator() throws Exception {
		Car car = new Car();
		Set<ConstraintViolation<Car>> violations = new HashSet<>();
		PowerMockito.mockStatic(Validation.class);
		PowerMockito.doReturn(factory).when(Validation.class, "buildDefaultValidatorFactory");
		when(factory.getValidator()).thenReturn(validator);
		when(validator.validate(car)).thenReturn(violations);
		ValidationHelper.validateCar(car);
		Map<Class<?>, ValidationHelper<?>> helpers = Whitebox.getInternalState(ValidationHelper.class, "helpers");
		Assert.assertFalse(helpers.isEmpty());
	}
	
	@Test
	public void whenValidatingValidCar_shouldntThrowAnything() throws Exception {
		Car car = new Car();
		Set<ConstraintViolation<Car>> violations = new HashSet<>();
		PowerMockito.mockStatic(Validation.class);
		PowerMockito.doReturn(factory).when(Validation.class, "buildDefaultValidatorFactory");
		when(factory.getValidator()).thenReturn(validator);
		when(validator.validate(car)).thenReturn(violations);
		carValidator.validate(car);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}
	
	@Test(expected = InvalidEntityException.class)
	public void whenValidatingAnInvalidCar_shouldThrowInvalidEntityException() throws Exception {
		Car car = new Car();
		Set<ConstraintViolation<Car>> violations = new HashSet<>();
		violations.add(violation);
		PowerMockito.mockStatic(Validation.class);
		PowerMockito.doReturn(factory).when(Validation.class, "buildDefaultValidatorFactory");
		when(factory.getValidator()).thenReturn(validator);
		when(validator.validate(car)).thenReturn(violations);
		when(violation.getMessage()).thenReturn("Validation error occurred");
		carValidator.validate(car);
		PowerMockito.verifyStatic(ValidationHelper.class);
	}

}
