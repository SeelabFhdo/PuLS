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
    private Date bookingCreatedDate;
    private Date lastModifiedDate;

    private Date startOfBooking;
    private Date endOfBooking;

    private float parkingPriceTotal;

    /*---------------------------------------------------------------------*/

    public ParkBookingUpdatedEvent(){}
    public ParkBookingUpdatedEvent(Long bookingId,
                                   Date bookingCreatedDate,
                                   Date lastModifiedDate,
                                   Date startOfBooking,
                                   Date endOfBooking,
                                   float parkingPriceTotal) {
        this.bookingId = bookingId;
        this.bookingCreatedDate = bookingCreatedDate;
        this.lastModifiedDate = lastModifiedDate;
        this.startOfBooking = startOfBooking;
        this.endOfBooking = endOfBooking;
        this.parkingPriceTotal = parkingPriceTotal;
    }

    /*---------------------------------------------------------------------*/

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Date getBookingCreatedDate() {
        return bookingCreatedDate;
    }

    public void setBookingCreatedDate(Date bookingCreatedDate) {
        this.bookingCreatedDate = bookingCreatedDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getStartOfBooking() {
        return startOfBooking;
    }

    public void setStartOfBooking(Date startOfBooking) {
        this.startOfBooking = startOfBooking;
    }

    public Date getEndOfBooking() {
        return endOfBooking;
    }

    public void setEndOfBooking(Date endOfBooking) {
        this.endOfBooking = endOfBooking;
    }

    public float getParkingPriceTotal() {
        return parkingPriceTotal;
    }

    public void setParkingPriceTotal(float parkingPriceTotal) {
        this.parkingPriceTotal = parkingPriceTotal;
    }

    /*---------------------------------------------------------------------*/

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
                "bookingId=" + bookingId + '\'' +
                ", bookingCreatedDate=" + bookingCreatedDate + '\'' +
                ", lastModifiedDate=" + lastModifiedDate + '\'' +
                ", startOfBooking=" + startOfBooking + '\'' +
                ", endOfBooking=" + endOfBooking + '\'' +
                ", parkingPriceTotal=" + parkingPriceTotal + '\'' +
                '}';
    }
}
