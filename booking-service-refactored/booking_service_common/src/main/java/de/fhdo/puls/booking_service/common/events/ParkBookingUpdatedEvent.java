package de.fhdo.puls.booking_service.common.events;

import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

/**
 * POJO (Plain old Java Object) for the interaction between the command-functions, the (kafka) event store
 * and the query-functions of other services (for the broker-functions of the event store)
 */
public class ParkBookingUpdatedEvent {

    private Long bookingId;
    private Date lastModified;

    private Date bookingStart;
    private Date bookingEnd;



    public ParkBookingUpdatedEvent() {}

    public ParkBookingUpdatedEvent(Long bookingId,
                                   Date lastModified,
                                   Date bookingStart,
                                   Date bookingEnd) {
        this.bookingId = bookingId;
        this.lastModified = lastModified;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }



    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getBookingStart() {
        return bookingStart;
    }

    public void setBookingStart(Date bookingStart) {
        this.bookingStart = bookingStart;
    }

    public Date getBookingEnd() {
        return bookingEnd;
    }

    public void setBookingEnd(Date bookingEnd) {
        this.bookingEnd = bookingEnd;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkBookingUpdatedEvent that = (ParkBookingUpdatedEvent) o;
        return bookingId == that.bookingId;
    }


    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }


    @Override
    public String toString() {
        return "ParkBookingUpdatedEvent{" +
                "bookingId=" + bookingId +
                ", lastModified=" + lastModified +
                ", bookingStart=" + bookingStart +
                ", bookingEnd=" + bookingEnd +
                '}';
    }
}
