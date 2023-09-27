package de.fhdo.puls.booking_service.common.commands;

/**
 * POJO (Plain old Java Object) for the interaction between the UI-service and the command-interface of
 * the booking-service (write access)
 */
public class CancelParkAndChargeBookingCommand {

    private Long bookingId;

    public CancelParkAndChargeBookingCommand(){}
    public CancelParkAndChargeBookingCommand(Long bookingId){
        this.bookingId = bookingId;
    }

    public Long getBookingId(){ return this.bookingId; }
}
