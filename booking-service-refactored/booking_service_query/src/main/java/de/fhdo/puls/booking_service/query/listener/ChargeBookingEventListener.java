package de.fhdo.puls.booking_service.query.listener;

import de.fhdo.puls.booking_service.common.events.ChargeBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ChargeBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ChargeBookingUpdatedEvent;
import de.fhdo.puls.booking_service.query.exception.BookingNotFoundException;
import de.fhdo.puls.booking_service.query.service.ChargeBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ChargeBookingEventListener {

    private ChargeBookingService chargeBookingService;


    @Autowired
    public ChargeBookingEventListener(ChargeBookingService service) {
        this.chargeBookingService = service;
    }


    @KafkaListener(topics = "${kafka.topic.chargebooking.created}", groupId = "${kafka.group.id}",
            containerFactory = "chargeBookingCreatedEventKafkaListenerContainerFactory")
    public void chargeBookingCreatedEventListener(ChargeBookingCreatedEvent event) {
        this.chargeBookingService.handleChargeBookingCreatedEvent(event);
    }


    @KafkaListener(topics = "${kafka.topic.chargebooking.updated}", groupId = "${kafka.group.id}",
            containerFactory = "chargeBookingUpdatedEventKafkaListenerContainerFactory")
    public void chargeBookingUpdatedEventListener(ChargeBookingUpdatedEvent event) throws BookingNotFoundException {
        try {
            this.chargeBookingService.handleChargeBookingUpdatedEvent(event);
        }
        catch (BookingNotFoundException e) {
            throw e;
        }
    }


    @KafkaListener(topics = "${kafka.topic.chargebooking.canceled}", groupId = "${kafka.group.id}",
            containerFactory = "chargeBookingCanceledEventKafkaListenerContainerFactory")
    public void chargeBookingCanceledEventListener(ChargeBookingCanceledEvent event) throws BookingNotFoundException {
        try {
            this.chargeBookingService.handleChargeBookingCanceledEvent(event);
        }
        catch (BookingNotFoundException e) {
            throw e;
        }
    }
}
