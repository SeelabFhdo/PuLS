package de.fhdo.puls.booking_service.common.dataTransferObjects;

public class BookingInvoiceDto {

    private Long invoiceId;
    private Long bookingId;
    private float invoiceAmount;


    public BookingInvoiceDto(){}

    public BookingInvoiceDto(Long invoiceId, Long bookingId, float invoiceAmount) {
        this.invoiceId = invoiceId;
        this.bookingId = bookingId;
        this.invoiceAmount = invoiceAmount;
    }


    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
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


    @Override
    public String toString() {
        return "BookingInvoiceDto{" +
                "invoiceId=" + invoiceId +
                ", bookingId=" + bookingId +
                ", invoiceAmount=" + invoiceAmount +
                '}';
    }
}
