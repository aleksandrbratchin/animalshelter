package ru.teamfour.controller.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ValidationController {

    @ExceptionHandler(value
            = {ConstraintViolationException.class, HttpMessageNotReadableException.class})
    protected ResponseEntity<?> handleConflict(
            RuntimeException ex, WebRequest request) {
        String textErr = "Ошибка при валидации: " + ex.getMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(textErr);
    }

}
