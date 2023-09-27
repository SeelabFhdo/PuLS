package de.fhdo.puls.booking_service.common.events;

import java.util.Date;
import java.util.Objects;

/**
 * POJO (Plain old Java Object) for the interaction between the command-functions, the (kafka) event store
 * and the query-functions of other services (for the broker-functions of the event store)
 */
public class ChargeBookingUpdatedEvent {

    private Long bookingId;
    private Date lastModified;

    private Date bookingStart;
    private Date bookingEnd;

    private int verifyCode;



    public ChargeBookingUpdatedEvent(){}
    public ChargeBookingUpdatedEvent(Long bookingId,
                                     Date lastModified,
                                     Date bookingStart,
                                     Date bookingEnd,
                                     int verifyCode) {
        this.bookingId = bookingId;
        this.lastModified = lastModified;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
        this.verifyCode = verifyCode;
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

    public int getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        this.verifyCode = verifyCode;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChargeBookingUpdatedEvent that = (ChargeBookingUpdatedEvent) o;
        return bookingId == that.bookingId;
    }


    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }


    @Override
    public String toString() {
        return "ChargeBookingUpdatedEvent{" +
                "bookingId=" + bookingId +
                ", lastModified=" + lastModified +
                ", bookingStart=" + bookingStart +
                ", bookingEnd=" + bookingEnd +
                ", verifyCode=" + verifyCode +
                '}';
    }
}
