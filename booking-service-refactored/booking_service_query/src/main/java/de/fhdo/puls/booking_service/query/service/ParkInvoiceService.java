package de.fhdo.puls.booking_service.query.service;

import de.fhdo.puls.booking_service.common.dataTransferObjects.BookingInvoiceDto;
import de.fhdo.puls.booking_service.common.events.InvoiceCreatedEvent;
import de.fhdo.puls.booking_service.query.domain.ParkInvoiceAggregate;
import de.fhdo.puls.booking_service.query.exception.InvoiceNotFoundException;
import de.fhdo.puls.booking_service.query.repository.ParkInvoiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ParkInvoiceService {

    private final ParkInvoiceRepo parkInvoiceRepo;


    @Autowired
    public ParkInvoiceService(ParkInvoiceRepo parkInvoiceRepo) {
        this.parkInvoiceRepo = parkInvoiceRepo;
    }


    /*** === GET AGGREGATE FROM EVENT === */

    private ParkInvoiceAggregate getAggregateFromCreatedEvent(InvoiceCreatedEvent event) {
        return new ParkInvoiceAggregate(
                event.getInvoiceId(),
                event.getBookingId(),
                event.getInvoiceAmount()
        );
    }


    /*** === EVENT HANDLING === */

    public void handleInvoiceCreatedEvent(InvoiceCreatedEvent event) {
        ParkInvoiceAggregate aggregate = getAggregateFromCreatedEvent(event);
        this.parkInvoiceRepo.save(aggregate);
    }


    /*** === GET DATA TRANSFER OBJECT (DTO) FROM AGGREGATE === */

    private BookingInvoiceDto getDtoFromAggregate(ParkInvoiceAggregate aggregate) {
        return new BookingInvoiceDto(
                aggregate.getOriginalId(),
                aggregate.getBookingId(),
                aggregate.getInvoiceAmount()
        );
    }

    /*** === QUERY HANDLING === */

    public BookingInvoiceDto getInvoice(Long invoiceId) throws InvoiceNotFoundException {
       var aggregate = this.parkInvoiceRepo.findByOriginalIdEquals(invoiceId);
       if (aggregate != null) {
           return getDtoFromAggregate(aggregate);
       }
       else {
           throw new InvoiceNotFoundException(
                   "ParkInvoice with invoice_ID " + invoiceId + " not found!"
           );
       }
    }


    public List<BookingInvoiceDto> getAllInvoices() {
        List<BookingInvoiceDto> parkInvoices = new LinkedList<>();
        var parkInvoiceAggregates = this.parkInvoiceRepo.findAll();
        parkInvoiceAggregates.forEach(aggregate -> {
            parkInvoices.add(getDtoFromAggregate(aggregate));
        });
        return parkInvoices;
    }


    public BookingInvoiceDto getInvoiceFromBooking(Long bookingId) throws InvoiceNotFoundException {
        var aggregate = this.parkInvoiceRepo.findByBookingIdEquals(bookingId);
        if (aggregate!=null) {
            return getDtoFromAggregate(aggregate);
        }
        else {
            throw new InvoiceNotFoundException(
                    "ParkInvoice with booking_ID " + bookingId +
                            " not found! Corresponding booking does not exist!"
            );
        }
    }
}
