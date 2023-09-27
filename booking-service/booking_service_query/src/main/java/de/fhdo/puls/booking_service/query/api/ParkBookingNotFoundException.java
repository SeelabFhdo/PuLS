package de.fhdo.puls.booking_service.query.api;

public class ParkBookingNotFoundException extends Exception {

    private Long bookingId;

    public ParkBookingNotFoundException(Long bookingId){
        super("Park booking with the booking_ID " + bookingId + " not found!");
        this.bookingId = bookingId;
    }

    public Long getBookingId(){ return this.bookingId; }
}
