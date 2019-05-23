package com.backend.exception;

import java.util.List;

import javax.interceptor.Interceptors;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response.Status;

import com.backend.entity.dto.ValidationErrorDto;
import com.backend.interceptor.LogInterceptor;

/**
 * Exception occurred when a Bean Validation has errors
 * @author gmiralle
 *
 */
@Interceptors(LogInterceptor.class)
public class InvalidEntityException extends CarApiBaseException {

	private static final String ERRORS_OCURRED_ON_DATA_VALIDATION = "Errors ocurred on data validation.";
	private static final long serialVersionUID = 799190880834250761L;
	private List<ValidationErrorDto> validationErrors;
	
	/**
	 * Create exception that contains every validation error
	 * @param validationErrors Errors occurred during validation
	 */
	public InvalidEntityException(List<ValidationErrorDto> validationErrors) {
		super(ERRORS_OCURRED_ON_DATA_VALIDATION);
		this.validationErrors = validationErrors;
		this.statusToResponse = Status.BAD_REQUEST;
	}
	
	/**
	 * Create exception that contains every validation error
	 * @param validationErrors Errors occurred during validation
	 * @param entity Entity that provoked the exception
	 */
	public InvalidEntityException(List<ValidationErrorDto> validationErrors, GenericEntity<?> entity) {
		super(ERRORS_OCURRED_ON_DATA_VALIDATION);
		this.validationErrors = validationErrors;
		super.statusToResponse = Status.BAD_REQUEST;
		super.entity = entity;
	}

	public List<ValidationErrorDto> getValidationErrors() {
		return validationErrors;
	}

}
