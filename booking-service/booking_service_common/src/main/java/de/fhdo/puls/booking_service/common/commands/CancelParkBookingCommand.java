package de.fhdo.puls.booking_service.common.commands;

/**
 * POJO (Plain old Java Object) for the interaction between the UI-service and the command-interface of
 * the booking-service (write access)
 */
public class CancelParkBookingCommand {

    private Long bookingId;

    public CancelParkBookingCommand(){}
    public CancelParkBookingCommand(Long bookingId){
        this.bookingId = bookingId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}
