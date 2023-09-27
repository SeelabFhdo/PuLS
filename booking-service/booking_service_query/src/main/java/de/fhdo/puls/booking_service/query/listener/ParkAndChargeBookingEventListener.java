package de.fhdo.puls.booking_service.query.listener;

import de.fhdo.puls.booking_service.common.events.ParkAndChargeBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ParkAndChargeBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ParkAndChargeBookingUpdatedEvent;
import de.fhdo.puls.booking_service.query.service.ParkAndChargeBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ParkAndChargeBookingEventListener {

    private ParkAndChargeBookingService parkAndChargeBookingService;

    @Autowired
    public ParkAndChargeBookingEventListener(ParkAndChargeBookingService parkAndChargeBookingService){
        this.parkAndChargeBookingService = parkAndChargeBookingService;
    }

    /*---------------------------------------------------------------------*/

    @KafkaListener(topics = "${kafka.topic.parkandchargebooking.created}", groupId = "${kafka.group.id}")
    public void parkAndChargeBookingCreatedEventListener(ParkAndChargeBookingCreatedEvent parkAndChargeBookingCreatedEvent){
        parkAndChargeBookingService.handleParkAndChargeBookingCreatedEvent(parkAndChargeBookingCreatedEvent);
    }


    @KafkaListener(topics = "${kafka.topic.parkandchargebooking.updated}", groupId = "${kafka.group.id}")
    public void parkAndChargeBookingUpdatedEventListener(ParkAndChargeBookingUpdatedEvent parkAndChargeBookingUpdatedEvent){
        parkAndChargeBookingService.handleParkAndChargeBookingUpdatedEvent(parkAndChargeBookingUpdatedEvent);
    }


    @KafkaListener(topics = "${kafka.topic.parkandchargebooking.canceled}", groupId = "${kafka.group.id}")
    public void parkAndChargeBookingCanceledEventListener(ParkAndChargeBookingCanceledEvent parkAndChargeBookingCanceledEvent) {
        parkAndChargeBookingService.handleParkAndChargeBookingCanceledEvent(parkAndChargeBookingCanceledEvent);
    }
}
