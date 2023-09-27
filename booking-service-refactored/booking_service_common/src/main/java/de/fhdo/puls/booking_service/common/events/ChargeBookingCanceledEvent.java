package de.fhdo.puls.booking_service.common.events;

/**
 * POJO (Plain old Java Object) for the interaction between the command-functions, the (kafka) event store
 * and the query-functions of other services (for the broker-functions of the event store)
 */
public class ChargeBookingCanceledEvent {

    private Long bookingId;
    private boolean isCanceled;


    public ChargeBookingCanceledEvent(){}
    public ChargeBookingCanceledEvent(Long bookingId, boolean isCanceled){
        this.bookingId = bookingId;
        this.isCanceled = isCanceled;
    }


    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    @Override
    public String toString() {
        return "ChargeBookingCanceledEvent{" +
                "bookingId=" + bookingId +
                ", isCanceled=" + isCanceled +
                '}';
    }
}
