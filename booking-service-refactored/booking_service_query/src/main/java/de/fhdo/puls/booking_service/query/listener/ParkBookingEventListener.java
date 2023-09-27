package de.fhdo.puls.booking_service.query.listener;

import de.fhdo.puls.booking_service.common.events.ParkBookingCanceledEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingCreatedEvent;
import de.fhdo.puls.booking_service.common.events.ParkBookingUpdatedEvent;
import de.fhdo.puls.booking_service.query.exception.BookingNotFoundException;
import de.fhdo.puls.booking_service.query.service.ParkBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ParkBookingEventListener {

    private ParkBookingService parkBookingService;


    @Autowired
    public ParkBookingEventListener(ParkBookingService service) {
        this.parkBookingService = service;
    }


    @KafkaListener(topics = "${kafka.topic.parkbooking.created}", groupId = "${kafka.group.id}",
        containerFactory = "parkBookingCreatedEventKafkaListenerContainerFactory")
    public void parkBookingCreatedEventListener(ParkBookingCreatedEvent event) {
        this.parkBookingService.handleParkBookingCreatedEvent(event);
    }


    @KafkaListener(topics = "${kafka.topic.parkbooking.updated}", groupId = "${kafka.group.id}",
        containerFactory = "parkBookingUpdatedEventKafkaListenerContainerFactory")
    public void parkBookingUpdatedEventListener(ParkBookingUpdatedEvent event) throws BookingNotFoundException {
        try {
            this.parkBookingService.handleParkBookingUpdatedEvent(event);
        }
        catch (BookingNotFoundException e) {
            throw e;
        }
    }


    @KafkaListener(topics = "${kafka.topic.parkbooking.canceled}", groupId = "${kafka.group.id}",
        containerFactory = "parkBookingCanceledEventKafkaListenerContainerFactory")
    public void parkBookingCanceledEventListener(ParkBookingCanceledEvent event) throws  BookingNotFoundException {
        try {
            this.parkBookingService.handleParkBookingCanceledEvent(event);
        }
        catch (BookingNotFoundException e) {
            throw e;
        }
    }
}
