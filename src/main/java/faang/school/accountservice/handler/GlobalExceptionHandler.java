package faang.school.accountservice.handler;

import faang.school.accountservice.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import faang.school.accountservice.exception.AccountOperationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Method argument not valid error: ", e);
        return e.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        objectError -> ((FieldError) objectError).getField(),
                        objectError -> Objects.requireNonNull(objectError.getDefaultMessage(), "")));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleHashNotFoundException(EntityNotFoundException e) {
        log.error("Entity not found error: ", e);
        return e.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException e) {
        log.error("Runtime error: ", e);
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        log.error("Exception: ", e);
        return e.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(IllegalArgumentException e) {
        log.error("Exception: ", e);
        return e.getMessage();
    }

    @ExceptionHandler({AccountOperationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(AccountOperationException e) {
        log.error("Error: ", e);
        return e.getMessage();
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(DataIntegrityViolationException e) {
        String message = "Ошибка целостности данных: ";
        if (e.getCause() instanceof ConstraintViolationException cve) {
            message += "нарушение ограничения - " + cve.getConstraintName();
        } else {
            message += e.getMessage();
        }
        return message;
    }
}
