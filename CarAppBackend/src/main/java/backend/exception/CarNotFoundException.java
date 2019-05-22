package backend.exception;

/**
 * Exception thrown when a {@link Car} is looked for in the system
 * but it hasn't been found
 * @author gmiralle
 */
public class CarNotFoundException extends Exception {

	private static final long serialVersionUID = 3715877372548587658L;
	private static final String DEFAULT_MESSAGE = "The requested car couldn't be found. ";

	/**
	 * Create an exception with the a default message<br>
	 * <b>"The requested car couldn't be found. "</b>
	 */
	public CarNotFoundException() {
		super(DEFAULT_MESSAGE);
	}
	
	/**
	 * Create an exception with the default message and the Car's ID
	 * that provoked it
	 * @param carId Car's ID that provoked the exception
	 */
	public CarNotFoundException(String carId) {
		super(DEFAULT_MESSAGE + carId);
	}
}
