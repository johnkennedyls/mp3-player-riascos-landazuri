package exceptions;

@SuppressWarnings("serial")
public class NotFoundException extends Exception{

	public NotFoundException() {
		super("The element you're searching isn't here");
	}
	
}
