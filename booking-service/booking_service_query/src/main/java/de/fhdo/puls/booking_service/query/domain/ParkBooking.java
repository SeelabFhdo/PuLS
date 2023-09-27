package de.fhdo.puls.booking_service.query.domain;

import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

public class ParkBooking {

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
    private float bookingPriceTotal;

    private boolean bookingCanceled;

    /*---------------------------------------------------------------------*/

    public ParkBooking(){}
    public ParkBooking(Long userId,
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
                       float bookingPriceTotal,
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
        this.bookingPriceTotal = bookingPriceTotal;
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

    public float getBookingPriceTotal() {
        return bookingPriceTotal;
    }

    public void setBookingPriceTotal(float bookingPriceTotal) {
        this.bookingPriceTotal = bookingPriceTotal;
    }

    public boolean isBookingCanceled() {
        return bookingCanceled;
    }

    public void setBookingCanceled(boolean bookingCanceled) {
        this.bookingCanceled = bookingCanceled;
    }
}
