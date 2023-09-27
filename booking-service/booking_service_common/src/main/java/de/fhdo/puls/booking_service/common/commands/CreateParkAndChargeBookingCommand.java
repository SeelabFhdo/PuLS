package de.fhdo.puls.booking_service.common.commands;

import java.time.LocalTime;
import java.util.Date;

/**
 * POJO (Plain old Java Object) for the interaction between the UI-service and the command-interface of
 * the booking-service (write access)
 */
public class CreateParkAndChargeBookingCommand {

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
    private float parkingPricePerHour;
    private float chargingPricePerKwh;
    private float parkingPriceTotal;

    /*---------------------------------------------------------------------*/

    public CreateParkAndChargeBookingCommand(){}
    public CreateParkAndChargeBookingCommand(Long userId,
                                             String userName,
                                             String userLastName,
                                             String parkingSpaceId,
                                             String parkingCity,
                                             int postCode,
                                             String parkingStreet,
                                             String parkingStreetNumber,
                                             Date startOfBooking,
                                             Date endOfBooking,
                                             float parkingPricePerHour,
                                             float chargingPricePerKwh,
                                             float parkingPriceTotal){
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
        this.parkingPricePerHour = parkingPricePerHour;
        this.chargingPricePerKwh = chargingPricePerKwh;
        this.parkingPriceTotal = parkingPriceTotal;
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

    public float getParkingPriceTotal() {
        return parkingPriceTotal;
    }

    public void setParkingPriceTotal(float parkingPriceTotal) {
        this.parkingPriceTotal = parkingPriceTotal;
    }


    @Override
    public String toString() {
        return "CreateParkAndChargeBookingCommand{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", parkingSpaceId='" + parkingSpaceId + '\'' +
                ", parkingCity='" + parkingCity + '\'' +
                ", postCode=" + postCode +
                ", parkingStreet='" + parkingStreet + '\'' +
                ", parkingStreetNumber='" + parkingStreetNumber + '\'' +
                ", startOfBooking=" + startOfBooking + '\'' +
                ", endOfBooking=" + endOfBooking + '\'' +
                ", parkingPricePerHour=" + parkingPricePerHour + '\'' +
                ", chargingPricePerKwh=" + chargingPricePerKwh + '\'' +
                ", parkingPriceTotal=" + parkingPriceTotal + '\'' +
                '}';
    }
}
