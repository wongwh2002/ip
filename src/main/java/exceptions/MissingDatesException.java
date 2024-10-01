package exceptions;

public class MissingDatesException extends Exception {
    public MissingDatesException(String errorMessage) {
        super(errorMessage);
    }
}