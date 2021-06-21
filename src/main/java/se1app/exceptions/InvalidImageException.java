package se1app.exceptions;


/**
 * Exception to be thrown when a e-mail address parser failure occured.
 */
public class InvalidImageException extends IllegalArgumentException {

    public InvalidImageException(String invalidImage) {
        super("'" + invalidImage + "' is no valid image extension!");
    }
}