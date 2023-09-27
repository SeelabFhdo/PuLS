package de.fhdo.puls.booking_service.common.events;

/**
 * POJO (Plain old Java Object) for the interaction between the command-functions, the (kafka) event store
 * and the query-functions of other services (for the broker-functions of the event store)
 */
public class ParkBookingCanceledEvent {

    private Long bookingId;
    private boolean isCanceled;

    public ParkBookingCanceledEvent(){}
    public ParkBookingCanceledEvent(Long bookingId, boolean isCanceled){
        this.bookingId = bookingId;
        this.isCanceled = isCanceled;
    }


    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public boolean isBookingCanceled() {
        return isCanceled;
    }

    public void setBookingCanceled(boolean isCanceled) {
        this.isCanceled = isCanceled;
    }


    @Override
    public String toString() {
        return "ParkBookingCanceledEvent{" +
                "bookingId=" + bookingId +
                ", isCanceled=" + isCanceled +
                '}';
    }
}
