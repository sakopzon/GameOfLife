package OOP.Solution;

public class ValidationException extends RuntimeException {
	// ingore this, it's required but irrelevant to the assignment.
	private static final long serialVersionUID = 1L;
	private String str;
	
	// There's not a lot for you to do here, just make sure the exception has
	// a constructor that takes a string and passes it to Exception's appropriate
	// constructor.
	public ValidationException(String ¢) {
		str = ¢;
	}

	public ValidationException() {
		str = "";
	}
	
	@Override
	public String getMessage() {
		return str;
	}
}
