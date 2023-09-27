package de.fhdo.puls.booking_service.query.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ParkInvoices")
public class ParkInvoiceAggregate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNull
    private Long originalId;

    @NotNull(message = "The BookingID must not be empty!")
    private Long bookingId;

    @NotNull
    private float invoiceAmount;


    public ParkInvoiceAggregate () {}

    public ParkInvoiceAggregate(
            @NotNull Long originalId,
            @NotNull(message = "The BookingID must not be empty!") Long bookingId,
            @NotNull float invoiceAmount)
    {
        this.originalId = originalId;
        this.bookingId = bookingId;
        this.invoiceAmount = invoiceAmount;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Long originalId) {
        this.originalId = originalId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public float getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(float invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }
}
