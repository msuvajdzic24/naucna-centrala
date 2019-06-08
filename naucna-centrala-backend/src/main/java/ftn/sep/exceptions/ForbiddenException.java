package ftn.sep.exceptions;

@SuppressWarnings("serial")
public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {}

    public ForbiddenException(String message) {
        super(message);
    }

}
