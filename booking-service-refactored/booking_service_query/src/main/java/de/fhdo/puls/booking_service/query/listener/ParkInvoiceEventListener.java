package de.fhdo.puls.booking_service.query.listener;

import de.fhdo.puls.booking_service.common.events.InvoiceCreatedEvent;
import de.fhdo.puls.booking_service.query.service.ParkBookingService;
import de.fhdo.puls.booking_service.query.service.ParkInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ParkInvoiceEventListener {

    private ParkInvoiceService parkInvoiceService;


    @Autowired
    public ParkInvoiceEventListener(ParkInvoiceService parkInvoiceService) {
        this.parkInvoiceService = parkInvoiceService;
    }


    @KafkaListener(topics = "${kafka.topic.parkinvoice.created}", groupId = "${kafka.group.id}",
        containerFactory = "invoiceCreatedEventKafkaListenerContainerFactory")
    public void invoiceCreatedEventListener(InvoiceCreatedEvent event) {
        this.parkInvoiceService.handleInvoiceCreatedEvent(event);
    }
}
