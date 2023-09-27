package de.fhdo.puls.booking_service.common.commands;

import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

/**
 * POJO (Plain old Java Object) for the interaction between the UI-service and the command-interface of
 * the booking-service (write access)
 */
public class UpdateParkAndChargeBookingCommand {

    private Long bookingId;
    private Date startOfBooking;
    private Date endOfBooking;
    private float parkingPriceTotal;

    /*---------------------------------------------------------------------*/

    public UpdateParkAndChargeBookingCommand(){}
    public UpdateParkAndChargeBookingCommand(Long bookingId,
                                             Date startOfBooking,
                                             Date endOfBooking,
                                             float parkingPriceTotal){
        this.bookingId = bookingId;
        this.startOfBooking = startOfBooking;
        this.endOfBooking = endOfBooking;
        this.parkingPriceTotal = parkingPriceTotal;
    }

    /*---------------------------------------------------------------------*/

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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

    public float getParkingPriceTotal() {
        return parkingPriceTotal;
    }

    public void setParkingPriceTotal(float parkingPriceTotal) {
        this.parkingPriceTotal = parkingPriceTotal;
    }

    /*---------------------------------------------------------------------*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateParkAndChargeBookingCommand that = (UpdateParkAndChargeBookingCommand) o;
        return bookingId == that.bookingId;
    }


    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }



    @Override
    public String toString() {
        return "UpdateParkAndChargeBookingCommand{" +
                "bookingId=" + bookingId + '\'' +
                ", startOfBooking=" + startOfBooking + '\'' +
                ", endOfBooking=" + endOfBooking + '\'' +
                ", parkingPriceTotal=" + parkingPriceTotal + '\'' +
                '}';
    }
}
