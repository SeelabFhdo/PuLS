package de.fhdo.puls.booking_service.common.commands;

import java.time.LocalTime;
import java.util.Date;

/**
 * POJO (Plain old Java Object) for the interaction between the UI-service and the command-interface of
 * the booking-service (write access)
 */
public class CreateChargeBookingCommand {

    private String bookerId;
    private String parkingSpaceId;
    private Date bookingStart;
    private Date bookingEnd;
    private float parkingPricePerHour;
    private float chargingPricePerKWh;



    public CreateChargeBookingCommand(){}
    public CreateChargeBookingCommand(String bookerId,
                                      String parkingSpaceId,
                                      Date bookingStart,
                                      Date bookingEnd,
                                      float parkingPricePerHour,
                                      float chargingPricePerKWh){
        this.bookerId = bookerId;
        this.parkingSpaceId = parkingSpaceId;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
        this.parkingPricePerHour = parkingPricePerHour;
        this.chargingPricePerKWh = chargingPricePerKWh;
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


    @Override
    public String toString() {
        return "CreateChargeBookingCommand{" +
                "bookerId=" + bookerId +
                ", parkingSpaceId='" + parkingSpaceId + '\'' +
                ", bookingStart=" + bookingStart +
                ", bookingEnd=" + bookingEnd +
                ", parkingPricePerHour=" + parkingPricePerHour +
                ", chargingPricePerKWh=" + chargingPricePerKWh +
                '}';
    }
}
