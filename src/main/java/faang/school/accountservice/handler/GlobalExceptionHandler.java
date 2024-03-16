package faang.school.accountservice.handler;

import faang.school.accountservice.exception.AccountOperationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AccountOperationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(AccountOperationException e, HttpServletRequest request) {
        log.error("Error: {}", e.getMessage());
        return getErrorResponse(request.getRequestURI(), HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(EntityNotFoundException e, HttpServletRequest request) {
        log.error("Error: {}", e.getMessage());
        return getErrorResponse(request.getRequestURI(), HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
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

    private ErrorResponse getErrorResponse(String url, HttpStatus status ,String message) {
        return ErrorResponse.builder()
                .url(url)
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

    }

}
