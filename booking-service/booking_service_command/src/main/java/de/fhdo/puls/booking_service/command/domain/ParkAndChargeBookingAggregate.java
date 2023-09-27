package de.fhdo.puls.booking_service.command.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "parkAndChargeBookings")
@EntityListeners(AuditingEntityListener.class)
public class ParkAndChargeBookingAggregate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @NotBlank
    private Long userId;
    @NotBlank
    private String userName;
    @NotBlank
    private String userLastName;

    @NotBlank
    private String parkingSpaceId;
    @NotBlank
    private String parkingCity;
    @NotBlank
    private int postCode;
    @NotBlank
    private String parkingStreet;
    @NotBlank
    private String parkingStreetNumber;

    @NotBlank(message = "The start time of booking must not me empty!")
    private Date startOfBooking;
    @NotBlank(message = "The end time of booking must not be empty!")
    private Date endOfBooking;

    @NotBlank
    private float bookingPricePerHour;
    @NotBlank
    private float chargingPricePerKwh;
    @NotBlank
    private float bookingPriceTotal;
    @NotBlank
    private int verifyCode;

    @NotBlank
    private boolean bookingCanceled;

    /*---------------------------------------------------------------------*/

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date bookingCreatedDate;
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastModifiedDate;

    /*---------------------------------------------------------------------*/

    public ParkAndChargeBookingAggregate(){}
    public ParkAndChargeBookingAggregate(@NotBlank Long userId,
                                         @NotBlank String userName,
                                         @NotBlank String userLastName,
                                         @NotBlank String parkingSpaceId,
                                         @NotBlank String parkingCity,
                                         @NotBlank int postCode,
                                         @NotBlank String parkingStreet,
                                         @NotBlank String parkingStreetNumber,
                                         @NotBlank Date startOfBooking,
                                         @NotBlank Date endOfBooking,
                                         @NotBlank float bookingPricePerHour,
                                         @NotBlank float chargingPricePerKwh,
                                         @NotBlank float bookingPriceTotal,
                                         @NotBlank int verifyCode) {
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
        this.bookingCanceled = false;
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
}
