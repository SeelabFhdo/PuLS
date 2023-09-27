package de.fhdo.puls.booking_service.common.dataTransferObjects;

import java.util.Date;

public class ChargeBookingDto {

    private Long bookingId;
    private String bookerId;
    private String parkingSpaceId;
    private Date bookingStart;
    private Date bookingEnd;
    private float pricePerHour;
    private float pricePerKWh;
    private int verifyCode;
    private boolean isCanceled;
    private Date bookingCreated;


    public ChargeBookingDto() {}

    public ChargeBookingDto(Long bookingId,
                            String bookerId,
                            String parkingSpaceId,
                            Date bookingStart,
                            Date bookingEnd,
                            float pricePerHour,
                            float pricePerKWh,
                            int verifyCode,
                            boolean isCanceled,
                            Date bookingCreated)
    {
        this.bookingId = bookingId;
        this.bookerId = bookerId;
        this.parkingSpaceId = parkingSpaceId;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
        this.pricePerHour = pricePerHour;
        this.pricePerKWh = pricePerKWh;
        this.verifyCode = verifyCode;
        this.isCanceled = isCanceled;
        this.bookingCreated = bookingCreated;
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

    public float getPricePerKWh() {
        return pricePerKWh;
    }

    public void setPricePerKWh(float pricePerKWh) {
        this.pricePerKWh = pricePerKWh;
    }

    public int getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        this.verifyCode = verifyCode;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public Date getBookingCreated() {
        return bookingCreated;
    }

    public void setBookingCreated(Date bookingCreated) {
        this.bookingCreated = bookingCreated;
    }


    @Override
    public String toString() {
        return "ChargeBookingDto{" +
                "bookingId=" + bookingId +
                ", bookerId=" + bookerId +
                ", parkingSpaceId='" + parkingSpaceId + '\'' +
                ", bookingStart=" + bookingStart +
                ", bookingEnd=" + bookingEnd +
                ", pricePerHour=" + pricePerHour +
                ", pricePerKWh=" + pricePerKWh +
                ", verifyCode=" + verifyCode +
                ", isCanceled=" + isCanceled +
                ", bookingCreated=" + bookingCreated +
                '}';
    }
}
