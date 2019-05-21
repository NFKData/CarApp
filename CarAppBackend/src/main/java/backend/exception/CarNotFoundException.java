package backend.exception;

public class CarNotFoundException extends Exception {
	
	private static final long serialVersionUID = 3715877372548587658L;
	private static final String DEFAULT_MESSAGE = "The requested car couldn't be found. ";

	public CarNotFoundException() {
		super(DEFAULT_MESSAGE);
	}

	public CarNotFoundException(String message, String carId) {
		super(message + carId);
	}
	
	public CarNotFoundException(String carId) {
		super(DEFAULT_MESSAGE + carId);
	}
}
