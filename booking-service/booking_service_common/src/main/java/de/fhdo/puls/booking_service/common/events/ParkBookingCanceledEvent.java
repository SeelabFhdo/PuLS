package de.fhdo.puls.booking_service.common.events;

/**
 * POJO (Plain old Java Object) for the interaction between the command-functions, the (kafka) event store
 * and the query-functions of other services (for the broker-functions of the event store)
 */
public class ParkBookingCanceledEvent {

    private Long bookingId;
    private boolean bookingCanceled;

    public ParkBookingCanceledEvent(){}
    public ParkBookingCanceledEvent(Long bookingId, boolean bookingCanceled){
        this.bookingId = bookingId;
        this.bookingCanceled = bookingCanceled;
    }


    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public boolean isBookingCanceled() {
        return bookingCanceled;
    }

    public void setBookingCanceled(boolean bookingCanceled) {
        this.bookingCanceled = bookingCanceled;
    }
}
