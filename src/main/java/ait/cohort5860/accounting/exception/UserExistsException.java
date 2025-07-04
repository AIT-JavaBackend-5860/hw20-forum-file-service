package ait.cohort5860.accounting.exception;

/**
 * Исключение выбрасывается, когда пользователь уже существует.
 */
public class UserExistsException extends RuntimeException {
    public UserExistsException(String message) {
        super(message);
    }

    public UserExistsException() {
        super("User already exists");
    }
}
