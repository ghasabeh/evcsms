package devolon.fi.evcsms.utils.exception;

public class CustomEntityNotFoundException extends RuntimeException{
    public CustomEntityNotFoundException() {
        super("Resource Not Found");
    }

    public CustomEntityNotFoundException(String message) {
        super(message);
    }
}
