package de.fhdo.puls.booking_service.common.events;

import java.time.LocalTime;
import java.util.Date;

/**
 * POJO (Plain old Java Object) for the interaction between the command-functions, the (kafka) event store
 * and the query-functions of other services (for the broker-functions of the event store)
 */
public class ParkBookingCreatedEvent {

    private Long bookingId;
    private String bookerId;
    private String parkingSpaceId;
    private Date bookingCreated;
    private Date lastModified;
    private Date bookingStart;
    private Date bookingEnd;
    private float pricePerHour;



    public ParkBookingCreatedEvent(){}
    public ParkBookingCreatedEvent(Long bookingId,
                                   String bookerId,
                                   String parkingSpaceId,
                                   Date bookingCreated,
                                   Date lastModified,
                                   Date bookingStart,
                                   Date bookingEnd,
                                   float pricePerHour) {
        this.bookingId = bookingId;
        this.bookerId = bookerId;
        this.parkingSpaceId = parkingSpaceId;
        this.bookingCreated = bookingCreated;
        this.lastModified = lastModified;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
        this.pricePerHour = pricePerHour;
    }


    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookerId() {
        return bookerId;
    }

    public void setBookerId(String bookerId) {
        this.bookerId = bookerId;
    }

    public String getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(String parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    public Date getBookingCreated() {
        return bookingCreated;
    }

    public void setBookingCreated(Date bookingCreated) {
        this.bookingCreated = bookingCreated;
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

    public float getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(float pricePerHour) {
        this.pricePerHour = pricePerHour;
    }


    @Override
    public String toString() {
        return "ParkBookingCreatedEvent{" +
                "bookingId=" + bookingId +
                ", bookerId=" + bookerId +
                ", parkingSpaceId='" + parkingSpaceId + '\'' +
                ", bookingCreated=" + bookingCreated +
                ", lastModified=" + lastModified +
                ", bookingStart=" + bookingStart +
                ", bookingEnd=" + bookingEnd +
                ", pricePerHour=" + pricePerHour +
                '}';
    }
}
