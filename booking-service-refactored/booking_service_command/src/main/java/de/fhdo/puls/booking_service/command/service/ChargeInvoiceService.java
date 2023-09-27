package de.fhdo.puls.booking_service.command.service;

import de.fhdo.puls.booking_service.command.configuration.DefaultTopicKafkaTemplate;
import de.fhdo.puls.booking_service.command.domain.ChargeInvoiceAggregate;
import de.fhdo.puls.booking_service.command.repository.ChargeInvoiceRepo;
import de.fhdo.puls.booking_service.common.commands.CreateBookingInvoiceCommand;
import de.fhdo.puls.booking_service.common.dataTransferObjects.BookingInvoiceDto;
import de.fhdo.puls.booking_service.common.events.InvoiceCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargeInvoiceService {

    private final ChargeInvoiceRepo chargeInvoiceRepo;
    private final DefaultTopicKafkaTemplate<String, InvoiceCreatedEvent> createEventProducer;

    @Autowired
    public ChargeInvoiceService(ChargeInvoiceRepo chargeInvoiceRepo,
                                DefaultTopicKafkaTemplate<String, InvoiceCreatedEvent> createEventProducer)
    {
        this.chargeInvoiceRepo = chargeInvoiceRepo;
        this.createEventProducer = createEventProducer;
    }


    /*** === GET AGGREGATE FROM COMMAND === */

    private ChargeInvoiceAggregate getAggregateFromCreateCommand(CreateBookingInvoiceCommand command) {
        return new ChargeInvoiceAggregate(command.getBookingId(),command.getInvoiceAmount());
    }


    /*** === GET EVENT FROM AGGREGATE === */

    private InvoiceCreatedEvent createEventFromAggregate(ChargeInvoiceAggregate aggregate) {
        return new InvoiceCreatedEvent(
                aggregate.getId(),
                aggregate.getBookingId(),
                aggregate.getInvoiceAmount()
        );
    }


    /*** === COMMAND HANDLING === */

    public BookingInvoiceDto handleCreateBookingInvoiceCommand(CreateBookingInvoiceCommand command) {
        ChargeInvoiceAggregate aggregate = getAggregateFromCreateCommand(command);
        aggregate = this.chargeInvoiceRepo.save(aggregate);
        //Send Invoice-Created-Event
        InvoiceCreatedEvent event = createEventFromAggregate(aggregate);
        this.createEventProducer.send("ChargeInvoiceCreatedTopic", event);

        return new BookingInvoiceDto(
                aggregate.getId(),
                aggregate.getBookingId(),
                aggregate.getInvoiceAmount()
        ) ;
    }
}
