package de.fhdo.puls.booking_service.query.listener;

import de.fhdo.puls.booking_service.common.events.InvoiceCreatedEvent;
import de.fhdo.puls.booking_service.query.service.ChargeInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ChargeInvoiceEventListener {

    private ChargeInvoiceService chargeInvoiceService;


    @Autowired
    public ChargeInvoiceEventListener(ChargeInvoiceService chargeInvoiceService) {
        this.chargeInvoiceService = chargeInvoiceService;
    }


    @KafkaListener(topics = "${kafka.topic.chargeinvoice.created}", groupId = "${kafka.group.id}",
        containerFactory = "invoiceCreatedEventKafkaListenerContainerFactory")
    public void invoiceCreatedEventListener(InvoiceCreatedEvent event) {
        this.chargeInvoiceService.handleInvoiceCreatedEvent(event);
    }
}
