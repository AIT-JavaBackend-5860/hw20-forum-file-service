package ait.cohort5860.post.controller;

public class FileStorageException extends RuntimeException {

    // Исключение для ошибок при работе с файлами (сохранение/чтение)

    public FileStorageException(String message) {
        super(message); // Передаём сообщение в RuntimeException
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause); // Передаём оба параметра в родительский класс
    }
}
