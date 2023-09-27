package de.fhdo.puls.booking_service.command.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger ERROR_LOGGER =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {BookingNotFoundException.class})
    public ResponseEntity<Object> handleBookingNotFoundException(BookingNotFoundException e,
                                                                 WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String exceptionMessage = e.getMessage();
        CustomErrorMessage errorMessage = new CustomErrorMessage(new Date(),httpStatus.value(),
                httpStatus, exceptionMessage, request.getDescription(false));
        ERROR_LOGGER.error(exceptionMessage);
        return new ResponseEntity<Object>(errorMessage,httpStatus);
    }
}
