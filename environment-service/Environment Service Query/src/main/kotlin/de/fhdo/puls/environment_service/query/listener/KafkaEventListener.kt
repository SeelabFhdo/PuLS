package de.fhdo.puls.environment_service.query.listener

import de.fhdo.puls.environment_service.common.event.EnvironmentDataReceivedEvent
import de.fhdo.puls.environment_service.common.event.EnvironmentSensorUnitCreatedEvent
import de.fhdo.puls.environment_service.query.service.EnvironmentSensorUnitService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaEventListener {
    @Autowired
    lateinit var environmentSensorUnitService: EnvironmentSensorUnitService

    private val logger = KotlinLogging.logger {}

    @KafkaListener(topics = ["\${kafka.topic.sensorunitevent.created}"],
            groupId = "\${kafka.group.id}",
            containerFactory = "environmentSensorUnitCreatedEventKafkaListenerContainerFactory")
    fun userCreatedEventListener(event: EnvironmentSensorUnitCreatedEvent) {
        logger.info { "Method userCreatedEventListener: $event " }
        environmentSensorUnitService.handleEnvironmentSensorUnitCreatedEvent(event)
    }

    @KafkaListener(topics = ["\${kafka.topic.sensorunitevent.received}"],
            groupId = "\${kafka.group.id}",
            containerFactory = "environmentDataReceivedEventKafkaListenerContainerFactory")
    fun environmentSensorValueReceivedListener(event: EnvironmentDataReceivedEvent) {
        logger.info { "Method userCreatedEventListener: $event " }
        environmentSensorUnitService.handleEnvironmentDataReceivedEvent(event)
    }
}