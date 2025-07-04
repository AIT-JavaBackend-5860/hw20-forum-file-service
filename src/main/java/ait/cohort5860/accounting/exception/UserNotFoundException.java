package ait.cohort5860.accounting.exception;

/**
 * Исключение выбрасывается, когда пользователь не найден.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("User not found");
    }
}
