package exceptions;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends Exception{

	public UserAlreadyExistsException() {
		super("The user is already registered");
	}

}
