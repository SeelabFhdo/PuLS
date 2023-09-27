package de.fhdo.puls.booking_service.query.api;

public class ParkAndChargeBookingNotFoundException extends Exception{

    private Long bookingId;

    public ParkAndChargeBookingNotFoundException(Long bookingId){
        super("Park and charge booking with the booking_ID " + bookingId + " not found!");
        this.bookingId = bookingId;
    }

    public Long getBookingId(){ return this.bookingId; }
}
