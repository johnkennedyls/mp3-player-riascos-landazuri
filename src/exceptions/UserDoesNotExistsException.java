package exceptions;

@SuppressWarnings("serial")
public class UserDoesNotExistsException extends Exception{
	
	public UserDoesNotExistsException() {
		super("The ID searched doesn't exists");
	}

}
