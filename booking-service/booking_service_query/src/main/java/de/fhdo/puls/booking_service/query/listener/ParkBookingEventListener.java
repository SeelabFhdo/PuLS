package de.fhdo.puls.booking_service.query.listener;

import de.fhdo.puls.booking_service.common.events.ParkBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingUpdatedEvent;
import de.fhdo.puls.booking_service.query.service.ParkBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ParkBookingEventListener {

    private ParkBookingService parkBookingService;

    @Autowired
    public ParkBookingEventListener(ParkBookingService parkBookingService){
        this.parkBookingService = parkBookingService;
    }

    /*---------------------------------------------------------------------*/

    @KafkaListener(topics = "${kafka.topic.parkbooking.created}", groupId = "${kafka.group.id}",
        containerFactory = "")
    public void parkBookingCreatedEventListener(ParkBookingCreatedEvent parkBookingCreatedEvent){
        parkBookingService.handleParkBookingCreatedEvent(parkBookingCreatedEvent);
    }


    @KafkaListener(topics = "${kafka.topic.parkbooking.updated}", groupId = "${kafka.group.id}",
        containerFactory = "")
    public void parkBookingUpdatedEventListener(ParkBookingUpdatedEvent parkBookingUpdatedEvent){
        parkBookingService.handleParkBookingUpdatedEvent(parkBookingUpdatedEvent);
    }


    @KafkaListener(topics = "${kafka.topic.parkbooking.canceled}", groupId = "${kafka.group.id}",
        containerFactory = "")
    public void parkBookingCanceledEventListener(ParkBookingCanceledEvent parkBookingCanceledEvent){
        parkBookingService.handleParkBookingCanceledEvent(parkBookingCanceledEvent);
    }
}
