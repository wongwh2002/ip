package exceptions;

public class IllegalCommandException extends Exception {
    public IllegalCommandException(String errorMessage) {
        super(errorMessage);
    }
}