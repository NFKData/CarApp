package backend.exception;

import backend.entity.CarEntity;

public class CarFoundException extends Exception {
	
	private static final long serialVersionUID = 3715877372548587658L;
	private static final String DEFAULT_MESSAGE = "A car has been found. ";

	public CarFoundException() {
		super(DEFAULT_MESSAGE);
	}

	public CarFoundException(String message) {
		super(message);
	}

	public CarFoundException(String message, CarEntity car) {
		super(message + car.toString());
	}
	
	public CarFoundException(CarEntity car) {
		super(DEFAULT_MESSAGE + car.toString());
	}
}
