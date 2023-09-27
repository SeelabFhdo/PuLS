package de.fhdo.puls.booking_service.common.commands;

/**
 * POJO (Plain old Java Object) for the interaction between the UI-service and the command-interface of
 * the booking-service (write access)
 */
public class CancelChargeBookingCommand {

    private Long bookingId;

    public CancelChargeBookingCommand(){}
    public CancelChargeBookingCommand(Long bookingId){
        this.bookingId = bookingId;
    }

    public Long getBookingId(){ return this.bookingId; }

    @Override
    public String toString() {
        return "CancelChargeBookingCommand{" +
                "bookingId=" + bookingId +
                '}';
    }
}
