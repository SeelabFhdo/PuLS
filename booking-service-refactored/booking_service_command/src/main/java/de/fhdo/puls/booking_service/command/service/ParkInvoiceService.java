package de.fhdo.puls.booking_service.command.service;

import de.fhdo.puls.booking_service.command.configuration.DefaultTopicKafkaTemplate;
import de.fhdo.puls.booking_service.command.domain.ParkInvoiceAggregate;
import de.fhdo.puls.booking_service.command.repository.ParkInvoiceRepo;
import de.fhdo.puls.booking_service.common.commands.CreateBookingInvoiceCommand;
import de.fhdo.puls.booking_service.common.dataTransferObjects.BookingInvoiceDto;
import de.fhdo.puls.booking_service.common.events.InvoiceCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkInvoiceService {

    private final ParkInvoiceRepo parkInvoiceRepo;
    private final DefaultTopicKafkaTemplate<String, InvoiceCreatedEvent> createdEventProducer;


    @Autowired
    public ParkInvoiceService(ParkInvoiceRepo repo,
                              DefaultTopicKafkaTemplate<String, InvoiceCreatedEvent> createdEventProducer)
    {
        this.parkInvoiceRepo = repo;
        this.createdEventProducer = createdEventProducer;
    }


    /*** === GET AGGREGATE FROM COMMAND === */

    private ParkInvoiceAggregate getAggregateFromCreateCommand(CreateBookingInvoiceCommand command) {
        return new ParkInvoiceAggregate(command.getBookingId(),command.getInvoiceAmount());
    }


    /*** === GET EVENT FROM AGGREGATE === */

    private InvoiceCreatedEvent createEventFromAggregate(ParkInvoiceAggregate aggregate) {
        return new InvoiceCreatedEvent(
                aggregate.getId(),
                aggregate.getBookingId(),
                aggregate.getInvoiceAmount()
        );
    }


    /*** === COMMAND HANDLING === */

    public BookingInvoiceDto handleCreateBookingInvoiceCommand(CreateBookingInvoiceCommand command) {
        ParkInvoiceAggregate aggregate = getAggregateFromCreateCommand(command);
        aggregate = this.parkInvoiceRepo.save(aggregate);
        //Send Invoice-Created-Event
        InvoiceCreatedEvent event = createEventFromAggregate(aggregate);
        this.createdEventProducer.send("ParkInvoiceCreatedTopic", event);

        return new BookingInvoiceDto(aggregate.getId(),
                aggregate.getBookingId(),
                aggregate.getInvoiceAmount()
        );
    }
}
