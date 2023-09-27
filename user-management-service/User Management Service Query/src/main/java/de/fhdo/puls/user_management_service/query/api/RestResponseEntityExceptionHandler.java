package de.fhdo.puls.user_management_service.query.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ UserNotFoundException.class })
    public ResponseEntity<Object> handleUserNotFoundException(Exception exception,
        WebRequest request) {
        var userNotFoundException = (UserNotFoundException) exception;
        return new ResponseEntity<>(userNotFoundException.getMessage(), new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }
}