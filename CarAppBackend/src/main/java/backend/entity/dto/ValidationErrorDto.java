package backend.entity.dto;

public class ValidationErrorDto {

	private int errorNumber;
	private String description;
	
	public ValidationErrorDto() {}

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

