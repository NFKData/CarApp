package com.backend.exception;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response.Status;

/**
 * Exception base for any custom exception of this api
 * @author gmiralle
 *
 */
public class CarApiBaseException extends Exception {
	
	private static final String DEFAULT_MESSAGE = "An error occurred";
	private static final long serialVersionUID = 1862371723683821260L;
	protected Status statusToResponse;
	protected GenericEntity<?> entity;
	
	/**
	 * Default constructor for INTERNAL_SERVER_ERROR HTTP Code
	 */
	public CarApiBaseException() {
		super(DEFAULT_MESSAGE);
		this.statusToResponse = Status.INTERNAL_SERVER_ERROR;
	}
	
	/**
	 * Constructor for returning a specified HTTP Code
	 * @param statusToResponse Status representing the HTTP Code
	 */
	public CarApiBaseException(Status statusToResponse) {
		super(DEFAULT_MESSAGE);
		this.statusToResponse = statusToResponse;
	}
	
	/**
	 * Constructor for logging a specified message and returning a specified HTTP Code
	 * @param message Message to be logged
	 * @param statusToResponse Status representing the HTTP Code
	 */
	public CarApiBaseException(String message, Status statusToResponse) {
		super(message);
		this.statusToResponse = statusToResponse;
	}
	
	/**
	 * Constructor for logging a specified message with INTERNAL_SERVER_ERROR HTTP Code
	 * @param message Message to be logged
	 */
	public CarApiBaseException(String message) {
		super(message);
		this.statusToResponse = Status.INTERNAL_SERVER_ERROR;
	}
	
	/**
	 * Constructor for logging and build a Response with entity
	 * @param message Message to be logged
	 * @param statusToResponse Status representing the HTTP Code
	 * @param entity Entity to send on response
	 */
	public CarApiBaseException(String message, Status statusToResponse, GenericEntity<?> entity) {
		super(message);
		this.statusToResponse = statusToResponse;
		this.entity = entity;
	}
	
	/**
	 * Constructor for logging and build a Response with entity, will send INTERNAL_SERVER_ERROR
	 * @param message Message to be logged
	 * @param entity Entity to send on response
	 */
	public CarApiBaseException(String message, GenericEntity<?> entity) {
		super(message);
		this.statusToResponse = Status.INTERNAL_SERVER_ERROR;
		this.entity = entity;
	}
	
	/**
	 * Constructor for send an entity on response with the specified HTTP Code
	 * @param statusToResponse Status representing the HTTP Code
	 * @param entity Entity to send on response
	 */
	public CarApiBaseException(Status statusToResponse, GenericEntity<?> entity) {
		super(DEFAULT_MESSAGE);
		this.statusToResponse = Status.INTERNAL_SERVER_ERROR;
		this.entity = entity;
	}
	
	public Status getStatusToResponse() {
		return statusToResponse;
	}

	public void setStatusToResponse(Status statusToResponse) {
		this.statusToResponse = statusToResponse;
	}

	public GenericEntity<?> getEntity() {
		return entity;
	}

	public void setEntity(GenericEntity<?> entity) {
		this.entity = entity;
	}
	
	

}
