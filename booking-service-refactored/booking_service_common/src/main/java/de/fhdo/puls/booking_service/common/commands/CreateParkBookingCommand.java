package de.fhdo.puls.booking_service.common.commands;

import java.time.LocalTime;
import java.util.Date;

/**
 * POJO (Plain old Java Object) for the interaction between the UI-service and the command-interface of
 * the booking-service (write access)
 */
public class CreateParkBookingCommand {

    private String bookerId;
    private String parkingSpaceId;
    private Date bookingStart;
    private Date bookingEnd;
    private float pricePerHour;



    public CreateParkBookingCommand(){}
    public CreateParkBookingCommand(String bookerId,
                                    String parkingSpaceId,
                                    Date bookingStart,
                                    Date bookingEnd,
                                    float pricePerHour){
        this.bookerId = bookerId;;
        this.parkingSpaceId = parkingSpaceId;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
        this.pricePerHour = pricePerHour;
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


    @Override
    public String toString() {
        return "CreateParkBookingCommand{" +
                "bookerId=" + bookerId +
                ", parkingSpaceId='" + parkingSpaceId + '\'' +
                ", bookingStart=" + bookingStart +
                ", bookingEnd=" + bookingEnd +
                ", pricePerHour=" + pricePerHour +
                '}';
    }
}
