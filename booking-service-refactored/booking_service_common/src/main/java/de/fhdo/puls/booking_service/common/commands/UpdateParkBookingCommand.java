package de.fhdo.puls.booking_service.common.commands;

import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

/**
 * POJO (Plain old Java Object) for the interaction between the UI-service and the command-interface of
 * the booking-service (write access)
 */
public class UpdateParkBookingCommand {

    private Long bookingId;
    private Date bookingStart;
    private Date bookingEnd;



    public UpdateParkBookingCommand(){}
    public UpdateParkBookingCommand(Long bookingId,
                                    Date bookingStart,
                                    Date bookingEnd){
        this.bookingId = bookingId;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
    }



    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateParkBookingCommand that = (UpdateParkBookingCommand) o;
        return bookingId == that.bookingId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }


    @Override
    public String toString() {
        return "UpdateParkBookingCommand{" +
                "bookingId=" + bookingId +
                ", bookingStart=" + bookingStart +
                ", bookingEnd=" + bookingEnd +
                '}';
    }
}
