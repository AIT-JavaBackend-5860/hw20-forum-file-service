package ait.cohort5860.post.exception;

/**
 * Базовое исключение для "не найдено".
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        super("Resource not found");
    }
}
