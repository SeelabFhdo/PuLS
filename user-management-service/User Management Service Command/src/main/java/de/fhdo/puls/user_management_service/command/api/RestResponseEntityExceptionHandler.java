package de.fhdo.puls.user_management_service.command.api;

import de.fhdo.puls.user_management_service.command.domain.user.UserAlreadyExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolationException(Exception exception,
        WebRequest request) {
        var constraintViolationException = (ConstraintViolationException) exception;
        var errors = constraintViolationException.getConstraintViolations()
            .stream()
            .map(e -> e.getMessage())
            .collect(Collectors.joining(", "));
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleSqlException(Exception exception, WebRequest request) {
        var dataIntegrityViolationException = (DataIntegrityViolationException) exception;
        var cause = dataIntegrityViolationException.getCause();
        if (!(cause instanceof org.hibernate.exception.ConstraintViolationException))
            return new ResponseEntity<>(cause.getMessage(), new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);

        var hibernateConstraintViolationException
            = (org.hibernate.exception.ConstraintViolationException) cause;
        return new ResponseEntity<>(
            hibernateConstraintViolationException.getMessage(),
            new HttpHeaders(),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({ UserAlreadyExistsException.class })
    public ResponseEntity<Object> handleUserAlreadyExistsException(Exception exception,
        WebRequest request) {
        var errorMessage = ((UserAlreadyExistsException) exception).getMessage();
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }
}