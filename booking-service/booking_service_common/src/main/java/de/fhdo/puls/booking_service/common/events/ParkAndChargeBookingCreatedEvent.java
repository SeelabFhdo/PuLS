package de.fhdo.puls.booking_service.common.events;

import java.time.LocalTime;
import java.util.Date;

/**
 * POJO (Plain old Java Object) for the interaction between the command-functions, the (kafka) event store
 * and the query-functions of other services (for the broker-functions of the event store)
 */
public class ParkAndChargeBookingCreatedEvent {

    private Long bookingId;
    private Long userId;
    private String userName;
    private String userLastName;

    private String parkingSpaceId;
    private String parkingCity;
    private int postCode;
    private String parkingStreet;
    private String parkingStreetNumber;

    private Date bookingCreatedDate;
    private Date lastModifiedDate;

    private Date startOfBooking;
    private Date endOfBooking;

    private float parkingPricePerHour;
    private float chargingPricePerKwh;
    private float getParkingPriceTotal;

    private int verifyCode;

    /*---------------------------------------------------------------------*/

    public ParkAndChargeBookingCreatedEvent(){}
    public ParkAndChargeBookingCreatedEvent(Long bookingId,
                                            Long userId,
                                            String userName,
                                            String userLastName,
                                            String parkingSpaceId,
                                            String parkingCity,
                                            int postCode,
                                            String parkingStreet,
                                            String parkingStreetNumber,
                                            Date bookingCreatedDate,
                                            Date lastModifiedDate,
                                            Date startOfBooking,
                                            Date endOfBooking,
                                            float parkingPricePerHour,
                                            float chargingPricePerKwh,
                                            float getParkingPriceTotal,
                                            int verifyCode) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.userName = userName;
        this.userLastName = userLastName;
        this.parkingSpaceId = parkingSpaceId;
        this.parkingCity = parkingCity;
        this.postCode = postCode;
        this.parkingStreet = parkingStreet;
        this.parkingStreetNumber = parkingStreetNumber;
        this.bookingCreatedDate = bookingCreatedDate;
        this.lastModifiedDate = lastModifiedDate;
        this.startOfBooking = startOfBooking;
        this.endOfBooking = endOfBooking;
        this.parkingPricePerHour = parkingPricePerHour;
        this.chargingPricePerKwh = chargingPricePerKwh;
        this.getParkingPriceTotal = getParkingPriceTotal;
        this.verifyCode = verifyCode;
    }

    /*---------------------------------------------------------------------*/

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

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

    public float getParkingPricePerHour() {
        return parkingPricePerHour;
    }

    public void setParkingPricePerHour(float parkingPricePerHour) {
        this.parkingPricePerHour = parkingPricePerHour;
    }

    public float getChargingPricePerKwh() {
        return chargingPricePerKwh;
    }

    public void setChargingPricePerKwh(float chargingPricePerKwh) {
        this.chargingPricePerKwh = chargingPricePerKwh;
    }

    public float getGetParkingPriceTotal() {
        return getParkingPriceTotal;
    }

    public void setGetParkingPriceTotal(float getParkingPriceTotal) {
        this.getParkingPriceTotal = getParkingPriceTotal;
    }

    public int getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(int verifyCode) {
        this.verifyCode = verifyCode;
    }


    @Override
    public String toString() {
        return "ParkAndChargeBookingCreatedEvent{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", parkingSpaceId='" + parkingSpaceId + '\'' +
                ", parkingCity='" + parkingCity + '\'' +
                ", postCode=" + postCode +
                ", parkingStreet='" + parkingStreet + '\'' +
                ", parkingStreetNumber='" + parkingStreetNumber + '\'' +
                ", bookingCreatedDate=" + bookingCreatedDate +
                ", startOfBooking=" + startOfBooking + '\'' +
                ", endOfBooking=" + endOfBooking + '\'' +
                ", parkingPricePerHour=" + parkingPricePerHour + '\'' +
                ", chargingPricePerKwh=" + chargingPricePerKwh + '\'' +
                ", getParkingPriceTotal=" + getParkingPriceTotal + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                '}';
    }
}
