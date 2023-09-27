package de.fhdo.puls.booking_service.query.service;

import de.fhdo.puls.booking_service.common.dataTransferObjects.BookingInvoiceDto;
import de.fhdo.puls.booking_service.common.events.InvoiceCreatedEvent;
import de.fhdo.puls.booking_service.query.domain.ChargeInvoiceAggregate;
import de.fhdo.puls.booking_service.query.exception.InvoiceNotFoundException;
import de.fhdo.puls.booking_service.query.repository.ChargeInvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ChargeInvoiceService {

    private final ChargeInvoiceRepo chargeInvoiceRepo;


    @Autowired
    public ChargeInvoiceService(ChargeInvoiceRepo chargeInvoiceRepo) {
        this.chargeInvoiceRepo = chargeInvoiceRepo;
    }


    /*** === GET AGGREGATE FROM EVENT === */

    private ChargeInvoiceAggregate getAggregateFromCreatedEvent(InvoiceCreatedEvent event) {
        return new ChargeInvoiceAggregate(
                event.getInvoiceId(),
                event.getBookingId(),
                event.getInvoiceAmount()
        );
    }


    /*** === EVENT HANDLING === */

    public void handleInvoiceCreatedEvent(InvoiceCreatedEvent event) {
        ChargeInvoiceAggregate aggregate = getAggregateFromCreatedEvent(event);
        this.chargeInvoiceRepo.save(aggregate);
    }


    /*** === GET DATA TRANSFER OBJECT (DTO) FROM AGGREGATE === */

    private BookingInvoiceDto getDtoFromAggregate(ChargeInvoiceAggregate aggregate) {
        return new BookingInvoiceDto(
                aggregate.getOriginalId(),
                aggregate.getBookingId(),
                aggregate.getInvoiceAmount()
        );
    }


    /*** === QUERY HANDLING === */

    public BookingInvoiceDto getInvoice(Long invoiceId) throws InvoiceNotFoundException {
        var aggregate = this.chargeInvoiceRepo.findByOriginalIdEquals(invoiceId);
        if (aggregate!=null) {
            return getDtoFromAggregate(aggregate);
        }
        else {
            throw new InvoiceNotFoundException(
                    "ChargeInvoice with invoice_ID " + invoiceId + " not found!"
            );
        }
    }


    public List<BookingInvoiceDto> getAllInvoices() {
        List<BookingInvoiceDto> chargeInvoices = new LinkedList<>();
        var chargeInvoiceAggregates = this.chargeInvoiceRepo.findAll();
        chargeInvoiceAggregates.forEach(aggregate -> {
            chargeInvoices.add(getDtoFromAggregate(aggregate));
        });
        return chargeInvoices;
    }


    public BookingInvoiceDto getInvoiceFromBooking(Long bookingId) throws InvoiceNotFoundException {
        var aggregate = this.chargeInvoiceRepo.findByBookingIdEquals(bookingId);
        if (aggregate!=null) {
            return getDtoFromAggregate(aggregate);
        }
        else {
            throw new InvoiceNotFoundException(
                    "ChargeInvoice with booking_ID " + bookingId +
                            " not found! Corresponding booking does not exist!"
            );
        }
    }
}
