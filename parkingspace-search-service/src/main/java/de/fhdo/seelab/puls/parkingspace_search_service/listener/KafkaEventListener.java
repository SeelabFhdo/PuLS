/**
 * ********************************************************************************************************
 * Project:         PuLS
 * Scope:           Backend Micro-Service
 * Description:     Geo Location Search Service
 * Author:          Alexander Stein
 * Dev. Date:       November 2020
 * ********************************************************************************************************
 */

package de.fhdo.seelab.puls.parkingspace_search_service.listener;

import de.fhdo.puls.park_and_charge_service.common.event.ElectrifiedParkingSpaceCreatedEvent;
import de.fhdo.puls.park_and_charge_service.common.event.ParkingSpaceCreatedEvent;
import de.fhdo.seelab.puls.parkingspace_search_service.events.ParkingSpaceDataReceivedEvent;
import de.fhdo.seelab.puls.parkingspace_search_service.utils.LocationDataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaEventListener.class);

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @KafkaListener(topics = "${kafka.topic.parkingspace.created}", groupId = "${kafka.group.id}",
            containerFactory = "parkingSpaceCreatedEventKafkaListenerContainerFactory")
    public void parkingSpaceCreatedEventListener(ParkingSpaceCreatedEvent parkingSpaceCreatedEvent) {
        LOGGER.info(parkingSpaceCreatedEvent.toString());
        this.applicationEventPublisher.publishEvent(new ParkingSpaceDataReceivedEvent(
                this, LocationDataFactory.produceFrom(parkingSpaceCreatedEvent))
        );
    }

    @KafkaListener(topics = "${kafka.topic.electrifiedparkingspace.created}", groupId = "${kafka.group.id}",
            containerFactory = "electrifiedParkingSpaceCreatedEventKafkaListenerContainerFactory")
    public void electrifiedParkingSpaceCreatedEventListener(
            ElectrifiedParkingSpaceCreatedEvent electrifiedParkingSpaceCreatedEvent) {
        LOGGER.info(electrifiedParkingSpaceCreatedEvent.toString());
        this.applicationEventPublisher.publishEvent(new ParkingSpaceDataReceivedEvent(
                this, LocationDataFactory.produceFrom(electrifiedParkingSpaceCreatedEvent))
        );
    }

}

