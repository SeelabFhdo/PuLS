package de.fhdo.puls.booking_service.command.exception;

public class ParkAndChargeBookingAlreadyExistsException extends Exception{

    public Long bookingId;

    public ParkAndChargeBookingAlreadyExistsException(Long bookingId){
        super("Park and charge booking with the booking ID " + bookingId + " already exists!");
        this.bookingId = bookingId;
    }

    public Long getBookingId(){ return this.bookingId; }
}
