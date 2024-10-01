package exceptions;

public class DescriptionEmptyException extends Exception {
    public DescriptionEmptyException(String errorMessage) {
        super(errorMessage);
    }
}