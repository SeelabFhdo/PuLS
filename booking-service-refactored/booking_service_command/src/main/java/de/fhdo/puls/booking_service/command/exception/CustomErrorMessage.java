package de.fhdo.puls.booking_service.command.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class CustomErrorMessage {

    private final Date timestamp;
    private final int httpStatusCode;
    private final HttpStatus httpStatus;
    private final String message;
    private final String path;

    public CustomErrorMessage(Date timestamp, int httpStatusCode, HttpStatus httpStatus,
                              String message, String path) {
        this.timestamp = timestamp;
        this.httpStatusCode = httpStatusCode;
        this.httpStatus= httpStatus;
        this.message = message;
        this.path = path;
    }

    public Date getTimestamp() { return timestamp; }

    public int getHttpStatusCode() { return  httpStatusCode; }

    public HttpStatus getHttpStatus() { return httpStatus; }

    public String getMessage() { return message; }

    public String getPath() { return path; }
}
