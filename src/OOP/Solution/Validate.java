package OOP.Solution;

/**
 * This class should hold the static validation methods.
 */
public class Validate {

	// If validations are active, make sure that cond==true. If not, throw.
	// If an exception is thrown, the details parameter should be passed to the exception's
	// constructor.
	public static void that(boolean cond, String details) throws ValidationException {
	}

	// If validations are active, make sure that both objects are equal. If not, throw.
	// If an exception is thrown, the details parameter should be passed to the exception's
	// constructor.
	public static void equal(Object o1, Object o2, String details) throws ValidationException {
	}
	
	// Turn on validation.
	public static void on() {
	}
	
	// Turn off validation.
	public static void off() {
	}
}
