package de.fhdo.puls.park_and_charge_service.query.listener

import de.fhdo.puls.park_and_charge_service.common.command.ChargecloudParkingSpacesSyncedEvent
import de.fhdo.puls.park_and_charge_service.common.event.*
import de.fhdo.puls.park_and_charge_service.query.service.ChargecloudService
import de.fhdo.puls.park_and_charge_service.query.service.ElectrifiedParkingSpaceService
import de.fhdo.puls.park_and_charge_service.query.service.ParkingSpaceService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaEventListener {
    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var parkingSpaceService: ParkingSpaceService

    @Autowired
    lateinit var electrifiedParkingSpaceService: ElectrifiedParkingSpaceService

    @Autowired
    lateinit var chargecloudService: ChargecloudService

    @KafkaListener(
        topics = ["\${kafka.topic.parkingspace.created}"],
        groupId = "\${kafka.group.id}",
        containerFactory = "parkingSpaceCreatedEventKafkaListenerContainerFactory"
    )
    fun parkingSpaceCreatedEventListener(event: ParkingSpaceCreatedEvent) {
        logger.info { "Method parkingSpaceCreatedEventListener: $event " }
        parkingSpaceService.handleParkingSpaceCreatedEvent(event)
    }

    @KafkaListener(
        topics = ["\${kafka.topic.electrifiedparkingspace.created}"],
        groupId = "\${kafka.group.id}",
        containerFactory = "electrifiedParkingSpaceCreatedEventKafkaListenerContainerFactory"
    )
    fun electrifiedParkingSpaceCreatedEventListener(event: ElectrifiedParkingSpaceCreatedEvent) {
        logger.info { "Method electrifiedParkingSpaceCreatedEventListener: $event " }
        electrifiedParkingSpaceService.handleElectrifiedParkingSpaceCreatedEvent(event)
    }

    @KafkaListener(
        topics = ["\${kafka.topic.chargingevent}"],
        groupId = "\${kafka.group.id}",
        containerFactory = "chargingInformationEventKafkaListenerContainerFactory"
    )
    fun chargingInformationEventListener(event: ChargingInformationEvent) {
        logger.info { "Method chargingInformationEventListener: $event " }
        electrifiedParkingSpaceService.handleChargingInformationEvent(event)
    }

    @KafkaListener(
            topics = ["\${kafka.topic.chargecloudparkingspace.synced}"],
            groupId = "\${kafka.group.id}",
            containerFactory = "chargecloudParkingSpacesSyncedEventKafkaListenerContainerFactory"
    )
    fun chargecloudParkingSpacesSyncedListener(event: ChargecloudParkingSpacesSyncedEvent) {
        logger.info { "Method chargecloudParkingSpacesSyncedEventListener: $event " }
        chargecloudService.handleChargecloudParkingSpacesSyncedEvent(event)
    }

    @KafkaListener(
        topics = ["\${kafka.topic.keepaliveevent}"],
        groupId = "\${kafka.group.id}",
        containerFactory = "keepAliveInformationEventKafkaListenerContainerFactory"
    )
    fun keepAliveInformationEventListener(event: KeepAliveInformationEvent) {
        logger.info { "Method keepAliveEventListener: $event " }
        electrifiedParkingSpaceService.handleKeepAliveInformationEvent(event)
    }

    @KafkaListener(
        topics = ["\${kafka.topic.statusevent}"],
        groupId = "\${kafka.group.id}",
        containerFactory = "statusInformationEventKafkaListenerContainerFactory"
    )
    fun statusInformationEventListener(event: StatusInformationEvent) {
        logger.info { "Method statusInformationEventListener: $event " }
        electrifiedParkingSpaceService.handleStatusInformationEvent(event)
    }

    @KafkaListener(
        topics = ["\${kafka.topic.parkingevent}"],
        groupId = "\${kafka.group.id}",
        containerFactory = "parkingEventKafkaListenerContainerFactory"
    )
    fun parkingEventListener(event: ParkingStatusEvent) {
        logger.info { "Method parkingEventListener: $event " }
        electrifiedParkingSpaceService.handleParkingStatusEvent(event)
    }
}