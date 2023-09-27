package de.fhdo.puls.booking_service.command.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "ParkBookings")
@EntityListeners(AuditingEntityListener.class)
public class ParkBookingAggregate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank(message = "The BookerID/booker email must not be empty!")
    private String bookerId;

    @NotBlank(message = "The ParkingSpaceID must not be null!")
    private String parkingSpaceId;

    @NotNull(message = "The booking start time must not be empty!")
    private Date bookingStart;

    @NotNull(message = "The booking end time must not be empty!")
    private Date bookingEnd;

    @NotNull
    private float pricePerHour;

    @NotNull
    private boolean isCanceled;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date bookingCreated;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastModified;



    public ParkBookingAggregate(){}

    public ParkBookingAggregate(
            @NotBlank(message = "The BookerID must not be empty!") String bookerId,
            @NotBlank(message = "The ParkingSpaceID must not be null!") String parkingSpaceId,
            @NotNull(message = "The booking start time must not be empty!") Date bookingStart,
            @NotNull(message = "The booking end time must not be empty!") Date bookingEnd,
            @NotNull float pricePerHour,
            @NotNull boolean isCanceled) {
        this.bookerId = bookerId;
        this.parkingSpaceId = parkingSpaceId;
        this.bookingStart = bookingStart;
        this.bookingEnd = bookingEnd;
        this.pricePerHour = pricePerHour;
        this.isCanceled = isCanceled;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
