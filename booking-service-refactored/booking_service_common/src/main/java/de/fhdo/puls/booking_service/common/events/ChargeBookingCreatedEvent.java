package de.fhdo.puls.booking_service.common.events;

import java.time.LocalTime;
import java.util.Date;

/**
 * POJO (Plain old Java Object) for the interaction between the command-functions, the (kafka) event store
 * and the query-functions of other services (for the broker-functions of the event store)
 */
public class ChargeBookingCreatedEvent {

    private Long bookingId;
    private String bookerId;
    private String parkingSpaceId;
    private Date bookingCreated;
    private Date lastModified;
    private Date bookingStart;
    private Date bookingEnd;
    private float parkingPricePerHour;
    private float chargingPricePerKWh;
    private int verifyCode;



    public ChargeBookingCreatedEvent(){}
    public ChargeBookingCreatedEvent(Long bookingId,
                                     String bookerId,
                                     String parkingSpaceId,
                                     Date bookingCreated,
                                     Date lastModified,
                                     Date bookingStart,
                                     Date bookingEnd,
                                     float parkingPricePerHour,
                                     float chargingPricePerKWh,
                                     int verifyCode) {
        this.bookingId = bookingId;
        this.bookerId = bookerId;
        this.parkingSpaceId = parkingSpaceId;
        this.bookingCreated = bookingCreated;
        this.lastModified = lastModified;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
        this.parkingPricePerHour = parkingPricePerHour;
        this.chargingPricePerKWh = chargingPricePerKWh;
        this.verifyCode = verifyCode;
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

    public float getParkingPricePerHour() {
        return parkingPricePerHour;
    }

    public void setParkingPricePerHour(float parkingPricePerHour) {
        this.parkingPricePerHour = parkingPricePerHour;
    }

    public float getChargingPricePerKWh() {
        return chargingPricePerKWh;
    }

    public void setChargingPricePerKWh(float chargingPricePerKWh) {
        this.chargingPricePerKWh = chargingPricePerKWh;
    }

    public int getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        this.verifyCode = verifyCode;
    }


    @Override
    public String toString() {
        return "ChargeBookingCreatedEvent{" +
                "bookingId=" + bookingId +
                ", bookerId=" + bookerId +
                ", parkingSpaceId='" + parkingSpaceId + '\'' +
                ", bookingCreated=" + bookingCreated +
                ", lastModified=" + lastModified +
                ", bookingStart=" + bookingStart +
                ", bookingEnd=" + bookingEnd +
                ", parkingPricePerHour=" + parkingPricePerHour +
                ", chargingPricePerKWh=" + chargingPricePerKWh +
                ", verifyCode=" + verifyCode +
                '}';
    }
}
