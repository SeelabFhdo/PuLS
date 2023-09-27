package de.fhdo.puls.booking_service.query.domain;

import java.time.LocalTime;
import java.util.Date;

public class ParkAndChargeBooking {

    private Long userId;
    private String userName;
    private String userLastName;

    private String parkingSpaceId;
    private String parkingCity;
    private int postCode;
    private String parkingStreet;
    private String parkingStreetNumber;

    private Date startOfBooking;
    private Date endOfBooking;

    private float bookingPricePerHour;
    private float chargingPricePerKwh;
    private float bookingPriceTotal;
    private int verifyCode;

    private boolean bookingCanceled;

    /*---------------------------------------------------------------------*/

    public ParkAndChargeBooking() {}
    public ParkAndChargeBooking(Long userId,
                                String userName,
                                String userLastName,
                                String parkingSpaceId,
                                String parkingCity,
                                int postCode,
                                String parkingStreet,
                                String parkingStreetNumber,
                                Date startOfBooking,
                                Date endOfBooking,
                                float bookingPricePerHour,
                                float chargingPricePerKwh,
                                float bookingPriceTotal,
                                int verifyCode,
                                boolean bookingCanceled) {
        this.userId = userId;
        this.userName = userName;
        this.userLastName = userLastName;
        this.parkingSpaceId = parkingSpaceId;
        this.parkingCity = parkingCity;
        this.postCode = postCode;
        this.parkingStreet = parkingStreet;
        this.parkingStreetNumber = parkingStreetNumber;
        this.startOfBooking = startOfBooking;
        this.endOfBooking = endOfBooking;
        this.bookingPricePerHour = bookingPricePerHour;
        this.chargingPricePerKwh = chargingPricePerKwh;
        this.bookingPriceTotal = bookingPriceTotal;
        this.verifyCode = verifyCode;
        this.bookingCanceled = bookingCanceled;
    }

    /*---------------------------------------------------------------------*/

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(String parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    public String getParkingCity() {
        return parkingCity;
    }

    public void setParkingCity(String parkingCity) {
        this.parkingCity = parkingCity;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getParkingStreet() {
        return parkingStreet;
    }

    public void setParkingStreet(String parkingStreet) {
        this.parkingStreet = parkingStreet;
    }

    public String getParkingStreetNumber() {
        return parkingStreetNumber;
    }

    public void setParkingStreetNumber(String parkingStreetNumber) {
        this.parkingStreetNumber = parkingStreetNumber;
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

    public float getBookingPricePerHour() {
        return bookingPricePerHour;
    }

    public void setBookingPricePerHour(float bookingPricePerHour) {
        this.bookingPricePerHour = bookingPricePerHour;
    }

    public float getChargingPricePerKwh() {
        return chargingPricePerKwh;
    }

    public void setChargingPricePerKwh(float chargingPricePerKwh) {
        this.chargingPricePerKwh = chargingPricePerKwh;
    }

    public float getBookingPriceTotal() {
        return bookingPriceTotal;
    }

    public void setBookingPriceTotal(float bookingPriceTotal) {
        this.bookingPriceTotal = bookingPriceTotal;
    }

    public int getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        this.verifyCode = verifyCode;
    }

    public boolean isBookingCanceled() {
        return bookingCanceled;
    }

    public void setBookingCanceled(boolean bookingCanceled) {
        this.bookingCanceled = bookingCanceled;
    }
}
