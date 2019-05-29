package com.backend.entity.dto;

import javax.interceptor.Interceptors;

import com.backend.interceptor.LogInterceptor;

/**
 * DTO for sending errors to client
 * @author gmiralle
 *
 */
@Interceptors(LogInterceptor.class)
public class ValidationErrorDto {

	private int errorNumber;
	private String description;
	
	/**
	 * Generic constructor needed for JSON serialization
	 */
	public ValidationErrorDto() {}

	/**
	 * Constructor of an error
	 * @param errorNumber Number of this error
	 * @param description Description of this error
	 */
	public ValidationErrorDto(int errorNumber, String description) {
			this.errorNumber = errorNumber;
			this.description = description;
		}

	public int getErrorNumber() {
		return errorNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}

}

