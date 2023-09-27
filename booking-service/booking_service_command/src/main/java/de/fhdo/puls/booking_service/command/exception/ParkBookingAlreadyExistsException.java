package de.fhdo.puls.booking_service.command.exception;

public class ParkBookingAlreadyExistsException extends Exception{

    private Long bookingId;

    public ParkBookingAlreadyExistsException(Long bookingId){
        super("Park booking with the booking ID " + bookingId + " already exists!");
        this.bookingId = bookingId;
    }

    public Long getBookingId(){ return this.bookingId; }
}
