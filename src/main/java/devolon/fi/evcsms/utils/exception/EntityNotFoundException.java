package devolon.fi.evcsms.utils.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException() {
        super("Resource Not Found");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
