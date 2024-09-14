package com.banquito.microservicio2.exception;
import com.banquito.microservicio2.exception.dto.RSError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<RSError> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        RSError errorResponse = new RSError("Error", "Ruta invalida");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RSError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        RSError errorResponse = new RSError("Error", "Metodo no permitido");
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }
}