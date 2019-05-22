package backend.exception;

import java.util.List;

import backend.entity.dto.ValidationErrorDto;

/**
 * Exception occurred when a Bean Validation has errors
 * @author gmiralle
 *
 */
public class ValidationNotSucceededException extends Exception {

	private static final long serialVersionUID = 799190880834250761L;
	private List<ValidationErrorDto> validationErrors;
	
	/**
	 * Create exception that contains every validation error
	 * @param validationErrors Errors occurred during validation
	 */
	public ValidationNotSucceededException(List<ValidationErrorDto> validationErrors) {
		super("Errors ocurred on data validation");
		this.validationErrors = validationErrors;
	}
	
	public List<ValidationErrorDto> getValidationErrors() {
		return validationErrors;
	}

}
