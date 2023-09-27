package de.fhdo.puls.booking_service.command.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ChargeInvoices")
public class ChargeInvoiceAggregate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNull(message = "The BookingID must not be empty!")
    private Long bookingId;

    @NotNull
    private float invoiceAmount;



    public ChargeInvoiceAggregate(){}

    public ChargeInvoiceAggregate(
            @NotNull(message = "The BookingID must not be empty!") Long bookingId,
            @NotNull float invoiceAmount) {
        this.bookingId = bookingId;
        this.invoiceAmount = invoiceAmount;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
