package de.fhdo.puls.booking_service.command.exception;

public class BookingNotFoundException extends Exception {

    public BookingNotFoundException(String message) {
        super(message);
    }
}
