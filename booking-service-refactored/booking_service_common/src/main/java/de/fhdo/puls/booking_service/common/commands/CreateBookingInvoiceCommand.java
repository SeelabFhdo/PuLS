package de.fhdo.puls.booking_service.common.commands;

public class CreateBookingInvoiceCommand {

    private Long bookingId;
    private float invoiceAmount;


    public CreateBookingInvoiceCommand(){}

    public CreateBookingInvoiceCommand(Long bookingId, float invoiceAmount) {
        this.bookingId = bookingId;
        this.invoiceAmount = invoiceAmount;
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
        return "CreateBookingInvoiceCommand{" +
                "bookingId=" + bookingId +
                ", invoiceAmount=" + invoiceAmount +
                '}';
    }
}
