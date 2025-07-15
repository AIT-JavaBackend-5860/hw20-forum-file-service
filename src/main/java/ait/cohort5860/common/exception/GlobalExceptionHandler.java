package ait.cohort5860.common.exception;

import ait.cohort5860.accounting.dto.EmailDto;
import ait.cohort5860.accounting.exception.InvalidDataException;
import ait.cohort5860.accounting.exception.UserExistsException;
import ait.cohort5860.accounting.exception.UserNotFoundException;
import ait.cohort5860.accounting.service.AccountingService;
import ait.cohort5860.post.controller.FileStorageException;
import ait.cohort5860.post.exception.PostFileNotFoundException;
import ait.cohort5860.post.exception.PostNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;                                       // Подключаем логгер (log.warn, log.error и т.п.)
import org.springframework.http.HttpStatus;                            // Используем HTTP-статусы (например, 400, 404)
import org.springframework.http.ResponseEntity;                        // Формируем ответы с телом и статусом
import org.springframework.web.bind.MethodArgumentNotValidException;  // Обрабатываем ошибки валидации DTO (@Valid)
import org.springframework.web.bind.annotation.ControllerAdvice;      // Помечаем класс как глобальный обработчик исключений
import org.springframework.web.bind.annotation.ExceptionHandler;      // Указываем, какие исключения обрабатывать
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException; // Обрабатываем ошибки параметров
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;                                       // Получаем текущее время
import java.util.HashMap;                                             // Используем Map для списка ошибок
import java.util.Map;                                                 // Интерфейс Map

@Slf4j                                                                // Включаем поддержку логирования
@RequiredArgsConstructor
@ControllerAdvice                                                     // Назначаем класс глобальным обработчиком ошибок
public class GlobalExceptionHandler { // Обрабатываем все исключения приложения

    private final AccountingService service;

    @Value("${spring.mail.username}") // Получаем email из переменных окружения (EMAIL=romashko.re@gmail.com)
    private String systemEmail;

    @ExceptionHandler(RuntimeException.class) // Обрабатываем любые необработанные исключения типа RuntimeException
    public ResponseEntity<ErrorResponse> handleGenericRuntime(RuntimeException ex) {

        // Формируем DTO для отправки email
        EmailDto dto = new EmailDto(
                systemEmail, // Отправляем на системный email из настроек (переменной окружения)
                "❗AIT SpringBoot: Ошибка в приложении (RuntimeException)", // Тема письма
                "Сообщение: " + ex.getMessage() + "\n" +    // Основной текст — сообщение ошибки
                        "Класс: " + ex.getClass().getSimpleName()   // Дополнительно — имя класса исключения
        );

        service.sendEmail(dto); // Отправляем письмо через уже существующий сервис

        // Возвращаем стандартный JSON-ответ об ошибке с кодом 500 (внутренняя ошибка сервера)
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error: " + ex.getMessage());
    }


    @ExceptionHandler(UserExistsException.class)                      // Обрабатываем: пользователь уже существует
    public ResponseEntity<ErrorResponse> handleUserExists(UserExistsException ex) {
        log.warn("UserExistsException: {}", ex.getMessage());         // Записываем предупреждение в лог
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage()); // Возвращаем 409 и сообщение
    }

    @ExceptionHandler(UserNotFoundException.class)                    // Обрабатываем: пользователь не найден
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        log.warn("UserNotFoundException: {}", ex.getMessage());       // Записываем предупреждение в лог
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()); // Возвращаем 404 и сообщение
    }

    @ExceptionHandler(InvalidDataException.class)                     // Обрабатываем: данные недопустимы
    public ResponseEntity<ErrorResponse> handleInvalidData(InvalidDataException ex) {
        log.warn("InvalidDataException: {}", ex.getMessage());        // Записываем предупреждение в лог
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()); // Возвращаем 400 и сообщение
    }

    @ExceptionHandler(PostNotFoundException.class)                    // Обрабатываем: пост не найден
    public ResponseEntity<ErrorResponse> handlePostNotFound(PostNotFoundException ex) {
        log.warn("PostNotFoundException: {}", ex.getMessage());       // Записываем предупреждение в лог
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()); // Возвращаем 404 и сообщение
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)          // Обрабатываем ошибки валидации DTO
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();                 // Формируем Map: поле → сообщение об ошибке
        ex.getBindingResult().getFieldErrors().forEach(error ->       // Перебираем все ошибки
                errors.put(error.getField(), error.getDefaultMessage())); // Добавляем в Map
        log.warn("Validation error: {}", errors);                     // Записываем все ошибки валидации в лог
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors); // Возвращаем 400 и Map ошибок
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)      // Обрабатываем: неправильный тип параметра в URL
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format(                               // Формируем сообщение об ошибке
                "Invalid value for parameter '%s': %s",
                ex.getName(), ex.getValue());
        log.warn("Type mismatch: {}", message);                       // Записываем в лог несоответствие типов
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);   // Возвращаем 400 и сообщение
    }

    @ExceptionHandler(FileStorageException.class)                         // Обрабатываем: ошибка при сохранении файла
    public ResponseEntity<ErrorResponse> handleFileStorage(FileStorageException ex) {
        log.warn("FileStorageException: {}", ex.getMessage());            // Пишем в лог предупреждение
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()); // Возвращаем 400 и сообщение
    }

    @ExceptionHandler(FileNotFoundException.class)                        // Обрабатываем: файл не найден
    public ResponseEntity<ErrorResponse> handleFileNotFound(FileNotFoundException ex) {
        log.warn("FileNotFoundException: {}", ex.getMessage());           // Пишем в лог предупреждение
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()); // Возвращаем 404 и сообщение
    }

    @ExceptionHandler(PostFileNotFoundException.class)                        // Обрабатываем: файл поста не найден
    public ResponseEntity<ErrorResponse> handleFileNotFound(PostFileNotFoundException ex) {
        log.warn("PostFileNotFoundException: {}", ex.getMessage());           // Пишем в лог предупреждение
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()); // Возвращаем 404 и сообщение
    }

    @ExceptionHandler(MultipartException.class) // Обрабатываем: неверный формат multipart
    public ResponseEntity<ErrorResponse> handleMultipartException(MultipartException ex) {
        log.warn("MultipartException: {}", ex.getMessage()); // Логируем предупреждение
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Неверный формат запроса: ожидается multipart/form-data.");
    }

    @ExceptionHandler(NoHandlerFoundException.class) // Обрабатываем: путь не найден
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(NoHandlerFoundException ex) {
        log.warn("NoHandlerFoundException: {}", ex.getMessage()); // Логируем предупреждение
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Запрашиваемый путь не найден");
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse(                      // Формируем объект ответа
                status.value(),                                       // Устанавливаем HTTP-код (400, 404, 500 и т.д.)
                message,                                              // Устанавливаем сообщение
                LocalDateTime.now());                                 // Указываем текущую дату и время
        return ResponseEntity.status(status).body(error);            // Возвращаем ResponseEntity с телом ошибки
    }



}
