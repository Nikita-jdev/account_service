package faang.school.accountservice.handler;

import faang.school.accountservice.exception.DataValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataValidationException.class)
    public ResponseEntity<Object> handleDataValidationException(DataValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
