package ait.cohort5860.accounting.exception;

/**
 * Исключение выбрасывается при передаче недопустимых данных
 * (например, неверный старый пароль, дублирующая роль и т.п.).
 */
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException() {
        super("Invalid data");
    }
}